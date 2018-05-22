package tech.subluminal.client.presentation;

import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.input.KeyCode;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.util.ConfigModifier;

/**
 * A KeyMap is used to get the preferred Keys from a user, store, update and expose them to the GUI.
 */
public class KeyMap {

  //Map<KeyCode, defaultKey>
  private ObservableMap<String, ObjectProperty<KeyCode>> defaultKeyMap = FXCollections.observableHashMap();
  private ObservableMap<String, ObjectProperty<KeyCode>> keyMap = FXCollections.observableHashMap();
  private final KeyCode FULLSCREEN_DEFAULT =  KeyCode.F11;
  private final KeyCode CHAT_DEFAULT =  KeyCode.C;
  private final KeyCode FPS_DEFAULT =  KeyCode.F4;
  private final KeyCode FPS_MONITOR_DEFAULT =  KeyCode.F5;
  private final KeyCode TPS_DEFAULT =  KeyCode.F6;
  private final KeyCode TPS_MONITOR_DEFAULT =  KeyCode.F7;
  private final KeyCode SETTINGS_DEFAULT =  KeyCode.S;
  private final KeyCode SKIP_DEFAULT =  KeyCode.ESCAPE;

  private final ObjectProperty<KeyCode> fullscreen = new SimpleObjectProperty<>(FULLSCREEN_DEFAULT);
  private final ObjectProperty<KeyCode> chatToggle = new SimpleObjectProperty<>(CHAT_DEFAULT);
  private final ObjectProperty<KeyCode> fps = new SimpleObjectProperty<>(FPS_DEFAULT);
  private final ObjectProperty<KeyCode> fpsMonitor = new SimpleObjectProperty<>(FPS_MONITOR_DEFAULT);
  private final ObjectProperty<KeyCode> tps = new SimpleObjectProperty<>(TPS_DEFAULT);
  private final ObjectProperty<KeyCode> tpsMonitor = new SimpleObjectProperty<>(TPS_MONITOR_DEFAULT);
  private final ObjectProperty<KeyCode> settings = new SimpleObjectProperty<>(SETTINGS_DEFAULT);
  private final ObjectProperty<KeyCode> skip = new SimpleObjectProperty<>(SKIP_DEFAULT);

  /**
   * Initializes the KeyMap with default values.
   */
  public KeyMap() {
    defaultKeyMap.put("Fullscreen", fullscreen);
    defaultKeyMap.put("Chat", chatToggle);
    defaultKeyMap.put("FPS", fps);
    defaultKeyMap.put("FPSMonitor", fpsMonitor);
    defaultKeyMap.put("TPS", tps);
    defaultKeyMap.put("TPSMonitor", tpsMonitor);
    defaultKeyMap.put("Settings", settings);
    defaultKeyMap.put("Skip", skip);

    //Property files needs to be pulled here

    ConfigModifier<String, String> cm = new ConfigModifier<>("settings");
    cm.attachToFile("keymap.properties");
    ObservableMap fileMap = cm.getProps();


    defaultKeyMap.forEach((k,v) -> {
      if(fileMap.get(k) == null){
        keyMap.put(k, new SimpleObjectProperty<>(defaultKeyMap.get(k).getValue()));
        fileMap.put(k, defaultKeyMap.get(k).getValue().getName().toUpperCase());
      }else{
        keyMap.put(k, new SimpleObjectProperty<>(KeyCode.getKeyCode((String)fileMap.get(k))));
      }

    });

    keyMap.forEach((k,v) -> {
      v.addListener((observable, oldValue, newValue) -> {
        fileMap.put(k, v.getValue().getName());
      });
    });

  }

  private void updateKeyMapFile(String key, ObjectProperty value) {
    Logger.trace("Property changed: " + value.toString());
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
