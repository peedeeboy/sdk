package com.jme3.gde.core.util.datatransfer;

import com.jme3.anim.AnimClip;
import com.jme3.anim.AnimComposer;
import com.jme3.gde.core.scene.ApplicationLogHandler;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.scene.Spatial;
import com.jme3.util.clone.Cloner;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copies AnimComposer and AnimClips from an updated spatial to the original.
 */
public final class AnimationDataFromOriginal implements SpatialDataTransferInterface {

    private static final Logger LOGGER =
            Logger.getLogger(AnimationDataFromOriginal.class.getName());

    private final TaggedSpatialFinder finder;

    public AnimationDataFromOriginal(final TaggedSpatialFinder finder) {
        this.finder = finder;
    }

    @Override
    public void update(final Spatial root, final Spatial original) {
        //loop through original to also find new AnimControls, we expect all 
        // nodes etc. to exist
        removeAnimData(root);
        original.depthFirstTraversal(spatial -> {
            final AnimComposer animComposer =
                    spatial.getControl(AnimComposer.class);
            if (animComposer != null) {
                Spatial mySpatial = finder.find(root, spatial);
                if (mySpatial != null) {
                    //TODO: move attachments: have to scan through all
                    // nodes and find the ones
                    //where UserData "AttachedBone" == Bone and move it
                    // to new Bone
                    final AnimComposer myAnimControl =
                            mySpatial.getControl(AnimComposer.class);

                    if (myAnimControl != null) {
                        mySpatial.removeControl(myAnimControl);
                    }

                    myAnimControl.cloneFields(new Cloner(),
                            animComposer.jmeClone());
                    copyAnimClips(myAnimControl, animComposer);
                    if (mySpatial.getControl(AnimComposer.class) == null) {
                        LOGGER.log(Level.FINE, "Adding control for {0}",
                                mySpatial.getName());
                        mySpatial.addControl(myAnimControl);
                    } else {
                        LOGGER.log(Level.FINE, "Control for {0} was added"
                                + " automatically", mySpatial.getName());
                    }

                    LOGGER.log(ApplicationLogHandler.LogLevel.FINE,
                            "Updated animation for {0}",
                            mySpatial.getName());
                } else {
                    LOGGER.log(Level.WARNING, "Could not find sibling for"
                            + " {0} in root {1} when trying to apply "
                            + "AnimControl data", new Object[]{spatial, root});
                }
            }
        });
    }

    private void copyAnimClips(final AnimComposer control, final AnimComposer original) {
        final Collection<AnimClip> clips = original.getAnimClips();
        for (AnimClip c : clips) {
            control.addAnimClip(c);
        }
    }


    private void removeAnimData(final Spatial root) {
        root.depthFirstTraversal(spatial -> {
            AnimComposer animControl = spatial.getControl(AnimComposer.class);
            if (animControl != null) {
                spatial.removeControl(animControl);
            }
        });
    }

}
