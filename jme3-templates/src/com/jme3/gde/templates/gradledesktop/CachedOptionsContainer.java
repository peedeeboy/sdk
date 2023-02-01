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
import java.util.Collections;
import java.util.List;
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

    private List<TemplateLibrary> additionalLibraries;
    private List<TemplateLibrary> guiLibraries;
    private List<TemplateLibrary> networkingLibraries;
    private List<TemplateLibrary> physicsLibraries;

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

    private List<TemplateLibrary> initLibaries(MavenVersionChecker mavenVersionChecker, TemplateLibrary[] libraries) {
        List<TemplateLibrary> libs = new ArrayList<>(libraries.length);
        for (TemplateLibrary templateLibrary : libraries) {
            libs.add(new TemplateLibrary() {

                private final CompletableFuture<String> latestVersion = (getGroupId() == null || getArtifactId() == null)
                        ? null : mavenVersionChecker.getLatestVersion(templateLibrary.getGroupId(), templateLibrary.getArtifactId())
                                .whenComplete((result, exception) -> {

                            if (exception != null) {
                                logger.log(Level.WARNING, exception,
                                        () -> String.format("Failed to acquire version information for Maven artifact %s:%s", new Object[]{getGroupId(), getArtifactId()}));
                            } else if (result == null) {
                                logger.log(Level.WARNING,
                                        () -> String.format("Failed to acquire version information for Maven artifact %s:%s", new Object[]{getGroupId(), getArtifactId()}));
                            } else {
                                version = result;
                            }
                        });

                private String version;

                @Override
                public String getLabel() {
                    return templateLibrary.getLabel();
                }

                @Override
                public String getDescription() {
                    return templateLibrary.getDescription();
                }

                @Override
                public boolean getIsCoreJmeLibrary() {
                    return templateLibrary.getIsCoreJmeLibrary();
                }

                @Override
                public String getGroupId() {
                    return templateLibrary.getGroupId();
                }

                @Override
                public String getArtifactId() {
                    return templateLibrary.getArtifactId();
                }

                @Override
                public String getVersion() {
                    return version != null ? version : templateLibrary.getVersion();
                }

                @Override
                public String toString() {
                    return templateLibrary.getLabel();
                }

            });
        }

        return Collections.unmodifiableList(libs);
    }

    public List<TemplateLibrary> getAdditionalLibraries() {
        return additionalLibraries;
    }

    public List<TemplateLibrary> getGuiLibraries() {
        return guiLibraries;
    }

    public List<TemplateLibrary> getNetworkingLibraries() {
        return networkingLibraries;
    }

    public List<TemplateLibrary> getPhysicsLibraries() {
        return physicsLibraries;
    }

}
