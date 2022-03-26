package com.jme3.gde.core.util.datatransfer;

import com.jme3.gde.core.scene.ApplicationLogHandler;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.logging.Logger;

/**
 * Copies Transform data (translation, rotation, scale) from an updated
 * spatial to the original.
 */
public class TransformDataFromOriginal implements SpatialDataTransferInterface {

    private static final Logger LOGGER =
            Logger.getLogger(AnimationDataFromOriginal.class.getName());

    private final TaggedSpatialFinder finder;

    public TransformDataFromOriginal(final TaggedSpatialFinder finder) {
        this.finder = finder;
    }

    @Override
    public void update(final Spatial root, final Spatial original) {
        original.depthFirstTraversal(new com.jme3.scene.SceneGraphVisitorAdapter() {

            @Override
            public void visit(final Geometry geom) {
                final Geometry spat = (Geometry) finder.find(root, geom);
                if (spat != null) {
                    spat.setLocalTransform(geom.getLocalTransform());
                    LOGGER.log(ApplicationLogHandler.LogLevel.FINE,
                            "Updated transform for Geometry {0}",
                            geom.getName());
                }
            }

            @Override
            public void visit(final Node node) {
                final Node spat = (Node) finder.find(root, node);
                if (spat != null) {
                    spat.setLocalTransform(node.getLocalTransform());
                    LOGGER.log(ApplicationLogHandler.LogLevel.FINE,
                            "Updated transform for Node {0}", node.getName());
                }
            }
        });
    }
}
