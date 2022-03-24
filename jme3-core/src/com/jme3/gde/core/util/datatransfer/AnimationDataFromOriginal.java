package com.jme3.gde.core.util.datatransfer;

import com.jme3.anim.AnimClip;
import com.jme3.anim.AnimComposer;
import com.jme3.gde.core.scene.ApplicationLogHandler;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.util.clone.Cloner;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimationDataFromOriginal implements SpatialDataTransferInterface {

    private static final Logger logger =
            Logger.getLogger(AnimationDataFromOriginal.class.getName());

    private final TaggedSpatialFinder finder;

    public AnimationDataFromOriginal(TaggedSpatialFinder finder) {
        this.finder = finder;
    }

    @Override
    public void update(final Spatial root, final Spatial original) {
        //loop through original to also find new AnimControls, we expect all 
        // nodes etc. to exist
        //TODO: can (blender) AnimControls end up in other nodes that are not
        // a parent of the geometry they modify?
        removeAnimData(root);
        original.depthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spat) {
                AnimComposer animComposer = spat.getControl(AnimComposer.class);
                if (animComposer != null) {
                    Spatial mySpatial = finder.find(root, spat);
                    if (mySpatial != null) {
                        //TODO: move attachments: have to scan through all 
                        // nodes and find the ones
                        //where UserData "AttachedBone" == Bone and move it 
                        // to new Bone
                        AnimComposer myAnimControl =
                                mySpatial.getControl(AnimComposer.class);

                        if (myAnimControl != null) {
                            mySpatial.removeControl(myAnimControl);
                        }

                        AnimComposer newControl = new AnimComposer();
                        newControl.cloneFields(new Cloner(),
                                animComposer.jmeClone());
                        copyAnimClips(newControl, animComposer);
                        if (mySpatial.getControl(AnimComposer.class) == null) {
                            logger.log(Level.INFO, "Adding control for {0}",
                                    mySpatial.getName());
                            mySpatial.addControl(newControl);
                        } else {
                            logger.log(Level.INFO, "Control for {0} was added" +
                                    " automatically", mySpatial.getName());
                        }

                        logger.log(ApplicationLogHandler.LogLevel.USERINFO,
                                "Updated animation for {0}",
                                mySpatial.getName());
                    } else {
                        logger.log(Level.WARNING, "Could not find sibling for" +
                                " {0} in root {1} when trying to apply " +
                                "AnimControl data", new Object[]{spat, root});
                    }
                }
            }
        });
        //TODO: remove old AnimControls?
    }

    private void copyAnimClips(AnimComposer control, AnimComposer original) {
        Collection<AnimClip> clips = original.getAnimClips();
        for (AnimClip c : clips) {
            control.addAnimClip(c);
        }
    }


    private void removeAnimData(Spatial root) {
        root.depthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spat) {
                AnimComposer animControl = spat.getControl(AnimComposer.class);
                if (animControl != null) {
                    spat.removeControl(animControl);

                }
            }
        });
    }

}
