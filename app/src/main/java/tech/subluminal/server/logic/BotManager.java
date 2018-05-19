package tech.subluminal.server.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.messages.ChatMessageIn;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.Channel;

public class BotManager {

  private static final String NASHORN = "nashorn";

  private final ScriptEngine engine = new ScriptEngineManager().getEngineByName(NASHORN);
  private final Set<String> botNames = new HashSet<>();
  private final MessageDistributor distributor;

  public BotManager(MessageDistributor distributor) {
    this.distributor = distributor;
    distributor.addConnectionOpenedListener(this::attachListeners);
    loadBots();
  }

  private void loadBots() {
    Map<String, String > bots = new HashMap<>();
    engine.put("reply", (BiFunction<String, String, Consumer<String>>) (name, id) -> msg -> {
      distributor.sendMessage(new ChatMessageIn(msg, name, Channel.WHISPER), id);
    });

    engine.put("replyAll", (Function<String, Consumer<String>>) name -> msg -> {
      distributor.broadcast(new ChatMessageIn(msg, name, Channel.GLOBAL));
    });

    bots.put("cat", "function(msg, reply, replyAll) { replyAll(msg); }");
    bots.put("list", "function(msg, reply, replyAll) { reply(bots); }");

    bots.forEach((name, func) -> {
      try {
        engine.eval("var " + name + " = " + func + ";");
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
