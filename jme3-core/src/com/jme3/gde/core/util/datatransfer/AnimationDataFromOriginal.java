package com.jme3.gde.core.util.datatransfer;

import com.jme3.anim.AnimClip;
import com.jme3.anim.AnimComposer;
import com.jme3.anim.Armature;
import com.jme3.anim.Joint;
import com.jme3.anim.SkinningControl;
import com.jme3.anim.util.AnimMigrationUtils;
import com.jme3.gde.core.scene.ApplicationLogHandler;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.util.clone.Cloner;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copies AnimComposer and AnimClips from an updated spatial to the original.
 */
public final class AnimationDataFromOriginal implements SpatialDataTransferInterface {

    private static final Logger LOGGER
            = Logger.getLogger(AnimationDataFromOriginal.class.getName());

    private final TaggedSpatialFinder finder;

    public AnimationDataFromOriginal(final TaggedSpatialFinder finder) {
        this.finder = finder;
    }

    @Override
    public void update(final Spatial root, final Spatial original) {
        //loop through original to also find new AnimControls, we expect all 
        // nodes etc. to exist
        removeAnimData(root);
        original.depthFirstTraversal(new SceneGraphVisitor() {

            @Override
            public void visit(final Spatial spatial) {
                final AnimComposer animComposer
                        = spatial.getControl(AnimComposer.class);
                if (animComposer != null) {
                    final Spatial mySpatial = finder.find(root, spatial);
                    if (mySpatial != null) {
                        //TODO: move attachments: have to scan through all
                        // nodes and find the ones
                        //where UserData "AttachedBone" == Bone and move it
                        // to new Bone
                        getAndRemoveControl(mySpatial);

                        updateAndAddControl(mySpatial,
                                animComposer, spatial.getControl(SkinningControl.class));

                        LOGGER.log(ApplicationLogHandler.LogLevel.FINE,
                                "Updated animation for {0}",
                                mySpatial.getName());
                    } else {
                        LOGGER.log(Level.WARNING, "Could not find sibling for"
                                + " {0} in root {1} when trying to apply "
                                + "AnimControl data", new Object[]{spatial,
                                    root});
                    }
                }
            }
        });
    }

    private AnimComposer getAndRemoveControl(final Spatial mySpatial) {
        final AnimComposer myAnimControl
                = mySpatial.getControl(AnimComposer.class);
        if (myAnimControl != null) {
            mySpatial.removeControl(myAnimControl);
        }
        return myAnimControl;
    }

    private void updateAndAddControl(final Spatial spatial,
            final AnimComposer originalAnimComposer,
            final SkinningControl originalSkinningControl) {
        Cloner cloner = new Cloner();
        AnimComposer newControl = new AnimComposer();
        newControl.cloneFields(cloner,
                originalAnimComposer);

        copyAnimClips(newControl, originalAnimComposer);
        if (spatial.getControl(AnimComposer.class) == null) {
            LOGGER.log(Level.FINE, "Adding AnimComposer for {0}",
                    spatial.getName());
            spatial.addControl(newControl);
        } else {
            LOGGER.log(Level.FINE, "Control for {0} was added"
                    + " automatically", spatial.getName());
        }
        if (spatial.getControl(SkinningControl.class) == null) {
            if (originalSkinningControl == null) {
                LOGGER.log(Level.INFO, "Could not add a SkinningControl. Broken file?");
            } else {
                // Armature only used to initialize SkinningControl
                SkinningControl skinningControl = new SkinningControl(originalSkinningControl.getArmature());
                skinningControl.cloneFields(cloner,
                        originalSkinningControl);
                spatial.addControl(skinningControl);
                LOGGER.log(Level.FINE, "Adding SkinningControl for {0}",
                        spatial.getName());
            }
        }
    }

    private void copyAnimClips(final AnimComposer control,
            final AnimComposer original) {
        final Collection<AnimClip> clips2 = control.getAnimClips();
        for (final AnimClip c : clips2) {
            control.removeAnimClip(c);
        }

        final Collection<AnimClip> clips = original.getAnimClips();
        for (final AnimClip c : clips) {
            control.addAnimClip((AnimClip) c.jmeClone());
            control.makeAction(c.getName());
            LOGGER.log(Level.INFO, "Copied clip {0}",
                    c.getName());
        }
    }

    private void removeAnimData(final Spatial root) {
        root.depthFirstTraversal(spatial -> {
            AnimComposer animControl = spatial.getControl(AnimComposer.class);
            if (animControl != null) {
                spatial.removeControl(animControl);
            }
            SkinningControl skinningControl = spatial.getControl(SkinningControl.class);
            if (skinningControl != null) {
                spatial.removeControl(skinningControl);
            }
        });
    }

}
