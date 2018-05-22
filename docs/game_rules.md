# Game Rules

1. The maximum number of players is 8.
2. At the beginning of a game, the map is created randomly.
3. The map consists of stars.
4. At the beginning of a game, every player's mothership is randomly assigned to a star.
5. You lose the game when your mothership dies.
6. You win the game when your mothership is the only remaining mothership on the map.
7. Every player is able to see the whole map.
8. When a player colonizes a star, the star automatically starts to produce ships for the player, which accumulate on that star.
9. The ships that are local to a star that is owned by a player, can be sent to neighbouring stars as fleets.
10. Stars can be colonized by sending fleets to them. If a star already belongs to another player, dematerialization occurs between the two fleets and the winning player (the one with the bigger fleet) keeps the star.
11. Since the fleets have to fuel up at some point, the maximum jumping distance between two stars is restricted.
12. The rule above implicates	that further destinations can only be reached by one's ships by "hopping" from star to star to be able to fuel up.
13. A pathfinding algorithm computes the shortest path between the origin and target star. The target star can only be reached by that path.
14. "Hopping" can occur on both neutral and owned (colonized) stars.
15. By hopping onto a star that is owned by an opponent, the hopping player gets punished by dematerialization of a small part of his hopping fleet.
16. Hopping and colonizing stars are two separate processes. Accidental colonization while hopping a star can not occur.
17. Every move one’s ships make originates from the mothership. Thus, the order takes longer to get to the concerning ships, the further their base star is away from the mothership.
18. The rule above also implies that, the further away from one's mothership something happens, the more outdated the respective information is when it arrives at a player (i.e. his mothership).
19. When a player orders a fleet to go colonize another star, the information about the number of available regular ships is always outdated (because of the distance). So the player simply enters the maximum number of ships to be sent and, if there are not that many, all available ships are sent.
20. Every event on the map is broadcasted to all the players and reaches a player's mothership according to the time delay caused by the distance.
21. The mothership can also be moved from her base, it's slower than ships.
22. The mothership is as strong as 5 ships.
23. If the base of the mothership is intruded, the mothership is the last one to be torn down.