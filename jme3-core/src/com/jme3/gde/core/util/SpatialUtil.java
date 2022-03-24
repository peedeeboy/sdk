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
package com.jme3.gde.core.util;

import com.jme3.anim.AnimComposer;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Various utilities, mostly for operating on Spatials recursively and to copy
 * data over from "original" spatials (meshes, animations etc.). Mainly used by
 * the "external changes" scanner for models.
 *
 * @author normenhansen
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SpatialUtil {

    private static final Logger logger =
            Logger.getLogger(SpatialUtil.class.getName());


    /**
     * Gets a "pathname" for the given Spatial, combines the Spatials and
     * parents names to make a long name. This "path" is stored in geometry
     * after the first import for example.
     *
     * @param spat
     * @return
     */
    public static String getSpatialPath(Spatial spat) {
        StringBuilder geometryIdentifier = new StringBuilder();
        while (spat != null) {
            String name = spat.getName();
            if (name == null) {
                logger.log(Level.WARNING, "Null spatial name!");
                name = "null";
            }
            geometryIdentifier.insert(0, name);
            geometryIdentifier.insert(0, '/');
            spat = spat.getParent();
        }
        String id = geometryIdentifier.toString();
        return id;
    }

    /**
     * Stores ORIGINAL_NAME and ORIGINAL_PATH UserData to given Spatial and all
     * sub-Spatials.
     *
     * @param spat
     */
    public static void storeOriginalPathUserData(Spatial spat) {
        //TODO: only stores for geometry atm
        final ArrayList<String> geomMap = new ArrayList<String>();
        if (spat != null) {
            spat.depthFirstTraversal(new SceneGraphVisitor() {
                @Override
                public void visit(Spatial geom) {
                    Spatial curSpat = geom;
                    String geomName = curSpat.getName();
                    if (geomName == null) {
                        logger.log(Level.WARNING, "Null Spatial name!");
                        geomName = "null";
                    }
                    geom.setUserData("ORIGINAL_NAME", geomName);
                    logger.log(Level.FINE, "Set ORIGINAL_NAME for {0}",
                            geomName);
                    String id = SpatialUtil.getSpatialPath(curSpat);
                    if (geomMap.contains(id)) {
                        logger.log(Level.WARNING, "Cannot create unique name " +
                                "for Spatial {0}: {1}", new Object[]{geom, id});
                    }
                    geomMap.add(id);
                    geom.setUserData("ORIGINAL_PATH", id);
                    logger.log(Level.FINE, "Set ORIGINAL_PATH for {0}", id);
                }
            });
        } else {
            logger.log(Level.SEVERE, "No Spatial available when trying to add" +
                    " Spatial paths.");
        }
    }

    public static void clearRemovedOriginals(final Spatial root,
                                             final Spatial original) {
        //TODO: Clear old stuff at all?
        return;
    }

    /**
     * Finds out if a spatial has animations.
     *
     * @param root
     */
    public static boolean hasAnimations(final Spatial root) {
        final AtomicBoolean animFound = new AtomicBoolean(false);
        root.depthFirstTraversal(new SceneGraphVisitor() {
            public void visit(Spatial spatial) {
                if (spatial.getControl(AnimComposer.class) != null) {
                    animFound.set(true);
                }
            }
        });
        return animFound.get();
    }

}
