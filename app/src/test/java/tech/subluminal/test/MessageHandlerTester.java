package tech.subluminal.test;

import static junit.framework.TestCase.fail;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.function.Consumer;
import org.mockito.ArgumentCaptor;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONRepresentable;

public class MessageHandlerTester<M extends SONRepresentable> {

  private final ArgumentCaptor<SONConverter<M>> converterCaptor = ArgumentCaptor.forClass(SONConverter.class);
  private final ArgumentCaptor<Consumer<M>> handlerCaptor = ArgumentCaptor.forClass(Consumer.class);
  private final Class<M> messageType;

  public MessageHandlerTester(Connection connection, Class<M> messageType, M... messages) {
    this.messageType = messageType;

    verify(connection, atLeast(0))
        .registerHandler(not(eq(messageType)), any(), any());
    verify(connection, times(1))
        .registerHandler(eq(messageType), converterCaptor.capture(), handlerCaptor.capture());

    sendMessage(messages);
  }

  public MessageHandlerTester<M> sendMessage(M... messages) {
    M msg = null;
    for (M message : messages) {
      try {
        msg = converterCaptor.getValue().convert(message.asSON());
      } catch (SONConversionError sonConversionError) {
        fail("Manager failed to parse " + messageType.getSimpleName() + " correctly");
      }
      handlerCaptor.getValue().accept(msg);
    }

    return this;
  }
}
