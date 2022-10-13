/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.core.scene.state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.gde.core.scene.SceneApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
import java.util.ArrayList;
import java.util.List;
import jme3tools.optimize.GeometryBatchFactory;

/**
 * Generates a set of geometries for debugging normals. Does not support moving
 * or animated meshes. Implementation ref:
 * https://hub.jmonkeyengine.org/t/debug-graphics-show-normals-as-lines/35058/3
 *
 * @author rickard
 */
public class NormalViewState extends BaseAppState {

    private final Node debugNode = new Node("Normals debug");

    @Override
    protected void initialize(Application arg0) {
    }

    @Override
    protected void cleanup(Application arg0) {
    }

    @Override
    protected void onEnable() {
        final SceneApplication app = (SceneApplication) getApplication();
        final List<Geometry> geometries = new ArrayList<>();
        GeometryBatchFactory.gatherGeoms(app.getRootNode(), geometries);
        final Material debugMat = app.getAssetManager().loadMaterial("Common/Materials/VertexColor.j3m");

        for (Geometry geometry : geometries) {
            Geometry debug = new Geometry("Debug normals",
                    TangentBinormalGenerator.genNormalLines(geometry.getMesh(),
                            0.2f));
            debug.setMaterial(debugMat);
            debug.setCullHint(Spatial.CullHint.Never);
            debug.setLocalTransform(geometry.getWorldTransform());
            debugNode.attachChild(debug);
        }
        app.getRootNode().attachChild(debugNode);
    }

    @Override
    protected void onDisable() {
        debugNode.removeFromParent();
        debugNode.detachAllChildren();
    }

}
