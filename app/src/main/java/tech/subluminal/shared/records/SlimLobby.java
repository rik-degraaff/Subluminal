package tech.subluminal.shared.records;

import tech.subluminal.shared.stores.records.Identifiable;

public class SlimLobby extends Identifiable {

  // Lobby properties
  private String name;
  private String adminID;
  private int minPlayers = 2;
  private int maxPlayers = 8;

  public SlimLobby(
      String id, String name, String adminID) {
    super(id);
    this.name = name;
    this.adminID = adminID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdminID() {
    return adminID;
  }

  public void setAdminID(String adminID) {
    this.adminID = adminID;
  }

  public int getMinPlayers() {
    return minPlayers;
  }

  public void setMinPlayers(int minPlayers) {
    this.minPlayers = minPlayers;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }
}
