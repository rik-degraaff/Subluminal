package tech.subluminal.client.presentation;

import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyMap {

  //Map<KeyCode, defaultKey>
  private ObservableMap<String, ObjectProperty<KeyCode>> keyMap = FXCollections.observableHashMap();

  private final ObjectProperty fullscreen = new SimpleObjectProperty(KeyCode.F11);
  private final ObjectProperty chatToggle = new SimpleObjectProperty(KeyCode.C);
  private final ObjectProperty fps = new SimpleObjectProperty(KeyCode.F4);
  private final ObjectProperty debugMonitor = new SimpleObjectProperty(KeyCode.F5);
  private final ObjectProperty settings = new SimpleObjectProperty(KeyCode.S);
  private final ObjectProperty skip = new SimpleObjectProperty(KeyCode.ESCAPE);

  public KeyMap() {

    keyMap.put("Fullscreen", fullscreen);
    keyMap.put("Chat", chatToggle);
    keyMap.put("Fps", fps);
    keyMap.put("DebugMonitor", debugMonitor);
    keyMap.put("Settings", settings);
    keyMap.put("Skip", skip);

    keyMap.addListener((MapChangeListener<String, ObjectProperty>) change ->
        updateKeyMapFile(change.getKey(), change.getValueAdded())
    );

  }

  private void updateKeyMapFile(String key, ObjectProperty value) {
    System.out.println("Property changed: " + value.toString());
  }

  public Map<String, ObjectProperty<KeyCode>> getKeyMap() {
    return keyMap;
  }

  public ObjectProperty get(String key) {
    return keyMap.get(key);
  }

}
