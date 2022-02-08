/*
 * Copyright (c) 2022 jMonkeyEngine
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
package com.jme3.gde.templates.gradledesktop.options;

import org.openide.util.NbBundle;

/**
 *
 * @author peedeeboy
 */
public enum LWJGLVersion {
    LWJGL_3("LWJGL 3.x", NbBundle.getMessage(LWJGLVersion.class, 
            "lwjgl.lwjgl3.description"), "org.jmonkeyengine:jme3-lwjgl3"),
    LWJGL_2("LWJGL 2.x", NbBundle.getMessage(LWJGLVersion.class, 
            "lwjgl.lwjgl2.description"), "org.jmonkeyengine:jme3-lwjgl");
    
    private final String label;
    private final String description;
    private final String artifact;
    
    private LWJGLVersion(String label, String description, String artifact) {
        this.label = label;
        this.description = description;
        this.artifact = artifact;
    }

    public String getLabel() {
        return label;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getArtifact() {
        return artifact;
    }
    
    @Override
    public String toString() {
        return this.label;
    } 
}
