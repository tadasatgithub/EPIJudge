package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class MinimumWeightPathInATriangle {
  @EpiTest(testDataFile = "minimum_weight_path_in_a_triangle.tsv")

  public static int minimumPathTotal(List<List<Integer>> triangle) {
    int minPathSum = Integer.MAX_VALUE;
    for (int r=0; r < triangle.size(); r++) {
      List<Integer> row = triangle.get(r);

      if (row.size() == 1) {
        continue;
      }

      for (int pos=0; pos < row.size(); pos++) {
        int total = row.get(pos);
        if (pos ==0) {
          total += triangle.get(r-1).getFirst();
        } else if (pos == row.size()-1) {
          total += triangle.get(r-1).getLast();
        } else {
          total += Math.min(triangle.get(r-1).get(pos-1), triangle.get(r-1).get(pos));
        }
        row.set(pos, total);
        if (r == triangle.size()-1) {
          minPathSum = Math.min(minPathSum, total);
        }
      }
    }

    return minPathSum == Integer.MAX_VALUE ? 0 : minPathSum;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumWeightPathInATriangle.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
