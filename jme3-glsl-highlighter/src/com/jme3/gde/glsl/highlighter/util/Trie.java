/*
 * Copyright (c) 2003-2024 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
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
package com.jme3.gde.glsl.highlighter.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple and efficient text search
 */
public class Trie {

    private static class TrieNode {

        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    public enum MatchType {
        NO_MATCH,
        PARTIAL_MATCH,
        FULL_MATCH
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * Insert word to the Trie structure
     *
     * @param word word to insert
     */
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    /**
     * Searches for the string
     *
     * @param word word to search for
     * @return match type
     */
    public MatchType search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return MatchType.NO_MATCH;
            }
            current = current.children.get(ch);
        }

        return current.isEndOfWord ? MatchType.FULL_MATCH : MatchType.PARTIAL_MATCH;
    }

}
