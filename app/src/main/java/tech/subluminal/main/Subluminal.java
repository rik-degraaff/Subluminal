package tech.subluminal.main;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import tech.subluminal.client.init.ClientInitializer;
import tech.subluminal.server.init.ServerInitializer;
import tech.subluminal.shared.records.GlobalSettings;
import tech.subluminal.shared.util.ConfigModifier;
import tech.subluminal.shared.util.SettingsReaderWriter;

/**
 * The main class of the Subluminal project containing the main function which starts the program.
 */
@Command(
    name = "Subluminal",
    description = "Starts the game in server or client mode.",
    showDefaultValues = true
)
public class Subluminal {

  // ======= COMMAND LINE ARGUMENTS =======
  @Option(names = {"-h", "--help"}, description = "Display help/usage.", help = true)
  boolean help;
  @Option(names = {"-ll", "--loglevel"}, description = "Sets the loglevel for the application. ")
  private String loglevel = "off";
  @Option(names = {"-lf", "--logfile"}, description = "Sets the path and filename for the logfile")
  private String logfile = "";
  @Option(names = {"-d", "--debug"}, description = "Enables the debug mode.")
  private boolean debug;
  @Parameters(index = "0", arity = "1", description = "Sets the application mode. Must be one of "
      + "\"server, client\".")
  private String mode;
  @Parameters(index = "1", arity = "0..1", description = "Specifies the connection details."
      + "In case of server this needs to be a \"port\"."
      + "In case of client this needs to be a \"host:port\".")
  private String hostAndOrPort = "164.132.199.58:1729";
  @Parameters(index = "2", arity = "0..1", description =
      "Sets the username. If none is specified the "
          + "system username will be used instead.")
  private String username = System.getProperty("user.name");

  // ======= OTHER VARIABLES =======
  private static final SettingsReaderWriter srw = new SettingsReaderWriter();

  /**
   * Parses the command line arguments and calls the relevant packages.
   *
   * @param args are the command line arguments.
   */
  public static void main(String[] args) {
    System.setProperty("file.encoding","UTF-8");
    Field charset = null;
    try {
      charset = Charset.class.getDeclaredField("defaultCharset");
      charset.setAccessible(true);
      charset.set(null,null);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    
    final Subluminal subl = CommandLine.populateCommand(new Subluminal(), args);

    if (subl.help) {
      System.out.println(printASCII());
      CommandLine.usage(subl, System.out, CommandLine.Help.Ansi.AUTO);
    } else {
      List<String> modes = Arrays.asList("server", "client");
      Map<String, Level> levelMap = new HashMap<String, Level>() {{
        put("off", Level.OFF);
        put("trace", Level.TRACE);
        put("debug", Level.DEBUG);
        put("info", Level.INFO);
        put("warning", Level.WARNING);
        put("error", Level.ERROR);
      }};

      if (levelMap.get(subl.loglevel) != null) {
        Configurator.currentConfig().level(levelMap.get(subl.loglevel)).activate();
      } else {
        Configurator.currentConfig().level(Level.OFF);
      }

      final SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
        Logger.info("Security Manager found. Trying to suppress access checks.");
      } else {
        Logger.info("No security manager detected");
      }

      GlobalSettings.PATH_JAR = getJarPath().toString();
      ConfigModifier<String, String> cm = new ConfigModifier("settings");
      cm.attachToFile("keys/keymap.properties");
      cm.getProps().put("Shenanigans", "Alt+G");

      if (subl.debug) {
        srw.run(GlobalSettings.class, GlobalSettings.class, GlobalSettings.PATH_JAR);
      }

      String host = "localhost";
      int port = 1729;
      
      Logger.debug("mode:" + subl.mode + " hostAndOrPort:" + subl.hostAndOrPort + " debug:" + String
          .valueOf(subl.debug) + " logfile:" + subl.logfile + " loglevel:" + subl.loglevel
          + " username:" + subl.username);

      String[] parts = subl.hostAndOrPort.split(":");

      if ("client".equals(subl.mode)) {
        if (parts.length != 2) {
          System.out.println("Host and port were not specified properly.");
          System.exit(1);
        }
        host = parts[0];
        try {
          port = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
          System.out.println(port);
        }

        initClient(host, port, subl.username, subl.debug);

      } else if ("server".equals(subl.mode)) {
        try {
          port = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
          System.out.println(port);
        }

        initServer(port, subl.debug);

      } else {
        System.out.println("Invalid mode argument. Must be one of " + modes);
        System.exit(1);
      }
    }
  }

  private static void initClient(String host, int port, String username, boolean debug) {
    if (port >= 1 && port <= 1024) {
      System.out.println(printASCII("Client (root)"));
      Application.launch(ClientInitializer.class, host, Integer.toString(port), username,
          String.valueOf(debug));
    } else if (port > 1024 && port < 65535) {
      System.out.println(printASCII("Client"));
      Application.launch(ClientInitializer.class, host, Integer.toString(port), username,
          String.valueOf(debug));
    } else {
      Logger.error("Port must be between 0 and 65535.");
      System.out.printf("Port must be between 0 and 65535.");
      System.exit(1);
    }
  }

  private static void initServer(int port, boolean debug) {
    if (port >= 1 && port <= 1024) {
      System.out.println(printASCII("Server (root)"));
      ServerInitializer.init(port, debug);
    } else if (port > 1024 && port < 65535) {
      System.out.println(printASCII("Server"));
      ServerInitializer.init(port, debug);
    } else {
      Logger.error("Port must be between 0 and 65535.");
      System.out.printf("Port must be between 0 and 65535.");
      System.exit(1);
    }
  }

  private static String printASCII() {
    return printASCII("", "");
  }

  private static String printASCII(String msg) {
    return printASCII(msg, "");
  }

  private static String printASCII(String msg, String motd) {
    String logo = "" + "\n" +
        "Welcome to" + "\n" +
        "  _____       _     _                 _             _ " + "\n" +
        " / ____|     | |   | |               (_)           | |" + "\n" +
        "| (___  _   _| |__ | |_   _ _ __ ___  _ _ __   __ _| |" + "\n" +
        " \\___ \\| | | | '_ \\| | | | | '_ ` _ \\| | '_ \\ / _` | |" + "\n" +
        " ____) | |_| | |_) | | |_| | | | | | | | | | | (_| | |" + "\n" +
        "|_____/ \\__,_|_.__/|_|\\__,_|_| |_| |_|_|_| |_|\\__,_|_| " + msg + "\n" +
        "" + "\n" +
        motd;
    ;
    return logo;
  }

  private static File getJarPath() {
    File jar = null;
    try {
      jar = new File(
          Subluminal.class.getProtectionDomain().getCodeSource().getLocation().toURI()
              .getPath()).getParentFile();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return jar;
  }
}

