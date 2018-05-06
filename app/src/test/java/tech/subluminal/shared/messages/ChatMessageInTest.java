package tech.subluminal.shared.messages;

import org.junit.Assert;
import org.junit.Test;
import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsing;
import tech.subluminal.shared.son.SONParsingError;

public class ChatMessageInTest {

  @Test
  public void testStringifyAndParsing() {
    String message = "!Hello!";
    String username = "Luke";
    Channel channel = Channel.CRITICAL;
    ChatMessageIn chatMessageIn = new ChatMessageIn(message, username, channel);

    String chatMessageInMsg = chatMessageIn.asSON().asString();
    String parsedMessage = null;
    String parsedUsername = null;
    Channel parsedChannel = null;

    try {
      ChatMessageIn parsedChatMessageIn = ChatMessageIn.fromSON(SON.parse(chatMessageInMsg));
      Assert.assertNotNull(parsedChatMessageIn);
      parsedChannel = parsedChatMessageIn.getChannel();
      parsedUsername = parsedChatMessageIn.getUsername();
      parsedMessage = parsedChatMessageIn.getMessage();
    } catch (SONParsingError | SONConversionError e) {
      e.printStackTrace();
    }

    System.out.println(chatMessageInMsg);
    Assert.assertEquals(message, parsedMessage);
    Assert.assertEquals(channel, parsedChannel);
    Assert.assertEquals(username, parsedUsername);


  }
}
