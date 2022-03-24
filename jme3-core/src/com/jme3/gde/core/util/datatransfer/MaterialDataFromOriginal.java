package com.jme3.gde.core.util.datatransfer;

import com.jme3.gde.core.scene.ApplicationLogHandler;
import com.jme3.gde.core.util.TaggedSpatialFinder;
import com.jme3.scene.Geometry;
import com.jme3.scene.SceneGraphVisitorAdapter;
import com.jme3.scene.Spatial;

import java.util.logging.Logger;

public class MaterialDataFromOriginal implements SpatialDataTransferInterface {

    private static final Logger logger =
            Logger.getLogger(MaterialDataFromOriginal.class.getName());

    private final TaggedSpatialFinder finder;

    public MaterialDataFromOriginal(TaggedSpatialFinder finder) {
        this.finder = finder;
    }

    @Override
    public void update(final Spatial root, final Spatial original) {
        //loop through original to also find new geometry
        original.depthFirstTraversal(new SceneGraphVisitorAdapter() {
            @Override
            public void visit(Geometry geom) {
                //will always return same class type as 2nd param, so casting
                // is safe
                Geometry spat = (Geometry) finder.find(root, geom);
                if (spat != null && spat.getMaterial() != null && geom.getMaterial() != null) {
                    spat.setMaterial(geom.getMaterial());
                    logger.log(ApplicationLogHandler.LogLevel.USERINFO,
                            "Updated material for Geometry {0}",
                            geom.getName());
                }
            }
        });
    }
}
