package tech.subluminal.client.presentation;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

public class KeyMap {

  //Map<KeyCode, defaultKey>
  private final Map<String, StringProperty> keyMap = new HashMap<>();

  private final StringProperty fullscreen = new SimpleStringProperty(KeyCode.F11.getName());
  private final StringProperty chatToggle = new SimpleStringProperty(KeyCode.C.getName());

  public KeyMap(){
    keyMap.put("fullscreen",fullscreen);
    keyMap.put("chat", chatToggle);
  }

  public Map<String, StringProperty> getKeyMap() {
    return keyMap;
  }
}
