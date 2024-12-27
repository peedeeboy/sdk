# jMonkeyEngine Software Development Kit (SDK) [![Build Status](https://github.com/jMonkeyEngine/sdk/actions/workflows/gradle.yml/badge.svg)](../../actions)

Welcome to the repository of the jMonkeyEngine Software Development Kit (SDK).
This SDK simplifies developing 3D Applications with the jMonkeyEngine (jME). It contains everything ever needed to develop a full application.  
  
Our SDK (or jmonkeyplatform) is based on the Netbeans IDE but includes additional features like:
-  A Scene Editor (SceneExplorer: Preview your scene, adjust all positions, add `Controls` and `AppStates` (WYSIWYG) and then just save the scene)

-  A Material Editor

-  A Filter Editor (combine multiple filters and just load them in-game)

-  Bundled JDK for each platform (so you don't need to install java first)

It is important to know that the SDK is not coupled with the engine itself, so if you have issues which are unrelated to the IDE, report them [here](https://github.com/jMonkeyEngine/jmonkeyengine).
This however also means, that the SDK can have a different pace than the engine but you can nonetheless work on a different engine version than the one which is bundled. For your own projects, you should consider using Gradle build system which also gives you flexibility on choosing any jMonkeyEngine version. More on this later.

## Getting Started / Downloading the SDK
Just have a look at our [releases](https://github.com/jMonkeyEngine/sdk/releases) section.
There you can download the version you desire. The SDK will generally follow the convention that it's version number is lined up with the matching engine version
plus some suffixes for different SDK releases on the same engine version.

There are multiple files from which you can choose:  
You can take the platform agnostic `jmonkeyplatform.zip` which contains the full SDK able to be run on "any" (64-bit) platform (Windows, Mac OS, Linux) however it lacks the JDK.  
Thus the prefered download is `jmonkeyplatform-windows-x64.exe` which essentially __is__ `jmonkeyplatform.zip` and the correct version of the JDK.

After the SDK is up and running. A good starting point is to look at the jME examples. Under *File | New project | JME3 Tests* you can create a new project, populated with the jME build in examples. You can freely mess around with these and try out stuff. Your changes can always be reverted by simply creating a new *JME3 Tests* project. Once you are all comfortable and ready to embark on your own exciting journey, *File | New project | Basic game (with Gradle)* is the recommended starting point.

## Building the SDK
### Requirements
* JDK21

### Process
The jMonkeyEngine SDK is a Netbeans Platform Project, that uses Maven with the [Netbeans Module Plugin](https://bits.netbeans.org/mavenutilities/nbm-maven-plugin/nbm-maven-plugin/index.html) as its build system.
The Netbeans Module Plugin handles downloading and configuring the Netbeans Harness used as the base for the SDK.

After cloning this repo, install the Maven dependencies and compile the application with:

```shell
./mvnw install
```

Then create the Netbeans cluster in the Application sub-project with:
```shell
cd application
../mvnw nbm:cluster-app
```

Finally, still in the `/application` folder, run the following to stand up the Netbeans harness with the currently compiled SDK code:
```shell
../mvnw nbm:run-platform
```

### Packaging the installers
TODO


## Developing
The jMonkeyEngine SDK can be developed in any IDE that supports the Maven build tool, e.g. IntelliJ IDEA or Netbeans.  

Given the SDK is a Netbeans Platform Application, some additional helpful tooling is available when developing in Netbeans.

### Local Dependencies
Local dependencies are stored in the `/lib` folder.  To import dependencies use the Maven `install-file` goal:

```shell
./mvnw install:install-file -Dfile=/path/to/libary/somelibrary.jar -DgroupId=com.someorganisation -DartifactId=somelibrary \
 -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=./lib -DcreateChecksum=true
```

Add the `/lib` local repository to the `pom.xml` of the Module:
```xml
<repositories>
    <repository>
        <id>lib</id>
        <name>Local Maven Repo</name>
        <url>file://${maven.multiModuleProjectDirectory}/lib</url>
    </repository>
</repositories>
```

Then declare a Maven dependency as usual.

### Implementation dependencies
To declare a dependency on a Netbeans API without public access (to get around the `Module dependency has friend 
dependency on org.netbeans.xxxx but is not listed as friend`, declare an 'Implementation Dependency' in the 
`<configuration>` of the Netbeans plugin, e.g.

```xml
<configuration>
    <moduleDependencies>
        <dependency>
            <id>org.netbeans.modules:org-netbeans-modules-editor-settings-storage</id>
            <type>impl</type>
        </dependency>
    </moduleDependencies>
</configuration>
```

### Netbeans Module Plugin help
Run the following command to get a list of configuration options for the Netbeans Module Plugin:

```shell
./mvnw help:describe -Dplugin=org.apache.netbeans.utilities:nbm-maven-plugin -Ddetail
```

### Updating Netbeans
To update the SDK to run on a newer version of Netbeans, update the `<netbeans.version>` property in the root `pom.xml`:

`<netbeans.version>RELEASE240</netbeans.version>`

This property should be referenced whenever declaring a dependency on a Netbeans Platform API, e.g. `${netbeans.version}` 
so that an upgrade only involves updating this one property.

### Updating jMonkeyEngine
To update the underlying version of jMonkeyEngine powering the SDK, update the `<jmonkeyengine.version>` property in the
root `pom.xml`:

`<jmonkeyengine.version>3.7.0</jmonkeyengine.version>`

This property is used in the `jme3-core-baselibs` project to download the jMonkeyEngine libraries.  Modules that need
jMonkeyEngine libraries should declare a dependency on the `jme3-core-baselibs` module.

## Contributing
First of all, I suggest you to take a look at [docs/](https://github.com/jMonkeyEngine/sdk/tree/master/docs). Those docs are a loose collection of things I came across during development, but they prevent you from re-doing the same experiences. 
Other than that, `Netbeans Platform` is your google keyword for any NB related issues.
Basically the only tricky thing is how we handle custom entries in the SceneExplorer. This is called the Netbeans Nodes API and is somewhat unintuitive.
Just take a look at the `Motion Event Pull Request`, which should've been added around `March 2016`, there you can see what was needed to add MotionEvents to the SDK.

Other than that, we are more than happy to help, even if your addition is incomplete. Make sure you use the Netbeans formatting, obvious variable naming and commented and especially documented code, though.
If you think you've encountered a bug in the SDK, please open an issue to let the developers know or post [on the hub](https://hub.jmonkeyengine.org) using the `Troubleshooting | jmonkeyplatform` category.

## Known Issues / To Do
* The `jme3-documentation` module has an Ant task to try and download the current Wiki.
It probably hasn't worked for a long time. It might be possible to get it working with the Maven
Ant Run plugin, but there might be a better way to fix it.
* The Application Test doesn't work (and is currently commented out) as it needs the JVM paramaters
to open JVM modules.  Need to fix or refactor the SDK not to use non-public Java APIs.