package tech.subluminal.test;

import static org.mockito.Mockito.verify;

import java.util.function.BiConsumer;
import org.mockito.ArgumentCaptor;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.shared.net.Connection;

public class DistributorTester {

  private int maxId = 0;
  private final ArgumentCaptor<BiConsumer<String, Connection>> onOpenedCaptor = ArgumentCaptor
      .forClass(BiConsumer.class);

  public DistributorTester(MessageDistributor distributor, Connection... connections) {
    verify(distributor).addConnectionOpenedListener(onOpenedCaptor.capture());

    openConnection(connections);
  }

  private DistributorTester openConnection(Connection... connections) {
    for (Connection connection: connections) {
      onOpenedCaptor.getValue().accept("" + (maxId++), connection);
    }

    return this;
  }

}
