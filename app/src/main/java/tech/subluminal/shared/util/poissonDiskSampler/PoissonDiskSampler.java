package tech.subluminal.shared.util.poissonDiskSampler;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import tech.subluminal.shared.stores.records.game.Coordinates;

/**
 * Algorithm based on <i>Fast Poisson Disk Sampling in Arbitrary Dimensions</i> by Robert
 * Bridson, but with an arbitrary minimum distance function. See also the paper <i>A Spatial Data
 * Structure for Fast Poisson-Disk Sample Generation</i> Daniel Dunbar and Greg Humphreys for
 * other algorithms and a comparison.
 */
public class PoissonDiskSampler {

  private final static int DEFAULT_POINTS_TO_GENERATE = 30;
  private final int pointsToGenerate; // k in literature
  private final Coordinates p0, p1;
  private final Coordinates dimensions;
  private final double cellSize; // r / sqrt(n), for 2D: r / sqrt(2)
  private final double minDist; // r
  private final int gridWidth, gridHeight;
  private static final Random rand = new Random();

  /**
   * A safety measure - no more than this number of points are produced by ther algorithm.
   */
  public final static int MAX_POINTS = 100000;

  private RealFunction2DDouble distribution;

  /**
   * Construct a new PoissonDisk object, with a given domain and minimum distance between points.
   *
   * @param x0 x-coordinate of bottom left corner of domain.
   * @param y0 x-coordinate of bottom left corner of domain.
   * @param x1 x-coordinate of bottom left corner of domain.
   * @param y1 x-coordinate of bottom left corner of domain.
   * @param distribution A function that gives the minimum radius between points in the vicinity of
   * a point.
   */
  public PoissonDiskSampler(double x0, double y0, double x1, double y1, double minDist,
      RealFunction2DDouble distribution, int pointsToGenerate) {
    p0 = new Coordinates(x0, y0);
    p1 = new Coordinates(x1, y1);
    dimensions = new Coordinates(x1 - x0, y1 - y0);

    this.minDist = minDist;
    this.distribution = distribution;
    this.pointsToGenerate = pointsToGenerate;
    cellSize = minDist / Math.sqrt(2);
    gridWidth = (int) (dimensions.getX() / cellSize) + 1;
    gridHeight = (int) (dimensions.getY() / cellSize) + 1;
  }

  /**
   * Construct a new PoissonDisk object, with a given domain and minimum distance between points.
   *
   * @param x0 x-coordinate of bottom left corner of domain.
   * @param y0 x-coordinate of bottom left corner of domain.
   * @param x1 x-coordinate of bottom left corner of domain.
   * @param y1 x-coordinate of bottom left corner of domain.
   * @param distribution A function that gives the minimum radius between points in the vicinity of
   * a point.
   */
  public PoissonDiskSampler(double x0, double y0, double x1, double y1, double minDist,
      RealFunction2DDouble distribution) {
    this(x0, y0, x1, y1, minDist, distribution, DEFAULT_POINTS_TO_GENERATE);
  }

  /**
   * Generates a list of points following the Poisson distribution. No more than MAX_POINTS are
   * produced.
   *
   * @return The sample set.
   */
  @SuppressWarnings("unchecked")
  public List<Coordinates> sample() {
    List<Coordinates> activeList = new LinkedList<Coordinates>();
    List<Coordinates> pointList = new LinkedList<Coordinates>();
    List<Coordinates> grid[][] = new List[gridWidth][gridHeight];

    for (int i = 0; i < gridWidth; i++) {
      for (int j = 0; j < gridHeight; j++) {
        grid[i][j] = new LinkedList<Coordinates>();
      }
    }

    addFirstPoint(grid, activeList, pointList);

    while (!activeList.isEmpty() && (pointList.size() < MAX_POINTS)) {
      int listIndex = rand.nextInt(activeList.size());

      Coordinates point = activeList.get(listIndex);
      boolean found = false;

      for (int k = 0; k < pointsToGenerate; k++) {
        found |= addNextPoint(grid, activeList, pointList, point);
      }

      if (!found) {
        activeList.remove(listIndex);
      }
    }

    return pointList;
  }

  private boolean addNextPoint(List<Coordinates>[][] grid, List<Coordinates> activeList,
      List<Coordinates> pointList, Coordinates point) {
    boolean found = false;
    double fraction = distribution.getDouble((int) point.getX(), (int) point.getY());
    Coordinates q = generateRandomAround(point, fraction * minDist);

    if ((q.getX() >= p0.getX()) && (q.getX() < p1.getX()) && (q.getY() > p0.getY()) && (q.getY() < p1.getY())) {
      Coordinates qIndex = pointDoubleToInt(q, p0, cellSize);

      boolean tooClose = false;

      for (int i = Math.max(0, qIndex.getIntX() - 2); (i < Math.min(gridWidth, qIndex.getIntX() + 3)) && !tooClose;
          i++) {
        for (int j = Math.max(0, qIndex.getIntY() - 2);
            (j < Math.min(gridHeight, qIndex.getIntY() + 3)) && !tooClose; j++) {
          for (Coordinates gridPoint : grid[i][j]) {
            if (gridPoint.getDistanceFrom(q) < minDist * fraction) {
              tooClose = true;
            }
          }
        }
      }

      if (!tooClose) {
        found = true;
        activeList.add(q);
        pointList.add(q);
        grid[qIndex.getIntX()][qIndex.getIntY()].add(q);
      }
    }

    return found;
  }

  private void addFirstPoint(List<Coordinates>[][] grid, List<Coordinates> activeList,
      List<Coordinates> pointList) {
    double d = rand.nextDouble();
    double xr = p0.getX() + dimensions.getX() * (d);

    d = rand.nextDouble();
    double yr = p0.getY() + dimensions.getY() * (d);

    Coordinates p = new Coordinates(xr, yr);
    Coordinates index = pointDoubleToInt(p, p0, cellSize);

    grid[index.getIntX()][index.getIntY()].add(p);
    activeList.add(p);
    pointList.add(p);
  }

  /**
   * Converts a PointDouble to a PointInt that represents the index coordinates of the point in the
   * background grid.
   */
  static Coordinates pointDoubleToInt(Coordinates pointDouble, Coordinates origin,
      double cellSize) {
    return new Coordinates((int) ((pointDouble.getX() - origin.getX()) / cellSize),
        (int) ((pointDouble.getY() - origin.getY()) / cellSize));
  }

  /**
   * Generates a random point in the analus around the given point. The analus has inner radius
   * minimum distance and outer radius twice that.
   *
   * @param centre The point around which the random point should be.
   * @return A new point, randomly selected.
   */
  static Coordinates generateRandomAround(Coordinates centre, double minDist) {
    double d = rand.nextDouble();
    double radius = (minDist + minDist * (d));

    d = rand.nextDouble();
    double angle = 2 * Math.PI * (d);

    double newX = radius * Math.sin(angle);
    double newY = radius * Math.cos(angle);

    Coordinates randomPoint = new Coordinates(centre.getX() + newX, centre.getY() + newY);

    return randomPoint;
  }
}
