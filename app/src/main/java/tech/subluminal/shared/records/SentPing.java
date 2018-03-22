package tech.subluminal.shared.records;

/**
 * Represents a ping that was sent to the server/client.
 */
public class SentPing {

  private long sentTime;
  private String id;

  /**
   * Creates a ping sent record with a sent time and an id
   *
   * @param sentTime the time (currentmillis) when the ping was sent
   * @param id the id of the ping that was sent
   */
  public SentPing(long sentTime, String id) {
    this.sentTime = sentTime;
    this.id = id;
  }

  /**
   * @return the time when the ping was sent.
   */
  public long getSentTime() {
    return sentTime;
  }

  /**
   * @param sentTime teh time when the ping was sent.
   */
  public void setSentTime(long sentTime) {
    this.sentTime = sentTime;
  }

  /**
   * @return the id of the sent ping.
   */
  public String getId() {
    return id;
  }

  /**
   * @param id teh id of the sent ping.
   */
  public void setId(String id) {
    this.id = id;
  }
}
