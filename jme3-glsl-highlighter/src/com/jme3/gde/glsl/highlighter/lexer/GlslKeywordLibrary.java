/*
 * Copyright (c) 2003-2018 jMonkeyEngine
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
package com.jme3.gde.glsl.highlighter.lexer;

import com.jme3.gde.glsl.highlighter.util.Trie;

/**
 * Brace, yourselves, this file contains every word that means something in
 * GLSL. Expect about 400 lines of code that just adds strings.
 *
 * @author grizeldi
 */
final class GlslKeywordLibrary {
    
    public enum KeywordType {
        KEYWORD, BUILTIN_FUNCTION, BUILTIN_VARIABLE, BASIC_TYPE, UNFINISHED;
    }
    
    private static final Trie keywordsTrie = new Trie();
    private static final Trie builtinFunctionsTrie = new Trie();
    private static final Trie builtinVariablesTrie = new Trie();
    private static final Trie basicTypesTrie = new Trie();

    static {
        //keywords
        keywordsTrie.insert("attribute");
        keywordsTrie.insert("const");
        keywordsTrie.insert("uniform");
        keywordsTrie.insert("varying");
        keywordsTrie.insert("buffer");
        keywordsTrie.insert("shared");
        keywordsTrie.insert("coherent");
        keywordsTrie.insert("volatile");
        keywordsTrie.insert("restrict");
        keywordsTrie.insert("readonly");
        keywordsTrie.insert("writeonly");
        keywordsTrie.insert("atomic_uint");
        keywordsTrie.insert("layout");
        keywordsTrie.insert("centroid");
        keywordsTrie.insert("flat");
        keywordsTrie.insert("smooth");
        keywordsTrie.insert("noperspective");
        keywordsTrie.insert("patch");
        keywordsTrie.insert("sample");
        keywordsTrie.insert("break");
        keywordsTrie.insert("continue");
        keywordsTrie.insert("do");
        keywordsTrie.insert("for");
        keywordsTrie.insert("while");
        keywordsTrie.insert("switch");
        keywordsTrie.insert("case");
        keywordsTrie.insert("default");
        keywordsTrie.insert("if");
        keywordsTrie.insert("else");
        keywordsTrie.insert("subroutine");
        keywordsTrie.insert("in");
        keywordsTrie.insert("out");
        keywordsTrie.insert("inout");
        keywordsTrie.insert("void");
        keywordsTrie.insert("true");
        keywordsTrie.insert("false");
        keywordsTrie.insert("invariant");
        keywordsTrie.insert("precise");
        keywordsTrie.insert("discard");
        keywordsTrie.insert("return");
        //primitives and other types
        basicTypesTrie.insert("float");
        basicTypesTrie.insert("double");
        basicTypesTrie.insert("int");
        basicTypesTrie.insert("bool");
        basicTypesTrie.insert("mat2");
        basicTypesTrie.insert("mat3");
        basicTypesTrie.insert("mat4");
        basicTypesTrie.insert("dmat2");
        basicTypesTrie.insert("dmat3");
        basicTypesTrie.insert("dmat4");
        basicTypesTrie.insert("mat2x2");
        basicTypesTrie.insert("mat2x3");
        basicTypesTrie.insert("mat2x4");
        basicTypesTrie.insert("dmat2x2");
        basicTypesTrie.insert("dmat2x3");
        basicTypesTrie.insert("dmat2x4");
        basicTypesTrie.insert("mat3x2");
        basicTypesTrie.insert("mat3x3");
        basicTypesTrie.insert("mat3x4");
        basicTypesTrie.insert("dmat3x2");
        basicTypesTrie.insert("dmat3x3");
        basicTypesTrie.insert("dmat3x4");
        basicTypesTrie.insert("mat4x2");
        basicTypesTrie.insert("mat4x3");
        basicTypesTrie.insert("mat4x4");
        basicTypesTrie.insert("dmat4x2");
        basicTypesTrie.insert("dmat4x3");
        basicTypesTrie.insert("dmat4x4");
        basicTypesTrie.insert("vec2");
        basicTypesTrie.insert("vec3");
        basicTypesTrie.insert("vec4");
        basicTypesTrie.insert("ivec2");
        basicTypesTrie.insert("ivec3");
        basicTypesTrie.insert("ivec4");
        basicTypesTrie.insert("bvec2");
        basicTypesTrie.insert("bvec3");
        basicTypesTrie.insert("bvec4");
        basicTypesTrie.insert("dvec2");
        basicTypesTrie.insert("dvec3");
        basicTypesTrie.insert("dvec4");
        basicTypesTrie.insert("uint");
        basicTypesTrie.insert("uvec2");
        basicTypesTrie.insert("uvec3");
        basicTypesTrie.insert("uvec4");
        basicTypesTrie.insert("lowp");
        basicTypesTrie.insert("mediump");
        basicTypesTrie.insert("highp");
        basicTypesTrie.insert("precision");
        basicTypesTrie.insert("sampler1D");
        basicTypesTrie.insert("sampler2D");
        basicTypesTrie.insert("sampler3D");
        basicTypesTrie.insert("samplerCube");
        basicTypesTrie.insert("sampler1DShadow");
        basicTypesTrie.insert("sampler2DShadow");
        basicTypesTrie.insert("samplerCubeShadow");
        basicTypesTrie.insert("sampler1DArray");
        basicTypesTrie.insert("sampler2DArray");
        basicTypesTrie.insert("sampler1DArrayShadow");
        basicTypesTrie.insert("sampler2DArrayShadow");
        basicTypesTrie.insert("isampler1D");
        basicTypesTrie.insert("isampler2D");
        basicTypesTrie.insert("isampler3D");
        basicTypesTrie.insert("isamplerCube");
        basicTypesTrie.insert("isampler1DArray");
        basicTypesTrie.insert("isampler2DArray");
        basicTypesTrie.insert("usampler1D");
        basicTypesTrie.insert("usampler2D");
        basicTypesTrie.insert("usampler3D");
        basicTypesTrie.insert("usamplerCube");
        basicTypesTrie.insert("usampler1DArray");
        basicTypesTrie.insert("usampler2DArray");
        basicTypesTrie.insert("sampler2DRect");
        basicTypesTrie.insert("sampler2DRectShadow");
        basicTypesTrie.insert("isampler2DRect");
        basicTypesTrie.insert("usampler2DRect");
        basicTypesTrie.insert("samplerBuffer");
        basicTypesTrie.insert("isamplerBuffer");
        basicTypesTrie.insert("usamplerBuffer");
        basicTypesTrie.insert("sampler2DMS");
        basicTypesTrie.insert("isampler2DMS");
        basicTypesTrie.insert("usampler2DMS");
        basicTypesTrie.insert("sampler2DMSArray");
        basicTypesTrie.insert("isampler2DMSArray");
        basicTypesTrie.insert("usampler2DMSArray");
        basicTypesTrie.insert("samplerCubeArray");
        basicTypesTrie.insert("samplerCubeArrayShadow");
        basicTypesTrie.insert("isamplerCubeArray");
        basicTypesTrie.insert("usamplerCubeArray");
        basicTypesTrie.insert("image1D");
        basicTypesTrie.insert("iimage1D");
        basicTypesTrie.insert("uimage1D");
        basicTypesTrie.insert("image2D");
        basicTypesTrie.insert("iimage2D");
        basicTypesTrie.insert("uimage2D");
        basicTypesTrie.insert("image3D");
        basicTypesTrie.insert("iimage3D");
        basicTypesTrie.insert("uimage3D");
        basicTypesTrie.insert("image2DRect");
        basicTypesTrie.insert("iimage2DRect");
        basicTypesTrie.insert("uimage2DRect");
        basicTypesTrie.insert("imageCube");
        basicTypesTrie.insert("iimageCube");
        basicTypesTrie.insert("uimageCube");
        basicTypesTrie.insert("imageBuffer");
        basicTypesTrie.insert("iimageBuffer");
        basicTypesTrie.insert("uimageBuffer");
        basicTypesTrie.insert("image1DArray");
        basicTypesTrie.insert("iimage1DArray");
        basicTypesTrie.insert("uimage1DArray");
        basicTypesTrie.insert("image2DArray");
        basicTypesTrie.insert("iimage2DArray");
        basicTypesTrie.insert("uimage2DArray");
        basicTypesTrie.insert("imageCubeArray");
        basicTypesTrie.insert("iimageCubeArray");
        basicTypesTrie.insert("uimageCubeArray");
        basicTypesTrie.insert("image2DMS");
        basicTypesTrie.insert("iimage2DMS");
        basicTypesTrie.insert("uimage2DMS");
        basicTypesTrie.insert("image2DMSArray");
        basicTypesTrie.insert("iimage2DMSArray");
        basicTypesTrie.insert("uimage2DMSArray");
        basicTypesTrie.insert("struct");
        //builtin variables
        //compute shaders
        builtinVariablesTrie.insert("gl_NumWorkGroups");
        builtinVariablesTrie.insert("gl_WorkGroupSize");
        builtinVariablesTrie.insert("gl_WorkGroupID");
        builtinVariablesTrie.insert("gl_LocalInvocationID");
        builtinVariablesTrie.insert("gl_GlobalInvocationID");
        builtinVariablesTrie.insert("gl_LocalInvocationIndex");
        //vertex shaders
        builtinVariablesTrie.insert("gl_VertexID");
        builtinVariablesTrie.insert("gl_InstanceID");
        builtinVariablesTrie.insert("gl_Position");
        //geometry shaders
        builtinVariablesTrie.insert("gl_PrimitiveIDIn");
        builtinVariablesTrie.insert("gl_Layer");
        builtinVariablesTrie.insert("gl_ViewportIndex");
        //tesselation shaders
        builtinVariablesTrie.insert("gl_MaxPatchVertices");
        builtinVariablesTrie.insert("gl_PatchVerticesIn");
        builtinVariablesTrie.insert("gl_TessLevelOuter");
        builtinVariablesTrie.insert("gl_TessLevelInner");
        builtinVariablesTrie.insert("gl_TessCoord");
        //fragment shaders
        builtinVariablesTrie.insert("gl_FragCoord");
        builtinVariablesTrie.insert("gl_FrontFacing");
        builtinVariablesTrie.insert("gl_PointCoord");
        builtinVariablesTrie.insert("gl_SampleID");
        builtinVariablesTrie.insert("gl_SamplePosition");
        builtinVariablesTrie.insert("gl_SampleMaskIn");
        builtinVariablesTrie.insert("gl_Layer");
        builtinVariablesTrie.insert("gl_ViewportIndex");
        builtinVariablesTrie.insert("gl_FragColor");
        //general
        builtinVariablesTrie.insert("gl_Position");
        builtinVariablesTrie.insert("gl_PointSize");
        builtinVariablesTrie.insert("gl_ClipDistance");
        builtinVariablesTrie.insert("gl_InvocationID");
        builtinVariablesTrie.insert("gl_PrimitiveID");
        //jme variables - this is why we build custom plugins :) (apart from existing being under GPL)
        builtinVariablesTrie.insert("inPosition");
        builtinVariablesTrie.insert("inNormal");
        builtinVariablesTrie.insert("inColor");
        builtinVariablesTrie.insert("inTextCoord");
        builtinVariablesTrie.insert("g_WorldMatrix");
        builtinVariablesTrie.insert("g_ViewMatrix");
        builtinVariablesTrie.insert("g_ProjectionMatrix");
        builtinVariablesTrie.insert("g_WorldViewMatrix");
        builtinVariablesTrie.insert("g_WorldViewProjectionMatrix");
        builtinVariablesTrie.insert("g_WorldNormalMatrix");
        builtinVariablesTrie.insert("g_NormalMatrix");
        builtinVariablesTrie.insert("g_ViewProjectionMatrix");
        builtinVariablesTrie.insert("g_WorldMatrixInverseTranspose");
        builtinVariablesTrie.insert("g_WorldMatrixInverse");
        builtinVariablesTrie.insert("g_ViewMatrixInverse");
        builtinVariablesTrie.insert("g_ProjectionMatrixInverse");
        builtinVariablesTrie.insert("g_ViewProjectionMatrixInverse");
        builtinVariablesTrie.insert("g_WorldViewMatrixInverse");
        builtinVariablesTrie.insert("g_NormalMatrixInverse");
        builtinVariablesTrie.insert("g_WorldViewProjectionMatrixInverse");
        builtinVariablesTrie.insert("g_ViewPort");
        builtinVariablesTrie.insert("g_FrustumNearFar");
        builtinVariablesTrie.insert("g_Resolution");
        builtinVariablesTrie.insert("g_ResolutionInverse");
        builtinVariablesTrie.insert("g_Aspect");
        builtinVariablesTrie.insert("g_CameraPosition");
        builtinVariablesTrie.insert("g_CameraDirection");
        builtinVariablesTrie.insert("g_CameraLeft");
        builtinVariablesTrie.insert("g_CameraUp");
        builtinVariablesTrie.insert("g_Time");
        builtinVariablesTrie.insert("g_Tpf");
        builtinVariablesTrie.insert("g_FrameRate");
        builtinVariablesTrie.insert("g_LightDirection");
        builtinVariablesTrie.insert("g_LightPosition");
        builtinVariablesTrie.insert("g_LightColor");
        builtinVariablesTrie.insert("g_AmbientLightColor");
        //builtin functions
        builtinFunctionsTrie.insert("radians");
        builtinFunctionsTrie.insert("degrees");
        builtinFunctionsTrie.insert("sin");
        builtinFunctionsTrie.insert("cos");
        builtinFunctionsTrie.insert("tan");
        builtinFunctionsTrie.insert("asin");
        builtinFunctionsTrie.insert("acos");
        builtinFunctionsTrie.insert("atan");
        builtinFunctionsTrie.insert("sinh");
        builtinFunctionsTrie.insert("cosh");
        builtinFunctionsTrie.insert("tanh");
        builtinFunctionsTrie.insert("asinh");
        builtinFunctionsTrie.insert("acosh");
        builtinFunctionsTrie.insert("atanh");
        builtinFunctionsTrie.insert("pow");
        builtinFunctionsTrie.insert("exp");
        builtinFunctionsTrie.insert("log");
        builtinFunctionsTrie.insert("exp2");
        builtinFunctionsTrie.insert("log2");
        builtinFunctionsTrie.insert("sqrt");
        builtinFunctionsTrie.insert("inversesqrt");
        builtinFunctionsTrie.insert("abs");
        builtinFunctionsTrie.insert("sign");
        builtinFunctionsTrie.insert("floor");
        builtinFunctionsTrie.insert("trunc");
        builtinFunctionsTrie.insert("round");
        builtinFunctionsTrie.insert("roundEven");
        builtinFunctionsTrie.insert("ceil");
        builtinFunctionsTrie.insert("fract");
        builtinFunctionsTrie.insert("mod");
        builtinFunctionsTrie.insert("modf");
        builtinFunctionsTrie.insert("min");
        builtinFunctionsTrie.insert("max");
        builtinFunctionsTrie.insert("clamp");
        builtinFunctionsTrie.insert("mix");
        builtinFunctionsTrie.insert("step");
        builtinFunctionsTrie.insert("smoothstep");
        builtinFunctionsTrie.insert("isnan");
        builtinFunctionsTrie.insert("isinf");
        builtinFunctionsTrie.insert("floatBitsToInt");
        builtinFunctionsTrie.insert("floatBitsToUInt");
        builtinFunctionsTrie.insert("intBitsToFloat");
        builtinFunctionsTrie.insert("uintBitsToFloat");
        builtinFunctionsTrie.insert("fma");
        builtinFunctionsTrie.insert("frexp");
        builtinFunctionsTrie.insert("packUnorm2x16");
        builtinFunctionsTrie.insert("packSnorm2x16");
        builtinFunctionsTrie.insert("packUnorm4x8");
        builtinFunctionsTrie.insert("packSnorm4x8");
        builtinFunctionsTrie.insert("unpackUnorm2x16");
        builtinFunctionsTrie.insert("unpackSnorm2x16");
        builtinFunctionsTrie.insert("unpackUnorm4x8");
        builtinFunctionsTrie.insert("unpackSnorm4x8");
        builtinFunctionsTrie.insert("packDouble2x32");
        builtinFunctionsTrie.insert("unpackDouble2x32");
        builtinFunctionsTrie.insert("packHalf2x16");
        builtinFunctionsTrie.insert("unpackHalf2x16");
        builtinFunctionsTrie.insert("length");
        builtinFunctionsTrie.insert("distance");
        builtinFunctionsTrie.insert("dot");
        builtinFunctionsTrie.insert("cross");
        builtinFunctionsTrie.insert("normalize");
        builtinFunctionsTrie.insert("ftransform");
        builtinFunctionsTrie.insert("faceforward");
        builtinFunctionsTrie.insert("reflect");
        builtinFunctionsTrie.insert("refract");
        builtinFunctionsTrie.insert("matrixCompMult");
        builtinFunctionsTrie.insert("outerProduct");
        builtinFunctionsTrie.insert("transpose");
        builtinFunctionsTrie.insert("determinant");
        builtinFunctionsTrie.insert("inverse");
        builtinFunctionsTrie.insert("lessThan");
        builtinFunctionsTrie.insert("lessThanEqual");
        builtinFunctionsTrie.insert("greaterThan");
        builtinFunctionsTrie.insert("greaterThanEqual");
        builtinFunctionsTrie.insert("equal");
        builtinFunctionsTrie.insert("notEqual");
        builtinFunctionsTrie.insert("any");
        builtinFunctionsTrie.insert("all");
        builtinFunctionsTrie.insert("not");
        builtinFunctionsTrie.insert("uaddCarry");
        builtinFunctionsTrie.insert("usubBorrow");
        builtinFunctionsTrie.insert("umulExtended");
        builtinFunctionsTrie.insert("imulExtended");
        builtinFunctionsTrie.insert("bitfieldExtract");
        builtinFunctionsTrie.insert("bitfieldInsert");
        builtinFunctionsTrie.insert("bitfieldReverse");
        builtinFunctionsTrie.insert("bitCount");
        builtinFunctionsTrie.insert("findLSB");
        builtinFunctionsTrie.insert("findMSB");
        builtinFunctionsTrie.insert("textureSize");
        builtinFunctionsTrie.insert("textureQueryLod");
        builtinFunctionsTrie.insert("textureQueryLevels");
        builtinFunctionsTrie.insert("texture");
        builtinFunctionsTrie.insert("textureProj");
        builtinFunctionsTrie.insert("textureLod");
        builtinFunctionsTrie.insert("textureOffset");
        builtinFunctionsTrie.insert("texelFetch");
        builtinFunctionsTrie.insert("texelFetchOffset");
        builtinFunctionsTrie.insert("textureProjOffset");
        builtinFunctionsTrie.insert("textureLodOffset");
        builtinFunctionsTrie.insert("textureProjLod");
        builtinFunctionsTrie.insert("textureProjLodOffset");
        builtinFunctionsTrie.insert("textureGrad");
        builtinFunctionsTrie.insert("textureGradOffset");
        builtinFunctionsTrie.insert("textureProjGrad");
        builtinFunctionsTrie.insert("textureProjGradOffset");
        builtinFunctionsTrie.insert("textureGather");
        builtinFunctionsTrie.insert("textureGatherOffset");
        builtinFunctionsTrie.insert("textureGatherOffsets");
        builtinFunctionsTrie.insert("texture1D");
        builtinFunctionsTrie.insert("texture1DProj");
        builtinFunctionsTrie.insert("texture1DLod");
        builtinFunctionsTrie.insert("texture1DProjLod");
        builtinFunctionsTrie.insert("texture2D");
        builtinFunctionsTrie.insert("texture2DProj");
        builtinFunctionsTrie.insert("texture2DLod");
        builtinFunctionsTrie.insert("texture2DProjLod");
        builtinFunctionsTrie.insert("texture3D");
        builtinFunctionsTrie.insert("texture3DProj");
        builtinFunctionsTrie.insert("texture3DLod");
        builtinFunctionsTrie.insert("texture3DProjLod");
        builtinFunctionsTrie.insert("textureCube");
        builtinFunctionsTrie.insert("textureCubeLod");
        builtinFunctionsTrie.insert("shadow1D");
        builtinFunctionsTrie.insert("shadow2D");
        builtinFunctionsTrie.insert("shadow1DProj");
        builtinFunctionsTrie.insert("shadow2DProj");
        builtinFunctionsTrie.insert("shadow1DLod");
        builtinFunctionsTrie.insert("shadow2DLod");
        builtinFunctionsTrie.insert("shadow1DProjLod");
        builtinFunctionsTrie.insert("shadow2DProjLod");
        builtinFunctionsTrie.insert("atomicCounterIncrement");
        builtinFunctionsTrie.insert("atomicCounterDecrement");
        builtinFunctionsTrie.insert("atomicCounter");
        builtinFunctionsTrie.insert("atomicAdd");
        builtinFunctionsTrie.insert("atomicMin");
        builtinFunctionsTrie.insert("atomicMax");
        builtinFunctionsTrie.insert("atomicAnd");
        builtinFunctionsTrie.insert("atomicOr");
        builtinFunctionsTrie.insert("atomicXor");
        builtinFunctionsTrie.insert("atomicExchange");
        builtinFunctionsTrie.insert("atomicCompSwap");
        builtinFunctionsTrie.insert("imageSize");
        builtinFunctionsTrie.insert("imageLoad");
        builtinFunctionsTrie.insert("imageStore");
        builtinFunctionsTrie.insert("imageAtomicAdd");
        builtinFunctionsTrie.insert("imageAtomicMin");
        builtinFunctionsTrie.insert("imageAtomicMax");
        builtinFunctionsTrie.insert("imageAtomicAnd");
        builtinFunctionsTrie.insert("imageAtomicOr");
        builtinFunctionsTrie.insert("imageAtomicXor");
        builtinFunctionsTrie.insert("imageAtomicExchange");
        builtinFunctionsTrie.insert("imageAtomicCompSwap");
        builtinFunctionsTrie.insert("dFdx");
        builtinFunctionsTrie.insert("dFdy");
        builtinFunctionsTrie.insert("fwidth");
        builtinFunctionsTrie.insert("interpolateAtCentroid");
        builtinFunctionsTrie.insert("interpolateAtSample");
        builtinFunctionsTrie.insert("interpolateAtOffset");
        builtinFunctionsTrie.insert("noise1");
        builtinFunctionsTrie.insert("noise2");
        builtinFunctionsTrie.insert("noise3");
        builtinFunctionsTrie.insert("noise4");
        builtinFunctionsTrie.insert("EmitStreamVertex");
        builtinFunctionsTrie.insert("EndStreamPrimitive");
        builtinFunctionsTrie.insert("EmitVertex");
        builtinFunctionsTrie.insert("EndPrimitive");
        builtinFunctionsTrie.insert("barrier");
        builtinFunctionsTrie.insert("memoryBarrier");
        builtinFunctionsTrie.insert("memoryBarrierAtomicCounter");
        builtinFunctionsTrie.insert("memoryBarrierBuffer");
        builtinFunctionsTrie.insert("memoryBarrierShared");
        builtinFunctionsTrie.insert("memoryBarrierImage");
        builtinFunctionsTrie.insert("groupMemoryBarrier");
    }

    public static KeywordType lookup(String s) {
        KeywordType returnType = null;
        returnType = lookup(s, returnType, KeywordType.BASIC_TYPE, basicTypesTrie);
        if (returnType == KeywordType.UNFINISHED || returnType == null) {
            returnType = lookup(s, returnType, KeywordType.BUILTIN_VARIABLE, builtinVariablesTrie);
        }
        if (returnType == KeywordType.UNFINISHED || returnType == null) {
            returnType = lookup(s, returnType, KeywordType.BUILTIN_FUNCTION, builtinFunctionsTrie);
        }
        if (returnType == KeywordType.UNFINISHED || returnType == null) {
            returnType = lookup(s, returnType, KeywordType.KEYWORD, keywordsTrie);
        }

        return returnType;
    }

    private static KeywordType lookup(String s, KeywordType currentType, KeywordType matchType, Trie searchTrie) {
        Trie.MatchType match = searchTrie.search(s);
        if (match == Trie.MatchType.FULL_MATCH) {
            return matchType;
        }
        if (match == Trie.MatchType.PARTIAL_MATCH) {
            return KeywordType.UNFINISHED;
        }

        return currentType;
    }
}
