package tech.subluminal.shared.util.poissonDiskSampler;

import java.util.List;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class SamplerTest {
  public static void main(String[] args) {
    double x0 = 0;
    double x1 = 1;
    double y0 = 0;
    double y1 = 1;
    double minDist = 0.1;
    int amount = 7;

    uniform(x0, y0, x1, y1, minDist, amount);
    //perlin(x0, y0, x1, y1, minDist);
    //falloff(x0, y0, x1, y1, minDist);
  }

  public static void uniform(double x0, double y0, double x1, double y1, double minDist, int amount) {
    UniformPoissonDiskSampler ufpds = new UniformPoissonDiskSampler(x0,y0,x1,y1,minDist, amount);
    List<Coordinates> points = ufpds.sample();
  }

  public static void perlin(double x0, double y0, double x1, double y1, double minDist) {
    RealFunction2DWrapper realfn = new RealFunction2DWrapper(new PerlinFunction2D((int) x1,(int) y1, 3), 0.1, 1, 0.01, 1);
    PoissonDiskSampler pds = new PoissonDiskSampler(x0,y0,x1,y1,minDist, realfn);
    List<Coordinates> points = pds.sample();
  }


  public static void falloff(double x0, double y0, double x1, double y1, double minDist) {
    Falloff realfn = new Falloff(x1 / 2, y1 / 2, x1 / 2, 1, .5);
    PoissonDiskSampler pds = new PoissonDiskSampler(x0,y0,x1,y1,minDist, realfn);
    List<Coordinates> points = pds.sample();
  }
}
