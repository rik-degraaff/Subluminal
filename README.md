# Subluminal Project Repository

[![Logo](./assets/logo/subluminal_logo.png)](http://subluminal.tech) **by Bordeaux Ink.**


## Developer setup
1. Generate a pair of SSH keys and add them to your Gitlab account (follow [these instructions](https://git.scicore.unibas.ch/help/ssh/README#generating-a-new-ssh-key-pair) or run  
``ssh-keygen -t rsa -C "your.email@stud.unibas.ch" -b 4096``)
2. Configure your local git installation by entering the following commands into your command line:
(Careful: Don't use the --global flag if you have an existing git installation. Change into the project directory and run the commands without the flag.)
```sh
$ git config --global user.name "User Name - shortname00"  
$ git config --global user.email "your.email@stud.unibas.ch"
$ git config --global core.autocrlf true  
```
3. Clone the repo to your harddrive ``git clone https://git.scicore.unibas.ch/CS108-FS18/Gruppe-11.git``  
4. For pushing your commit to gitlab via Git Bash, you need to perform the following steps:
```sh
$ eval $(ssh-agent -s)
$ ssh-add /c/users/$(whoami)/.ssh/id_rsa
--> via ssh: Connect Cisco VPN (ssh key based auth)
--> via https: none (credential based auth)
$ git push ...
```


## Contributing (& Branching)
1. Make sure to checkout the most recent ``dev`` branch.
2. Create a new branch to commit your changes.  Your branch names should adhere to the following conventions:  
2.1. Start with a branch type descriptor
2.2. Contain your branch name in snake case (word separated by underscores)  
2.3. Contain your name token  

###For example:  
| Type            | Name          | Token |
| --------------- | ------------- | ----- |
| -ft (features)  | ...           | -dav  |
| -hf (hotfixes)  |               | -lgk  |
| -rl (releases)  |               | -luc  |
| ...             | ...           | -rik  |

3. On ``git.scicore.unibas.ch`` create a pull request to the ``dev`` branch. It will be merged on review by the collective.
4. Merges into master should be a rare thing (milestones and important releases).

For example:
```sh
$ git pull origin dev
$ git checkout dev
$ git branch "myFeature"
... do some coding
$ git commit -m "I made some awesome changes!"
$ git push origin myFeature
--> Create pull request on git.scicore.unibas.ch to dev branch.
```


## Game Concept
For a detailed description go to the [/design/](/design/) folder.  

![Mockup Image 1](./assets/mockup/ui_1.png)  

![Mockup Image 2](./assets/mockup/ui_2.png)


## Useful Resources/Tutorials
- [https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet]()
- ...

## License
**GNU AGPLv3** ([https://choosealicense.com/licenses/agpl-3.0/]())


## Webpage
[http://subluminal.tech]()  
[http://merch.subluminal.tech]()  