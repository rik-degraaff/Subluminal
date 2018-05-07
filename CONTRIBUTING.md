# Contributing
This document is intended to give you guideline on how to contribute to this repository.

## Workflow
1. Generate a pair of ssh keys and add them to your gitlab account (follow [these instructions](https://git.scicore.unibas.ch/help/ssh/README#generating-a-new-ssh-key-pair) or run  
``ssh-keygen -t rsa -C "your.email@stud.unibas.ch" -b 4096`` and add the key to gitlab via the web interface).
2. Configure your local git installation by entering the following commands into your command line:
(Careful: Don't use the ``--global`` flag if you have an existing git installation. Change into the project directory and run the commands **without** ``--global``.)  
```sh
$ git config --global user.name "User Name - shortname00"
$ git config --global user.email "your.email@stud.unibas.ch"
$ git config --global core.autocrlf true
```
3. Make sure to checkout the most recent ``dev`` branch.
4. Create a new feature/bugfix/hotfix branch to commit your changes. Your branch names have to adhere to the gitflow conventions.
4.1 Open an issue for the feature you are working on, and tag it with the ``doing`` label.
5. Use GitKraken to stage you changes, commit and push your code to remote. 
5.1 To push your commits to gitlab via Git-Bash, you need to perform the following steps:  
```sh
--> via ssh: Connect Cisco VPN (ssh key based auth)
$ eval $(ssh-agent -s)
$ ssh-add /c/users/$(whoami)/.ssh/id_rsa
--> via https: none (credential based auth [https://stackoverflow.com/a/5343146](https://stackoverflow.com/a/5343146))
$ git push ...  
```
6. On ``git.scicore.unibas.ch`` create a pull request to the ``dev`` branch. Assign someone to review your changes.


## Branch name
(taken from [https://nvie.com/posts/a-successful-git-branching-model/](https://nvie.com/posts/a-successful-git-branching-model/))

| Branch type            | Prefix          |
| --------------- | ------------- |
| Master  | master  |
| Develop  | dev  |
| Releases  | release/  |
| Features  | feature/  |
| Hotfixes  | hotfix/  |


## Versioning
(taken from [https://semver.org/](https://semver.org/))

Given a version number MAJOR.MINOR.PATCH, increment the:
1. MAJOR version when you make incompatible API changes,
2. MINOR version when you add functionality in a backwards-compatible manner, and
3. PATCH version when you make backwards-compatible bug fixes.
Additional labels for pre-release and build metadata are available as extensions to the MAJOR.MINOR.PATCH format.

