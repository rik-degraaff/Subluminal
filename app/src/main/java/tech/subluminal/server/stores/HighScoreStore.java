package tech.subluminal.server.stores;

import java.util.Collections;
import java.util.List;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.util.RemoteSynchronized;
import tech.subluminal.shared.util.Synchronized;

public class HighScoreStore {

  Synchronized<List<HighScore>> highScores = new RemoteSynchronized<>(
      HighScoreStore::getHighScoresFromFile,
      HighScoreStore::writeHighscoresToFile);

  public Synchronized<List<HighScore>> highScores() {
    return highScores;
  }

  private static List<HighScore> getHighScoresFromFile() {
    //TODO: read from file
    return Collections.emptyList();
  }

  private static void writeHighscoresToFile(List<HighScore> highScores) {
    //TODO: write to file
  }
}
