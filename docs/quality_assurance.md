# Quality assurance concept

## Basics
1. Branching model (git-flow)
2. Issues and merge requests
3. Checkstyle / Unittests
[Google CodeStyle](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml) with check task via gradle and in IntelliJ.  
Unit tests for java code with JUnit and Mockito.
4. Code Metrics
5. Manuel testing
Functionality is randomly tested by hand (play tests), i.a. on different physical machines.
6. Automatic testing  
  6.1 Continous integration  
  6.2  Continous deployment  
  via Docker ([https://hub.docker.com/u/subluminalthegame/](https://hub.docker.com/u/subluminalthegame/))


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
