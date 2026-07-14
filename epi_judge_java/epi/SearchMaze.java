package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.*;

public class SearchMaze {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Coordinate {
    public int x, y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Coordinate that = (Coordinate)o;
      if (x != that.x || y != that.y) {
        return false;
      }
      return true;
    }
  }

  public enum Color { WHITE, BLACK }

  public static List<Coordinate> searchMaze(List<List<Color>> maze,
                                            Coordinate s, Coordinate e) {
    List<Coordinate> res = new ArrayList<>();
    Queue<Coordinate> queue = new LinkedList<>();
    boolean[][] visited = new boolean[maze.size()][maze.getFirst().size()];
    Coordinate[][] parents = new Coordinate[maze.size()][maze.getFirst().size()];
    queue.offer(s);
    visited[s.x][s.y] = true;
    int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    boolean found = false;
    parents[s.x][s.y] = null;

    while (!queue.isEmpty()) {
      Coordinate cur = queue.poll();

      if (cur.x == e.x && cur.y == e.y) {
        found = true;
        break;
      }

      for (int[] d : dir) {
        int nx = cur.x + d[0];
        int ny = cur.y + d[1];

        if (nx < 0 ||
                nx >= maze.size() ||
                ny < 0 ||
                ny >= maze.get(nx).size() ||
                maze.get(nx).get(ny) == Color.BLACK ||
                visited[nx][ny]) {
          continue;
        }
        parents[nx][ny] = cur;
        visited[nx][ny] = true;
        Coordinate nc = new Coordinate(nx, ny);
        queue.offer(nc);
      }
    }

    if (found) {
      Coordinate parentCell = parents[e.x][e.y];
      res.add(e);
      while (parentCell != null) {
        res.add(parentCell);
        parentCell = parents[parentCell.x][parentCell.y];
      }

      return res.reversed();
    }

    return Collections.emptyList();
  }

  // private static boolean dfs()
  public static boolean pathElementIsFeasible(List<List<Integer>> maze,
                                              Coordinate prev, Coordinate cur) {
    if (!(0 <= cur.x && cur.x < maze.size() && 0 <= cur.y &&
          cur.y < maze.get(cur.x).size() && maze.get(cur.x).get(cur.y) == 0)) {
      return false;
    }
    return cur.x == prev.x + 1 && cur.y == prev.y ||
        cur.x == prev.x - 1 && cur.y == prev.y ||
        cur.x == prev.x && cur.y == prev.y + 1 ||
        cur.x == prev.x && cur.y == prev.y - 1;
  }

  @EpiTest(testDataFile = "search_maze.tsv")
  public static boolean searchMazeWrapper(List<List<Integer>> maze,
                                          Coordinate s, Coordinate e)
      throws TestFailure {
    List<List<Color>> colored = new ArrayList<>();
    for (List<Integer> col : maze) {
      List<Color> tmp = new ArrayList<>();
      for (Integer i : col) {
        tmp.add(i == 0 ? Color.WHITE : Color.BLACK);
      }
      colored.add(tmp);
    }
    List<Coordinate> path = searchMaze(colored, s, e);
    if (path.isEmpty()) {
      return s.equals(e);
    }

    if (!path.get(0).equals(s) || !path.get(path.size() - 1).equals(e)) {
      throw new TestFailure("Path doesn't lay between start and end points");
    }

    for (int i = 1; i < path.size(); i++) {
      if (!pathElementIsFeasible(maze, path.get(i - 1), path.get(i))) {
        throw new TestFailure("Path contains invalid segments");
      }
    }

    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchMaze.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
