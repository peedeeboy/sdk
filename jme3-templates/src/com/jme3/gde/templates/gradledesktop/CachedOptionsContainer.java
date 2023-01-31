/*
 * Copyright (c) 2009-2023 jMonkeyEngine
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
package com.jme3.gde.templates.gradledesktop;

import com.jme3.gde.templates.gradledesktop.options.AdditionalLibrary;
import com.jme3.gde.templates.gradledesktop.options.GUILibrary;
import com.jme3.gde.templates.gradledesktop.options.NetworkingLibrary;
import com.jme3.gde.templates.gradledesktop.options.PhysicsLibrary;
import com.jme3.gde.templates.gradledesktop.options.TemplateLibrary;
import com.jme3.gde.templates.utils.mavensearch.MavenApiVersionChecker;
import com.jme3.gde.templates.utils.mavensearch.MavenVersionChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton that contains all the options. Tries to go online to get all the
 * latest options
 */
public class CachedOptionsContainer {

    private static CachedOptionsContainer instance;

    private static final Logger logger = Logger.getLogger(CachedOptionsContainer.class.getName());

    private SortedMap<TemplateLibrary, CompletableFuture<String>> additionalLibraries;
    private SortedMap<TemplateLibrary, CompletableFuture<String>> guiLibraries;
    private SortedMap<TemplateLibrary, CompletableFuture<String>> networkingLibraries;
    private SortedMap<TemplateLibrary, CompletableFuture<String>> physicsLibraries;

    private CachedOptionsContainer() {
        initialize();
    }

    public static CachedOptionsContainer getInstance() {
        if (instance == null) {
            synchronized (CachedOptionsContainer.class) {
                if (instance == null) {
                    instance = new CachedOptionsContainer();
                }
            }
        }
        return instance;
    }

    private void initialize() {
        MavenVersionChecker mavenVersionChecker = new MavenApiVersionChecker();

        additionalLibraries = initLibaries(mavenVersionChecker, AdditionalLibrary.values());
        guiLibraries = initLibaries(mavenVersionChecker, GUILibrary.values());
        networkingLibraries = initLibaries(mavenVersionChecker, NetworkingLibrary.values());
        physicsLibraries = initLibaries(mavenVersionChecker, PhysicsLibrary.values());
    }

    private SortedMap<TemplateLibrary, CompletableFuture<String>> initLibaries(MavenVersionChecker mavenVersionChecker, TemplateLibrary[] libraries) {
        SortedMap<TemplateLibrary, CompletableFuture<String>> libs = new TreeMap<>();
        for (TemplateLibrary templateLibrary : libraries) {
            libs.put(templateLibrary, mavenVersionChecker.getLatestVersion(templateLibrary.getGroupId(), templateLibrary.getArtifactId()));
        }

        return libs;
    }

    public List<TemplateLibrary> getAdditionalLibraries() {
        return getLibraries(additionalLibraries);
    }

    public List<TemplateLibrary> getGuiLibraries() {
        return getLibraries(guiLibraries);
    }

    public List<TemplateLibrary> getNetworkingLibraries() {
        return getLibraries(networkingLibraries);
    }

    public List<TemplateLibrary> getPhysicsLibraries() {
        return getLibraries(physicsLibraries);
    }

    private List<TemplateLibrary> getLibraries(SortedMap<TemplateLibrary, CompletableFuture<String>> libraries) {
        List<TemplateLibrary> results = new ArrayList<>(libraries.size());

        for (Map.Entry<TemplateLibrary, CompletableFuture<String>> entry : libraries.entrySet()) {
            results.add(new TemplateLibrary() {

                @Override
                public String getLabel() {
                    return entry.getKey().getLabel();
                }

                @Override
                public String getDescription() {
                    return entry.getKey().getDescription();
                }

                @Override
                public boolean getIsCoreJmeLibrary() {
                    return entry.getKey().getIsCoreJmeLibrary();
                }

                @Override
                public String getGroupId() {
                    return entry.getKey().getGroupId();
                }

                @Override
                public String getArtifactId() {
                    return entry.getKey().getArtifactId();
                }

                @Override
                public String getVersion() {
                    try {
                        return entry.getValue().getNow(entry.getKey().getVersion());
                    } catch (Exception e) {
                        logger.log(Level.WARNING, e,
                                () -> String.format("Failed to acquire version information for Maven artifact {0}:{1}", new Object[]{getGroupId(), getArtifactId()}));
                        entry.getValue().obtrudeValue(entry.getKey().getVersion());

                        return entry.getKey().getVersion();
                    }
                }

                @Override
                public String toString() {
                    return entry.getKey().getLabel();
                }

            });
        }

        return results;
    }

}
