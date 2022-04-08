package com.jme3.gde.core.util.datatransfer;

import com.jme3.gde.core.scene.ApplicationLogHandler.LogLevel;
import com.jme3.gde.core.util.SpatialUtil;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;
import java.util.logging.Level;

import java.util.logging.Logger;

public class MeshDataFromOriginal implements SpatialDataTransferInterface{

    private static final Logger LOGGER =
            Logger.getLogger(MeshDataFromOriginal.class.getName());

    private final TaggedSpatialFinder finder;
    public MeshDataFromOriginal(final TaggedSpatialFinder finder) {
        this.finder = finder;
    }

    @Override
    public void update(final Spatial root, final Spatial original) {
        //loop through original to also find new geometry
        original.depthFirstTraversal(new SceneGraphVisitorAdapter() {
            @Override
            public void visit(Geometry geom) {
                //will always return same class type as 2nd param, so casting is safe
                Geometry spat = (Geometry) finder.find(root, geom);
                if (spat != null) {
                    spat.setMesh(geom.getMesh());
                    LOGGER.log(LogLevel.USERINFO, "Updated mesh for Geometry {0}", geom.getName());
                } else {
                    addLeafWithNonExistingParents(root, geom);
                }
            }
        });
    }
    
    /**
     * Adds a leaf to a spatial, including all nonexisting parents.
     *
     * @param root
     * @param original
     */
    private void addLeafWithNonExistingParents(Spatial root, Spatial leaf) {
        if (!(root instanceof Node)) {
            LOGGER.log(Level.WARNING, "Cannot add new Leaf {0} to {1}, is not a Node!", new Object[]{leaf.getName(), root.getName()});
            return;
        }
        for (Spatial s = leaf; s.getParent() != null; s = s.getParent()) {
            Spatial parent = s.getParent();
            Spatial other = finder.find(root, parent);
            if (other == null) {
                continue;
            }
            if (other instanceof Node) {
                LOGGER.log(Level.INFO, "Attaching {0} to {1} in root {2} to add leaf {3}", new Object[]{s, other, root, leaf});
                //set original path data to leaf and new parents
                for (Spatial spt = leaf; spt != parent; spt = spt.getParent()) {
                    if(spt == null){
                        return; // this is to avoid a crash when changing names of meshes externally
                    }
                    spt.setUserData(SpatialUtil.ORIGINAL_NAME, spt.getName());
                    spt.setUserData(SpatialUtil.ORIGINAL_PATH, SpatialUtil.getSpatialPath(spt));
                    spt = spt.getParent();
                }
                //attach to new node in own root
                Node otherNode = (Node) other;
                otherNode.attachChild(s);
                LOGGER.log(LogLevel.USERINFO, "Attached Node {0} with leaf {0}", new Object[]{other.getName(), leaf.getName()});
                return;
            } else {
                LOGGER.log(Level.WARNING, "Cannot attach leaf {0} to found spatial {1} in root {2}, not a node.", new Object[]{leaf, other, root});
            }
        }
        LOGGER.log(Level.WARNING, "Could not attach new Leaf {0}, no root node found.", leaf.getName());
    }
}
