package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class NumberOfTraversalsMatrix {
  @EpiTest(testDataFile = "number_of_traversals_matrix.tsv")

  public static int numberOfWays(int n, int m) {
    int[][] dp = new int[n][m];

    for (int r=0; r < n; r++) {
      dp[r][0] = 1;
    }

    for (int c=0; c < m; c++) {
      dp[0][c] = 1;
    }

    for (int r=1; r < n; r++) {
      for (int c=1; c < m; c++) {
        dp[r][c] = dp[r-1][c] + dp[r][c-1];
      }
    }

    return dp[n-1][m-1];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfTraversalsMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
