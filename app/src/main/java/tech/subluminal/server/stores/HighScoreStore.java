package tech.subluminal.server.stores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.util.RemoteSynchronized;
import tech.subluminal.shared.util.Synchronized;

public class HighScoreStore {

  private final Synchronized<List<HighScore>> highScores = new RemoteSynchronized<>(
      HighScoreStore::getHighScoresFromFile,
      HighScoreStore::writeHighscoresToFile);

  private static List<HighScore> getHighScoresFromFile() {
    Scanner sc = new Scanner(
        HighScoreStore.class.getResourceAsStream("/tech/subluminal/resources/highscores.son"));

    List<HighScore> list = new LinkedList<>();

    while (sc.hasNext()) {
      try {
        HighScore.fromSON(SON.parse(sc.next())).ifPresent(list::add);
      } catch (SONParsingError sonParsingError) {
        sonParsingError.printStackTrace(); // TODO: handle this
      }
    }

    return list;
  }

  private static void writeHighscoresToFile(List<HighScore> highScores) {
    File file = new File(
        HighScoreStore.class.getResource("/tech/subluminal/resources/highscores.son").getPath());

    try {
      new PrintWriter(file).print(highScores.stream().map(hs -> hs.asSON().asString())
          .reduce("", (acc, s) -> acc + System.lineSeparator() + s));
    } catch (FileNotFoundException e) {
      e.printStackTrace(); // TODO: handle this more graciously
    }
  }

  public Synchronized<List<HighScore>> highScores() {
    return highScores;
  }
}
