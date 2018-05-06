# TODO
This is a temporary task store. Please create an issue on gitlab for the task you are working on, and delete it from this file.

## Game design
-

## Code
* [X] correct all the style errors (+SON exception).
* [X] integrate Log4J and take debug level as command line argument.
* [X] command line argument parser.
* [X] get current version of google code style for gradle/style.
* [X] write unit tests.
* [X] use mockito framework
* [X] implement SONList
* [ ] master server for game browser
* [X] global configuration store 
* [X] random string generator (usernames, loading messages, planets, solar systems)
* [ ] make console input work during message receive

## Artwork
* [X] game icon for social media, may also be used as a program icon.

## Web
* [X] disclaimer for homepage (copyright, steam store, ...).
* [X] publish game rules document (--> markdown render).

## Gradle/IntelliJ/Gitlab
* [X] reroute ``out`` folder in IntelliJ to ``app/out/`` using the idea gradle plugin.
* [X] javadocs output to docs folder.
* [X] gitlab CI is not working... docker problem?
* [X] use watch plugin for gradle.(--continuous option)
* [X] get IntelliJ to play nice with gradle project.
* [ ] gradle 'rootProject.projectDir' option necessary?
* [X] crate gradle 'run' task for client/server (plugin application).
* [X] compile and execute java stuff with utf8 encoding

## CI
* [X] create (manual) deploy script for server in docker container.
* [X] additional stages for CI, make allow_fail for low priority branches.
* [X] attach binaries from CI to release in gitlab

## Other
* [X] update diary with more entries.
* [X] flesh out network protocol documentation, draw UML diagram.
* [X] create quality assurance workflow according to slides from Mr. Schuldt.
* [X] think about presentation structure/sequence.
* [X] update software dependencies.
* [X] open javadocs and test report on completion.
* [X] generate CHANGELOG from commits.
* [X] update CONTRIBUTING file.
* [X] create ``.template`` folder for gitlab.
* [X] overhaul build vs branch matrix.
* [X] define version numbering.