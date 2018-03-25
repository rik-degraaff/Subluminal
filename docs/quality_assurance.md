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
9. We code by Google Java code style conventions, which are checked by the automated gradle style checker.


## Merge requirements


| Branch          |                                       |
|:----------------|---------------------------------------|
| ``master``    : | Checkstyle ✔️, UnitTest ✔️, Build ✔️ |
| ``dev``       : | Checkstyle ✔️, UnitTest ✔️, Build ⭕   |
| ``ft-branch`` : | Checkstyle ✔, UnitTest ⭕, Build ⭕ |


## Build process
//TODOS


## Javadocs
//TODOS


## UML diagrams
//TODOS