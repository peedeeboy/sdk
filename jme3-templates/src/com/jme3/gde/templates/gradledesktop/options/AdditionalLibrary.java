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
 * <p>To add a new additional library:</p>
 * <ul>
 * <li>
 * Add a new entry to this enum.
 * </li>
 * <li>
 * The label is what will be displayed in the jComboBox.
 * </li>
 * <li>
 * The description should be added to the <code>bundle.properties</code>, and
 * referenced in the 2nd parameter.
 * </li>
 * <li>
 *
 * If the library is 3rd party, the artifact should contain the version number.
 * If the library is Core, then the artifact should not contain the version
 * number, as the correct jMonkeyEngine version number will be automatically
 * appended during project creation.
 * </li>
 * <li>
 * isCoreJmeLibrary should be set to <code>true</code> if this is a core
 * jMonkeyEngine library, or <code>false</code> if it is 3rd party.
 * </li>
 * </ul>
 *
 * @author peedeeboy
 */
public enum AdditionalLibrary {

    JME3_EFFECTS("jMonkeyEngine Effects (jme3-effects)",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.jme3-effects.description"),
            "org.jmonkeyengine:jme3-effects", true),
    JME3_TERRAIN("jMonkeyEngine TerraMonkey (jme3-terrain)",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.jme3-terrain.description"),
            "org.jmonkeyengine:jme3-terrain", true),
    JME3_TESTDATA("jMonkeyEngine Test Data (jme3-testdata)",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.jme3-testdata.description"),
            "org.jmonkeyengine:jme3-testdata", true),
    JME3_VR("jMonkeyEngine Virtual Reality (jme3-vr)",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.jme3-vr.description"),
            "org.jmonkeyengine:jme3-vr", true),
    HEART("Heart Library", NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.heart.description"),
            "com.github.stephengold:Heart:8.1.0", false),
    PARTICLE_MONKEY("Particle Monkey",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.particlemonkey.description"),
            "com.github.Jeddic:particlemonkey:1.0.2", false),
    SHADERBLOW_EX("ShaderBlowEx", NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.shaderblowex.description"),
            "com.github.polincdev:ShaderBlowEx:master-SNAPSHOT", false),
    SIO2("SiO2", NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.sio2.description"),
            "com.simsilica:sio2:1.7.0", false),
    ZAY_ES("Zay-ES Entity Component System",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.zayes.description"),
            "com.simsilica:zay-es:1.4.0", false),
    ZAY_ES_NET("Zay-ES-Net Networking Extension",
            NbBundle.getMessage(AdditionalLibrary.class,
            "additionalLibrary.zayesnet.description"),
            "com.simsilica:zay-es-net:1.5.0", false),;

    /**
     * The name of the library. This will be displayed in the jComboBox in the
     * New Project wizard.
     */
    private final String label;
    /**
     * Long description of the library. This should be stored in the
     * <code>bundle.properties</code> file.
     */
    private final String description;
    /**
     * Gradle artifact string. If this is <strong>not</strong> a core JME
     * library, then the artifact string should include the version number. If
     * the library <strong>is</strong> a core JME library, then the version
     * should be omitted, as they jMonkeyEngine version will be appended
     * automatically by the template.
     */
    private final String artifact;
    /**
     * Is this library a core jMonkeyEngine library? True if the library is a
     * part of jMonkeyengine, false if it is 3rd party.
     */
    private final boolean isCoreJmeLibrary;

    /**
     * Private constructor to create an instance of this enum.
     *
     * @param label The name of the library.
     * @param description Long description of the library.
     * @param artifact Gradle artifact string.
     * @param isCoreJmeLibrary Is this library a core jMonkeyEngine library?
     */
    AdditionalLibrary(String label, String description, String artifact,
            boolean isCoreJmeLibrary) {
        this.label = label;
        this.description = description;
        this.artifact = artifact;
        this.isCoreJmeLibrary = isCoreJmeLibrary;
    }

    /**
     * Get the label for this Additional Library.
     *
     * @return the label for this Additional Library.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the long description for this Additional Library.
     *
     * @return the long description for this Additional Library.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the Gradle artifact string.
     *
     * @return the Gradle artifact string.
     */
    public String getArtifact() {
        return artifact;
    }

    /**
     * Is this a Core jMonkeyEngine library?
     *
     * @return true if this is a core jMonkeyEngine library.
     */
    public boolean getIsCoreJmeLibrary() {
        return isCoreJmeLibrary;
    }

    /**
     * Override the <code>toString()</code> method to return the label, so that
     * this enum will display nicely in a jComboBox.
     *
     * @return <code>label</code> as a String
     */
    @Override
    public String toString() {
        return label;
    }
}
