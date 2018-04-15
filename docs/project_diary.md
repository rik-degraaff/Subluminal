
# Project Diary

**DAV** = David Lengweiler  
**LGK** = Lucas Galery Käser  
**LUC** = Luc Kury  
**RIK** = Rik de Graaff

### Friday-Sunday, April 13th-15th, 2018
- Fine-tuning and optimization of user interface, embedding of lobby functionality in GUI.
- Implementation of server game logic within game loop was finished.
- Gantt and diary were polished and made milestone-presentable.
- Added missing javadocs, minimized checkstyle errors.

### Thursday, April 12th, 2018
- **DAV** and **LUC** started worked on the necessary message packages for lobby functionalities and also the client- and server-sided lobby managers. **LGK** and **RIK** worked on client- and server-sided game logic and game loop.
- **LUC** optimized CI in GitLab and enabled advanced command line parsing using picocli.
- **LUC** also created the template for our game manual.

### Monday, April 9th, 2018
- In the future, the chat is going to contain five channels:
  - WHISPER, GAME and GLOBAL, which can be used by the players intentionally.
  - INFO and CRITICAL, which are used by the server's and clients' systems.
  - INFO will inform players about stuff like new joined players or name changes and will be mutable by the players. CRITICAL cannot be muted and will send warnings or more important messages to players, for example if a player tries to whisper to a non-existing player.
- Stars on the map are going to be represented as white circles and ships/fleets as isoceles triangles.
- The shortest jumping path between two stars is going to be determined by a pathfinding algorithm and is going to be shown in the GUI as a line intersecting all the stars on the path.

### Thursday, April 5th, 2018
- For now, we are going to test the following classes:
    - SON
    - GameStateDelta
    - Graph
- After milestone 3, we are probably going to unit-test some of the messages to make sure that they are parsed correctly.

### Wednesday, April 4th, 2018
- A game store will store the the game state.
- A game manager will listen to game state deltas which contain the features that need to be updated. The game manager then updates these features. It will also take care of the information delay, which is a key feature in our game. 
- The mentioned game state delta message will contain the changes to be made to the game state.
- The GUI background view will be a dynamic outer space view, which makes the viewer feel like he or she is in a cockpit of a space ship, travelling at a really high speed. The lobby chat window will be at the bottom right corner of the window, in a command line-like design.
- To make things easier, for milestone 3 we are simply going to implement the movement of motherships, the colonization of stars by the motherships and the confrontation of two motherships on the same star. 

### Saturday, March 31st, 2018
- We are going to define Lobby-Stores because it is easier to store Users and Lobbies separately.
- **DAV** and **LUC** are going to be working on the client and server lobby functionality and GUI.
- **LGK** and **RIK** are going to be working on the client and server game logic.
- For the reconnection procedure we need later, our current idea is: If a player loses connection to the game, a cookie file is generated and temporarily stored on the client. This file contains data the client needs to send a reconnection request to the server.
- Name changing during a game shall not be possible. However, this issue has a low priority.
- Nice-to-haves:
    - ship fleets are represented by swarms
    - game speed settings
    - reconnection to lobby with password
    - reconnection to game via user ID
- Representation of the current structure a single game state will have on the client side:
```
gameState
    |__listOfPlayers
    |   |
    |   |__<Player_X>
    |   .       |__motherShip
    |   .       |       |__position
    |   .       |__listOfFleets
    |                   |__<Fleet_n°8>
    |                   |      |__n°ofShips
    |                   .      |__position/direction
    |                   .
    |                   .
    |__listOfStars
        |
        |__<Star_Y>
        |       |__coordinates
        .       |__owner possession
        .       |__owner ID (may be null)
        . 
```    

### Thursday, March 29th, 2018
- Justified master merges are now allowed anytime. Issue templates for example need to be in the master branch to be detected correctly. However, direct merges of source code into the master branch are still forbidden.
- Current to do's are:  
  - to fix the NetCat-Situation of M2 (to be specified)
  - to fix the faulty parsing of double quotes in strings
  - to improve the reaction logic of the server when it doesn't get the client's pong message anymore
  - To overwork the QA plan: Instead of the latency time, we want to track the client and server tick rates and achieve certain rates (which yet are to be set).
- Log4J and Mockito are external libraries we are going to use until M3.
### Sunday, March 25th, 2018
- Logout functionality implementation was finished.
- Protocol document was created.
- Source code documentation was updated.

### Friday, March 23rd, 2018
- DAV created a syntax for all "/"-commands for console messages.
- SON parsing functionality was implemented.
- Ping manager was implemented.
- Name change functionality was implemented.
- Logout functionality was starting to be implemented.
![coding with some pizza](../assets/pictures/m2_friday_pizza.jpg) 

### Thursday, March 22nd, 2018
- Added feature: When you prepend "/" or "@" to your command-line message you can add functionality to it, for example "/logout", or "@alex02". These changes were made in ConsolePresenter.java. 

### Wednesday, March 21st, 2018
- Long coding evening, implementing all methods of the SON.java class, the network chat functionality and the client-side chat presentation.
- Our tutor Marco recommended us to take better care of our diary.
![late night coding](../assets/pictures/m2_random_coding_session.jpg)

### Sunday, March 18th, 2018
We spent half a day implementing the first classes and interfaces.

### Wednesday, March 14th, 2018
- Our code style is going to be the Google Java code style.
- We specified the package structure for the project (in the app folder).
The structure is as follows:  

```
src
|
|__main   
     |
     |__java  
         |
         |__tech.subluminal
             |
             |__client
             |   |
             |   |__init
             |   |
             |   |__logic   
             |   | 
             |   |__presentation
             |   |
             |   |__stores
             |
             |__shared 
                 |
                 |__messages
                 |
                 |__net
                 |
                 |__records
                 |
                 |__son
                       
```

### Sunday, March 11th, 2018
After some discussion within the group and consulting the tutors, we decided to use our own implementation of the JSON format. We named it SON (**S**ubluminal **O**bject **N**otation). This format will be used to generalize the network communication between the client and the server. It is designed as an interface and thus allows us to parse any Java object into a transferable object.

### Thursday, March 9th, 2018
![Our Team at Milestone 1](../assets/pictures/m1_presentation.jpg)

### Monday, March 5th, 2018
In this meeting we decided about most of the rules of our game. They are:
##### _Rules_
- Possibly 2-n players, for the moment we decided the maximum number of players will be 8.
- At the beginning of a game, the map is created randomly.
- You lose the game when your mother ship does not exist anymore.
- You win the game when your mother ship is the only remaining mother ship on the map.
- Starting positions of the players are assigned randomly, but in a way no player has a big advantage or disadvantage.
- Every player is able to see the whole map.
- The map consists of stars, which also are solar systems. At the beginning of a game every player's mother ship is randomly assigned to a star.
- Each star has its own attributes, which influence:  
	a) The frequency of production of new regular ships.
	b) The basic defense of a star against intruders.
	c) How much effort it takes to colonize the star.
- If a player colonizes a new star, the star automatically starts producing regular ships for the player, which are local to that star.
- The regular ships that are local to a star that is owned by a player can be sent out by the player.
- Every order to move regular ships from one star to another originate from the mother ship.
- Each star also has an attribute that regulates the maximum radius a ship can reach without having to fuel up on another star.
- The rule above implicates	that further destinations can only be reached by one's regular ships by "hopping" from star to star to be able to fuel up.
- "Hopping" can occur on both neutral and owned (colonized) stars.
- By hopping onto a star that is owned by an opponent, the hopping player gets punished by automatic tear-down of a certain percentage of his hopping ships.
- If a player targets a certain star he or she can influence the hopping path his or her ships or use the one that is automatically calculated by the system, which is the shortest.
- Hopping stars and colonizing stars are two separate processes. Accidental colonization while hopping a star can not occur.
- When regular ships reach the target they hopped to, two scenarios can happen:
	a) Nobody is there yet, the star is neutral. The player's regular ships start colonizing the star. The speed of the colonization progress depends from the number of regular ships the player sent, the basic defense of the star and how much effort the star takes to be colonized.
	b) The star is already under possession of an opponent. In this case the fleets of the opponents start tearing down themselves, until only regular ships of one of the fleets remain. If the remaining regular ships belong to the intruder, they immediately start neutralizing the star to then colonize it. If the remaining regular ships belong to the previous owner of the star, nothing else happens.
- Every move one's regular ships make originates from the mother ship. Thus, the order takes longer to get to the concerning regular ships, the further their base star is away from the mother ship.
- When a player orders a fleet to go colonize another star, the information about the number of available regular ships is always outdated (because of the distance). So the player has to options:
	a) He/She sends out the order that a certain percentage of the regular ships should leave their base star.
	b) He/She sends out the order that a certain number or regular ships should be the maximum number of sent out ships. If there are not that many regular ships left the moment the order arrives at the base, all regular ships are sent out.
- Fleets of regular ships that are hopping to their target send out information to their mother ship about their position in regular intervals.
- The mother ship can also be moved from her base, it's slower than the regular ships.
- The mother ship has an own basic defense value.
- If the base of the mother ship is intruded, the mother ship is the last one to be torn down.
- Every mother ship and every regular ship broadcast their position so that every player can see it.

## Sunday, March 4th, 2018
- The language for all documents concerning the programming project will be English.
- The game concept will be concreted on Monday, March 5th.
- On Monday we will also make a final decision if the game is going to be round-based or in real time.

##### Task division
**LUC**: Definition of our software requirements for the project; brainstorming/design of our repository and branch structure and of our IntelliJ configuration  
**DAV**: Setup of a rudimentary merchandise website as a gag for the first milestone

### Friday, March 2nd, 2018
##### Decisions/ideas:
- **Ideas for game names**: Ether, Far Away, Separation, Delay, Subluminal
- **Team name**: Bordeaux Ink.
- **Basic game principle**: The game happens on an outer space map with stars. In the beginning every player has his main ship placed in a random planet system and produces his fleet of smaller ships with the resources that his planets offer. The goal is to conquer the biggest number of planet systems. The players can inform themselves about the position of their adversaries and in which state of colonization other planet systems are by sending out requests. The twist is that the messenger ships take some time to get to their target and back, so when they are back the information is already not the latest anymore and the player has to make decisions upon outdated information.

##### Task division:  
**DAV**: Mock-ups; graphical sketches  
**RIK**: Division client/server; definition of the network protocol  
**LGK**: Planning of the presentation; definition of the style and structure of upcoming presentations  
**LUC**: (Create a git-repository;) Planning of the structure of the git-repository; decision if we should use the IntelliJ-plugin for git