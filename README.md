# metagen

> Generates meta-data information about a jar file in an XML file format.

##Goal
This small java program generates meta-data information about a jar file. It's also compatible with war and ear files. The information is stored in an XML file that will be created in the same folder where the jar file is located, and the XML file will have the jar file name followed by the .xml extension.

The XML contains the jar file name, its MD5 check sum and information extracted from the jar's MANIFEST file.

In a nutshell, run this program passing as argument the list of jar files:

```shell
java -jar metagen file1.jar file2.jar file3.jar
```
This command will create 3 xml files: `file1.jar.xml`, `file2.jar.xml` and `file3.jar.xml`, preserving the path declared in the jar files. The XML file will look like the one below:

```
<package>
  <build-number>3700</build-number>
  <file-name>file1.jar</file-name>
  <MD5>7f0b072a5f5967c2ba4d413df035c4d7</MD5>
  <build-date>2014-09-12_01:16</build-date>
  <version>2.1.0</version>
</package>
```
Where:

- **file-name** is the jar file name (path information is removed);
- **build-number**  is the `Build-Number` property value stored in META-INF\MANIFEST.MF;
- **build-date** is the `Build-Date` property value stored in META-INF\MANIFEST.MF;
- **version** is the `Implementation-Version` property value sored in META-INF\MANIFEST.MF;
- **MD5** is the MD5 hash of the jar file content;

## Motivation
This program is part of my build system. Whenever a new version of my application is released, metagen generates an XML with information about the new version released, making it easier for third party programs to track it.