package tech.subluminal.shared.util;

import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;

public class ObservableMappingValue<S, T> extends ObservableValueBase<T> {

  private final ObservableValue<S> source;
  private final Function<S, T> mapper;

  public ObservableMappingValue(ObservableValue<S> source, Function<S,T> mapper) {
    this.source = source;
    this.mapper = mapper;
    source.addListener((o, oldVal, newVal) -> {
      fireValueChangedEvent();
    });
  }

  @Override
  public T getValue() {
    T value = mapper.apply(source.getValue());
    System.out.println(value.toString());
    return mapper.apply(source.getValue());
  }
}
