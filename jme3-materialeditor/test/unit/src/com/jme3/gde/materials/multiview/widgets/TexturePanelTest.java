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
package com.jme3.gde.materials.multiview.widgets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


/**
 *
 * @author rickard
 */
public class TexturePanelTest {
    
    public TexturePanelTest() {
    }

    @Test
    public void testExtractTextureName() {
        TexturePanel texturePanel = new TexturePanel();
        String textureName = "\"simple_name.jpg\"";
        String extractedName = texturePanel.extractTextureName(textureName);
        assertEquals("simple_name.jpg", extractedName);
        
        String textureNameWithModifier = "Flip Repeat \"simple_name.jpg\"";
        extractedName = texturePanel.extractTextureName(textureNameWithModifier);
        assertEquals("simple_name.jpg", extractedName);
        
        String textureNameWithSpaces = "\"texture name with spaces.jpg\"";
        extractedName = texturePanel.extractTextureName(textureNameWithSpaces);
        assertEquals("texture name with spaces.jpg", extractedName);
        
        String textureNameWithSpaceAndModifier = "Flip Repeat \"texture name with spaces.jpg\"";
        extractedName = texturePanel.extractTextureName(textureNameWithSpaceAndModifier);
        assertEquals("texture name with spaces.jpg", extractedName);
    }

    /**
     * Test of updateFlipRepeat method, of class TexturePanel.
     */
    @org.junit.Test
    public void testUpdateFlipRepeat() {
    }

}
