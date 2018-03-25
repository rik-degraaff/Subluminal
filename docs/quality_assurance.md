# Quality assurance (code)

## Basics
1. Branch structure: ``master`` ist only used for releases and milestone commits. ``dev`` is used for shared development, but not pushed to directly. Feature branches are used to make code/repo changes. Then later a merge-request on gitlab is used to integrate the changes into the parent branch.  
2. The nomenclature for working branches is defined in CONTRIBUTING.md.
3. Merge request is assigned to other team member for sanity check.43. Unit tests for java code with JUnit and Mockito.
4. [Google CodeStyle](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml) with check task via gradle and in IntelliJ.
5. Continuous integration via gitlab.
6. Assignable issues in gitlab for specific to do's.
7. Javadoc comments are not created for private functions.
8. Functionality is also randomly tested by hand, i.a. on different physical machines.

## Quality characteristics
### 1. Efficiency / Latency
To ensure a fluid gaming experience, the average roundtrip time of a package from client to server should be no more than 150 ms. This can be tracked with an internal timer and a simple logging statement. Also relevant for this metric could be the effective package size. WireShark will be used to determine the average package size in a test environment.

### 2. Reliability / Error tolerance
The software is rigorously tested with unit tests to cover all edge cases. As a metric we use the code coverage percentage ("degree to which the source code of a program is executed when a particular test suite runs". [Wikipedia](https://en.wikipedia.org/w/index.php?title=Code_coverage&oldid=831669504)). Initial goal shall be to achieve a coverage of around 50 %.

## Merge requirements

| Branch          |                                               |
|:----------------|-----------------------------------------------|
| ``master``    : | Checkstyle ✔️, UnitTest ✔️, Build ✔️, JavaDoc ✔️  |
| ``rl-branch`` : | Checkstyle ✔, UnitTest ✔️, Build ✔️, JavaDoc ✔️  |
| ``dev``       : | Checkstyle ✔️, UnitTest ✔️, Build ✔️, JavaDoc ⭕️ |
| ``ft-branch`` : | Checkstyle ✔, UnitTest ✔, Build ⭕, JavaDoc ⭕ |


## Build process
//TODO


## Javadocs
//TODO
