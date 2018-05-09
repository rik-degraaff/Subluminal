package tech.subluminal.shared.messages;

import org.junit.Assert;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;

public class ChatMessageOutTest {

  @Test
  public void testWhisperMessage() throws SONParsingError, SONConversionError{
    String message = "Who let the dogs out?";
    String receiverID = "1234";
    boolean global = true;
    // this true shouldn't matter; it should be ignored because of the present receiverID

    ChatMessageOut chatMessageOut = new ChatMessageOut(message, receiverID, global);

    String chatMessageOutMsg = chatMessageOut.asSON().asString();
    String parsedMessage = null;
    String parsedReceiverID = null;
    boolean parsedGlobal = true; // should change to false after conversion from SON

    ChatMessageOut parsedChatMessageOut = ChatMessageOut.fromSON(SON.parse(chatMessageOutMsg));
    Assert.assertNotNull(parsedChatMessageOut);
    parsedMessage = parsedChatMessageOut.getMessage();
    parsedReceiverID = parsedChatMessageOut.getReceiverID();
    parsedGlobal = parsedChatMessageOut.isGlobal();

    System.out.println(chatMessageOutMsg);
    Assert.assertEquals(message, parsedMessage);
    Assert.assertEquals(receiverID, parsedReceiverID);
    Assert.assertEquals(false, parsedGlobal);

  }

  @Test
  public void testIngameMessage() {
    String message = "Who let the dogs out?";
    String receiverID = null;
    boolean global = false;

    ChatMessageOut chatMessageOut = new ChatMessageOut(message, receiverID, global);

    String chatMessageOutMsg = chatMessageOut.asSON().asString();
    String parsedMessage = null;
    String parsedReceiverID = null;
    boolean parsedGlobal = true; // should change to false after conversion from SON

    try {
      ChatMessageOut parsedChatMessageOut = ChatMessageOut.fromSON(SON.parse(chatMessageOutMsg));
      Assert.assertNotNull(parsedChatMessageOut);
      parsedMessage = parsedChatMessageOut.getMessage();
      parsedReceiverID = parsedChatMessageOut.getReceiverID();
      parsedGlobal = parsedChatMessageOut.isGlobal();
    } catch (SONParsingError | SONConversionError error) {
      error.printStackTrace();
    }

    System.out.println(chatMessageOutMsg);
    Assert.assertEquals(message, parsedMessage);
    Assert.assertEquals(receiverID, parsedReceiverID);
    Assert.assertEquals(false, parsedGlobal);

  }
}
