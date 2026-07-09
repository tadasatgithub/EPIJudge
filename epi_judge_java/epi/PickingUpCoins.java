package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class PickingUpCoins {
  @EpiTest(testDataFile = "picking_up_coins.tsv")

  public static int pickUpCoins(List<Integer> coins) {
    Integer[][] dp = new Integer[coins.size()][coins.size()];
    int sum = 0;
    for (int coin : coins) {
      sum += coin;
    }
    return maxRevenue(coins, 0, coins.size()-1, sum, dp);
  }

  private static int maxRevenue(List<Integer> coins, int start, int end, int sum, Integer[][] dp) {
    if (start > end) {
      return 0;
    }
    int startValue = coins.get(start);
    int endValue = coins.get(end);
    if (dp[start][end] == null) {
      dp[start][end] = Math.max(startValue + (sum - startValue) - maxRevenue(coins, start + 1, end, sum - startValue, dp),
              endValue + (sum - endValue) - maxRevenue(coins, start, end - 1, sum - endValue, dp));
    }

    return dp[start][end];
  }



  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PickingUpCoins.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
