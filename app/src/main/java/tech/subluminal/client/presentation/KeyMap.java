package tech.subluminal.client.presentation;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;

public class KeyMap {

  //Map<KeyCode, defaultKey>
  private ObservableMap<String, StringProperty> keyMap = FXCollections.observableHashMap();

  private final StringProperty fullscreen = new SimpleStringProperty(KeyCode.F11.getName());
  private final StringProperty chatToggle = new SimpleStringProperty(KeyCode.C.getName());
  private final StringProperty fps = new SimpleStringProperty(KeyCode.F4.getName());
  private final StringProperty debugMonitor = new SimpleStringProperty(KeyCode.F5.getName());

  public KeyMap() {

    keyMap.put("fullscreen", fullscreen);
    keyMap.put("chat", chatToggle);
    keyMap.put("fps", fps);
    keyMap.put("debugMonitor", debugMonitor);

    /*

    keyMap.addListener((observable, oldValue, newValue) -> {
      if (oldValue != newValue) {
        updateKeyMapFile(newValue);
      }
    });
    */
  }

  public Map<String, StringProperty> getKeyMap() {
    return keyMap;
  }

  private void updateKeyMapFile(
      ObservableMap<String, StringProperty> newValue) {
    System.out.println("Property changed: " + newValue.toString());
  }

  public StringProperty get(String key) {
    return keyMap.get(key);
  }

}
