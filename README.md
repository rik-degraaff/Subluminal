![build](https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11/badges/dev/build.svg) ![build](https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11/badges/dev/coverage.svg)
# Subluminal /ˌsʌbˈl(j)uːmɪnl/ - The Game

> Once upon a time a clever programmer named Bob created in the basement of his mom's house 4 different AIs. Throughout his whole life Bob tried to get the AIs to work together, but sadly he never achived his this goal. After Bob passed away the world forgot about him and his work. But the AI's never forgot, ever single one of them evolve and adopted, with the goal to be the best AI in the whole universe ...  

Meaning of game title: [Oxforddictionaries](https://en.oxforddictionaries.com/definition/us/subluminal), [Wiktionary](https://en.wiktionary.org/wiki/subluminal), [Wikipedia](https://en.wikipedia.org/wiki/Faster-than-light)

[![Logo](./assets/logo/subluminal_logo.png)](http://subluminal.tech) **by Bordeaux Ink.**

| Table of Contents                             |
|:----------------------------------------------|
| [1. Overview](#Overview)                      | 
| [2. Getting Started](#Getting-Started)        |
| [3. Contributing](#Contributing)              |
| [4. Game Concept](#Game-Concept)              |
| [5. Useful Resources/Tutorials](#useful-resourcestutorials) |
| [6. License](#License)                        |
| [7. Webpage](#Webpage)                        |


## 1. Overview
### Basic Usage:
Download the current jar bundle (Subluminal-0.2.0-m2.jar) or clone the repo and run ``gradle build`` (output in ``./app/build/libs/``).  Start the game with the following command:
```sh
# Start server.
java -jar Subluminal-0.2.0-m2.jar client <hostaddress>:<port> [<username>]
# Start client.
java -jar Subluminal-0.2.0-m2.jar server <port>
```
On the client you can use */changename* to change your username and */logout* to exit the client.

## 2. Getting Started
1. Generate a pair of SSH keys and add them to your Gitlab account (follow [these instructions](https://git.scicore.unibas.ch/help/ssh/README#generating-a-new-ssh-key-pair) or run  
``ssh-keygen -t rsa -C "your.email@stud.unibas.ch" -b 4096``)
2. Configure your local git installation by entering the following commands into your command line:
(Careful: Don't use the ``--global`` flag if you have an existing git installation. Change into the project directory and run the commands **without** ``--global``.)
```sh
$ git config --global user.name "User Name - shortname00"
$ git config --global user.email "your.email@stud.unibas.ch"
$ git config --global core.autocrlf true
```
3. Clone the repo to your harddrive ``git clone https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11.git``  
4. cd into the project directory and try to build the project with ``./gradlew build`` (on Unix) or ``gradlew.bat build`` (on Windows).


## 3. Contributing
1. Make sure to checkout the most recent ``dev`` branch.
2. Create a new branch to commit your changes. Your branch names should adhere to the following conventions:  
2.1. Start with a branch type descriptor.  
2.2. Contain your branch name in snake case (word separated by underscores).  
2.3. Contain your name token.  
3. To push your commits to gitlab via Git-Bash, you need to perform the following steps:
```sh
--> via ssh: Connect Cisco VPN (ssh key based auth)
$ eval $(ssh-agent -s)
$ ssh-add /c/users/$(whoami)/.ssh/id_rsa
--> via https: none (credential based auth)
$ git push ...
```
4. On ``git.scicore.unibas.ch`` create a pull request to the ``dev`` branch. Assign someone to review your changes.
5. A more specific document on code quality assurance is located in [docs/quality_assurance.md](docs/quality_assurance.md) and [CONTRIBUTING.md](CONTRIBUTING.md).
6. Check and add to [TODO.md](TODO.md). Open issues for missing feature on gitlab and remove them from TODO file.


## 4. Game Concept
A detailed description is kept in [docs/game_rules.md](docs/game_rules.md) folder.  

![Mockup Image 4](./assets/mockup/ui_4.png)
![Mockup Image 1](./assets/mockup/ui_1.jpg)  


## 5. Useful Resources/Tutorials
- more under [docs/resources.md](docs/resources.md)
- Essential Git. [https://nhs.io/git/](https://gitlab.com/ci/lint).
- Online .gitlab-ci.yml linter. [https://gitlab.com/ci/lint](https://gitlab.com/ci/lint).
- Markdown Cheatsheet. [https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet).


## 6. License
![GNU AGPL Logo](assets/other/AGPLv3_Logo.png)  
**GNU AGPLv3**. [https://choosealicense.com/licenses/agpl-3.0/]()


## 7. Webpage
[subluminal.tech](http://subluminal.tech)  /   [merch.subluminal.tech](http://merch.subluminal.tech)  /   [buy.subluminal.tech](http://buy.subluminal.tech)