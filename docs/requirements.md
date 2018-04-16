**Table of Contents**  
[**1.** Software requirements](#1-software-requirements)  
[1.1 Coding](#1-1-coding)  
[1.2 Graphics & Audio](#1-2-graphics---audio)  
[1.3 Other](#1-3-other)  
[**2.** Libraries](#2-libraries)  
[2.1 Logging](#2-1-logging)  
[2.2 Command line parsing]()  
[2.3 Settings store]()  
[2.4 Unit tests]()  
[2.5 Benchmark]()  


# 1. Software requirements

## 1.1 Coding
| Application                      | Version     | Download                                                                                      |
|:---------------------------------|:------------|:----------------------------------------------------------------------------------------------|
| Java SE Development Kit          | >=1.8.0_144 | [``https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html``][1] |
| JetBrains IntelliJ IDEA Ultimate | >=2017.3.4  | [``https://www.jetbrains.com/idea/``][2]                                                      |
| Git                              | >=2.16.2    | [``https://git-scm.com/download/win/``][3]                                                    |
| Git-lfs                          | >=2.3.4     | [``https://git-lfs.github.com/``][4]                                                          |
| Git-flow                         | ?           | ``via GitKraken``                                                                             |
| GitKraken                        | >=3.5.0     | [``https://www.gitkraken.com/download/``][5]                                                  |
| Gradle                           | =4.6.0      | ``Download via gradle wrapper task``                                                          |

## 1.2 Graphics & Audio
| Application | Version | Download          |
|:------------|:--------|:------------------|
| Photoshop   | =CC2018 | ``via Adobe``     |
| Illustrator | =CC2018 | ``via Adobe``     |
| Visio       | =2016   | ``via Microsoft`` |

## 1.3 Other
| Application  | Version | Download                                            |
|:-------------|:--------|:----------------------------------------------------|
| GanttProject | >=2.8.6 | [``https://www.ganttproject.biz/download/free``][6] |
| PowerPoint   | =2016   | ``via Microsoft``                                   |
| WireShark    | =2.4.5  | [``https://www.wireshark.org/#download``][7]        |

# 2. Libraries
This project uses a variety of external libraries. The following list is divided into groups with libraries of the same purpose. We compare the pros and cons amongst them and decide on which ones to use (final decision marked in **bold**).

## 2.1 Logging
We wanted a small logging library that could write to console and a file. Tinylog allows us to do that with an extremely small footprint. It support 5 commons log levels and configuration at runtime.

| Name             | Version | Pros                                                                  | Cons                                                             |
|:-----------------|:--------|:----------------------------------------------------------------------|:-----------------------------------------------------------------|
| [**tinylog**][8] | >=1.3.4 | easy to use (static class), console- and file writer, no dependencies | no support for tags                                              |
| [**Log4J 2**][9] | -       | Huge functionality, solid documentation, support for filters          | too much overhead (we only need simple logging)                  |
| [Logback][10]    | -       | Successor of log4j,                                                   | Based on Java7, not as knows as Log4J 2 also bigger than tinylog |


## 2.2 Command line parsing
Initially we parsed the command line argument as strings by hand. The nice part about picocli is, we can annotate any field in the main class and it will become a command line parameter or option. This is very handy for quickly added new ones. As a bonus, the "--help" command is generated automatically with the provided field description.

| Name             | Version         | Pros                                                                        | Cons                                                                                        |
|:-----------------|:----------------|:----------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------|
| [args4j][15]     | -               | Relatively simple to setup, html doc output                                 | Developed by individual, last release more than two years ago, no multiple name for options |
| [airline][12]    | -               |                                                                             | Focused on multi command applications                                                       |
| [**picoli**][14] | >=3.0.0-Alpha-3 | Automatic usage information, simple annotations, optional parameter support | Creates instance of class to parse arguments                                                |
| [jcommander][17] | -               | Similar functionality to picocli (contender)                                | Less documentation then picocli                                                             |


## 2.3 Settings store
Usage: client/server settings, highscore, reconnect.  
Settings: Using a textfile based configuration to overwrite default values (no need for recompiling).
Highscore: Save server highscores in a file, read it on server start.
Reconnect: When connecting to a server, write userID and server ip in a textfile in a temporary location. If game crashes file will still be there and client can reconnect automatically. On normal shutdown, file will be deleted.

| Name                           | Version | Pros                                  | Cons                                             |
|:-------------------------------|:--------|:--------------------------------------|:-------------------------------------------------|
| [**java.util.Properties**][18] | -       | Built in, key/value store, plain text | Can be slow for large amounts of key/value pairs |
| No alternatives found/needed   | -       | -                                     | -                                                |



## 2.4 Unit tests
Unit tests are part of our quality assurance concept. JUnit allows us to test all our core components for integrity and make sure the application runs stable.

| Name              | Version | Pros                                                              | Cons                                                     |
|:------------------|:--------|:------------------------------------------------------------------|:---------------------------------------------------------|
| [**junit**][19]   | =4.12   | Widely used, big community, required/recommended by tutors        | has dependency on                                        |
| [**mockito**][20] | =2.17.5 | Used in conjunction with junit, easy mock classes from interfaces | more overhead, than only unittests - in the end worth it |
| [TestNG][24]      | -       | No contender, junit is a project requirement                      |                                                          |
| [Arquillan][25]   | -       | No contender, junit is a project requirement                      |                                                          |

## 2.5 Benchmark
//FOR FUTURE USE

| Name            | Version     | Pros                                                                                                   | Cons                                                                     |
|:----------------|:------------|:-------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------|
| [caliper][21]   | =1.0-beta-2 | Recommended by Research Assistant, developed by Google, simple annotation of function to get benchmark |                                                                          |
| [spf4j][22]     |             | Powerful performance testing framework                                                                 | Has too many features and is harder to setup                             |
| [javasimon][23] |             | Very simple framework                                                                                  | Has only 2 functions, stopwatch needs to be started and stopped manually |


[1]: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[2]: https://www.jetbrains.com/idea/
[3]: https://git-scm.com/download/win
[4]: https://git-lfs.github.com/
[5]: https://www.gitkraken.com/download
[6]: https://www.ganttproject.biz/download/free
[7]: https://www.wireshark.org/#download
[8]: http://www.tinylog.org/
[9]: https://logging.apache.org/log4j/2.x/
[10]: https://logback.qos.ch/
[11]: https://github.com/argparse4j/argparse4j
[12]: https://github.com/airlift/airline
[13]: https://commons.apache.org/proper/commons-cli/
[14]: http://picocli.info/
[15]: https://github.com/kohsuke/args4j
[16]: https://pholser.github.io/jopt-simple/
[17]: https://github.com/cbeust/jcommander
[18]: https://docs.oracle.com/javase/8/docs/api/java/util/Properties.html
[19]: https://junit.org/junit4/
[20]: http://site.mockito.org/
[21]: https://github.com/google/caliper
[22]: http://www.spf4j.org/
[23]: https://github.com/virgo47/javasimon
[24]: http://testng.org/doc/
[25]: http://arquillian.org/