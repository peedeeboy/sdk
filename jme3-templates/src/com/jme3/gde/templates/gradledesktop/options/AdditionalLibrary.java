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
 * Enum representing a recommended Additional Library (either Core or 3rd party) 
 * that can be added to a jMonkeyEngine project.
 * 
 * To add a new additional library:
 * <ul>
 *   <li>
 *       Add a new entry to this enum.  
 *   </li>
 *   <li>
 *       The label is what will be displayed in the jComboBox.
 *   </li>
 *   <li>
 *       The description should be added to the <code>bundle.properties</code>,
 *       and referenced in the 2nd parameter.
 *   </li>
 *   <li>
 *       
 *       If the library is 3rd party, the artifact should contain the version number.
 *       If the library is Core, then the artifact should not contain the version
 *       number, as the correct jMonkeyEngine version number will be automatically appended
 *       during project creation.
 *   </li>
 *   <li>
 *       isCoreJmeLibrary should be set to <code>true</code> if this is a core jMonkeyEngine
 *       library, or <code>false</code> if it is 3rd party.
 *   </li>
 * </ul>
 * 
 * @author peedeeboy
 */
public enum AdditionalLibrary {
    
    JME3_TERRAIN("jMonkeyEngine TerraMonkey (jm3-terrain)", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.jm3-terrain.description"), "org.jmonkeyengine:jme3-terrain", true),
    JME3_TESTDATA("jMonkeyengine Test Data (jm3-testdata)", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.jm3-testdata.description"), "org.jmonkeyengine:jme3-testdata", true),
    JME3_VR("jMonkeyengine Virtual Reality (jm3-vr)", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.jm3-vr.description"), "org.jmonkeyengine:jme3-vr", true),
    HEART("Heart Library", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.heart.description"), "com.github.stephengold:Heart:7.2.0", false),
    PARTICLE_MONKEY("Particle Monkey", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.particlemonkey.description"), "com.github.Jeddic:particlemonkey:1.0.2", false),
    SHADERBLOW_EX("ShaderBlowEx", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.shaderblowex.description"), "com.github.polincdev:ShaderBlowEx:master-SNAPSHOT", false),
    SIO2("SiO2", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.sio2.description"), "com.simsilica:sio2:1.6.0", false),
    ZAY_ES("Zay-ES Entity Component System", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.zayes.description"), "com.simsilica:zay-es:1.3.2", false),
    ZAY_ES_NET("Zay-ES-Net Networking Extension", NbBundle.getMessage(AdditionalLibrary.class, 
            "additionalLibrary.zayesnet.description"), "com.simsilica:zay-es-net:1.4.3", false),;
    
    /**
     * The name of the library.  This will be displayed in the jComboBox in the 
     * New Project wizard.
     */
    private final String label;
    /**
     * Long description of the library.  This should be stored in the <code>bundle.properties</code>
     * file.
     */
    private final String description;
    
    private final String artifact;
    private final boolean isCoreJmeLibrary;
    
    private AdditionalLibrary(String label, String description, String artifact,
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
