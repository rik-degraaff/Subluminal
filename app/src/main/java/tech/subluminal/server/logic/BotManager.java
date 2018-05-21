package tech.subluminal.server.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.messages.ChatMessageIn;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.util.ConfigModifier;

/**
 * Manages built in and custom mods that can reply to a chat message starting with a '&gt;'.
 * Custom bots are read from the bots directory and should be named {@code name.js}
 * and can be called upon by user when they type {@code >name [message]}.
 * A simple die rolling bot could be implemented like this:
 * <pre>
 *   <i>bots/roll.js</i>
 *   <b>function</b>(msg, reply, replyAll) <b>{</b>
 *       <b>var</b> sides = parseInt(msg) || 6<b>;</b>
 *       <b>var</b> res = Math.floor(Math.random() * sides) + 1<b>;</b>
 *       replyAll('you rolled a ' + res)<b>;</b>
 *   <b>}</b>
 * </pre>
 * This bot can be called as:
 * <pre>
 *   you: &gt;roll 20
 *   roll: you rolled a 16
 * </pre>
 */
public class BotManager {

  private static final String NASHORN = "nashorn";

  private final ScriptEngine engine = new ScriptEngineManager().getEngineByName(NASHORN);
  private final Set<String> botNames = new HashSet<>();
  private final MessageDistributor distributor;

  /**
   * Creates a bot manager.
   *
   * @param distributor the distributor this manager will listen to and send replies through.
   */
  public BotManager(MessageDistributor distributor) {
    this.distributor = distributor;
    distributor.addConnectionOpenedListener(this::attachListeners);
    loadBots();
  }

  private void loadBots() {
    Map<String, String> bots = new HashMap<>();
    engine.put("reply", (BiFunction<String, String, Consumer<String>>) (name, id) -> msg -> {
      distributor.sendMessage(new ChatMessageIn(msg, name, Channel.WHISPER), id);
    });

    engine.put("replyAll", (Function<String, Consumer<String>>) name -> msg -> {
      distributor.broadcast(new ChatMessageIn(msg, name, Channel.GLOBAL));
    });

    bots.put("cat", "function(msg, reply, replyAll) { replyAll(msg); }");
    bots.put("list", "function(msg, reply, replyAll) { reply(bots); }");

    new ConfigModifier<String, String>("bots")
        .getAllFiles()
        .stream()
        .filter(file -> file.getName().endsWith(".js"))
        .forEach(file -> {
          final String name = file.getName().substring(0, file.getName().length() - 3);
          try {
            final BufferedReader br = new BufferedReader(new FileReader(file));
            final List<String> lines = br.lines().collect(Collectors.toList());
            final String code = String.join(System.lineSeparator(), lines);
            bots.put(name, code);
          } catch (FileNotFoundException e) {
            Logger.error(e);
          }
        });

    bots.forEach((name, func) -> {
      try {
        engine.eval(name + " = " + func + ";");
        botNames.add(name);
      } catch (ScriptException e) {
        Logger.error("bot: " + name + " didn't load properly:");
        Logger.error(e);
      }
    });

    engine.put("bots", String.join(", ", botNames));
  }

  private void attachListeners(String id, Connection connection) {
    connection.registerHandler(ChatMessageOut.class, ChatMessageOut::fromSON,
        req -> onMessage(req, id));
  }

  private void onMessage(ChatMessageOut req, String id) {
    String message = req.getMessage().trim();
    if (message.startsWith(">")) {
      final String[] parts = message.substring(1, message.length()).split(" ", 2);
      final String name = parts[0];
      if (!botNames.contains(name)) {
        return;
      }

      final String msg = parts.length == 2
          ? parts[1]
          .replace("\\", "\\\\")
          .replace("'", "\\'")
          : "";

      try {
        engine.eval(name + "('" + msg + "',"
            + "reply('" + name + "', '" + id + "'),"
            + "replyAll('" + name + "'));");
      } catch (ScriptException e) {
        Logger.error("bot: " + name + " didn't handle a message properly:");
        Logger.error(e);
      }
    }
  }
}
