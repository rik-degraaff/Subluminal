# TODO
This is a temporary task store. Please create an issue on gitlab for the task you are working on, and delete it from this file.

## Game design
* [ ] ...

## Code
* [X] correct all the checkstyle errors (+SON exception).
* [ ] integrate Log4J and take debug lvl as command line argument.
* [ ] command line argument parser.
* [X] get current version of google code style for gradle/checkstyle.
* [ ] write unittests.
* [ ] use mockito framework
* [ ] implement SONList
* [ ] master server for game browser
* [ ] global config store 
* [ ] random string generator (usernames, loading messags, planets, solar systems)
* [ ] make consolue input work during message receive

## Artwork
* [X] game icon for social media, may also be used as a program icon.

## Web
* [X] disclaimer for homepage (copyright, steamstore, ...).
* [X] pusblish game rules document (--> markdown render).

## Gradle/IntelliJ/Gitlab
* [X] reroute ``out`` folder in IntelliJ to ``app/out/`` using the idea gradle plugin.
* [X] javadocs output to docs folder.
* [X] gitlab CI is not working... docker problem?
* [ ] use watch plugin for gradle.(--continous option)
* [X] get IntelliJ to play nice with gradle project.
* [ ] gradle 'rootProject.projectDir' option necessary?
* [ ] crate gradle 'run' task for client/server (plugin application).
* [ ] compile and execute java stuff with utf8 encondig

## CI
* [ ] create (manual) deploy script for server in docker container.
* [X] additional stages for CI, make allow_fail for low prio branches.
* [ ] attach binaries from CI to release in gitlab

## Other
* [X] update diary with more entries.
* [X] flesh out network protocol documentation, draw UML diagram.
* [X] create quality assurance workflow acording to slides from Schuldt.
* [ ] think about presentation structure/sequence.
* [X] update software dependencies.
* [ ] open javadoc and test report on completation.
* [ ] generate CHANGELOG from commits.
* [ ] update CONTRIBUTING file.
* [X] create ``.template`` folder for gitlab
* [ ] overhaul build vs branch matrix