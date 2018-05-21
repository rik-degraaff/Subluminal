package tech.subluminal.shared.util.poissonDiskSampler;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import tech.subluminal.shared.stores.records.game.Coordinates;


/**
 * Algorithm based on <emph>Fast Poisson Disk Sampling in Arbitrary Dimensions</emph> by Robert
 * Bridson. To use, construct a new PoissonDisk with suitable parameters, and call generate to get a
 * list of points.
 *
 * @author Herman Tulleken
 */
public class UniformPoissonDiskSampler {

  private final static int DEFAULT_POINTS_TO_GENERATE = 30;
  private final int pointsToGenerate; // k in literature
  private final Coordinates p0, p1;
  private final Coordinates dimensions;
  private final double cellSize; // r / sqrt(n), for 2D: r / sqrt(2)
  private final double minDist; // r
  private final int gridWidth, gridHeight;
  private final Random rand = new Random();

  /**
   * Construct a new PoissonDisk object, with a given domain and minimum distance between points.
   *
   * @param x0 x-coordinate of bottom left corner of domain.
   * @param y0 x-coordinate of bottom left corner of domain.
   * @param x1 x-coordinate of bottom left corner of domain.
   * @param y1 x-coordinate of bottom left corner of domain.
   * @param minDist The minimum distance between two points.
   */
  public UniformPoissonDiskSampler(double x0, double y0, double x1, double y1, double minDist) {
    this(x0, y0, x1, y1, minDist, DEFAULT_POINTS_TO_GENERATE);
  }

  public UniformPoissonDiskSampler(double x0, double y0, double x1, double y1, double minDist,
      int pointsToGenerate) {
    p0 = new Coordinates(x0, y0);
    p1 = new Coordinates(x1, y1);
    dimensions = new Coordinates(x1 - x0, y1 - y0);

    this.minDist = minDist;
    this.pointsToGenerate = pointsToGenerate;
    cellSize = minDist / Math.sqrt(2);
    gridWidth = (int) (dimensions.getX() / cellSize) + 1;
    gridHeight = (int) (dimensions.getY() / cellSize) + 1;
  }

  /**
   * Generates a list of points following the Poisson distribution. No more than MAX_POINTS are
   * produced.
   */
  public List<Coordinates> sample() {
    Coordinates grid[][] = new Coordinates[gridWidth][gridHeight]; // background grid

    List<Coordinates> activeList = new LinkedList<Coordinates>();
    List<Coordinates> pointList = new LinkedList<Coordinates>();

    for (int i = 0; i < gridWidth; i++) {
      for (int j = 0; j < gridHeight; j++) {
        grid[i][j] = null;
      }
    }

    addFirstPoint(grid, activeList, pointList);

    while (!activeList.isEmpty() && (pointList.size() < PoissonDiskSampler.MAX_POINTS)) {
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

  /**
   * Adds a given point in the sampling collection, provided it is not too close to an existing
   * sampling point.
   *
   * @param grid The background grid, used to obtain points near a point quickly.
   * @param activeList Points not yet processed.
   * @param pointList Points in the sampling collection.
   * @param point The new point to add.
   */
  private boolean addNextPoint(Coordinates[][] grid, List<Coordinates> activeList,
      List<Coordinates> pointList, Coordinates point) {
    boolean found = false;
    Coordinates q = PoissonDiskSampler.generateRandomAround(point, minDist);

    if ((q.getX() >= p0.getX()) && (q.getX() < p1.getX()) && (q.getY() > p0.getY()) && (q.getY() < p1.getY())) {
      Coordinates qIndex = PoissonDiskSampler.pointDoubleToInt(q, p0, cellSize);
      boolean tooClose = false;

      for (int i = Math.max(0, qIndex.getIntX() - 2); (i < Math.min(gridWidth, qIndex.getIntX() + 3)) && !tooClose;
          i++) {
        for (int j = Math.max(0, qIndex.getIntY() - 2);
            (j < Math.min(gridHeight, qIndex.getIntY() + 3)) && !tooClose; j++) {
          if (grid[i][j] != null) {
            if (grid[i][j].getDistanceFrom(q) < minDist) {
              tooClose = true;
            }
          }
        }
      }

      if (!tooClose) {
        found = true;
        activeList.add(q);
        pointList.add(q);
        grid[qIndex.getIntX()][qIndex.getIntY()] = q;
      }
    }
    return found;
  }

  /**
   * Randomly selects the first sampling point.
   *
   * @param grid The background grid, used to obtain points near a point quickly.
   * @param activeList Points not yet processed.
   * @param pointList Points in the sampling collection.
   */
  private void addFirstPoint(Coordinates[][] grid, List<Coordinates> activeList,
      List<Coordinates> pointList) {
    double d = rand.nextDouble();
    double xr = p0.getX() + dimensions.getX() * (d);

    d = rand.nextDouble();
    double yr = p0.getY() + dimensions.getY() * (d);

    Coordinates p = new Coordinates(xr, yr);
    Coordinates index = PoissonDiskSampler.pointDoubleToInt(p, p0, cellSize);

    grid[index.getIntX()][index.getIntY()] = p;

    activeList.add(p);
    pointList.add(p);
  }
}
