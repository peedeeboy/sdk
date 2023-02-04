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
package com.jme3.gde.templates.gradledesktop.options;

import com.jme3.gde.templates.utils.mavensearch.MavenApiVersionChecker;
import com.jme3.gde.templates.utils.mavensearch.MavenVersionChecker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Singleton that contains all the options. Tries to go online to get all the
 * latest options
 */
public class CachedOptionsContainer {

    private static CachedOptionsContainer instance;

    private static final Logger logger = Logger.getLogger(CachedOptionsContainer.class.getName());

    private List<LibraryVersion<JMEVersionInfo>> jmeVersions;
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

        jmeVersions = initVersions(mavenVersionChecker,
                MavenArtifact.JME_GROUP_ID,
                JMEVersion.JME_ARTIFACT_ID,
                "-stable$", new JMEVersionComparator(),
                JMEVersion.values(), (result) -> {
            jmeVersions = result;
        }, (version) -> {
            return new JMEVersionInfo(version);
        },
                JMEVersion.DEFAULT_PATCH_NOTES_PATH);
        additionalLibraries = initLibaries(mavenVersionChecker, AdditionalLibrary.values());
        guiLibraries = initLibaries(mavenVersionChecker, GUILibrary.values());
        networkingLibraries = initLibaries(mavenVersionChecker, NetworkingLibrary.values());
        physicsLibraries = initLibaries(mavenVersionChecker, PhysicsLibrary.values());
    }

    private List<TemplateLibrary> initLibaries(MavenVersionChecker mavenVersionChecker, TemplateLibrary[] libraries) {
        List<TemplateLibrary> libs = new ArrayList<>(libraries.length);
        for (TemplateLibrary templateLibrary : libraries) {
            libs.add(new TemplateLibrary() {

                private String version;

                {
                    if (templateLibrary.getGroupId() != null && templateLibrary.getArtifactId() != null) {
                        mavenVersionChecker.getLatestVersion(templateLibrary.getGroupId(), templateLibrary.getArtifactId())
                                .whenComplete((result, exception) -> {

                                    if (exception != null || result == null) {
                                        logger.log(Level.WARNING, exception,
                                        () -> String.format("Failed to acquire version information for Maven artifact %s (%s:%s)", new Object[]{getLabel(), getGroupId(), getArtifactId()}));

                                return;
                            }
                            version = result;
                                });
                    }
                }

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

    public List<LibraryVersion<JMEVersionInfo>> getJmeVersions() {
        return jmeVersions;
    }

    private <T extends VersionInfo> List<LibraryVersion<T>> initVersions(MavenVersionChecker mavenVersionChecker, String groupId,
            String artifactId, String pattern, Comparator<LibraryVersion<T>> versionComparator,
            LibraryVersion<T>[] versions, Consumer<List<LibraryVersion<T>>> completedVersionsConsumer,
            Function<String, T> versionInfoSupplier, String defaultPatchNotes) {
        mavenVersionChecker.getAllVersions(groupId, artifactId).whenComplete((result, exception) -> {

            if (exception != null || result == null) {
                logger.log(Level.WARNING, exception,
                        () -> String.format("Failed to acquire version information for Maven artifact %s:%s", new Object[]{groupId, artifactId}));

                return;
            }

            initVersionList(result, pattern, versionComparator, versions, groupId, artifactId, completedVersionsConsumer, versionInfoSupplier, defaultPatchNotes);
        });

        return Collections.unmodifiableList(Arrays.asList(versions));
    }

    private static <T extends VersionInfo> void initVersionList(List<String> result, String pattern,
            Comparator<LibraryVersion<T>> versionComparator, LibraryVersion<T>[] versions,
            String groupId, String artifactId, Consumer<List<LibraryVersion<T>>> completedVersionsConsumer,
            Function<String, T> versionInfoSupplier, String defaultPatchNotes) {

        // Filter the vesions list
        List<String> vList = result;
        if (pattern != null) {
            Pattern p = Pattern.compile(pattern);
            vList = vList.stream().filter(p.asPredicate()).collect(Collectors.toList());
        }

        // Compile the results
        SortedSet<LibraryVersion<T>> allVersions = new TreeSet<>(versionComparator);
        allVersions.addAll(Arrays.asList(versions));
        for (String v : vList) {
            allVersions.add(new LibraryVersion<T>() {

                private final T versionInfo = versionInfoSupplier.apply(v);

                @Override
                public String getGroupId() {
                    return groupId;
                }

                @Override
                public String getArtifactId() {
                    return artifactId;
                }

                @Override
                public String getVersion() {
                    return v;
                }

                @Override
                public String getPatchNotesPath() {
                    return defaultPatchNotes;
                }

                @Override
                public String toString() {
                    return v;
                }

                @Override
                public T getVersionInfo() {
                    return versionInfo;
                }

                @Override
                public int hashCode() {
                    return Objects.hashCode(v);
                }

                @Override
                public boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null) {
                        return false;
                    }
                    if (!(obj instanceof LibraryVersion)) {
                        return false;
                    }
                    final LibraryVersion other = (LibraryVersion) obj;

                    return Objects.equals(getVersion(), other.getVersion());
                }

            });
        }

        completedVersionsConsumer.accept(Collections.unmodifiableList(new ArrayList<>(allVersions)));
    }

}
