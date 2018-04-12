package tech.subluminal.client.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in a graph.
 *
 * @param <E> the type the node should consist of.
 */
class Node<E> {

  private Map<Node<E>, Double> neighbours = new HashMap<>();

  private E data;

  /**
   * Creates a new node containing data of a generic type.
   *
   * @param data the element to be stored in the node.
   */
  public Node(E data) {
    this.data = data;
  }

  /**
   * Adds another node to the collection of nodes within range of this object.
   *
   * @param neighbour the additional node within range of this object.
   * @param weight the distance of the new node to this object.
   */
  public void addNeighbour(Node<E> neighbour, double weight) {
    neighbours.put(neighbour, weight);
  }

  /**
   * @return the collection of the nodes within reach of this object..
   */
  Map<Node<E>, Double> getNeighbours() {
    return neighbours;
  }

  /**
   * @return the data stores in this node.
   */
  public E getData() {
    return data;
  }

}
