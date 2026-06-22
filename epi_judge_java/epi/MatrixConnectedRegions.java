package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatrixConnectedRegions {
  public static void flipColor(int x, int y, List<List<Boolean>> image) {
    int maxx=image.size();
    int maxy=image.get(0).size();
    Queue<int[]> queue = new LinkedList<>();
    int[][] track = new int[maxx][maxy];

    queue.offer(new int[] {x, y});
    int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    boolean color = image.get(x).get(y);
    track[x][y] = 1; // waiting
    image.get(x).set(y, !color);

    while (!queue.isEmpty()) {
      int[] cur = queue.poll();

      for (int[] dir : directions) {
        int nx = cur[0] + dir[0];
        int ny = cur[1] + dir[1];

        if (nx < 0 || nx >= maxx || ny < 0 || ny >= maxy || track[nx][ny] != 0 || image.get(nx).get(ny) != color) {
          continue;
        }

        track[nx][ny] = 1;
        image.get(nx).set(ny, !color);
        queue.offer(new int[] {nx, ny});
      }
    }
    
  }
  @EpiTest(testDataFile = "painting.tsv")
  public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                     int x, int y,
                                                     List<List<Integer>> image)
      throws Exception {
    List<List<Boolean>> B = new ArrayList<>();
    for (int i = 0; i < image.size(); i++) {
      B.add(new ArrayList<>());
      for (int j = 0; j < image.get(i).size(); j++) {
        B.get(i).add(image.get(i).get(j) == 1);
      }
    }

    executor.run(() -> flipColor(x, y, B));

    image = new ArrayList<>();
    for (int i = 0; i < B.size(); i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < B.get(i).size(); j++) {
        image.get(i).add(B.get(i).get(j) ? 1 : 0);
      }
    }

    return image;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
