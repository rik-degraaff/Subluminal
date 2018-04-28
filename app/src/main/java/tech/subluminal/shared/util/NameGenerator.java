package tech.subluminal.shared.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

public class NameGenerator {

  Collection<String[]> starNames;
  Collection<String[]> planetNames;

  public NameGenerator() {
    this.starNames = new HashSet<>();
  }

  public void readStarFiles() {
    Reflections reflections = new Reflections("tech.subluminal", new ResourcesScanner());
    Set<String> fileNames = reflections.getResources(Pattern.compile(".*\\.csv"));

    System.out.println(".csv files found:");
    fileNames.forEach(name -> {
      System.out.println(name);
      readLines(name);
    });


  }

  public void readLines(String path) {
    Scanner sc = new Scanner(
        NameGenerator.class.getResourceAsStream("/"+path));

    while (sc.hasNext()) {
      System.out.println(sc.next());
    }
    sc.close();
  }

}
