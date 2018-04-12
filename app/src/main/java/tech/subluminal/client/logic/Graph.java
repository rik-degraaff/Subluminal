package tech.subluminal.client.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Graph<E> {

  private Set<Node<E>> nodes;

  public Graph(Set<E> nodes, BiPredicate<E, E> canReach, BiFunction<E, E, Double> weightCalculator,
      boolean symmetric) {
    this.nodes = nodes.stream().map(Node::new).collect(Collectors.toSet());

    connectNodes(canReach, weightCalculator, symmetric);
  }

  private void connectNodes(BiPredicate<E, E> canReach, BiFunction<E, E, Double> weightCalculator,
      boolean symmetric) {
    List<Node<E>> nodes = this.nodes.stream().collect(Collectors.toList());

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
        if (!symmetric && canReach.test(n2.getData(), n1.getData())) {
          double weight = weightCalculator.apply(n2.getData(), n1.getData());
          n2.addNeighbour(n1, weight);
        }
      }
    }
  }

  public List<E> findShortestPath(E alpha, E omega) {
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
      // hol mit dqueue aus border: current
      current = border.remove();
      // wenn end: break
      if (current.node.equals(end)) {
        break;
      }
      // wenn schon visited: mach nichts
      if (visitedNodes.contains(current)) {
        continue;
      }

      List<E> currentPath = current.path;
      E currentData = current.node.getData();
      double currentDistance = current.distance;
      // dann mache für alle unvisited neighbours einen eintrag in border
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
