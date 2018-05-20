package tech.subluminal.client.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Represents a graph with connected nodes.
 *
 * @param <E> the type the nodes in the graph consist of.
 */
public class Graph<E> {

  private Set<Node<E>> nodes;

  /**
   * Creates a new graph.
   *
   * @param nodes the set of nodes to be included in this graph.
   * @param canReach tests whether a specific node is within reach of another specific node.
   * @param weightCalculator calculates the distance between two given nodes
   * @param symmetric determines whether the reachability of two given nodes shall be symmetric or
   * asymmetric.
   */
  public Graph(
      Set<E> nodes, BiPredicate<E, E> canReach, BiFunction<E, E, Double> weightCalculator,
      boolean symmetric
  ) {
    this.nodes = nodes.stream().map(Node::new).collect(Collectors.toSet());

    connectNodes(canReach, weightCalculator, symmetric);
    System.out.println("path created");
    nodes.forEach(n -> System.out.println(n.toString()));
  }

  private void connectNodes(BiPredicate<E, E> canReach, BiFunction<E, E, Double> weightCalculator,
      boolean symmetric) {
    List<Node<E>> nodes = new ArrayList<>(this.nodes);

    for (int i = 0; i < nodes.size(); i++) {
      int start = symmetric ? i + 1 : 0;
      for (int j = start; j < nodes.size(); j++) {
        if (i == j) {
          continue;
        }
        Node<E> n1 = nodes.get(i);
        Node<E> n2 = nodes.get(j);
        if (canReach.test(n1.getData(), n2.getData())) {
          double weight = weightCalculator.apply(n1.getData(), n2.getData());
          n1.addNeighbour(n2, weight);
          if (symmetric) {
            n2.addNeighbour(n1, weight);
          }
        }
      }
    }
  }

  /**
   * Returns a List containing the node elements representing the shortest path from alpha to
   * omega.
   *
   * @param alpha the element to be started from.
   * @param omega the target element.
   * @return the node elements representing the shortest path from alpha to omega.
   */
  public List<E> findShortestPath(E alpha, E omega) {
    System.out.println("PATH: " + alpha+ " " + omega);
    Node<E> start = nodes.stream()
        .filter(n -> n.getData().equals(alpha))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Called findShortestPath with an 'alpha' that is not accessible."
        ));

    Node<E> end = nodes.stream()
        .filter(n -> n.getData().equals(omega))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "Called findShortestPath with an 'omega' that is not accessible."
        ));

    PriorityQueue<NodeEntry> border = new PriorityQueue<>(
        (n1, n2) -> n1.distance < n2.distance ? -1 : 1);
    Set<Node<E>> visitedNodes = new HashSet<>();
    border.offer(new NodeEntry(start, 0, Collections.emptyList()));

    NodeEntry current;

    do {
      current = border.remove();
      if (current.node.equals(end)) {
        break;
      }
      if (visitedNodes.contains(current.node)) {
        continue;
      }

      visitedNodes.add(current.node);

      List<E> currentPath = current.path;
      E currentData = current.node.getData();
      double currentDistance = current.distance;
      current.node.getNeighbours().forEach((n, d) -> {
        if (!visitedNodes.contains(n)) {
          List<E> newPath = new ArrayList<>(currentPath.size() + 1);
          newPath.addAll(currentPath);
          newPath.add(currentData);
          border.add(new NodeEntry(n, currentDistance + d, newPath));
        }
      });
    } while (!border.isEmpty());

    if (current.node.equals(end)) {
      current.path.add(omega);
      return current.path;
    }

    return Collections.emptyList();
  }

  private class NodeEntry {

    private final List<E> path;
    private final Node<E> node;
    private final double distance;

    private NodeEntry(Node<E> node, double distance, List<E> path) {
      this.node = node;
      this.distance = distance;
      this.path = path;
    }

  }

}
