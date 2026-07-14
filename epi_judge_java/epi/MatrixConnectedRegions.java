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

    int[][] dir = new int[][] {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    Queue<int[]> queue = new LinkedList<>();

    queue.offer(new int[]{x, y});
    boolean initialColor = image.get(x).get(y);
    image.get(x).set(y, !initialColor);

    while (!queue.isEmpty()) {
      int[] cur = queue.poll();

      for (int[] d : dir) {
        int nx=cur[0]+d[0];
        int ny=cur[1]+d[1];

        if (nx < 0 || nx >= image.size() || ny < 0 || ny >= image.getFirst().size() || (image.get(nx).get(ny) != initialColor)) {
          continue;
        }

        image.get(nx).set(ny, !initialColor);
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
