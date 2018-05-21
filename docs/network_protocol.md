# Network protocol
Our implementation of a network protocol is strongly influenced by JSON, we called it **SON** (**S**ubluminal **O**bject **N**otation). The packets are constructed like objects. Before sending all, whitespace is removed and the keys and values are concatenated with their respective delimiters. The only whitespace remaining is a single space between package type and message content.

## Supported commands
### /changename
***Client***: Sets new username.
``/changename <new username>``

### /logout
***Client***: Closes your connection to the server and exits the client.
``/logout`` (no arguments)

### chat
***Client***
```@all - Send message to all games```
```@game - Send message to game/lobby only```
```@<user> - Send message to specific user```

## Protocol properties
- Package length/size is not calculated and transmitted.
- The packet preamble contains only the type.
- Between preamble and message content, there is a single ``space`` as delimiter.
- A packet is terminated by the newline character ``\r\n``.
- Object notation: 
  - Objects: ``{ }``
  - Lists: ``[ ]``
  - Key/Value delimiter: ``:``
  - Field separator: ``,``


## Protocol message structure
Each message can contain zero or more values of the following java variable types: ``boolean (b)``, ``integer (i)``, ``double (d)``, ``String (s)``, ``list<SON> (l)`` and our own defined type ``SON (o)``. The key is always a string, enclosed in quotes (parsing help). The value is a string representation of its original value, enclosed in quotes (parsing help), prepended with a char to represent its original type for later parsing. 

**As of milestone 3 the message packets are documented in the source code directly ([here](../app/src/main/java/tech/subluminal/shared/messages/))**. Below, we an example of the stringified version of each message type. The values of the types may not be meaningful nor realistic.


### ChatMessageIn
The chat message which is received by the client from the server.  
Example:
```
ChatMessageIn {
  "channel":s"CRITICAL",
  "message":s"!Hello!",
  "username":s"Luke"
}
```
### ChatMessageOut
The chat message object which was built by the client for the server.  
Example:
```
ChatMessageOut {
  "receiverID":s"1234",
  "message":s"Who let the dogs out?"
}  
```
### ClearGame
Represents the clear game message from the server to the client.  
Example:
```
ClearGame {}
```
### EndGameRes
The message which is sent to all clients when a game ends.  
Example:
```
EndGameRes {
  "game":s"4054",
  "winner":s"4053"
}
```
### FleetMoveReq
Represents a fleet move request from a client to the server.  
Example:
```
FleetMoveReq {
  "amount":i3,
  "originID":s"1234",
  "starList":l[
    s"1234",
    s"2345",
    s"3456"
  ]
}
```
### GameLeaveReq
Message that is sent from a leaving client to the server.  
Example:
```
GameLeaveReq {}
```
### GameLeaveRes
Message that is sent from a server to a leaving client.  
Example:
```
GameLeaveRes {}
```
### GameStartReq
Represents a game start request from a client to the server.  
Example:
```
GameStartReq {}
```
### GameStartRes
Represents the response of the server to a game start request from the client.  
Example:
```
GameStartRes {
  "tps":d9.95,
  "gameID":s"0123",
  "playerColors":l[
    o{
      "id":s"1235",
      "color":o{
        "red":d0.46941840648651123,
        "green":d0.3046164810657501,
        "blue":d0.49118220806121826
      }
    },
    o{
      "id":s"1234",
      "color":o{
        "red":d0.4259662926197052,
        "green":d0.24026921391487122,
        "blue":d0.3313233256340027
      }
    },
    o{
      "id":s"3234",
      "color":o{
        "red":d0.4506761431694031,
        "green":d0.25912126898765564,
        "blue":d0.11660823225975037
      }
    }
  ]
}
```
### GameStateDelta
Contains the changes that need to be made to the game state.  
Example:
```
GameStateDelta {
  "players":l[
    o{
      "motherShip":o{
        "movable":o{
          "speed":d0.2,
          "target":s"3",
          "gameObject":o{
            "identifiable":o{
              "id":s"4321"
            },"coordinates":o{
              "x":d1.0,
              "y":d2.3
            }
          },
          "targets":l[
            s"1",
            s"2",
            s"3"
          ]
        }
      },
      "identifiable":o{
        "id":s"1234"
      },
      "fleets":l[
        o{
          "amount":i17,
          "movable":o{
            "speed":d0.2,
            "target":s"3",
            "gameObject":o{
              "identifiable":o{
                "id":s"4321321"
              },
              "coordinates":o{
                "x":d1.0,
                "y":d2.3
              }
            },
            "targets":l[
              s"2",
              s"3"
            ]
          }
        },
        o{
          "amount":i17,
          "movable":o{
            "speed":d0.2,
            "target":s"3",
            "gameObject":o{
              "identifiable":o{
                "id":s"4qd321"
              },
              "coordinates":o{
                "x":d0.0,
                "y":d2000.13
              }
            },
            "targets":l[
              s"1",
              s"2",
              s"3"
            ]
          }
        },
        o{
          "amount":i17,
          "movable":o{
            "speed":d0.2,
            "target":s"2",
            "gameObject":o{
              "identifiable":o{
                "id":s"432gte1"
              },
              "coordinates":o{
                "x":d1.0,
                "y":d12.3
              }
            },
            "targets":l[
              s"2"
            ]
          }
        }
      ]
    },
    o{
      "motherShip":o{
        "movable":o{
          "speed":d0.2,
          "target":s"5",
          "gameObject":o{
            "identifiable":o{
              "id":s"4321"
            },
            "coordinates":o{
              "x":d10.0,
              "y":d2.3
            }
          },
          "targets":l[
          ]
        }
      },
      "identifiable":o{
        "id":s"2345"
      },
      "fleets":l[
      ]
    }
  ],
  "removedFleets":l[
    o{
      "key":s"1234",
      "value":l[
        s"645",
        s"123"
      ]
    },
    o{
      "key":s"4321",
      "value":l[
        s"345",
        s"134vj"
      ]
    }
  ],
  "stars":l[
    o{
      "possession":d1.0,
      "jump":d0.1,
      "generating":btrue,
      "name":s"Twinkle",
      "ownerID":s"1234",
      "gameObject":o{
        "identifiable":o{
          "id":s"starid"
        },
        "coordinates":o{
          "x":d0.0,
          "y":d0.0
        }
      }
    },
    o{
      "possession":d0.0,
      "jump":d0.3,
      "generating":bfalse,
      "name":s"TwinkleTwinkle",
      "gameObject":o{
        "identifiable":o{
          "id":s"starid2"
        },
        "coordinates":o{
          "x":d0.0,
          "y":d42.0
        }
      }
    }
  ],
  "removedPlayers":l[
    s"ftdq"
  ]
}
```
### HighScoreReq
Represents a request message for the highscore.  
Example:
```
HighScoreReq {}
```
### HighScoreRes
Represents an answer to a highscore request and contains the highscore in form of a list.  
Example:
```
HighScoreRes {
  "highScores":l[
    o{
      "score":d19.0,
      "username":s"Ana"
    },
    o{
       "score":d13.0,
       "username":s"Sofia"
    }
  ]
}
```
### InitialUsers
Represents the initial users connected to the server.  
Example:
```
InitialUsers {
  "users":l[
    o{
      "username":s"sölkdfa",
      "identifiable":o{
        "id":s"2917"
      }
    },
    o{
      "username":s"asdöflkj",
      "identifiable":o{
        "id":s"049851"
      }
    }
  ]
}
```
### LobbyCreateReq
Represents a lobby create request from the client to the server.  
Example:
```
LobbyCreateReq {
  "name":s"greatest lobby ever"
}
```
### LobbyCreateRes
Represents a lobby create response from the server to the client.  
Example:
```
LobbyCreateRes {
  "id":s"4053"
}
```
### LobbyJoinReq
Represents a request from client to server to join a lobby.  
Example:
```
LobbyJoinReq {
  "id":s"4053"
}
```
### LobbyJoinRes
Represents a response from server to client with the joined lobby and sends the users that are
already in the lobby.  
Example:
```
LobbyJoinRes {
  "lobby":o{
    "playerCount":i0,
    "status":s"INGAME",
    "settings":o{
      "maxPlayers":i8,
      "minPlayers":i2,
      "mapSize":d2.0,
      "gameSpeed":d1.0,
      "name":s"Batman",
      "adminID":s"9000"
    },
    "identifiable":o{
      "id":s"1729"
    },
    "users":l[
    ]
  }
}
```
### LobbyLeaveReq
Represents the lobby leave request from the client to the server.  
Example:
```
LobbyLeaveReq {}
```
### LobbyLeaveRes
Represents the lobby leave response from the server to the client.  
Example:
```
LobbyLeaveRes {}
```
### LobbyListReq
Represents a lobby list request from a client to the server.  
Example:
```
LobbyListReq {}
```
### LobbyListRes
Represents a lobby list response form server to client after a lobby list request.  
Example:
```
LobbyListRes {
  "lobbies":l[
    o{
      "playerCount":i0,
      "status":s"INGAME",
      "settings":o{
        "maxPlayers":i8,
        "minPlayers":i2,
        "mapSize":d2.0,
        "gameSpeed":d1.0,
        "name":s"weoi",
        "adminID":s"3472"
      },
      "identifiable":o{
        "id":s"9439"
      }
    },
    o{
      "playerCount":i0,
      "status":s"FINISHED",
      "settings":o{
        "maxPlayers":i8,
        "minPlayers":i2,
        "mapSize":d2.0,
        "gameSpeed":d1.0,
        "name":s"eruo",
        "adminID":s"1338"
      },
      "identifiable":o{
        "id":s"1790"
      }
    },
    o{
      "playerCount":i0,
      "status":s"OPEN",
      "settings":o{
        "maxPlayers":i8,
        "minPlayers":i2,
        "mapSize":d2.0,
        "gameSpeed":d1.0,
        "name":s"sdfu",
        "adminID":s"1237"
      },
      "identifiable":o{
        "id":s"1295"
      }
    }
  ]
}
```
### LobbyUpdateReq
Represents a lobby update request message from client to server.  
Example:
```
LobbyUpdateReq {
  "settings":o{
    "maxPlayers":i5,
    "minPlayers":i3,
    "mapSize":d3.14,
    "gameSpeed":d9.81,
    "name":s"Rolf",
    "adminID":s"8000"
  }
}
```
### LobbyUpdateRes
Represents a lobby update response message from server to client.  
Example:
```
LobbyUpdateRes {
  "settings":o{
    "maxPlayers":i5,
    "minPlayers":i3,
    "mapSize":d3.14,
    "gameSpeed":d9.81,
    "name":s"Rolf",
    "adminID":s"8000"
  }
}
```
### LoginReq
Represents a login request from client to server.  
Example:
```
LoginReq {
  "username":s"Bob"
}
```
### LoginRes
Represents the login response from the server to the client.  
Example:
```
LoginRes {
  "userID":s"1111",
  "username":s"Patrick"
}
```
### LogoutReq
Represents the logout request from the client to the server.  
Example:
```
LogoutReq {}
```
### MotherShipMoveReq
Represents the request from a client to the server to move the mothership.  
Example:
```
MotherShipMoveReq {
  "starList":l[
    s"9876",
    s"8765",
    s"7654"
  ]
}
```
### Ping
Represents a ping from client to server or vice versa.  
Example:
```
Ping {
  "id":s"1234"
}
```
### PlayerJoin
Represents the message that a player joined.  
Example:
```
PlayerJoin {
  "user":o{
    "username":s"Luc",
    "identifiable":o{
      "id":s"4107"
    }
  }
}
```
### PlayerLeave
Represents the message that a player left.  
Example:
```
PlayerLeave {
  "id":s"911"
}
```
### PlayerUpdate
Updates player usernames.  
Example:
```
PlayerUpdate {
  "id":s"007",
  "username":s"Rik"
}
```
### Pong
Represents a pong (an answer to a ping) from client to server or vice versa.  
Example:
```
Pong {
  "id":s"1234"
}
```
### ReconnectReq
Represents a reconnect request from client to server.  
Example:
```
ReconnectReq {
  "id":s"his mom's basement",
  "username":s"Bob"
}
```
### SpecateGameReq
Represents a request from client to server to enter spectate mode.  
Example:
```
SpectateGameReq {
  "id":s"8000"
}
```
### StartTutorialReq
Represents the start tutorial request from the client to the server.  
Example:
```
StartTutorailReq {}
```
### Toast
Represents a text message the server wants the client to show on the screen.  
Example:
```
Toast {
  "permanent":btrue,
  "message":s"Snoop Dogg"
}
```
### UsernameReq
Represents a username change request from the client to the server.  
Example:
```
UsernameReq {
  "username":s"Pingu"
}
```
### UsernameRes
Represents a username change response from the server to the client.  
Example:
```
UsernameRes {
  "username":s"Donald"
}
```
### YouLose
A message that is sent from the server to the client when the particular player loses in a game.  
Example:
```
YouLose {}
```

## Diagram
![UML Diagram of basic messaging](./../assets/other/basic_messaging1.png)  
Example: Sending a ``LoginReq`` message from client to server. 