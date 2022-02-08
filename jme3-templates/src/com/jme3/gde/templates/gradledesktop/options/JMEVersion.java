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
 * Enum representing a jMonkeyEngine version to be used when creating a new
 * Gradle based project.
 * 
 * To add a new version of the engine to the options for a Gradle project:
 * <ul>
 *   <li>
 *       Create a new .html file in the <code>com.jme3.gde.templates.files.patchnotes</code>
 *       package containing the Patch Notes copied from GitHub.  The <code>class=""</code>
 *       attributes should be removed using a regex like: class="[a-zA-Z0-9:;\.\s\(\)\-,]*"
 *   </li>
 *   <li>
 *       Add a new entry to this enum.  The label should match the Maven/Gradle version.
 *       The patchNotesPath should point to the .html file containing the Patch Notes
 *   </li>
 * </ul>
 * 
 * @author peedeeboy
 */
public enum JMEVersion {
    JME_3_5_0("3.5.0-stable", "/com/jme3/gde/templates/files/patchnotes/350-stable.html"),
    JME_3_4_1("3.4.1-stable", "/com/jme3/gde/templates/files/patchnotes/341-stable.html"),
    JME_3_4_0("3.4.0-stable", "/com/jme3/gde/templates/files/patchnotes/340-stable.html"),
    JME_3_3_2("3.3.2-stable", "/com/jme3/gde/templates/files/patchnotes/332-stable.html"),
    JME_3_3_0("3.3.0-stable", "/com/jme3/gde/templates/files/patchnotes/330-stable.html");

    /**
     * Name of the jMonkeyEngine version.  This should match the Maven/Gradle version.
     */
    private final String label;
    /**
     * Path to a .html file containing the Patch Notes for this version of the Engine.
     */
    private final String patchNotesPath;

    /**
     * Private constructor to create an instance of this enum.
     * 
     * @param label Name of the jMonkeyEngine version.
     * @param patchNotesPath Path to a .html file containing the Patch Notes for this version of the Engine.
     */
    private JMEVersion(String label, String patchNotesPath) {
        this.label = label;
        this.patchNotesPath = patchNotesPath;
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

    /**
     * Get the label for this jMonkeyEngineVersion
     * @return 
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the path to the .html file containing the Patch Notes for this
     * jMonkeyEngine version
     * @return 
     */
    public String getPatchNotesPath() {
        return patchNotesPath;
    }

}
