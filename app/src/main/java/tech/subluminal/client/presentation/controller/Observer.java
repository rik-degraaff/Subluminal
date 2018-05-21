package tech.subluminal.client.presentation.controller;

/**
 * Passes the main controller around.
 */
public interface Observer {

  /**
   * Sets the main controller on target object.
   * @param main is the main controller.
   */
  void setMainController(MainController main);
}
