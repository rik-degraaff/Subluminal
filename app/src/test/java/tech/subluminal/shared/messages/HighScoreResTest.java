package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;
import tech.subluminal.server.stores.records.HighScore;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class HighScoreResTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    List<HighScore> scores = new ArrayList<>();
    scores.add(new HighScore("Ana", 19));
    scores.add(new HighScore("Sofia", 13));
    HighScoreRes h = new HighScoreRes(scores);
    String hMsg = h.asSON().asString();
    HighScoreRes parsedH = null;
    List<HighScore> parsedScores = new ArrayList<>();

    parsedH = HighScoreRes.fromSON(SON.parse(hMsg));
    parsedScores = parsedH.getHighScores();
    System.out.println(hMsg);
    boolean containsName = false;
    boolean scoreIsRight = false;


    for (int i = 0; i < scores.size(); i++) {
      containsName = false;
      scoreIsRight = false;
      for (int j = 0; j < parsedScores.size(); j++) {
        if (scores.get(i).getUsername().equals(parsedScores.get(j).getUsername())) {
          containsName = true;
          if (scores.get(i).getScore() == parsedScores.get(j).getScore()) {
            scoreIsRight = true;
          }
        }
      }
    }
    assertTrue(containsName);
    assertTrue(scoreIsRight);
    assertEquals(scores.size(), parsedScores.size());


  }
}
