package com.jme3.gde.core.util;

import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds a previously marked spatial in the supplied root Spatial, creates
 * the name and path to be looked for from the given needle Spatial.
 */
public class TaggedSpatialFinder {

    private static final Logger logger =
            Logger.getLogger(TaggedSpatialFinder.class.getName());

    public Spatial find(final Spatial root, final Spatial needle) {
        if (needle == null) {
            logger.log(Level.WARNING, "Trying to find null needle for {0}",
                    root);
            return null;
        }
        final String name = needle.getName();
        final String path = SpatialUtil.getSpatialPath(needle);
        if (name == null || path == null) {
            logger.log(Level.WARNING, "Trying to find tagged Spatial with " +
                    "null name spatial for {0}.", root);
            return null;
        }
        final Class clazz = needle.getClass();
        String rootName = root.getUserData("ORIGINAL_NAME");
        String rootPath = root.getUserData("ORIGINAL_PATH");
        if (name.equals(rootName) && path.equals(rootPath)) {
            return root;
        }
        final SpatialHolder holder = new SpatialHolder();
        root.depthFirstTraversal(new SceneGraphVisitor() {
            @Override
            public void visit(Spatial spatial) {
                String spName = spatial.getUserData("ORIGINAL_NAME");
                String spPath = spatial.getUserData("ORIGINAL_PATH");
                if (name.equals(spName) && path.equals(spPath) && clazz.isInstance(spatial)) {
                    if (holder.spatial == null) {
                        holder.spatial = spatial;
                    } else {
                        logger.log(Level.WARNING, "Found Spatial {0} twice in" +
                                " {1}", new Object[]{path, root});
                    }
                }
            }
        });
        return holder.spatial;
    }

    private static class SpatialHolder {

        Spatial spatial;
    }
}
