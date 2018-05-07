# Changelog


## (unreleased)

### New

* Random starnames from txt files with keyword *star* in their names. [Luc Kury]

### Other

* Created gradle task according to tutors specifications. [Luc Kury]

* Generate map and respect the JUMP_DISTACNCE constant. [Luc Kury]

* Pulled from dev. [David Lengweiler lendav00]

* Changed pattern in getResources for starname files. [Luc Kury]

* Mothership is no hovering besides the star. [David Lengweiler lendav00]

* Stopped the mothership anim, no more rotation. [David Lengweiler lendav00]

* Revert path to circle, glow on transparent circle is causing preformance issues. [David Lengweiler lendav00]

* Replaced transparent circle with circular path, better fps. [David Lengweiler lendav00]

* Leaving the game is working, TODO: some callback. [David Lengweiler lendav00]

* Map needs to be reset after the game. [David Lengweiler lendav00]

* Added a button to go back after the game finished. [David Lengweiler lendav00]

* Endgame should now be handled correctly. [David Lengweiler lendav00]

* Temporary highscore gui working. [David Lengweiler lendav00]

* Start pointer for every player now. [David Lengweiler lendav00]

* Send buttons use full width. [David Lengweiler lendav00]

* Seperate row for the send butttons. [David Lengweiler lendav00]

* Onetime fps counter clas. [David Lengweiler lendav00]

* Added a chart for the fps. [David Lengweiler lendav00]

* Added fps counter on f4. [David Lengweiler lendav00]

* Send all button and some cleanup for status. [datomo]

* Started implementation of ChatManagerTest. [Galery Käser Lucas - galluc00]

* Finished slick implementation of UserManagerTest. [Galery Käser Lucas - galluc00]

* Figured out Mockito and made UserManagerTest compile and succeed. [de Graaff Rik - graaff]

* First attempt at using mockito to test the UserManager. [Galery Käser Lucas - galluc00]

* Namechange from buttons is working. [David Lengweiler lendav00]

* On @message chat is opening. [David Lengweiler lendav00]

* Restructured userList Board. [David Lengweiler lendav00]

* PlayArea slightly smaller, to create some space for the gui. [David Lengweiler lendav00]

* Added a better API for the ifPresent function. [de Graaff Rik - graaff]

* Playercolors for images is now working. [David Lengweiler lendav00]

* Fixed the ship color. [David Lengweiler lendav00]

* Remove fleet if star has allready fleet. [David Lengweiler lendav00]

* Correctly merge fleets on arrival at star. [de Graaff Rik - graaff]

* Make sure players know which fleets are destroyed. [de Graaff Rik - graaff]

* Added the ability to send fleets on the server side. closes #50. [de Graaff Rik - graaff]

* Added InitialUsersTest and LobbyListResTest. [Galery Käser Lucas - galluc00]

* Implemented GameStartReq-/Res and HighScoreReq-/Res. [Galery Käser Lucas - galluc00]

* Added ChatMessageOutTest and eliminated the client.presentation package from the jacocotestreport. [Galery Käser Lucas - galluc00]

* Implemented ChatMessageInTest. [Galery Käser Lucas - galluc00]

* Added PlayerJoinTest and MotherShipMoveReqTest. [Galery Käser Lucas - galluc00]

* Wrote unit test for FleetMoveReq. [Galery Käser Lucas - galluc00]

* Implemented PlayerLeaveTest. [Galery Käser Lucas - galluc00]

* Created tests for UsernameReq/-Res and PlayerUpdate, optimized something in PingTest and made field in UsernameRes private. [Galery Käser Lucas - galluc00]

* Finished PingTest and PongTest. [Galery Käser Lucas - galluc00]

* Implemented test for Ping message. [Galery Käser Lucas - galluc00]

* NEW: Can load settings from property file. [Luc Kury]

  and export settings defined in GlobalSettings class to property file.

* DFlag has to be set. [David Lengweiler lendav00]

* Characters get printed correctly if Dflag is set. [David Lengweiler lendav00]

* Some cleanup for the reader, still no fix. [David Lengweiler lendav00]

* Using namegenerator now correctly. [David Lengweiler lendav00]

* Resolved BindException if socket port was already in use. [Luc Kury]

* Updated illustrator file. [David Lengweiler lendav00]

* Fleets placeholder now representing fleets. [David Lengweiler lendav00]

* Motherships and fleets are now differently represented. [David Lengweiler lendav00]

* Started working on the image for the ships. [David Lengweiler lendav00]

* Try recursive operator for class files. [Luc Kury]

* Modified watch job to have less gradle tasks. [Luc Kury]

* Gitlab ci yml class files artifacts. [Luc Kury]

* Using wildcard to include class files. [Luc Kury]

* Trying new yaml syntax. [Luc Kury]

* Fixing build stage artifacts. [Luc Kury]

* Added a ASCII art in help message. [Luc Kury]

* Added package-info.java files for all packages. [Luc Kury]

  Updated JavaDocCoverage percentage. Currently at 74.40%

* Overloaded constructor needed for the fleets. [David Lengweiler lendav00]

* Correct coordinates in gamecontroller. [David Lengweiler lendav00]

* Reformated empty lanes, added a resource. [Luc Kury]

* Added badges to main readme for masterbranch. [Luc Kury]

  Javadoc coverage and version need to be adjusted manually

* Reorder fleet and mothershiptComponent. [David Lengweiler lendav00]

* Main ship component to remove dublicate code. [David Lengweiler lendav00]

* Fixed missing runner, excluded libs folder. [Luc Kury]

  in out artifacts, only take class files

* Added pages setup to ci yml. [Luc Kury]

* Added ping example in ci for remote server. [Luc Kury]

* Further changed to artifacts, because jacoco is a bitch. [Luc Kury]

* More changes to ci. [Luc Kury]

  all runners changed to dind

* Changed artifacts. [Luc Kury]

* Typo in gitlab-ci.yml. [Luc Kury]

* Set image for test deploy job. [Luc Kury]

* Artifacts expire faster, coverage regex inside job, exclude log.txt. [Luc Kury]

* Ping successful. why won't ssh work... testing further. [Luc Kury - kurluc00]

* Test server connection. [Luc Kury - kurluc00]

  ssh says timeout to host

* Set deploy host, cause no secret variable exists. [Luc Kury]

* Fixed misspelled gradle task. [Luc Kury]

* Second env variable as well. [Luc Kury]

* Env variables not being printed correctly. [Luc Kury]

* Fixed dockerfile patch and missing build-args. [Luc Kury]

* Testing docker dind on gitlab ci with new runner tag. [Luc Kury]

* Jumpcicle is now working properly, and star are glowing. [David Lengweiler lendav00]

* Ships turn in the right direction, some glowing stars effect added. [David Lengweiler lendav00]

* Ships should change direciton to the stars. [David Lengweiler lendav00]

* Started fixing the mothership rotation. [David Lengweiler lendav00]

* Back to .wav-files, javafx cant handle standalone .aac-files.. [David Lengweiler lendav00]

* Forgot to change filesnames in settingscontroller. [David Lengweiler lendav00]

* Changed wav files for .aac files. [David Lengweiler lendav00]

* Added new nice playercolors. [David Lengweiler lendav00]

* Got the wav in lfs working. [David Lengweiler lendav00]

* Removed '"' in gitabtributes. [David Lengweiler lendav00]

* Readded wav files. [David Lengweiler lendav00]

* Removed wav files short-term. [David Lengweiler lendav00]

* Git lfs should track all '*.mp3' now. [David Lengweiler lendav00]

* Added '.wav'-files to git lfs. [David Lengweiler lendav00]

* Jumpbox now has the right position. [David Lengweiler - lendav00]

* Chat can now be pulled out. [David Lengweiler lendav00]

* Started working on movable chat. [David Lengweiler - lendav00]

* Handled the sound in the settings. [David Lengweiler - lendav00]

* Some more refactoring. [David Lengweiler lendav00]

* Fixed ship generation rate. partial #49. [de Graaff Rik - graaff]

* Ships should now be desplayed on the map. [David Lengweiler lendav00]

* Added server-side ship generation. partial #49. [de Graaff Rik - graaff]

* Pushed version to 4.0.0. [David Lengweiler lendav00]

* Refactored and removed unnecessary imports. [David Lengweiler lendav00]

* Started removing unneccesary getters and setters in presentation layer. [David Lengweiler lendav00]

* Checks for shipcolor and possession now. [David Lengweiler lendav00]

* Ship is now moving straigt but not smooth and not in the center. [David Lengweiler lendav00]

* Fixing ship animation. [David Lengweiler lendav00]

* Fixed another anchor link. [Luc Kury]

* Bumped version to release 3.5.0. [Luc Kury]

* Fixed anchor links in requirements. [Luc Kury]

* Fixed anchor links. [Luc Kury]

* Fixed markdown tables in requirements. [Luc Kury]

* Implemented NameGenerator for stars. [Luc Kury]

  Need fix for greek symbols eventually. Maybe JavaFX can handle it.

* Imported list of star names and read them from file. [Luc Kury]

* Take Loglevel as argument from commandline. [Luc Kury]

* WIP: picocli parsing not working. [Luc Kury]


## 3.3.0 (2018-04-16)

### New

* WIP// Server side lobby management. [Luc Kury]

  Implemented 4 new package type regarding lobby functionality. Started with lobby store and lobby manager.

### Other

* Added highscore list. [de Graaff Rik - graaff]

* Added basic structure for a highscore store. partial #44. [de Graaff Rik - graaff]

* Added justifications for decisions to diary. [Galery Käser Lucas - galluc00]

* Finished readmes for milestone 3. [Luc Kury]

* Updated requirements. [Luc Kury]

* Revert "Merge branch 'ft-network-luc' into dev" [Luc Kury]

  This reverts commit 27cfffca216dbd7d47a96854fddf8ac88eb47b1c.

* Changed filename. [Galery Käser Lucas - galluc00]

* Made screenshots for M3 presentation. [Galery Käser Lucas - galluc00]

* One of the last gantt changes. [Galery Käser Lucas - galluc00]

* Diary updated. [Galery Käser Lucas - galluc00]

* Added new line to diary. [Galery Käser Lucas - galluc00]

* Diary is practically milestone-ready. [Galery Käser Lucas - galluc00]

* Continued polishing diary. [Galery Käser Lucas - galluc00]

* Prettied project timeline. [Galery Käser Lucas - galluc00]

* Updated gantt. [Galery Käser Lucas - galluc00]

* Updated our gantt timeline. [Galery Käser Lucas - galluc00]

* Adapted the gantt diagram to the current state of the project. Further specifications to be made. [Galery Käser Lucas - galluc00]

* Added some small changes to gantt. [Galery Käser Lucas - galluc00]

* Updated the gantt diagram. [Galery Käser Lucas - galluc00]

* Updated diary. [Galery Käser Lucas - galluc00]

* Updated the diary. [Galery Käser Lucas - galluc00]

* Added todays new decisions to the diary. [Galery Käser Lucas - galluc00]

* Added the external libraries we are going to use to the diary. [Galery Käser Lucas - galluc00]

* Added the decisions of March 29th to the diary. [Galery Käser Lucas - galluc00]

* Changed some fonts sizes and added the missing animation on the last slide. [Galery Käser Lucas - galluc00]

* Finished presentation for milestone 3. [Luc Kury]

* Embedded font in pp. [David Lengweiler lendav00]

* Configured masterslide. [David Lengweiler lendav00]

* Started prepering the slides for milestone 3. [David Lengweiler lendav00]

* Finished manual for milestone 3. [Luc Kury]

* Added game rules to munual. [Luc Kury]

* Added prototype for manual as tex document. [Luc Kury]

  A little rough around the edges still.

* Added tex template for manual. [Luc Kury]

* Reconfigure gradle to build javadoc coverage. [Luc Kury]

* All message types should be javadoc'd now. [Galery Käser Lucas - galluc00]

* Added/Completed javadocs. Docs still missing from ChatMessageIn to LobbyCreateReq. [Galery Käser Lucas - galluc00]

* Players should be different colors. [David Lengweiler lendav00]

* Ship movement and colonization. [David Lengweiler lendav00]

* Ships are now moving. [David Lengweiler lendav00]

* Finished correct path display. [David Lengweiler lendav00]

* Implemented pathfinding. [David Lengweiler lendav00]

* Deleted a comment in german. [Galery Käser Lucas - galluc00]

* Implemented unit tests and javadocs for the pathfinding classes. [Galery Käser Lucas - galluc00]

* Tested nearly 100% of Graph. [Galery Käser Lucas - galluc00]

* Finished implementation of pathfinding. [Galery Käser Lucas - galluc00]

* Pre graph pull. [David Lengweiler lendav00]

* Ships are rotating correctly. [David Lengweiler lendav00]

* Motherships should be displayed correctly. [David Lengweiler lendav00]

* Displaying the initial map now. [David Lengweiler lendav00]

* Working on drawing the stars. [David Lengweiler lendav00]

* Game opens the map. [David Lengweiler lendav00]

* Fixed gameloop tps. [David Lengweiler lendav00]

* Gameloop is starting correctly now. [David Lengweiler lendav00]

* Finished lobby connecting with gui. [David Lengweiler lendav00]

* Lobby list updates correctly. [David Lengweiler lendav00]

* Buttons in listviews seem to cause problems.. [David Lengweiler lendav00]

* Lobbylist upadates correctly. [David Lengweiler lendav00]

* Lobby creation and presention is now working. [David Lengweiler lendav00]

* Started on putting all togheter. [David Lengweiler lendav00]

* Small additions to the game logic so tasks actually get executed, added endTarget to Movables and connected game logic in ServerInitializer. [de Graaff Rik - graaff]

* Removed teststars. [David Lengweiler lendav00]

* Possession works now. [David Lengweiler lendav00]

* Some refactor and lobbycomponent implements now lobbypresenter. [David Lengweiler lendav00]

* LobbyComponent to handle different states of the lobby. [David Lengweiler lendav00]

* Completed the first gamecontroller. [David Lengweiler lendav00]

* Implemented first version of fleetcomponent. [David Lengweiler lendav00]

* Finished gamePresenter interface and started working on implementing it in GameController. [David Lengweiler lendav00]

* Added jumpbox and mothership. [David Lengweiler lendav00]

* Properly bound the jumppath to the stars+ [David Lengweiler lendav00]

* Nearly done with paths. [David Lengweiler lendav00]

* Planets radius is bound to rectangle. [David Lengweiler lendav00]

* Added an algorithmus. [David Lengweiler lendav00]

* Color binding is now working. [David Lengweiler lendav00]

* On hover for stars and placing them on the grid. [David Lengweiler lendav00]

* Started working on game map and stars. [David Lengweiler lendav00]

* Fixed annoing overrun bug in chat. [David Lengweiler - lendav00]

* Fixed overlap error temporarly. [David Lengweiler lendav00]

* Fixed buld error caused by old ChatManager file. [Luc Kury]

* Finished Lobby Management. [Luc Kury]

  Missing processing of GameStartRes ... need gamepresenter on client

* Created GameStart message packages and registered handlers. [Luc Kury]

  See TODO for missing implementations.

* Fixed LobbyStore shenannigans. [de Graaff Rik - graaff]

* Unfinished Busines LobbyListRes. [Luc Kury]

* Lobby stuff finished, except ... [Luc Kury]

  ... RobbyListRes.fromSON() function.

* Updated package and store structure for lobbyies on server and client. [Luc Kury]

* Removed second build task from CI file. [Luc Kury]

* Added proper movement and connected to the network, so the client can listen to it. partial #43. [de Graaff Rik - graaff]

* Added boilerplate for ship generation, dematerialisation and added Ship mmovement, still needs connecting. partin #43. [de Graaff Rik - graaff]

* Added MoveRequest handling and send GamestateDeltas to players. partial #43. [de Graaff Rik - graaff]

* Added Signals to the game state. [de Graaff Rik - graaff]

* Added javadocs for Either. [de Graaff Rik - graaff]

* Made game history entries destroyable. [de Graaff Rik - graaff]

* Implemented a basic version of map generation and provide an entry point for starting a game. closes #42, baby steps for #43. [de Graaff Rik - graaff]

* Removed non conflicting SocketConnection file. [Luc Kury]

  The file was not marked as conflicted on merging ...

* TODO for error message in SocketConnection. [Luc Kury]

* NEW: Implemented new argument parser for command line (picocli) [Luc Kury]

* WIP: picocli imports. [Luc Kury]

* Added picocli dependency. [Luc Kury]

* Houston, we got sound! [Luc Kury]

  :-)

* Fully modeled game state. closes #40. [de Graaff Rik - graaff]

* Created basic structure of server side GameStore, implemented MoveRequests fully. partial #40. [de Graaff Rik - graaff]

* Build script and gitlab-ci.yml updated. [Luc Kury]

  Use the make.sh script to start servers and clients.
  Use ./make.sh watch to start a continuous gradle build task

* Updated deploy scripts for future use. [Luc Kury]

* Testing docker image building on scicore. [Luc Kury]

* Refactored code and removed debug lines. [David Lengweiler lendav00]

* Fixed the version number. [David Lengweiler lendav00]

* Added functionality of move requests. [Galery Käser Lucas - galluc00]

* Added javadocs. [Galery Käser Lucas - galluc00]

* Added some javadocs. [Galery Käser Lucas - galluc00]

* Fixed runlater on server error. [David Lengweiler lendav00]

* Fixed PlayerStatusComponent runLater. [David Lengweiler lendav00]

* UserList updates dynamicly. [David Lengweiler lendav00]

* New windowContainer for better modularity. [David Lengweiler lendav00]

* New windowContainer for better modularity. [David Lengweiler lendav00]

* Started implementing the hostView. [David Lengweiler lendav00]

* Made stores observable, so they can more easily be connected to the views. [de Graaff Rik - graaff]

* Removed message debug line and fixed '@message' output in chat. [David Lengweiler lendav00]

* Fixed whisper messages and related issues. [de Graaff Rik - graaff]

* Use more force when closing socket and don't shut down on NoElementException. [de Graaff Rik - graaff]

* Started working on fix for @messages. [David Lengweiler - lendav00]

* Fixed initialUsers bug. [David Lengweiler lendav00]

* Finished implementation of playerjoin, -leave and update client-side. [David Lengweiler lendav00]

* Finished implementation of playerUpdate message. [David Lengweiler lendav00]

* Started working on the lobbyHostView. [David Lengweiler lendav00]

* Updated Userstore and added new PlayerJoin and PlayerLeave messages. [David Lengweiler lendav00]

* Some formating and shorted the playerstatus and lobbystatus enum. [David Lengweiler lendav00]

* Started working on settingsView and hostlobbyview. [David Lengweiler - lendav00]

* Fixed missing runner tag in ci file. [Luc Kury]

* Added sh script to easily start test client and servers. [Luc Kury]

  Also modified ci file for automatic staging deployment

* Udated version to 3.3.0. [Luc Kury]

* Imported tinylog intro project. [Luc Kury]

  Replaced println() with Logger.{level} where it made sense,

* Moved GameStateDelta test to a sensible package/class. [de Graaff Rik - graaff]

* Fixed test for SONList iteration. Use synchronized block in Threads. [de Graaff Rik - graaff]

* Let's try this again. [Luc Kury]

* Fixed ci file, 2 different build tasks found. [Luc Kury]

* Added missing LobbyLeave... packages from dev branch. [Luc Kury]

  DAVE WHAT THE FUCK!!!

* Added funtionaltity to add new Lobbies to the lobbylist and updated them. [David Lengweiler - lendav00]

* Added missing Streamable file. [de Graaff Rik - graaff]

* Added javadoc comments for Streamables. closes #41. [de Graaff Rik - graaff]

* Defined Streamable interface and created methods that return it from a SONList. #41. [de Graaff Rik - graaff]

* Created interfaces and classes on server and client to represent lobbies. [Luc Kury]

* Implemented game manager. [Galery Käser Lucas - galluc00]

* Added missing implementation of asSON() in Player. [de Graaff Rik - graaff]

* Finished implementing SONRepresentable for GameStateDelta. [Galery Käser Lucas - galluc00]

* Made Star SONRepresentable. [Galery Käser Lucas - galluc00]

* Finished GameStateDelta asSON implementation. The rest in the three files is not finished yet. [Galery Käser Lucas - galluc00]

* Implemented new UserStore for joinLobby and leaveLobby. [Luc Kury]

* Fixed minor formatting errors. [de Graaff Rik - graaff]

* Implemented PingStore using IdentifiableCollection and SingleEntity and made some changes to both to allow more use cases. [de Graaff Rik - graaff]

* Added SingleEntity class and implemented getWithPredicate() on IdentifiableCollection. [de Graaff Rik - graaff]

* Added getWithPredicate() function on IdentifiableCollection. [de Graaff Rik - graaff]

* Added IdentifiableCollection to make implementing stores easier. [de Graaff Rik - graaff]

* Started working on the event bus, betterscalability. [David Lengweiler - lendav00]

* Removed most component code from main controller started working on subcontroller calling. [David Lengweiler - lendav00]

* Removed wrongly pulled stuff from lobby logic and fixed path in images. [David Lengweiler - lendav00]

* // WIP: restrukture lobby store. [Luc Kury]

* Worked on GameStateDelta. [Galery Käser Lucas - galluc00]

* Created GameStateDelta. [Galery Käser Lucas - galluc00]

* Implemented Player record. [Galery Käser Lucas - galluc00]

* Adapted the Synchronized functionality to the GameStore. [Galery Käser Lucas - galluc00]

* Added the Identifiable class. [Galery Käser Lucas - galluc00]

* Added some game logic essential assets. [Galery Käser Lucas - galluc00]

* Started defining client side GameStore interface. [de Graaff Rik - graaff]

* Added Records for game logic. [Galery Käser Lucas - galluc00]

* Added generic game loop. [Galery Käser Lucas - galluc00]

* Added a game dev dedicated e-book to the resources. [Galery Käser Lucas - galluc00]

* Update all the readme in docs. [Luc Kury]

  based on the milestone 3 goals and feedback from miletone 2 presentation

* // WIP: requirements.md. [Luc Kury]

* Custom compoenent structure layout. [David Lengweiler lendav00]

* Removed synchronized(userStore) block from PingManager. [de Graaff Rik - graaff]

* Corrected errors in PingManager and ChatManager resulting from updated UserStore. [de Graaff Rik - graaff]

* Added Synchronized Class for use in user stores and updated server side user store to use it. [de Graaff Rik - graaff]

* Added some more complex test for SON (parsing) and fixed some issues that showed up. closes #28, #39. [de Graaff Rik - graaff]

* Added some basic tests for SON parsing. partial #39. [de Graaff Rik - graaff]

* SONList implementation + parsing. [de Graaff Rik - graaff]

* Made the artifacts of all jobs the same. [Luc Kury]

* Fixed coverage report again #2. [Luc Kury]

* Fixed GRADLE_USER_HOME environment variable. [Luc Kury]

* Fixing coverage report again. [Luc Kury]

* ObservervableList for the chat. [David Lengweiler lendav00]

* Added controller and view for playerStatusBoard. [David Lengweiler - lendav00]

* Started working on the PlayerList. [David Lengweiler lendav00]

* Fixing failing coverage report task in gitlab ci. [Luc Kury]

  Cached the app path

* Corrected error in path spec for coverage report output dir. [Luc Kury]

* New image for gitlab ci including javafx. [Luc Kury]

* Fixed build pipeline for Gitlab CI. [Luc Kury]

* Updated configuration of build tool chain. [Luc Kury]

* // WIP: docker-compose.yml. [Luc Kury]

* Filter funktion for the chat and used vbox and label. [David Lengweiler lendav00]

* Finished first lobby background and console. [David Lengweiler lendav00]

* Started working on the background. [David Lengweiler - lendav00]

* Started implementing the user input parsing. [David Lengweiler - lendav00]

* Chat and user delegate are working fine with gui. [David Lengweiler - lendav00]

* Got the implementation layout for the gui figuered out. [David Lengweiler - lendav00]

* Change main font to IBM VGA9 started working on the layout. [David Lengweiler - lendav00]

* Removed editible splitpanel. [David Lengweiler - lendav00]

* Added a first version of a lobby gui. [David Lengweiler - lendav00]

* Dockerfile for server deployment. [Luc Kury]

* Added out folder to artifacts in build stage. [Luc Kury]

  in .gitlab-ci.yml

* Corrected absolut path for artifacts in .gitlab-ci.yml. [Luc Kury]

* Changed build tools settings (.gitlab-ci.yml and build.gradle). [Luc Kury]

* Added some javafx. [David Lengweiler - lendav00]

* Changed various md files. Added outline for m2 presentation. [Luc Kury]

* Added issue templates for Bug and Feature. [Luc Kury]


## 0.2.0 (2018-03-26)

### Other

* Added some documentation to the SONParsing object. [David Lengweiler - lendav00]

* Hotfix in diary. [Galery Käser Lucas - galluc00]

* Prepared release 0.2.0-m2. [Luc Kury - kurluc00]

* Quality characteristics added. [Luc Kury - kurluc00]

* Updated documentation for m2. [Luc Kury - kurluc00]

* Updated the QA plan. [Galery Käser Lucas - galluc00]

* Updated our quality assurance plan for MS 2. Some more changes might have to be made. [Galery Käser Lucas - galluc00]

* Network protocol specification 90%. [Luc Kury - kurluc00]

* New tasks in TODO list (for after m2). [Luc Kury - kurluc00]

* Final update to TODO. [Luc Kury - kurluc00]

* Added more tasks to TODO. [Luc Kury - kurluc00]

* Updated TODO list. [Luc Kury - kurluc00]

* Finished diary for M2. [Galery Käser Lucas - galluc00]

* Put in placeholder for the date we decided on our network protocol. [Galery Käser Lucas - galluc00]

* Updated the diary to Sunday, March 25th, 14h48. [Galery Käser Lucas - galluc00]

* Updated the diary. [Galery Käser Lucas - galluc00]

* Minor annotaions for SON parsing. [David Lengweiler - lendav00]

* CI yaml valid again. [Luc Kury - kurluc00]

* Updated build.gradle with open browser task. [Luc Kury - kurluc00]

* Changed javadoc output directory. [Luc Kury - kurluc00]

* Removed JUnit dependencie form regular compile task. [Luc Kury - kurluc00]

* Renamed old checkstyle. [Luc Kury - kurluc00]

* WIP: Fixed javadoc errors. [Luc Kury - kurluc00]

* Removed JUnit dependencie form regular compile task. [Luc Kury - kurluc00]

* FINAL CHANGE TO GRADLE VERSION, I PROMISE! :-P. [Luc Kury - kurluc00]

* Added some more javadoc comments. #22 partial. [Galery Käser Lucas - galluc00]

* Corrected most Checkstyle erros. partial #22. [David Lengweiler - lendav00]

* Added more javadoc comments. partial #22. [Galery Käser Lucas - galluc00]

* Some more javadoc comments. partial #22. [David Lengweiler - lendav00]

* Added some javadoc comments. partial #22. [Galery Käser Lucas - galluc00]

* Started working on missing javadocs. partial #24. [David Lengweiler - lendav00]

* Auto stylecheck and reanrranged. [David Lengweiler - lendav00]

* Removed double entry in gitignore and added *.log files. [David Lengweiler - lendav00]

* Clients can handle losing the connection. closes #22. [David Lengweiler - lendav00]

* Added client-side pingStore. [David Lengweiler - lendav00]

* Implemented correct logout handling in client. closes #20. [de Graaff Rik - graaff]

* Actually tell connectionClosedListeners that a connection closed. [de Graaff Rik - graaff]

* Try interrupt for closing thread. [David Lengweiler - lendav00]

* Return an empty SON object instead of null from LogoutReq::fromSON. [de Graaff Rik - graaff]

* Added the Logut ConnectionClosedListeners. [David Lengweiler - lendav00]

* Added LogoutReq object and implemented for client-side. #20. [David Lengweiler - lendav00]

* Send received Chatmessages to ChatPresenter. [David Lengweiler - lendav00]

* Correctly set global when receieverID is null in ChatMessageOut. [de Graaff Rik - graaff]

* Deal with missing receiver id in ChatMessageOut. [de Graaff Rik - graaff]

* Added the chatManager and the PingManager. [David Lengweiler - lendav00]

* Parsing the messages is now possible. [David Lengweiler - lendav00]

* SON parsing (except for lists). partial #15. [de Graaff Rik - graaff]

* Added the funcitonality to request a UsernameChange. [David Lengweiler - lendav00]

* Added Namechange and Login callback in console. [David Lengweiler - lendav00]

* Added the PingManager object to the client. [David Lengweiler - lendav00]

* Added visual callback in chatPresentor. [David Lengweiler - lendav00]

* Completed the asSON functions in the ChatMessages. [Galery Käser Lucas - galluc00]

* Implemented asString method for SON #15. [Galery Käser Lucas - galluc00]

* Added a missing curly brace in SON. [Galery Käser Lucas - galluc00]

* Implemented put and get methods in SON. partial #15. [Galery Käser Lucas - galluc00]

* Implemented ConnectionManager methods in SocketConnectionManager. partial #15. [de Graaff Rik - graaff]

* Implmented Connection methods in SocketConnection. [Luc Kury - kurluc00]

* Implemented server-side ChatManager. close #19. [de Graaff Rik - graaff]

* Connected UserManager, ChatManager and ConsolePresenter. [de Graaff Rik - graaff]

* Kept the developer language consistent. [David Lengweiler - lendav00]

* Completed the first version ChatManager and ChatPresenter. closes #8,#11, #13, #14. [David Lengweiler - lendav00]

* Added UserDelegate object. partial #18. [David Lengweiler - lendav00]

* Created a ChatmessageOut object. partial #18. [David Lengweiler - lendav00]

* Added consoleManager object. [David Lengweiler - lendav00]

* Added chatdelegate for chat presenter. [David Lengweiler - lendav00]

* Connected the PingManager in the server initializer. [de Graaff Rik - graaff]

* Implemented the ping loop in PingManager. Server-side of #17 theoretically done. [de Graaff Rik - graaff]

* Added allowed abbreviation 'SON' to google checkstyle xml. [Galery Käser Lucas - galluc00]

* Updated the diary up to the latest added feature in cmd-messages. [Galery Käser Lucas - galluc00]

* Updated and maintained the diary a little bit. Still some stuff to improve. [Galery Käser Lucas - galluc00]

* Updated the dirary until sunday, march 18th. [Galery Käser Lucas - galluc00]

* Implemented basic functionality of server-side PingManager (still needs ping loop). partial #17. [de Graaff Rik - graaff]

* Implemented InMemoryPingStore. [de Graaff Rik - graaff]

* Defined server-side PingStore. [de Graaff Rik - graaff]

* Created Ping and Pong Messages. initial #17. [de Graaff Rik - graaff]

* Created ChatPresenter object. [David Lengweiler - lendav00]

* Initializer: Server/client actually initialize socket connections. [Luc Kury - kurluc00]

* SocketConnectionManager: Implemented Connection manager with serverSockets. [Luc Kury - kurluc00]

* SocketConnection: Test fixed messages in Ouputsteam. [Luc Kury - kurluc00]

* Refactored share SocketConnection class. [Luc Kury - kurluc00]

* Moved ClientSocketConnection to shared package. [Luc Kury - kurluc00]

* WIP: Started implementing threaded chat server. [Luc Kury - kurluc00]

* Added a ChatPresenter interface. partial #14. [David Lengweiler - lendav00]

* FINAL CHANGE TO GRADLE VERSION, I PROMISE! :-P. [Luc Kury - kurluc00]

* Gradle 4.6.0 specified in build.gradle. [Luc Kury - kurluc00]

* Less specific version of gradle in wrapper task. [Luc Kury - kurluc00]

* Fixed absolute path in build.gradle. Updated gradle wrapper to 4.6.0 and checked into git. [Luc Kury - kurluc00]

* Removed unused import. [David Lengweiler - lendav00]

* Updated gantt project plan in m2. [Luc Kury - kurluc00]

* Track pdf and pptx files with lfs. [Luc Kury - kurluc00]

* Cleaning repo structure. [Luc Kury - kurluc00]

* Updated all the .md files with current information. [Luc Kury - kurluc00]

* Added details to network protocol and QA documents. [Luc Kury - kurluc00]

* Defined details of the network protocol. [Luc Kury - kurluc00]

* Added username changing to server-side UserManager. partial #16. [de Graaff Rik - graaff]

* Created UsernameReq and UsernameRes messages. partial #16. [de Graaff Rik - graaff]

* Added a Main class so the project can actually be started. [de Graaff Rik - graaff]

* Added an initializer for the server-side code. closes #8. [de Graaff Rik - graaff]

* Correct formatting for ConnectionMessageDistributor. [de Graaff Rik - graaff]

* Implemented ConnectionMessageDistributor. closes #10. [de Graaff Rik - graaff]

* Implemented InMemoryUserStore. closes #9. [de Graaff Rik - graaff]

* Created server-side Usermanager. partial #9. [de Graaff Rik - graaff]

* Created an interface for MessageDistributor. partial #10. [de Graaff Rik - graaff]

* Defined the interface for the server-side UserStore. partial #9, #8. [de Graaff Rik - graaff]

* Created ConsolePresenter object. partial #6, #11, #12. [David Lengweiler - lendav00]

* Created userpresenter. partial #11. [David Lengweiler - lendav00]

* Added the readonlyuserstore. [David Lengweiler - lendav00]

* Added todo for client initalizer. [David Lengweiler - lendav00]

* Added ClientInitializer. partial #6. [David Lengweiler - lendav00]

* Added user, userstore and usermanager object. partial #6, #7. [David Lengweiler - lendav00]

* Created login res and login req objects. partial #6. [David Lengweiler - lendav00]

* Reformatted code according to google codestyle. [de Graaff Rik - graaff]

* Changed req. gradle wrapper version. [David Lengweiler - lendav00]

* Fixed output path. [David Lengweiler - lendav00]

* Updated hover likns. [David Lengweiler - lendav00]

* Modification to build.gradle. [Luc Kury - kurluc00]

* Test gradle.build file changes. [Luc Kury - kurluc00]

* Using alpine image. [Luc Kury - kurluc00]

* CI: Fixed absolut path. [Luc Kury - kurluc00]

* CI: Remove sudo. [Luc Kury - kurluc00]

* CI: Use sudo? [Luc Kury - kurluc00]

* CI: Don't use wrapper. [Luc Kury - kurluc00]

* CI: Maybe installing the wrapper again? [Luc Kury - kurluc00]

* New task in build.gradle file. [Luc Kury - kurluc00]

* CI debug info. [Luc Kury - kurluc00]

* CI debug. [Luc Kury - kurluc00]

* Ignore stuff. [Luc Kury - kurluc00]

* Changed docker image to non-alpine version. [Luc Kury - kurluc00]

* New yml file. Testing... [Luc Kury - kurluc00]

* Tidied up ignores. Removed tracking information. [Luc Kury - kurluc00]

* Modified yml file. [Luc Kury - kurluc00]

* Changed docker image for CI from java to gradle. [Luc Kury - kurluc00]

* Tampered with yml file. [Luc Kury - kurluc00]

* New yml file. [Luc Kury - kurluc00]

* New yml file. [Luc Kury - kurluc00]

* New ci/cd test. [Luc Kury - kurluc00]

* Test new CI/CD yaml config. [Luc Kury - kurluc00]

* Added ConnectionManager interface and made Connection and ConnectionManager Closable, might require some sort of registerCloseListener function at a later time. closes #3. [de Graaff Rik - graaff]

* Renamed SONRepresentationError -> SONConversionError. [de Graaff Rik - graaff]

* Defined Connection interface, required some changes and additions to son package, partial #3. [de Graaff Rik - graaff]

* Defined the SONRepresentable interface, closes #5. [de Graaff Rik - graaff]

* Renamed subliminal package to sublUminal. [de Graaff Rik - graaff]

* Minor changes to SON and SONList, added JavaDoc comments. completes #2. [de Graaff Rik - graaff]

* Defined interface of SONList. #2, still needs comments. [de Graaff Rik - graaff]

* Starting on #2, defined the interface of SON class. [de Graaff Rik - graaff]

* Working version of style check. [Luc Kury - kurluc00]

* Hotfix for app path in build.gradle. [Luc Kury - kurluc00]

* Working yml and build config. [Luc Kury - kurluc00]

* Added checktyle config folder. [Luc Kury - kurluc00]

* Added google code style in gradle. [Luc Kury - kurluc00]

* Ignored *.iml files, removed unnecessary gradle code. [David Lengweiler - lendav00]

* Updated link structure. [David Lengweiler - lendav00]

* Added some styling and a Disclaimer Comp. [David Lengweiler - lendav00]

* Md render working now for game rules. [David Lengweiler - lendav00]

* Updated markdown component. [David Lengweiler - lendav00]

* Switched to vue-md-loader. [David Lengweiler - lendav00]

* Added md interpreter for gamerules on hp. [David Lengweiler - lendav00]

* Set up some latex support for homepage. [David Lengweiler - lendav00]

* Fixed gradle output destination. [David Lengweiler - lendav00]

* Added 2 new files in doc. Modified project management related files. [Luc Kury - kurluc00]

* I created a separate file for the current version of the game rules. [Galery Käser Lucas - galluc00]

* Additional links from RIK to the resources.md. [David Lengweiler - lendav00]

* Brackets behind links, seem to break the link. [David Lengweiler - lendav00]

* IntelliJ Gradle Config updated. [Luc Kury - kurluc00]

* Specified /app/out as output folder in build.gradle. [de Graaff Rik - graaff]

* Changed source directories in build.gradle. [de Graaff Rik - graaff]

* Final IntelliJ config? [Luc Kury - kurluc00]

* Test IntelliJ config with new build.gradle. [Luc Kury - kurluc00]

* Removed tracking information from .gradle/ folder. [Luc Kury - kurluc00]

* New gitignore for gradle. [Luc Kury - kurluc00]

* Reintroduced .gradle folder to project. [Luc Kury - kurluc00]

* Added the docs/resources.md file. [Luc Kury - kurluc00]

* Forbidden: Played with gitignore directly in dev. Pesky xml files. [Luc Kury - kurluc00]

* Minor restyling. [David Lengweiler - lendav00]

* Added IntelliJ to project. [Luc Kury - kurluc00]

* Reformat table. [Luc Kury - kurluc00]

* Reformat table. [Luc Kury - kurluc00]

* Reformat table. [Luc Kury - kurluc00]

* Reformat table. [Luc Kury - kurluc00]

* Reformat table. [Luc Kury - kurluc00]

* Reformat table. [Luc Kury - kurluc00]

* Fixed table in README.md. [Luc Kury - kurluc00]

* Updated diary. [Galery Käser Lucas - galluc00]

* Updated with pull. [David Lengweiler - lendav00]

* Updated gitignore to include node_modules. [David Lengweiler - lendav00]

* Some styling stuff. [David Lengweiler - lendav00]

* Working gradle.build file. Modified directory stucture on the occasion. [Luc Kury - kurluc00]

* Introduced gradle to the project. Wont compilegit status! [Luc Kury - kurluc00]

* Stuff. [David Lengweiler - lendav00]

* Some minor stuff. [David Lengweiler - lendav00]

* Added a possible game_icon. [David Lengweiler - lendav00]

* Added a vuejs project, cause I can. [David Lengweiler - lendav00]

* Game icon and disclaimer added in todos. [David Lengweiler - lendav00]

* Minor link fixes. [David Lengweiler - lendav00]

* Cleaned some mess at the landingpage, new links HYPE. [David Lengweiler - lendav00]

* Fixed time consuming redirekt from httrack and updated mockup screenshot. [Luc Kury - kurluc00]

* Updated the links on the landing page. [David Lengweiler - lendav00]

* Clean web structure. [David Lengweiler - lendav00]

* Finished steam store page. [Luc Kury - kurluc00]

* Modified index.html to get rid of all the factorio mentions. Added some Subluminal related content. [Luc Kury - kurluc00]

* Steam store webpage source code introduced The page was cloned with httrack. The source code will be edited to host a fake steam store page with our game. [Luc Kury - kurluc00]

* Rearanged the order. [David Lengweiler - lendav00]

* Web folder in main, updated loading page. [David Lengweiler - lendav00]

* Added small story to readme, minor logo change. [David Lengweiler - lendav00]

* First version of project diary. [de Graaff Rik - graaff]

* Updated the contributors section of the main README file. [Luc Kury - kurluc00]

* These are the slides for milestone 1. [Galery Käser Lucas - galluc00]

* Added new Subluminal logo as psd file in 8bit style (as test) [Luc Kury - kurluc00]

* Keeping mockup structure clean. [David Lengweiler - lendav00]

* Tracking gui files and merch joke images. [datomo]

* Added company logo .png. [de Graaff Rik - graaff]

* Revert "Merge branch 'ft-project_plan-rik' into 'master'" [de Graaff Rik - graaff]

  This reverts merge request !6

* Small change to gantt file for merge request to work. [Luc Kury - kurluc00]

* Added resources to gantt project. [de Graaff Rik - graaff]

* Added predecessors and duration to all tasks. [de Graaff Rik - graaff]

* Predecessors Milestone 1-3. [de Graaff Rik - graaff]

* Protocol.md was not the diary but the specification of the network protocol. [overactor]

* Updated repository structure and some markdown files. [Luc Kury - kurluc00]

* Initialized repository. [Luca Rossetto]

* Added gitignore and LICENSE file. [Luc Kury]

* Initial commit. [Luc Kury]


