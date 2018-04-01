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
This project uses a variety of external libraries. The following list is divided into groups with libraries of the same purpose. We compare the pros and cons amongst them and decide on which ones to use (final decision marked in bold).

## 2.1 Logging
| Name           | Version  | Pros                                                                                                       | Cons      |
|:---------------|:---------|:-----------------------------------------------------------------------------------------------------------|:----------|
| [tinylog][8]   | >=1.3.4  | Latest Release: February 23rd, 2018, easy to use (static class), console- and file writer, no dependencies | moon moon |
| [**Log4J**][9] | >=2.11.0 | Latest Release: March 30th, 2018, moon moon                                                                | moon moon |
| [Logback][10]  | >=1.2.3  | Latest Release: March 30th, 2017, moon moon                                                                | moon moon |

## 2.2 Command line parsing
| Name              | Version         | Pros | Cons |
|:------------------|:----------------|:-----|:-----|
| [args4j][15]      | >=2.33          |      |      |
| [argparse4j][11]  | >=0.8.1         |      |      |
| [airline][12]     | >=0.8           |      |      |
| [Commons CLI][13] | >=1.4           |      |      |
| [**picoli**][14]  | >=3.0.0-Alpha-3 |      |      |
| [jopt-simple][16] | >=4.9           |      |      |
| [jcommander][17]  | >=1.71          |      |      |


## 2.3 Settings store
| Name                           | Version | Pros                                  | Cons                                             |
|:-------------------------------|:--------|:--------------------------------------|:-------------------------------------------------|
| [**java.util.Properties**][18] | -       | Built in, key/value store, plain text | Can be slow for large amounts of key/value pairs |



## 2.4 Unit tests
| Name              | Version | Pros                                              | Cons |
|:------------------|:--------|:--------------------------------------------------|:-----|
| [**junit**][19]   | =4.12   | Widely used, big community, recommended by tutors |      |
| [**mockito**][20] | =2.17.5 |                                                   |      |
| [TestNG][24]      |         |                                                   |      |
| [Arquillan][25]   |         |                                                   |      |

## 2.5 Benchmark
| Name            | Version | Pros | Cons |
|:----------------|:--------|:-----|:-----|
| [caliper][21]   |         |      |      |
| [spf4j][22]     |         |      |      |
| [javasimon][23] |         |      |      |


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