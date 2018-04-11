package tech.subluminal.shared.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

public class MapperList<E, F> extends TransformationList<E, F> {

  private final Function<F, E> mapper;

  public MapperList(ObservableList<F> source, Function<F, E> mapper) {
    super(source);
    this.mapper = mapper;
  }

  /**
   * Called when a change from the source is triggered.
   *
   * @param c the change
   */
  @Override
  protected void sourceChanged(Change<? extends F> c) {
    fireChange(new Change<E>(this) {

      @Override
      public boolean wasAdded() {
        return c.wasAdded();
      }

      @Override
      public boolean wasRemoved() {
        return c.wasRemoved();
      }

      @Override
      public boolean wasReplaced() {
        return c.wasReplaced();
      }

      @Override
      public boolean wasUpdated() {
        return c.wasUpdated();
      }

      @Override
      public boolean wasPermutated() {
        return c.wasPermutated();
      }

      @Override
      public int getPermutation(int i) {
        return c.getPermutation(i);
      }

      @Override
      protected int[] getPermutation() {
        // this will should never be called.
        throw new AssertionError("This is never called.");
      }

      @Override
      public List<E> getRemoved() {
        return c.getRemoved().stream().map(mapper).collect(Collectors.toList());
      }

      @Override
      public int getFrom() {
        return c.getFrom();
      }

      @Override
      public int getTo() {
        return c.getTo();
      }

      @Override
      public boolean next() {
        return c.next();
      }

      @Override
      public void reset() {
        c.reset();
      }
    });
  }

  /**
   * Maps the index of this list's element to an index in the direct source list.
   *
   * @param index the index in this list
   * @return the index of the element's origin in the source list
   * @see #getSource()
   */
  @Override
  public int getSourceIndex(int index) {
    return index;
  }

  /**
   * {@inheritDoc}
   *
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override
  public E get(int index) {
    return mapper.apply(getSource().get(index));
  }

  @Override
  public int size() {
    return getSource().size();
  }
}
