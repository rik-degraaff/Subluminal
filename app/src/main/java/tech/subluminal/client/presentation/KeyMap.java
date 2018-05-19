package tech.subluminal.client.presentation;

import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;

/**
 * A KeyMap is used to get the prefered Keys from a user, store, update and expose them to the GUI.
 */
public class KeyMap {

  //Map<KeyCode, defaultKey>
  private ObservableMap<String, ObjectProperty<KeyCode>> defaultKeyMap = FXCollections.observableHashMap();
  private ObservableMap<String, ObjectProperty<KeyCode>> keyMap = FXCollections.observableHashMap();
  private final KeyCode FULLSCREEN_DEFAULT =  KeyCode.F11;
  private final KeyCode CHAT_DEFAULT =  KeyCode.C;
  private final KeyCode FPS_DEFAULT =  KeyCode.F4;
  private final KeyCode DEBUG_DEFAULT =  KeyCode.F5;
  private final KeyCode SETTINGS_DEFAULT =  KeyCode.S;
  private final KeyCode SKIP_DEFAULT =  KeyCode.ESCAPE;

  private final ObjectProperty fullscreen = new SimpleObjectProperty(FULLSCREEN_DEFAULT);
  private final ObjectProperty chatToggle = new SimpleObjectProperty(CHAT_DEFAULT);
  private final ObjectProperty fps = new SimpleObjectProperty(FPS_DEFAULT);
  private final ObjectProperty debugMonitor = new SimpleObjectProperty(DEBUG_DEFAULT);
  private final ObjectProperty settings = new SimpleObjectProperty(SETTINGS_DEFAULT);
  private final ObjectProperty skip = new SimpleObjectProperty(SKIP_DEFAULT);

  public KeyMap() {

    defaultKeyMap.put("Fullscreen", fullscreen);
    defaultKeyMap.put("Chat", chatToggle);
    defaultKeyMap.put("Fps", fps);
    defaultKeyMap.put("DebugMonitor", debugMonitor);
    defaultKeyMap.put("Settings", settings);
    defaultKeyMap.put("Skip", skip);

    //Property files needs to be pulled here
    defaultKeyMap.forEach((k,v) -> keyMap.put(k,new SimpleObjectProperty<KeyCode>(v.getValue())));

  }

  private void updateKeyMapFile(String key, ObjectProperty value) {
    System.out.println("Property changed: " + value.toString());
  }

  /**
   * Reset a single Key.
   * @param key the key which will be reset.
   */
  public void reset(String key){
    keyMap.get(key).setValue(defaultKeyMap.get(key).getValue());
  }

  public Map<String, ObjectProperty<KeyCode>> getKeyMap() {
    return keyMap;
  }

  public ObjectProperty get(String key) {
    return keyMap.get(key);
  }

  public void resetAll() {
    defaultKeyMap.forEach((k,v) -> keyMap.get(k).setValue(v.getValue()));
  }
}
