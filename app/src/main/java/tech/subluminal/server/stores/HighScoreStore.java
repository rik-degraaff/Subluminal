package tech.subluminal.server.stores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.records.GlobalSettings;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.util.ConfigModifier;
import tech.subluminal.shared.util.RemoteSynchronized;
import tech.subluminal.shared.util.Synchronized;

/**
 * A Store which contains the high scores stored in a file.
 */
public class HighScoreStore {

  private static ConfigModifier cm;
  private static String HIGHSCORES_PATH;
  private final Synchronized<List<HighScore>> highScores = new RemoteSynchronized<>(
      HighScoreStore::getHighScoresFromFile,
      HighScoreStore::writeHighscoresToFile);

  public HighScoreStore() {
    if (!GlobalSettings.PATH_JAR.equals("")) {
      cm = new ConfigModifier("highscore");
      cm.attachToFile(GlobalSettings.FILE_HIGHSCORE);
      HIGHSCORES_PATH = cm.getAttachedFile().getPath();
    } else {
      HIGHSCORES_PATH = "./files/highscores.son";
    }
  }

  private static List<HighScore> getHighScoresFromFile() {
    List<HighScore> list = new LinkedList<>();

    try {
      Scanner sc = new Scanner(new File(HIGHSCORES_PATH));
      while (sc.hasNext()) {
        try {
          HighScore.fromSON(SON.parse(sc.next())).ifPresent(list::add);
        } catch (SONParsingError sonParsingError) {
          // ignore improperly formatted lines
        }
      }
    } catch (FileNotFoundException e) {
      // if the file doesn't exist, we just return nothing
    }

    return list;
  }

  private static void writeHighscoresToFile(List<HighScore> highScores) {
    File file = new File(HIGHSCORES_PATH);
    try {
      if (!file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }

      final String scores = highScores.stream()
          .map(hs -> hs.asSON().asString())
          .reduce("", (acc, s) -> acc + System.lineSeparator() + s);

      final PrintWriter pw = new PrintWriter(file);
      pw.print(scores);
      pw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Synchronized<List<HighScore>> highScores() {
    return highScores;
  }
}
