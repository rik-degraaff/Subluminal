package tech.subluminal.shared.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
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
    Set<String> fileNames = reflections.getResources(Pattern.compile(".*\\.csv"));

    if (fileNames.isEmpty()) {
      throw new IllegalArgumentException("No list with starnames in resources found!");
    } else {
      Logger.info(".csv files found:");
      fileNames.forEach(file -> {
        Logger.info(file);
        readLines(file);
      });
    }
  }

  private void readLines(String path) {
//    File fileDir = new File("D:\\_projects\\Gruppe-11\\assets\\other\\starlist\\nearest-stars.csv");
//
//    try {
//      BufferedReader in = new BufferedReader(
//          new InputStreamReader(
//              new FileInputStream(fileDir)));
//
//      String str;
//
//      while ((str = in.readLine()) != null) {
//        System.out.println(str);
//      }
//
//      //System.out.println(System.getProperty("file.encoding"));
//      in.close();
//
//    } catch (UnsupportedEncodingException e) {
//      e.printStackTrace();
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    Scanner sc = new Scanner(NameGenerator.class.getResourceAsStream("/" + path), "UTF-8");
    while (sc.hasNext()) {
      String line = sc.nextLine();
      //System.out.println(line);
      starNames.add(line.split(";")[0]);
    }
    sc.close();
  }

  public String getName() {
    Random rand = new Random();
    int rIndex = rand.nextInt(starNames.size()-1);
    return starNames.remove(rIndex+1);
  }

  public List<String> getNames(int count) {
    Random rand = new Random();
    List<String> names = new LinkedList<>();
    int rIndex;
    for (int i = 0; i < count; i++) {
      rIndex = rand.nextInt(starNames.size()-1);
      if (names.contains(planetNames.get(rIndex+1))) {
        i--;
      } else {
        names.add(planetNames.remove(rIndex+1));
      }
    }
    return names;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    starNames.forEach(s -> sb.append(s+"\n"));

    return sb.toString();
  }
}
