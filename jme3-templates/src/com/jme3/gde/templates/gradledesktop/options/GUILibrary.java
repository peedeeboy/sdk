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
public enum GUILibrary {
    
    NONE("", NbBundle.getMessage(GUILibrary.class, "guilibrary.none.description"), 
            "", false),
    NIFTY("Nifty", NbBundle.getMessage(GUILibrary.class, "guilibrary.nifty.description"), 
            "org.jmonkeyengine:jme3-niftygui", true),
    LEMUR("Lemur", NbBundle.getMessage(GUILibrary.class, "guilibrary.lemur.description"), 
            "com.simsilica:lemur:1.15.0", false);
    
    private final String label;
    private final String description;
    private final String artifact;
    private final boolean isCoreJmeLibrary;
    
    private GUILibrary(String label, String description, String artifact,
            boolean isCoreJmeLibrary) {
        this.label = label;
        this.description = description;
        this.artifact = artifact;
        this.isCoreJmeLibrary = isCoreJmeLibrary;
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
    
    public boolean getIsCoreJmeLibrary() {
        return isCoreJmeLibrary;
    }
    
    @Override
    public String toString() {
        return this.label;
    } 
}
