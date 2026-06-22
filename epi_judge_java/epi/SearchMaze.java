package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
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
    int maxr = maze.size(); 
    int maxc = maze.get(0).size();

    int[][] track = new int[maxr][maxc];
    Coordinate[][] parent = new Coordinate[maxr][maxc];
    Queue<Coordinate> queue = new LinkedList<>();

    int[][] directions = new int[][] { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};                                            
    
    int cx = s.x;
    int cy = s.y;
  
    queue.offer(s);
    track[s.x][s.y] = 1;
    parent[s.x][s.y] = new Coordinate(-1, -1);
    boolean found = false;

    while (!queue.isEmpty()) {
      Coordinate cur = queue.poll();

      for (int[] dir : directions) {
        int nx = cur.x + dir[0];
        int ny = cur.y + dir[1];

        if (nx < 0 || nx >= maxr || ny < 0 || ny >= maxc || maze.get(nx).get(ny).equals(Color.BLACK) || track[nx][ny] != 0) {
          continue;
        }

        parent[nx][ny] = new Coordinate(cur.x, cur.y);
        track[nx][ny] = 1;

        if (nx == e.x && ny == e.y) {
          found = true;
          break;
        } 
        queue.offer(new Coordinate(nx, ny));
      }

      if (found) {
        break;
      }

      track[cur.x][cur.y] = 2;
    }
    List<Coordinate> res = new ArrayList<>();
    if (!found) {
      return res;
    }
    
    while (e != s) {
      //System.out.println("e.x : " + e.x + " e.y : " + e.y);
      if (e == null || e.x < 0 || e.y < 0) break;
      res.add(e);
      e = parent[e.x][e.y];
    }
    
    Collections.reverse(res);
    //print(res);
    return res;
  }

  private static void print(List<Coordinate> path) {
    for (Coordinate cor : path) {
      System.out.println(cor.x + " " + cor.y);
    }
  }

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
