package tech.subluminal.server.stores.records;

import java.util.Optional;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a highscore set by a player.
 */
public class HighScore implements SONRepresentable {

  private final String username;
  private final double score;

  public HighScore(String username, double score) {
    this.username = username;
    this.score = score;
  }

  public static Optional<HighScore> fromSON(SON son) {
    return son.getString("username").flatMap(username ->
        son.getDouble("score").map(score ->
            new HighScore(username, score)));
  }

  /**
   * @return the name of the user the highscore belongs to.
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the score the user achieved.
   */
  public double getScore() {
    return score;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(username, "username")
        .put(score, "score");
  }
}
