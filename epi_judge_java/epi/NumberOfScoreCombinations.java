package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class NumberOfScoreCombinations {
  @EpiTest(testDataFile = "number_of_score_combinations.tsv")

  public static int
  numCombinationsForFinalScore(int finalScore,
                               List<Integer> individualPlayScores) {
    int[][] dp = new int[individualPlayScores.size()][1+finalScore];

    for (int r=0; r < dp.length; r++) {
      dp[r][0] = 1;
    }

    for (int c=1; c <= finalScore; c++) {
      dp[0][c] = (c % individualPlayScores.getFirst()) == 0 ? 1 : 0;
    }

    for (int r=1; r < individualPlayScores.size(); r++) {
      int score = individualPlayScores.get(r);
      for (int c=1; c <= finalScore; c++) {
        dp[r][c] = dp[r-1][c] + ((score <= c) ? dp[r][c-score] : 0);
      }
    }
    return dp[individualPlayScores.size()-1][finalScore];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfScoreCombinations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
