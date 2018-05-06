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

public class FleetMoveReqTest {

  private String originID;
  private int amount;
  private FleetMoveReq fMoveReq;
  private List<String> stars;

  @Before
  public void initialize() {
    this.originID = "1234";
    this.amount = 3;
    this.stars = new ArrayList<>(Arrays.asList("1234", "2345", "3456"));
    this.fMoveReq = new FleetMoveReq(this.originID, this.amount, this.stars);
  }

  @Test
  public void testParsing() throws SONParsingError, SONConversionError {
    String fMoveReqMsg = this.fMoveReq.asSON().asString();
    String parsedOriginID = "";
    int parsedAmount = 0;
    List<String> parsedStars = null;

    FleetMoveReq parsedfMoveReq = FleetMoveReq.fromSON(SON.parse(fMoveReqMsg));
    parsedOriginID = parsedfMoveReq.getOriginID();
    parsedAmount = parsedfMoveReq.getAmount();
    parsedStars = parsedfMoveReq.getTargets();

    Assert.assertEquals(this.amount, parsedAmount);
    Assert.assertEquals(this.originID, parsedOriginID);
    Assert.assertTrue(this.stars.containsAll(parsedStars));
    System.out.println(fMoveReqMsg);
  }


  @Test
  public void testErrorThrowing() {

    String faultyAmountKey = "{\"Amount\":i3,\"originID\":s\"1234\",\"starList\":l[s\"1234\",s\"2345\",s\"3456\"]}";
    // the amount key is supposed to be lowercase, so a SONParsingError should be thrown
    boolean amountSuccessfullyParsed = true;
    String faultyOriginIDKey = "{\"amount\":i3,\"originid\":s\"1234\",\"starList\":l[s\"1234\",s\"2345\",s\"3456\"]}";
    // the originID key is supposed to be "originID", so s SONParsingError should be thrown
    boolean originIDSuccessfullyParsed = true;
    String faultyStarListKey = "{\"amount\":i3,\"originID\":s\"1234\",\"starlist\":l[s\"1234\",s\"2345\",s\"3456\"]}";
    // the starlist key is supposed to be "starList", so a SONParsingError should be thrown
    boolean starListSuccessfullyParsed = true;

    try {
      FleetMoveReq faultyAmountFMoveReq = FleetMoveReq.fromSON(SON.parse(faultyAmountKey));
    } catch (SONParsingError | SONConversionError e) {
      amountSuccessfullyParsed = false;
    }
    try {
      FleetMoveReq faultyOriginIDFMoveReq = FleetMoveReq.fromSON(SON.parse(faultyOriginIDKey));
    } catch (SONParsingError | SONConversionError e) {
      originIDSuccessfullyParsed = false;
    }
    try {
      FleetMoveReq faultyStarListFMoveReq = FleetMoveReq.fromSON(SON.parse(faultyStarListKey));
    } catch (SONParsingError | SONConversionError e) {
      starListSuccessfullyParsed = false;
    }

    Assert.assertFalse(amountSuccessfullyParsed);
    Assert.assertFalse(originIDSuccessfullyParsed);
    Assert.assertFalse(starListSuccessfullyParsed);
  }
}
