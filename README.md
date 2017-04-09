# Java to UML Diagram Parser

### Introduction

This repository is personal project for CMPE202 at San Jose State Univeristy lecturered by [Paul Nguyen](https://github.com/paulnguyen). Two kinds of parser are built in this project. Both parsers take Java Source Code as input and generate following diagrams:

- UML Class Diagrams
- UML Sequence Diagrams

Following document was copied from lecturer's project description.

### Requirements

Things to include:

- Default Package: All Java source files will be in the "default" package. That is, there will be only one directory (i.e. no subdirectories) 
- Dependencies & Uses Relationships for Interfaces Only: Do not include dependencies in output UML diagram except in the cases of "interfaces/uses"
- Class Declarations with optional Extends and Implements: Make sure to include proper notation for inheritance and interface implementations.
- Only Include Public Methods (ignore private, package and protected scope)
- Only Include Private and Public Attributes (ignore package and protected scope)
- Java Setters and Getters: Must Support also Java Style Public Attributes as "setters and getters"
- Must Include Types for Attributes, Parameters and Return types on Methods
- Classifier vs Attributes Compartment: If there is a Java source file, then there should be a "UML Class" on the Diagram for it. That is, if there is no Java source file for a class and the class is part of an instance variable, put the class/property in the attribute compartment
- Interfaces - Implements and Uses Notation: Show Interfaces along with Clients of Interfaces (as dependencies).

Things to exclude:

- Static and Abstract Notation: Static and Abstract notation in UML are usually denoted as "underline" and "italic", but rarely used in practice. Parsing this is not a requirement for this project.
- Relationships Between Interfaces: Although conceptually possible in UML, relationships between Interfaces (i.e. inheritance, dependencies) are rarely thought of in practice and generally bad practice. As such, this project does not expect parser to detect these situations. Focus your work on the relationships and dependencies from Classes to Interfaces.

### Tool Used:

- [JavaParser](http://javaparser.org): Transforming Java Source Code into Abstract Syntax Tree

- [UML Generator](http://plantuml.com): Take string as input and generates UML diagrams in png format

### Test Cases and Results

There are four test cases for UML Class Diagrams and one for UML Sequence Diagrams. If you are interested in looking at details of them, you can go to [resources folder](https://github.com/kanghuawu/cmpe202-personal-project/tree/master/java-uml-parser/src/main/resources). You can also briefly look at what diagrams are created:

Test case 1:

![alt text](https://github.com/kanghuawu/cmpe202-personal-project/blob/master/java-uml-parser/src/main/resources/uml-parser-test-1.png "Test case 1")

Test case 2:

![alt text](https://github.com/kanghuawu/cmpe202-personal-project/blob/master/java-uml-parser/src/main/resources/uml-parser-test-2.png "Test case 2")

Test case 3:

![alt text](https://github.com/kanghuawu/cmpe202-personal-project/blob/master/java-uml-parser/src/main/resources/uml-parser-test-3.png "Test case 3")

Test case 4:

![alt text](https://github.com/kanghuawu/cmpe202-personal-project/blob/master/java-uml-parser/src/main/resources/uml-parser-test-4.png "Test case 4")

Test case 5:

![alt text](https://github.com/kanghuawu/cmpe202-personal-project/blob/master/java-uml-parser/src/main/resources/uml-parser-test-5.png "Test case 5")