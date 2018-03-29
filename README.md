# Subluminal /ˌsʌbˈl(j)uːmɪnl/ - The Game

> Once upon a time a clever programmer named Bob created in the basement of his mom's house 4 different AIs. Throughout his whole life Bob tried to get the AIs to work together, but sadly he never achived his this goal. After Bob passed away the world forgot about him and his work. But the AI's never forgot, ever single one of them evolve and adopted, with the goal to be the best AI in the whole universe ...  

Meaning of game title: [Oxforddictionaries](https://en.oxforddictionaries.com/definition/us/subluminal), [Wiktionary](https://en.wiktionary.org/wiki/subluminal), [Wikipedia](https://en.wikipedia.org/wiki/Faster-than-light)

[![Logo](./assets/logo/subluminal_logo.png)](http://subluminal.tech) **by Bordeaux Ink.**

**Table of Contents**
[1. Overview](##Overview) 
[2. Getting Started](##Getting-Started)
[3. Game Concept](##Game-Concept)
[4. License](##License)
[5. Webpage](##Webpage)


## 1. Overview
### Basic Usage:
Download the current jar bundle (Subluminal-0.2.0-m2.jar) or clone the repo and run ``gradle build`` (output in ``./app/build/libs/``).  Start the game with the following command:  
```sh
# First start a server.
java -jar Subluminal-0.2.0-m2.jar server <port>
# Then start the clients.
java -jar Subluminal-0.2.0-m2.jar client <hostaddress>:<port> [<username>]
```
On the client you can use ***/changename*** to change your username and ***/logout*** to exit the client.


## 2. Getting Started
1. Generate a pair of SSH keys and add them to your Gitlab account (follow [these instructions](https://git.scicore.unibas.ch/help/ssh/README#generating-a-new-ssh-key-pair) or run  
``ssh-keygen -t rsa -C "your.email@stud.unibas.ch" -b 4096``)
3. Clone the repo to your harddrive ``git clone https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11.git``  
4. cd into the project directory and try to build the project with ``./gradlew build`` (on Unix) or ``gradlew.bat build`` (on Windows).
5. A more specific document on code quality assurance is located in [docs/quality_assurance.md](docs/quality_assurance.md) and [CONTRIBUTING.md](CONTRIBUTING.md).


## 3. Game Concept
Youc an find a detailed description on how the game is played, in [docs/game_rules.md](docs/game_rules.md) folder.  

![Mockup Image 4](./assets/mockup/ui_4.png)
![Mockup Image 1](./assets/mockup/ui_1.jpg)  


## 4. License
![GNU AGPL Logo](assets/other/AGPLv3_Logo.png)  
**GNU AGPLv3**. [https://choosealicense.com/licenses/agpl-3.0/]()


## 5. Web
[subluminal.tech](http://subluminal.tech)  /   [merch.subluminal.tech](http://merch.subluminal.tech)  /   [buy.subluminal.tech](http://buy.subluminal.tech)