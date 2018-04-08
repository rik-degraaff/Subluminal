package tech.subluminal.shared.stores.records;

/**
 * Represents a ping that was sent to the server/client.
 */
public class SentPing extends Identifiable {

  private long sentTime;
  private String userID;

  /**
   * Creates a ping sent record with a sent time a user id and an id.
   *
   * @param sentTime the time (currentmillis) when the ping was sent.
   * @param userID the id of the user this ping was sent to.
   * @param id the id of the ping that was sent.
   */
  public SentPing(long sentTime, String userID, String id) {
    super(id);
    this.sentTime = sentTime;
    this.userID = userID;
  }

  /**
   * Returns the time the ping was sent.
   *
   * @return the time when the ping was sent.
   */
  public long getSentTime() {
    return sentTime;
  }

  /**
   * Sets the sending time of a sent ping.
   *
   * @param sentTime the time the ping was sent.
   */
  public void setSentTime(long sentTime) {
    this.sentTime = sentTime;
  }

  /**
   * @return the id of the user the ping was sent to.
   */
  public String getUserID() {
    return userID;
  }

  /**
   * @param userID the id of the user the ping was sent to.
   */
  public void setUserID(String userID) {
    this.userID = userID;
  }
}
