# Using docker in this project
**HINT**: For these scripts to work, you need a bash shell (git-bash on windows will do, but change your line endings from *CRLF* to *LF*, i.e. using ``dos2unix``), a docker-daemon, git and sshpass. If your host is a windows based operating system, you need to share your drive in the settings of the desktop app, otherwise volumes won't be created properly.

## What is docker?
> Docker is a tool designed to make it easier to create, deploy, and run applications by using containers. Containers allow a developer to package up an application with all of the parts it needs, such as libraries and other dependencies, and ship it all out as one package. [opensource.com 2018](https://opensource.com/resources/what-docker)

## Local server
You can user docker to start the server locally. There aren't many benefits to this, except you have a controlled environment for the application to run in. We provide a docker compose file for ease of use. Switch to the ./docker/ directory and just type ...
``` sh
## Start the container as a service:
$ docker-compose up -d

## Stop the service:
$ docker-compose down
```
If you are a friend of the command line use it like this:
```
docker run -d -p 1729:1729/tcp --name subluminal_server --restart=always  subluminalthegame/subluminal-server:latest
```