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

import java.text.Collator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Versioning scheme like x.x.x and/or x.x.x+tag
 */
public final class SemanticPlusTagVersionInfo implements VersionInfo<SemanticPlusTagVersionInfo> {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<release>\\d+)\\+?(?<tag>.*)");

    private final int major;
    private final int minor;
    private final int release;
    private final String tag;
    private final String versionString;

    public SemanticPlusTagVersionInfo(String versionString) {
        this.versionString = versionString;

        Matcher m = VERSION_PATTERN.matcher(versionString);
        if (m.find()) {
            this.major = Integer.parseInt(m.group("major"));
            this.minor = Integer.parseInt(m.group("minor"));
            this.release = Integer.parseInt(m.group("release"));
            String t = m.group("tag");
            this.tag = t.isEmpty() ? null : t;
        } else {
            this.major = 0;
            this.minor = 0;
            this.release = 0;
            this.tag = null;
        }
    }

    @Override
    public int getMajor() {
        return major;
    }

    @Override
    public Integer getMinor() {
        return minor;
    }

    @Override
    public Integer getRelease() {
        return release;
    }

    @Override
    public String getType() {
        return tag;
    }

    @Override
    public String getVersionString() {
        return versionString;
    }

    @Override
    public int compareTo(SemanticPlusTagVersionInfo o) {
        int result = Integer.compare(major, o.major);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(minor, o.minor);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(release, o.release);
        if (result != 0) {
            return result;
        }

        return Collator.getInstance().compare(tag != null ? tag : "", o.tag != null ? o.tag : "");
    }

}
