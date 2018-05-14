package tech.subluminal.test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.mockito.ArgumentCaptor;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONConverter;
import tech.subluminal.shared.son.SONRepresentable;

public class DistributorTester {

  private final MessageDistributor distributor;
  private int maxId = 0;
  private final ArgumentCaptor<BiConsumer<String, Connection>> onOpenedCaptor = ArgumentCaptor
      .forClass(BiConsumer.class);

  public DistributorTester(MessageDistributor distributor, Connection... connections) {
    this.distributor = distributor;
    verify(distributor).addConnectionOpenedListener(onOpenedCaptor.capture());

    openConnection(connections);
  }

  public <M extends SONRepresentable> Optional<String> verifyLoginHandler(Connection connection, Class<M> messageType, M msg) {
    ArgumentCaptor<SONConverter<M>> converterCaptor = ArgumentCaptor.forClass(SONConverter.class);
    ArgumentCaptor<BiFunction<M, Connection, Optional<String>>> handlerCaptor = ArgumentCaptor
        .forClass(BiFunction.class);

    verify(distributor, atLeast(0))
        .addLoginHandler(not(eq(messageType)), any(), any());
    verify(distributor)
        .addLoginHandler(eq(messageType), converterCaptor.capture(), handlerCaptor.capture());

    try {
      final M message = converterCaptor.getValue().convert(msg.asSON());
      return handlerCaptor.getValue().apply(message, connection);
    } catch (SONConversionError sonConversionError) {
      fail("Manager failed to parse " + messageType.getSimpleName() + " correctly");
      return Optional.empty();
    }
  }

  private DistributorTester openConnection(Connection... connections) {
    for (Connection connection : connections) {
      onOpenedCaptor.getValue().accept("" + (maxId++), connection);
    }

    return this;
  }

  public DistributorTester openConnection(String id, Connection connection) {
    onOpenedCaptor.getValue().accept(id, connection);
    return this;
  }
}
