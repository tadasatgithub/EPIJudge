package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class IsStringInMatrix {
  @EpiTest(testDataFile = "is_string_in_matrix.tsv")
  public static boolean isPatternContainedInGrid(List<List<Integer>> grid,
                                                 List<Integer> pattern) {
    Boolean[][][] dp = new Boolean[grid.size()][grid.getFirst().size()][pattern.size()];
    for (int r=0; r < grid.size(); r++) {
      for (int c=0; c < grid.get(r).size(); c++) {
        if (grid.get(r).get(c).equals(pattern.getFirst())) {
          boolean pathRes = checkPath(grid, pattern, dp, r, c, 0);
          if (pathRes) {
            return pathRes;
          }
        }
      }
    }
    return false;
  }

  private static boolean checkPath(List<List<Integer>> grid, List<Integer> pattern, Boolean[][][] dp, int r, int c, int pos) {
    if (pos >= pattern.size()) {
      return true;
    }
    if (r < 0 || r >= grid.size() || c < 0 || c >= grid.get(r).size() || !pattern.get(pos).equals(grid.get(r).get(c))) {
      return false;
    }
    if (dp[r][c][pos] == null) {
      dp[r][c][pos] = checkPath(grid, pattern, dp, r - 1, c, pos + 1) ||
              checkPath(grid, pattern, dp, r + 1, c, pos + 1) ||
              checkPath(grid, pattern, dp, r, c + 1, pos + 1) ||
              checkPath(grid, pattern, dp, r, c - 1, pos + 1);
    }

    return dp[r][c][pos];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringInMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
