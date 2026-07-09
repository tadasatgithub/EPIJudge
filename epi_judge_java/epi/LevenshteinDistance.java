package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class LevenshteinDistance {
  @EpiTest(testDataFile = "levenshtein_distance.tsv")

  public static int levenshteinDistance(String A, String B) {
    char[] ac = A.toCharArray();
    char[] bc = B.toCharArray();

    int[][] dp = new int[1+ac.length][bc.length+1];

    for (int r=0; r <= ac.length; r++) {
      dp[r][0] = r;
    }

    for (int c=1; c <= bc.length; c++) {
      dp[0][c] = c;
    }

    for (int r=1; r <= ac.length; r++) {
      for (int c=1; c <= bc.length; c++) {
        dp[r][c] = Math.min(dp[r-1][c-1] + ((ac[r-1] == bc[c-1]) ? 0 : 1), 1+Math.min(dp[r][c-1], dp[r-1][c]));
      }
    }

    //print(dp);
    return dp[ac.length][bc.length];
  }

  private static void print(int[][] dp) {
    for (int r=0; r < dp.length; r++) {
      for (int c=0; c < dp[r].length; c++) {
        System.out.print(dp[r][c] + " ");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LevenshteinDistance.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
