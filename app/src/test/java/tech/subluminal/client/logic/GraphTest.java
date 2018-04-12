package tech.subluminal.client.logic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class GraphTest {

  @Test
  public void testSimpleGraph() {
    Set<String> nodes = new HashSet<>(Arrays.asList("a", "b", "c"));
    Set<String> connections = new HashSet<>(Arrays.asList("ab", "ba", "ac"));

    Graph<String> graph = new Graph<>(nodes,
        (n1, n2) -> connections.contains(n1 + n2),
        (n1, n2) -> 1.0,
        false);

    assertThat(graph.findShortestPath("a", "b"), is(Arrays.asList("a", "b")));
    assertThat(graph.findShortestPath("c", "a"), is(Collections.emptyList()));
    assertThat(graph.findShortestPath("b", "c"), is(Arrays.asList("b", "a", "c")));
  }

  @Test
  public void testComplexGraph() {
    Set<String> nodes = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g"));
    Map<String, Double> connections = new HashMap<String, Double>() {{
      put("ab", 3.0);
      put("ac", 1.0);
      put("bc", 1.0);
      put("bd", 1.0);
      put("df", 2.0);
      put("de", 2.0);
      put("fe", 1.0);
      put("cg", 4.0);
    }};

    Graph<String> graph = new Graph<>(nodes,
        (n1, n2) -> connections.keySet().contains(n1 + n2)
            || connections.keySet().contains(n2 + n1),
        (n1, n2) -> connections.keySet().contains(n1 + n2)
            ? connections.get(n1 + n2)
            : connections.get(n2 + n1),
        true);

    assertThat(graph.findShortestPath("a", "b"),
        is(Arrays.asList("a", "c", "b")));
    assertThat(graph.findShortestPath("f", "c"),
        is(Arrays.asList("f", "d", "b", "c")));
    assertThat(graph.findShortestPath("e", "g"),
        is(Arrays.asList("e", "d", "b", "c", "g")));
  }

  @Test
  public void testVeryComplexGraph() {
    Set<String> nodes = new HashSet<>(
        Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "x"));
    Map<String, Double> connections = new HashMap<String, Double>() {{
      put("ax", 1.0);
      put("xb", 2.0);
      put("xc", 3.0);
      put("bd", 1.0);
      put("cg", 2.0);
      put("de", 2.0);
      put("df", 2.0);
      put("ei", 2.0);
      put("fi", 1.0);
      put("gh", 3.0);
      put("hk", 1.0);
      put("ij", 2.0);
      put("fh", 2.0);
    }};

    Graph<String> graph = new Graph<>(nodes,
        (n1, n2) -> connections.keySet().contains(n1 + n2)
            || connections.keySet().contains(n2 + n1),
        (n1, n2) -> connections.keySet().contains(n1 + n2)
            ? connections.get(n1 + n2)
            : connections.get(n2 + n1),
        true);

    assertThat(graph.findShortestPath("x", "j"),
        is(Arrays.asList("x", "b", "d", "f", "i", "j")));
    assertThat(graph.findShortestPath("x", "k"),
        is(Arrays.asList("x", "b", "d", "f", "h", "k")));
    assertThat(graph.findShortestPath("g", "e"),
        is(Arrays.asList("g", "h", "f", "i", "e")));
  }

  @Test
  public void testInvalidInput() {
    Graph<String> graph = new Graph<>(Collections.singleton("a"),
        (n1, n2) -> true, (n1, n2) -> 0.0, true);

    try {
      graph.findShortestPath("b", "a");
      fail("Pathfinding with non-existing starting node. Did not throw an exception.");
    } catch (IllegalStateException  e) {

    }

    try {
      graph.findShortestPath("a", "c");
      fail("Pathfinding with non-existing target node. Did not throw an exception.");
    } catch (IllegalStateException  e) {

    }
  }
}
