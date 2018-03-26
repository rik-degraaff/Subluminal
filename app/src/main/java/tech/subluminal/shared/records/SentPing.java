package tech.subluminal.shared.records;

/**
 * Represents a ping that was sent to the server/client.
 */
public class SentPing {

  private long sentTime;
  private String id;

  /**
   * Creates a ping sent record with a sent time and an id.
   *
   * @param sentTime the time (currentmillis) when the ping was sent
   * @param id the id of the ping that was sent
   */
  public SentPing(long sentTime, String id) {
    this.sentTime = sentTime;
    this.id = id;
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
   * Returns the id of a sent ping.
   *
   * @return the id of the sent ping.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id of a sent ping.
   *
   * @param id the id of the sent ping.
   */
  public void setId(String id) {
    this.id = id;
  }
}
