jcamera
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jcamera/com.io7m.jcamera.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jcamera%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/com.io7m.jcamera/com.io7m.jcamera?server=https%3A%2F%2Fs01.oss.sonatype.org&style=flat-square)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/io7m/jcamera/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/jcamera.svg?style=flat-square)](https://codecov.io/gh/io7m-com/jcamera)

![com.io7m.jcamera](./src/site/resources/jcamera.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcamera/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/jcamera/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcamera/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/jcamera/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcamera/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/jcamera/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcamera/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/jcamera/actions?query=workflow%3Amain.windows.temurin.lts)|

## jcamera

The `jcamera` package provides a rendering-system-independent camera 
implementation.

## Features

* Rendering and input system independent camera implementations.
* Smooth first-person camera.
* Smooth spherical third-person camera.
* Fully documented with all mathematical derivations given, tutorials, and Javadoc.
* Implemented in pure Java 21 with no dependencies on any particular rendering system.
* Example renderer using JOGL and Swing.
* [OSGi-ready](https://www.osgi.org/)
* [JPMS-ready](https://en.wikipedia.org/wiki/Java_Platform_Module_System)
* ISC license.

## Usage

See the [user manual](https://www.io7m.com/software/jcamera).

![com.io7m.jcamera](./src/site/resources/cameralab.png?raw=true)

