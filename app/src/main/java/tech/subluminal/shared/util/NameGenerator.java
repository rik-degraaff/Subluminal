package tech.subluminal.shared.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
  private List<List<String>> starNames = new ArrayList<>();
  private List<Integer> starListWeights = new ArrayList<>();
  private int sumWeights;
  private int fileCounter = 0;
  private List<String> planetNames; // for later use

  /**
   * Creates a new name generator
   */
  public NameGenerator() {
    readStarFiles();
  }

  // reads fields with stars that are saved inside the jar.
  private void readStarFiles() {
    Reflections reflections = new Reflections("tech.subluminal", new ResourcesScanner());
    Pattern pat = Pattern.compile(".*\\.*star.*\\.txt");
    Set<String> fileNames = reflections.getResources(pat);

    if (fileNames.isEmpty()) {
      throw new IllegalArgumentException("No list with starnames in resources found!");
    } else {
      fileNames.forEach(file -> {
        starNames.add(new ArrayList<>());
        readLines(file);
        fileCounter++;
      });
    }
  }

  private void readLines(String path) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(
          NameGenerator.class.getResource("/" + path).openStream(), "UTF-8"));

      // check how rare these stars should be
      int tempWeight = 0;
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
      tempSum += starListWeights.get(tempIndex+1);
      tempIndex++;
    }

    return starNames.get(tempIndex).remove(rand.nextInt(starNames.get(tempIndex).size()));
  }

  /**
   * @param count the amount of names to generate.
   * @return a list of star names.
   */
  public List<String> getNames(int count) {
    List<String> names = new LinkedList<>();
    int rIndex;
    for (int i = 0; i < count; i++) {
      rIndex = rand.nextInt(starNames.size() - 1);
      if (names.contains(planetNames.get(rIndex + 1))) {
        i--;
      } else {
        names.add(planetNames.remove(rIndex + 1));
      }
    }
    return names;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    starNames.forEach(s -> sb.append(s + "\n"));

    return sb.toString();
  }
}
