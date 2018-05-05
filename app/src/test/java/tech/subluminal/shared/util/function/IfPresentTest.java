package tech.subluminal.shared.util.function;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class IfPresentTest {

  private Optional<String> empty() {
    return Optional.empty();
  }

  private <R> Optional<R> empty(Class<R> clz) {
    return Optional.empty();
  }

  @Test
  public void thenWithValue() {
    List<String> lst = new ArrayList<>();

    ifPresent(Optional.of("test"))
        .then(lst::add);

    assertThat(lst, hasItems("test"));
  }

  @Test
  public void thenWithoutValue() {
    List<String> lst = new ArrayList<>();

    ifPresent(empty())
        .then(lst::add);

    assertEquals(0, lst.size());
  }

  @Test
  public void elsWithValue() {
    List<String> lst = new ArrayList<>();

    ifPresent(Optional.of("test"))
        .els(() -> lst.add("test2"));

    assertThat(lst, not(hasItems(anyOf(is("test2"), is("test")))));
  }

  @Test
  public void elsWithoutValue() {
    List<String> lst = new ArrayList<>();

    ifPresent(empty())
        .els(() -> lst.add("test2"));

    assertThat(lst, hasItems("test2"));
  }

  @Test
  public void complexStatements() {
    List<String> lst1 = new ArrayList<>();

    ifPresent(empty())
        .then(s -> lst1.add("then"))
        .elseIfPresent(empty(Integer.class), i -> lst1.add("i"))
        .elseIfPresent(Optional.of(1), i -> lst1.add(i.toString()))
        .els(() -> lst1.add("els"));

    assertThat(lst1, hasItems("1"));
    assertThat(lst1, not(hasItems(anyOf(is("then"), is("i"), is("els")))));

    List<String> lst2 = new ArrayList<>();

    ifPresent(Optional.of("then"))
        .then(lst2::add)
        .elseIfPresent(empty(Integer.class), i -> lst2.add("i"))
        .elseIfPresent(Optional.of(1), i -> lst2.add(i.toString()))
        .els(() -> lst2.add("els"));

    assertThat(lst2, hasItems("then"));
    assertThat(lst2, not(hasItems(anyOf(is("i"), is("i"), is("els")))));
  }
}
