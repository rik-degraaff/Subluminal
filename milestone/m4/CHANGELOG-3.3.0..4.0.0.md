## 4.0.0 (2018-05-07)

### New

* Random starnames from txt files with keyword *star* in their names. [Luc Kury]

### Other

* Fixed bug where pongs are received before the ping was added to the pingstore. [de Graaff Rik - graaff]

* Leaves lobby when game leave. [David Lengweiler lendav00]

* Changed stars count for background back to 1000. [David Lengweiler lendav00]

* Bumped version for release. [Luc Kury]

* Finished updating the diary for M4. [Galery Käser Lucas - galluc00]

* Started updating the diary for M4. [Galery Käser Lucas - galluc00]

* Improved build-cs108 gradle task. [de Graaff Rik - graaff]

* Back to lobby from endgamecompoent fixed. [David Lengweiler lendav00]

* Clean up game a while after leaving the game. [de Graaff Rik - graaff]

* Updated qa document with screenshots. [Luc Kury]

* Remove fleet and ships from the map when they are destroyed again. [de Graaff Rik - graaff]

* Removed unnecessary system out. [David Lengweiler lendav00]

* Fix pipeline error in LobbyCreateReqTest. [Galery Käser Lucas - galluc00]

* Added string representation to the first 6 message types and removed unnecessary try/catch blocks from their corresponding unit tests. [Galery Käser Lucas - galluc00]

* Added SON.toString example to javadoc in Ping. [Galery Käser Lucas - galluc00]

* Implemented tests for LobbyListReq, LobbyUpdateReq, LobbyUpdateRes and LoginRes and added classes/ to gitignore. [Galery Käser Lucas - galluc00]

* Implemented test for LobbyJoinReq/-Res and LobbyLeaveReq/-Res. [Galery Käser Lucas - galluc00]

* Created LobbyCreateReq/-ResTest. [Galery Käser Lucas - galluc00]

* New Screenshots, more information in README, added pdfs for all markdown files. [Luc Kury]

* Send GameLeaveRes when a player requests to leave a game. [de Graaff Rik - graaff]

* Fixed nullpointer when delta arrives after game has been left. [de Graaff Rik - graaff]

* Change observable list in identifiable collection to be nullsafe. [de Graaff Rik - graaff]

* Game is playable if left. [David Lengweiler lendav00]

* Graph needs to be recalculated. [David Lengweiler lendav00]

* Adjusted tests to deal with new Player structure. [de Graaff Rik - graaff]

* Clear the fleet and ship list. [David Lengweiler lendav00]

* Make sure players no longer get updates about a game they're no longer in. [de Graaff Rik - graaff]

* Fixed endgame styling. [David Lengweiler lendav00]

* Fixed pat endgame. [David Lengweiler lendav00]

* Fixed possible null in endgameres. [David Lengweiler lendav00]

* Pane for the endgame component. [David Lengweiler lendav00]

* Fixed an error where a fleet with one ship can defeat a mother ship. closes #51 (hopefully) [de Graaff Rik - graaff]

* Fixed gamestartres. [David Lengweiler lendav00]

* End gamecomponent in hbox. [David Lengweiler lendav00]

* Endgame needs a height. [David Lengweiler lendav00]

* Binding has to be on run later. [David Lengweiler lendav00]

* End game component should now be centered. [David Lengweiler lendav00]

* Adding the component later. [David Lengweiler lendav00]

* End game should now work properly. [David Lengweiler lendav00]

* Modified the GameManagerTests to deal with the new Player structure. [de Graaff Rik - graaff]

* Send players a package notifying them the game has ended and shut down the game. partial #52. [de Graaff Rik - graaff]

* Removing motherships when destroyed, partial #52. [David Lengweiler lendav00]

* Fixed NPE when fleet is created and destroyed in the same tick. [de Graaff Rik - graaff]

* Revert "ships should now rotate correctly and display the number" [Lengweiler David - lendav00]

  This reverts commit c6d5744782924650856d516cdb9b6fe6478ffacd

* Ships should now rotate correctly and display the number. [David Lengweiler lendav00]

* Actually update fleets when they lose ships. #51. [de Graaff Rik - graaff]

* Made the game logic more testable and fixed dematerialization bug. partial #51. [de Graaff Rik - graaff]

* Notify player when they lose. partial #52. [de Graaff Rik - graaff]

* Modified tests for GameStateDelta to account for MotherSHips being optional. [de Graaff Rik - graaff]

* Don't add destroyed motherShips to InttermediateGameState. [de Graaff Rik - graaff]

* Ignore requests from the dead. [de Graaff Rik - graaff]

* Allow for players to no longer have a mothership. partial #51, #52. [de Graaff Rik - graaff]

* Added a preliminary impementation of dematerialization. partial #51. [de Graaff Rik - graaff]

* Some chat styling. [David Lengweiler lendav00]

* Jumppaths should be fixed. [David Lengweiler lendav00]

* Moved assets, bumped verison, README updates for new interface. [Luc Kury]

* Fixed task name with quotes. [Luc Kury]

* Added changelog. [Luc Kury]

* Created gradle task according to tutors specifications. [Luc Kury]

* Started fixing minor styling things. [David Lengweiler lendav00]

* Ships should now rotate correctly and display the number. [David Lengweiler lendav00]

  (cherry picked from commit c6d5744782924650856d516cdb9b6fe6478ffacd)

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


