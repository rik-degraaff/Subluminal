Login:
C: HI <username>
S: HI <username1>

Logout:
C: BYE
S: BYE

Username:
C: NAME <username>
S: NAME <username1>

Lobbbies:
C: GETGAMES
S: GAMES
     <id> <name> <players> <started>
     [...]

C: MKGAME <name>
S: NEWGAME <id> <name>

C: JOINGAME <id>
S: JOINGAME <id>

C: READY <game-id>
S: READY <game-id>

C: STAT <game-id>
S: STAT <game-id> <name>
     <player> <ready>
     [...]

Chat:
C: SAY <game-id> <message>

S: SAID <game-id> <player> <message>

Game:
S: GAMESTART <game-id>
     PLANETS
       <planet-id> <x> <y> <resources>
       [...]
     PLAYERS
       <player> <planet-id>

C: STAT <game-id>
S: STAT <game-id>
     PLAYERS
       <player>
         [<x> <y>] <target-planet-id> // Mothership location
         [<x> <y>] <target-planet-id> <signal-time> <ship-id> [...]
         [...]
       [...]
     PLANETS
       <planet-id> [<player> <capture-percent>]
       [...]

C: SIGNAL <game-id>
     <planet-id> <target-planet-id> <move-percent>

C: MOVE <game-id>
      <target-planet-id>

C: LEAVE <game-id>