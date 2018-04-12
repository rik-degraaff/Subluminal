package tech.subluminal.client.logic;

import java.util.HashMap;
import java.util.Map;

class Node<E> {

  private Map<Node<E>, Double> neighbours = new HashMap<>();

  private E data;

  public Node(E data) {
    this.data = data;
  }

  public void addNeighbour(Node<E> neighbour, double weight) {
    neighbours.put(neighbour, weight);
  }

  Map<Node<E>, Double> getNeighbours() {
    return neighbours;
  }

  public E getData() {
    return data;
  }

}
