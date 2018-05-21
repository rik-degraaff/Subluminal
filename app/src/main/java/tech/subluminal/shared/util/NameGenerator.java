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

public class NameGenerator {

  private Random rand = new Random(GlobalSettings.SHARED_RANDOM.nextLong());
  private List<List<String>> starNames = new ArrayList<>();
  private List<Integer> starListWeights = new ArrayList<>();
  private int sumWeights;
  private int fileCounter = 0;
  private ConfigModifier<String, String> cm = new ConfigModifier("mods");

  public NameGenerator() {
    starNamesMod = cm.getAllFiles("stars");
    readStarFiles();
  }

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
      starNamesMod.forEach(file -> {
        starNames.add(new LinkedList<>());
        readLines(file);
        fileCounter++;
      });
    } else {
      throw new IllegalArgumentException("No list with starnames in resources found!");
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
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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

//TODO: Implement this method for star names
//  public List<String> getNames(int count) {
//    List<String> names = new LinkedList<>();
//    int rIndex;
//    for (int i = 0; i < count; i++) {
//      rIndex = rand.nextInt(starNames.size() - 1);
//      if (names.contains(planetNames.get(rIndex + 1))) {
//        i--;
//      } else {
//        names.add(planetNames.remove(rIndex + 1));
//      }
//    }
//    return names;
//  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    starNames.forEach(s -> sb.append(s + "\n"));

    return sb.toString();
  }
}
