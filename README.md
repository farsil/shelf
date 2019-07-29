# Shelf  
[![Build Status](https://travis-ci.com/farsil/shelf.svg?branch=master)](https://travis-ci.com/farsil/shelf)

`shelf` is a java utility library which aims to complement modern versions of 
the java standard libraries. It features easy-to-use classes that either 
function as drop-in replacements that perform better than the original, or 
fill some uncovered areas of the standard libraries. Its only requirement is 
Java 1.8 or greater, and has no dependencies.

# Content
Given the early stage of this library, it only provides two main features:
- Throwing functional interfaces, which are alternatives to the interfaces 
found in `java.util.function` that allow an `Exception` to be thrown.
- The class `Try<T>`, a generalization of `java.util.Optional<T>` which is able
to handle any arbitrary failure.

# Maven
`shelf` is published in maven central. To use it, include the following 
dependency in your `pom.xml`.
```
<dependency>
	<groupId>eu.farsil.shelf</groupId>
	<artifactId>shelf</artifactId>
	<version>0.1.0</version>
<\dependency>
``` 
`shelf` adheres to the guidelines provided by
[semantic versioning](https://semver.org/). All non-major version changes are
 backwards-compatible.
 
# Documentation
The documentation for this library is published on
[javadoc.io](https://www.javadoc.io/doc/eu.farsil.commons/commons).


