<#-- FreeMarker template (see http://freemarker.org/) -->
plugins {
    id 'java'
    id 'application'
}

group 'com.mygame'
version '1.0'

mainClassName = "com.mygame.Main"

repositories {
    mavenCentral()
    jcenter()
    maven { url 'https://jitpack.io' }
}

project.ext {
  jmeVer = '${jmeVersion}'
}

project(":assets") {
    apply plugin: "java"

    buildDir = rootProject.file("build/assets")

    sourceSets {
        main {
            resources {
                srcDir '.'
            }
        }
    }
}

dependencies {

  // Core JME
  implementation "org.jmonkeyengine:jme3-core:$jmeVer"
  implementation "org.jmonkeyengine:jme3-desktop:$jmeVer"
  implementation "${lwjglArtifact}:$jmeVer"

  // Suppress errors / warnings building in SDK
  implementation "org.jmonkeyengine:jme3-jogg:$jmeVer"
  implementation "org.jmonkeyengine:jme3-plugins:$jmeVer"
  <#if guiLibrary.label != "">
  
  // GUI Library
  <#if guiLibrary.isCoreJmeLibrary == true>
  implementation "${guiLibrary.groupId}:${guiLibrary.artifactId}:$jmeVer"
  <#else>
  implementation "${guiLibrary.groupId}:${guiLibrary.artifactId}:${guiLibrary.version}"
  </#if>
  </#if>
  <#if physicsLibrary.label != "">
  
  // Physics Library
  <#if physicsLibrary.isCoreJmeLibrary == true>
  implementation "${physicsLibrary.groupId}:${physicsLibrary.artifactId}:$jmeVer"
  <#else>
  implementation "${physicsLibrary.groupId}:${physicsLibrary.artifactId}:${physicsLibrary.version}"
  </#if>
  </#if>
  <#if networkingLibrary.label != "">
  
  // Networking Library
  <#if networkingLibrary.isCoreJmeLibrary == true>
  implementation "${networkingLibrary.groupId}:${networkingLibrary.artifactId}:$jmeVer"
  <#else>
  implementation "${networkingLibrary.groupId}:${networkingLibrary.artifactId}:${networkingLibrary.version}"
  </#if>
  </#if>

  // Additional Libraries
  <#list additionalLibraries as additionalLibrary>
  <#if additionalLibrary.isCoreJmeLibrary == true>
  implementation "${additionalLibrary.groupId}:${additionalLibrary.artifactId}:$jmeVer"
  <#else>
  implementation "${additionalLibrary.groupId}:${additionalLibrary.artifactId}:${additionalLibrary.version}"
  </#if>
  </#list>

  // Assets sub-project
  runtimeOnly project(':assets')
}

jar {
    manifest {
        attributes 'Main-Class': "$mainClassName"
    }
}