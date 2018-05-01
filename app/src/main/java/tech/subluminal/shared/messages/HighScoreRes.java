package tech.subluminal.shared.messages;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;

public class HighScoreRes implements SONRepresentable {

  private List<HighScore> highScores;

  public HighScoreRes(List<HighScore> highScores) {
    this.highScores = highScores;
  }

  public static HighScoreRes fromSON(SON son) throws SONConversionError {
    final List<HighScore> highScores = son.getList("highScores")
        .orElse(new SONList())
        .objects()
        .stream()
        .map(HighScore::fromSON)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());

    return new HighScoreRes(highScores);
  }

  public List<HighScore> getHighScores() {
    return highScores;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    SONList list = new SONList();
    highScores.stream().map(HighScore::asSON).forEach(list::add);

    return new SON().put(list, "highScores");
  }
}
