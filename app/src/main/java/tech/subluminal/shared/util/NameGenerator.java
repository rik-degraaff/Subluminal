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

public class NameGenerator {

  private List<String> starNames = new ArrayList<>();
  private List<String> planetNames; //for later use

  public NameGenerator() {

    readStarFiles();

  }

  public void readStarFiles() {
    Reflections reflections = new Reflections("tech.subluminal", new ResourcesScanner());
    Set<String> fileNames = reflections.getResources(Pattern.compile(".*\\.txt"));

    if (fileNames.isEmpty()) {
      throw new IllegalArgumentException("No list with starnames in resources found!");
    } else {
      Logger.info(".txt files found:");
      fileNames.forEach(file -> {
        //Logger.info(file);
        readLines(file);
      });
    }


    /*for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        String file = listOfFiles[i].getName();
        System.out.println("File " + file);
        // get last index for '.' char
        int lastIndex = file.lastIndexOf('.');

        // get extension
        String str = file.substring(lastIndex);

        // match path name extension
        if (str.equals(".txt")) {
          readLines("/tech/subluminal/resources/namegenerator/stars/" + listOfFiles[i].getName());
        }

      }
    }*/
    //readLines("/tech/subluminal/resources/namegenerator/stars/nearest-stars-single.txt");
  }

  private void readLines(String path) {
    Logger.debug(path);
    //System.out.println(NameGenerator.class.getResource("/" + path).getPath());
    BufferedReader br = null;
    try {
      br = new BufferedReader(new InputStreamReader(
          NameGenerator.class.getResource("/" + path).openStream(), "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    br.lines().forEach(l -> {
      starNames.add(l);
      //System.out.println(l);
    });

  }

  public String getName() {
    Random rand = new Random();
    int rIndex = rand.nextInt(starNames.size() - 1);
    return starNames.remove(rIndex + 1);
  }

  public List<String> getNames(int count) {
    Random rand = new Random();
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
