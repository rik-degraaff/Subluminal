package tech.subluminal.shared.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import org.pmw.tinylog.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import tech.subluminal.shared.records.GlobalSettings;

/**
 * Allows you to generate any number of star names.
 */
public class NameGenerator {

  private Random rand = new Random(GlobalSettings.SHARED_RANDOM.nextLong());
  private List<List<String>> starNames = new LinkedList<>();
  private List<File> starNamesMod;
  private LinkedList<String> starNamesReserve = new LinkedList<>();
  private List<Integer> starListWeights = new LinkedList<>();
  private int sumWeights;
  private int fileCounter = 0;
  private ConfigModifier<String, String> cm = new ConfigModifier("mods");

  /**
   * Creates a new name generator
   */
  public NameGenerator() {
    starNamesMod = cm.getAllFiles("stars");
    readStarFiles();
  }

  // reads fields with stars that are saved inside the jar.
  private void readStarFiles() {
    Reflections reflections = new Reflections("tech.subluminal", new ResourcesScanner());
    Pattern pat = Pattern.compile(".*\\.*star.*\\.txt");
    Set<String> fileNames = reflections.getResources(pat);

    if (!fileNames.isEmpty()) {
      fileNames.forEach(path -> {
        starNames.add(new LinkedList<>());
        readLines(path);
        fileCounter++;
      });

      // check if there are star mods present and load star names
      starNamesMod.forEach(file -> {
        starNames.add(new LinkedList<>());
        readLines(file);
        fileCounter++;
      });
    } else {
      throw new IllegalArgumentException("No list with star names in resources found!");
    }
  }

  private void readLines(File file) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      readLines(br);
    } catch (FileNotFoundException e) {
      Logger.error(e);
    }
  }

  private void readLines(String path) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
          NameGenerator.class.getResource("/" + path).openStream(), "UTF-8"));
      readLines(br);
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  private void readLines(BufferedReader br) {
    try {
      // check how rare these stars should be
      int tempWeight;
      br.mark(1);
      if (br.read() != 0xFEFF) {
        br.reset();
        tempWeight = Integer.parseInt(br.readLine());
      } else {
        br.reset();
        tempWeight = Integer.parseInt(br.readLine().substring(1));
      }

      starListWeights.add(tempWeight);
      sumWeights += tempWeight;

      br.lines().forEach(l -> {
        starNames.get(fileCounter).add(l);
      });
    } catch (IOException e) {
      Logger.error(e);
    }
  }

  /**
   * @return a single random star name.
   */
  public String getName() {
    Random rand = new Random(GlobalSettings.SHARED_RANDOM.nextLong());
    int randRange = (int) (rand.nextDouble()*sumWeights);
    int tempIndex = 0;
    int tempSum = starListWeights.get(tempIndex);

    while (tempSum < randRange) {
      tempSum += starListWeights.get(tempIndex + 1);
      tempIndex++;
    }

    String newName;
    if (!starNames.get(tempIndex).isEmpty()) {
      newName = starNames.get(tempIndex).remove(rand.nextInt(starNames.get(tempIndex).size()));
      starNamesReserve.addLast(newName);
    } else {
      starNamesReserve.addLast(starNamesReserve.peekFirst());
      newName = starNamesReserve.getFirst();
    }
    return newName;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    starNames.forEach(s -> sb.append(s + "\n"));

    return sb.toString();
  }
}
