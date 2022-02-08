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
 * Enum representing a version of LightWeight Java Game Library (LWJGL)
 * supported by jMonkeyEngine.
 *
 * <p>To add a new LWJGL version:</p>
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
 * The artifact should contain the Gradle artifact string for the JME wrapped
 * LWJGL version. The correct JME version will be appended to the artifact by
 * the template.
 * </li>
 * </ul>
 *
 * @author peedeeboy
 */
public enum LWJGLVersion {

    LWJGL_3("LWJGL 3.x", NbBundle.getMessage(LWJGLVersion.class,
            "lwjgl.lwjgl3.description"), "org.jmonkeyengine:jme3-lwjgl3"),
    LWJGL_2("LWJGL 2.x", NbBundle.getMessage(LWJGLVersion.class,
            "lwjgl.lwjgl2.description"), "org.jmonkeyengine:jme3-lwjgl");

    /**
     * The name of the LWJGL library. This will be displayed in the jComboBox in
     * the New Project wizard.
     */
    private final String label;
    /**
     * Long description of the LWJGL version. This should be stored in the
     * <code>bundle.properties</code> file.
     */
    private final String description;
    /**
     * Gradle artifact string. This should exclude the jMonkeyEngine version, as
     * this will be added by the template.
     */
    private final String artifact;

    /**
     * Private constructor to create an instance of this enum.
     *
     * @param label The name of the LWJGL library.
     * @param description Long description of the LWJGL version.
     * @param artifact Gradle artifact string.
     */
    LWJGLVersion(String label, String description, String artifact) {
        this.label = label;
        this.description = description;
        this.artifact = artifact;
    }

    /**
     * Get the label for this LWJGL version.
     *
     * @return the label for this LWJGL version.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the long description for this LWJGL version.
     *
     * @return the long description for this LWJGL version.
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
     * Override the <code>toString()</code> method to return the label, so that
     * this enum will display nicely in a jComboBox.
     *
     * @return <code>label</code> as a String
     */
    @Override
    public String toString() {
        return this.label;
    }
}
