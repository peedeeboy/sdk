/*
 * Copyright (c) 2003-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.gde.core.assets;

import com.jme3.export.Savable;
import com.jme3.gde.core.scene.ApplicationLogHandler;
import com.jme3.gde.core.scene.SceneApplication;
import com.jme3.gde.core.util.SpatialUtil;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.gde.core.util.datatransfer.AnimationDataFromOriginal;
import com.jme3.gde.core.util.datatransfer.MaterialDataFromOriginal;
import com.jme3.gde.core.util.datatransfer.MeshDataFromOriginal;
import com.jme3.gde.core.util.datatransfer.TransformDataFromOriginal;
import com.jme3.scene.Spatial;
import org.netbeans.api.progress.ProgressHandle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class scans for external changes of a j3o models original file and tries
 * to update the data when it changed.
 *
 * @author normenhansen
 */
public class ExternalChangeScanner implements AssetDataPropertyChangeListener,
        FileChangeListener {

    private static final Logger LOGGER =
            Logger.getLogger(ExternalChangeScanner.class.getName());
    private static final AtomicBoolean userNotified = new AtomicBoolean(false);
    protected final AssetDataObject assetDataObject;
    protected final AssetData assetData;
    protected FileObject originalObject;

    public ExternalChangeScanner(AssetDataObject assetDataObject) {
        this.assetDataObject = assetDataObject;

        assetData = assetDataObject.getLookup().lookup(AssetData.class);
        if (assetData != null) {
            String path = assetData.getProperty("ORIGINAL_PATH");
            if (path != null) {
                setObservedFilePath(path);
            }
            assetData.addPropertyChangeListener(this);
            final ExternalChangeScanner main = this;
            assetDataObject.getPrimaryFile().addFileChangeListener(new FileChangeAdapter() {
                @Override
                public void fileDeleted(FileEvent fe) {
                    LOGGER.log(Level.INFO, "File {0} deleted, remove!",
                            new Object[]{fe.getFile()});
                    assetData.removePropertyChangeListener(main);
                    fe.getFile().removeFileChangeListener(this);
                    if (originalObject != null) {
                        LOGGER.log(Level.INFO, "Remove file change listener "
                                + "for {0}", originalObject);
                        originalObject.removeFileChangeListener(main);
                        originalObject = null;
                    }
                }
            });
        } else {
            LOGGER.log(Level.WARNING, "Trying to observer changes for asset "
                            + "{0} which has no AssetData in Lookup.",
                    assetDataObject.getName());
        }
    }

    private void notifyUser() {
        if (!userNotified.getAndSet(true)) {
            //TODO: execute on separate thread?
            java.awt.EventQueue.invokeLater(() -> {
                final String NO_OPTION = "No";
                final String ALL_OPTION = "All";
                final String MESH_OPTION = "Only mesh data";
                NotifyDescriptor.Confirmation mesg =
                        new NotifyDescriptor.Confirmation("Original file for "
                                + assetDataObject.getName() + " changed\nTry "
                                + "and reapply data to j3o file?",
                                "Original file changed",
                                NotifyDescriptor.YES_NO_CANCEL_OPTION,
                                NotifyDescriptor.QUESTION_MESSAGE);
                mesg.setOptions(new Object[]{ALL_OPTION, MESH_OPTION,
                        NO_OPTION});
                DialogDisplayer.getDefault().notify(mesg);
                if (mesg.getValue().equals(NO_OPTION)) {
                    userNotified.set(false);
                    return;
                }
                SceneApplication.getApplication().enqueue((Callable<Void>) () -> {
                    applyExternalData(mesg.getValue().equals(MESH_OPTION));
                    return null;
                });
                userNotified.set(false);
            });
        } else {
            LOGGER.log(Level.INFO, "User already notified about change in "
                    + "{0}", assetDataObject.getName());
        }
    }

    private void applyExternalData(boolean onlyMeshData) {
        ProgressHandle handle = ProgressHandle.createHandle("Updating file "
                + "data");
        handle.start();
        try {
            final Spatial original = loadOriginalSpatial();
            final Spatial spat = (Spatial) assetDataObject.loadAsset();
            final TaggedSpatialFinder finder = new TaggedSpatialFinder();

            new MeshDataFromOriginal(finder).update(spat, original);
            new TransformDataFromOriginal(finder).update(spat, original);
            if (!onlyMeshData) {
                new MaterialDataFromOriginal(finder).update(spat, original);
                if (SpatialUtil.hasAnimations(original)) {
                    new AnimationDataFromOriginal(finder).update(spat,
                            original);
                }
            }

            closeOriginalSpatial();
            assetDataObject.saveAsset();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception when trying to update "
                    + "external data.", e);
        } finally {
            handle.finish();
        }
    }

    private Spatial loadOriginalSpatial() {
        try {
            final DataObject dobj = DataObject.find(originalObject);
            final AssetData originalAssetData =
                    dobj.getLookup().lookup(AssetData.class);
            if (originalAssetData != null) {
                final Savable sav = originalAssetData.loadAsset();
                if (sav instanceof Spatial) {
                    return (Spatial) sav;
                } else {
                    LOGGER.log(Level.SEVERE, "Trying to load original for {0}"
                                    + " but it is not a Spatial: {1}",
                            new Object[]{assetDataObject.getName(),
                                    originalObject});
                }
            } else {
                LOGGER.log(Level.WARNING, "Could not get AssetData for {0}, "
                                + "original file {1}",
                        new Object[]{assetDataObject.getName(),
                                originalObject});
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    private Spatial closeOriginalSpatial() {
        try {
            final DataObject dobj = DataObject.find(originalObject);
            final AssetData originalAssetData =
                    dobj.getLookup().lookup(AssetData.class);
            if (originalAssetData != null) {
                originalAssetData.closeAsset();
            } else {
                LOGGER.log(Level.WARNING, "Could not get AssetData for {0}, "
                                + "original file {1}",
                        new Object[]{assetDataObject.getName(),
                                originalObject});
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    private void setObservedFilePath(final String assetName) {
        final ProjectAssetManager mgr =
                assetDataObject.getLookup().lookup(ProjectAssetManager.class);
        if (mgr == null) {
            LOGGER.log(Level.WARNING, "File is not part of a jME project but "
                    + "tries to find original model...");
            return;
        }
        final FileObject fileObject = mgr.getAssetFileObject(assetName);
        //ignoring same file -> old properties files
        if (fileObject != null) {
            if (!fileObject.equals(assetDataObject.getPrimaryFile())) {
                if (originalObject != null) {
                    originalObject.removeFileChangeListener(this);
                    LOGGER.log(Level.INFO, "{0} stops listening for external "
                                    + "changes on {1}",
                            new Object[]{assetDataObject.getName(),
                                    originalObject});
                }
                fileObject.addFileChangeListener(this);
                LOGGER.log(Level.INFO, "{0} listening for external changes on"
                        + " {1}", new Object[]{assetDataObject.getName(),
                        fileObject});
                originalObject = fileObject;
            } else {
                LOGGER.log(Level.FINE, "Ignoring old reference to self for "
                        + "{0}", assetDataObject.getName());
            }
        } else {
            LOGGER.log(Level.INFO, "Could not get FileObject for {0} when "
                    + "trying to update original data for {1}. Possibly deleted"
                    + ".", new Object[]{assetName, assetDataObject.getName()});
            //TODO: add folder listener for when recreated
        }
    }

    @Override
    public void assetDataPropertyChanged(String property, String before,
                                         String after) {
        if (SpatialUtil.ORIGINAL_PATH.equals(property)) {
            LOGGER.log(Level.INFO, "Notified about change in AssetData "
                    + "properties for {0}", assetDataObject.getName());
            setObservedFilePath(after);
        }
    }

    public void fileFolderCreated(FileEvent fe) {
    }

    public void fileDataCreated(FileEvent fe) {
    }

    public void fileChanged(FileEvent fe) {
        LOGGER.log(Level.INFO, "External file {0} for {1} changed!",
                new Object[]{fe.getFile(), assetDataObject.getName()});
        notifyUser();
    }

    public void fileDeleted(FileEvent fe) {
        LOGGER.log(Level.INFO, "External file {0} for {1} deleted!",
                new Object[]{fe.getFile(), assetDataObject.getName()});
        if (originalObject != null) {
            LOGGER.log(ApplicationLogHandler.LogLevel.INFO, "Remove file "
                            + "change listener for deleted object on {0}",
                    assetDataObject.getName());
            originalObject.removeFileChangeListener(this);
            originalObject = null;
        }
        //TODO: add folder listener for when recreated
    }

    public void fileRenamed(FileRenameEvent fe) {
        LOGGER.log(Level.INFO, "External file {0} for {1} renamed!",
                new Object[]{fe.getFile(), assetDataObject.getName()});
        if (originalObject != null) {
            LOGGER.log(Level.INFO, "Remove file change listener for renamed "
                    + "object on {0}", assetDataObject.getName());
            originalObject.removeFileChangeListener(this);
            originalObject = null;
        }
    }

    public void fileAttributeChanged(FileAttributeEvent fe) {
    }
}
