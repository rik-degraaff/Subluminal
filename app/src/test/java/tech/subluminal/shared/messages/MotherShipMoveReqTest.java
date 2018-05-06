package tech.subluminal.shared.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class MotherShipMoveReqTest {

  private List<String> stars;
  private MotherShipMoveReq moveReq;

  @Before
  public void initialize() {
    this.stars = new ArrayList<>(Arrays.asList("9876", "8765", "7654"));
    this.moveReq = new MotherShipMoveReq(this.stars);
  }

  @Test
  public void testParsing() {
    String mMoveReqMsg = this.moveReq.asSON().asString();
    List<String> parsedStars = null;
    try {
      MotherShipMoveReq parsedMoveReq = MotherShipMoveReq.fromSON(SON.parse(mMoveReqMsg));
      parsedStars = parsedMoveReq.getTargets();
    } catch (SONParsingError | SONConversionError e) {
      e.printStackTrace();
    }
    Assert.assertTrue(this.stars.containsAll(parsedStars));
    System.out.println(mMoveReqMsg);
  }

  @Test
  public void testErrorThrowing() {
    String faultyStarListKey = "{\"starlist\":l[s\"9876\",s\"8765\",s\"7654\"]}";
    // the starlist key should be "starList", so the typo should throw an error
    boolean starListSuccessfullyParsed = false;
    try {
      MotherShipMoveReq faultyStarListMoveReq = MotherShipMoveReq.fromSON(SON.parse(faultyStarListKey));
    } catch (SONConversionError | SONParsingError e) {
      e.printStackTrace();
      starListSuccessfullyParsed = false;
    }
    Assert.assertFalse(starListSuccessfullyParsed);
  }
}
