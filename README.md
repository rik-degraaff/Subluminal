[![pipeline status](https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11/badges/master/pipeline.svg)](https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11/master)
[![coverage report](https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11/badges/master/coverage.svg)](https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11/master)
[![current version](https://img.shields.io/badge/version-4.0.0-blue.svg)](https://subluminal.tech/releases)
[![javadoc coverage](https://img.shields.io/badge/JavaDoc-74.40%25-blue.svg)](https://subluminal.tech/docs)
[![MicroBadger Size (tag)](https://img.shields.io/microbadger/image-size/subluminalthegame/subluminal/latest.svg)](https://hub.docker.com/r/subluminalthegame/subluminal/tags/)
[![Twitter Follow](https://img.shields.io/twitter/follow/subluminalgame.svg?style=social&label=Follow)](https://twitter.com/subluminalgame)  

**Table of Contents**  
[1. Overview](#1-overview)  
[2. Getting Started](#2-getting-started)  
[3. Trailer & Screenshots](#3-trailer-screenshots)  
[4. How to play](#4-how-to-play)  
[5. Modding](#5-modding)  
[6. License](#6-license)  
[7. Web](#7-web)

# [Subluminal](https://en.wiktionary.org/wiki/subluminal) /ˌsʌbˈl(j)uːmɪnl/ - The Game
[![Logo](./assets/logo/subluminal_logo.png)](http://subluminal.tech) **by Bordeaux Ink.**

> Once upon a time a clever programmer named Bob created in the basement of his mom's house 4 different AIs. Throughout his whole life Bob tried to get the AIs to work together, but sadly he never achieved his this goal. After Bob passed away the world forgot about him and his work. But the AI's never forgot, ever single one of them evolve and adopted, with the goal to be the best AI in the whole universe ...  


## 1. Overview
### Basic Usage:
Download the current jar bundle (Subluminal-4.0.0.jar) or clone the repo and run ``gradle build-cs108`` (output in ``./app/build/libs/``).  Start the game with the following command:  
``` sh
# First start a server.
$ java -jar Subluminal-4.0.0.jar server <port>
# Then start the clients.
$ java -jar Subluminal-4.0.0.jar client <hostaddress>:<port> [<username>]
```

Our application supports a number of command line arguments. Use ``--help`` or ``-h`` to get the usage information as shown below:
```
$ java -jar app/build/libs/Subluminal-4.0.0.jar --help

Welcome to
  _____       _     _                 _             _
 / ____|     | |   | |               (_)           | |
| (___  _   _| |__ | |_   _ _ __ ___  _ _ __   __ _| |
 \___ \| | | | '_ \| | | | | '_ ` _ \| | '_ \ / _` | |
 ____) | |_| | |_) | | |_| | | | | | | | | | | (_| | |
|_____/ \__,_|_.__/|_|\__,_|_| |_| |_|_|_| |_|\__,_|_|


Usage: Subluminal [-dh] [-lf=<logfile>] [-ll=<loglevel>] <mode>
                  [<hostAndOrPort>] [<username>]
Starts the game in server or client mode.
      <mode>                  Sets the application mode. Must be one of
                                "server, client".
      [<hostAndOrPort>]       Specifies the connection details.In case of
                                server this needs to be a "port". In case of
                                client this needs to be a "host:port".
                                Default: 164.132.199.58:1729
      [<username>]            Sets the username. If none is specified the
                                system username will be used instead.
                                Default: lucku
  -d, --debug                 Enables the debug mode.
      -lf, --logfile=<logfile>
                              Sets the path and filename for the logfile
                                Default: log.txt
      -ll, --loglevel=<loglevel>
                              Sets the loglevel for the application.
                                Default: off
  -h, --help                  Display help/usage.
```

### Quicklinks for professors / assistants / tutors:

| Document         | Filename             | md                                    | pdf                                     | Description                                        |
| ---------------- | -------------------- | ------------------------------------- | --------------------------------------- | -------------------------------------------------- |
| Readme           | README.md            | [``<md>``](README.md)                 | [``<pdf>``](README.pdf)                 | Quickstart with lots of useful information         |
| Changelog        | CHANGELOG.md         | [``<md>``](CHANGELOG.md)              | [``<pdf>``](CHANGELOG.pdf)              | Automaticly generated changelog (based on commits) |
| Diary            | project_diary.md     | [``<md>``](docs/project_diary.md)     | [``<pdf>``](docs/project_diary.pdf)     | Project diary daily/weekly basis                   |
| QA concept       | quality_assurance.md | [``<md>``](docs/quality_assurance.md) | [``<pdf>``](docs/quality_assurance.pdf) | Quality assurance concept for the project          |
| Network protocol | network_protocol.md  | [``<md>``](docs/network_protocol.md)  | [``<pdf>``](docs/network_protocol.pdf)  | Network protocol specification                     |
| Game rules       | game_rules.md        | [``<md>``](docs/game_rules.md)        | [``<pdf>``](docs/game_rules.pdf)        | Short list with all game rules                     |
| Manual           | manual.pdf           | -                                     | [``<pdf>``](docs/manual/manual.pdf)     | Game manual                                        |

### Generated docs / reports:
These documents will only appear once the project has been built with gradle.

| Document         | Path                                                                 | Description                     |
| ---------------- | -------------------------------------------------------------------- | ------------------------------- |
| JavaDoc          | [docs/javadoc/](docs/javadoc/index.html)                             | Javadoc Webpage                 |
| JavaDocCoverage  | [docs/javadoc](docs/javadoc/javadoc-coverage.html)                   | Javadoc Coverage Report Webpage |
| Test Report      | [app/build/reports/test/](app/build/reports/test/index.html)         | JUnit Test Report               |
| Test Coverage    | [app/build/reports/coverage/](app/build/reports/coverage/index.html) | Test Coverage Report            |

## 2. Getting Started
1. Clone the repo to your hard drive ``git clone https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11.git``  
2. cd into the project directory and try to build the project with ``./gradlew build`` (on Unix) or ``gradlew.bat build`` (on Windows).
3. Start writing code ...
3.1 A more specific document on how to contribute is located in [docs/quality_assurance.md](docs/quality_assurance.md) and [CONTRIBUTING.md](CONTRIBUTING.md).

## 3. Trailer & Screenshots
> Insert Youtube trailer here  

You can find more images in the [./assets/screenshots/](assets/screenshots/) folder.  
![Menu](./assets/screenshots/menu-1.png)
![Ingame](./assets/screenshots/game-3.png)


## 4. How to play

> Insert Youtube video here ...


## 5. Modding
There is basic support for mods. Read the docs for more info ... [./docs/modding.md](./docs/modding.md). Additionally you can start the server in debug mode with the ``--debug`` flag. This will export the internal constants to a property file called ``settings.properties``. Now you can change and tweak the game settings to your liking. On the next launch of the server, the properties file is read back into the server. See [assets/settings/settings.properties](assets/settings/settings.properties) for example.


## 6. License
**GNU AGPLv3**. [https://choosealicense.com/licenses/agpl-3.0/]()


## 7. Web
[subluminal.tech](http://subluminal.tech)  /   [merch.subluminal.tech](http://merch.subluminal.tech)  /   [buy.subluminal.tech](http://buy.subluminal.tech)