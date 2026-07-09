package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.List;
public class Knapsack {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class Item {
    public Integer weight;
    public Integer value;

    public Item(Integer weight, Integer value) {
      this.weight = weight;
      this.value = value;
    }
  }

  @EpiTest(testDataFile = "knapsack.tsv")

  public static int optimumSubjectToCapacity(List<Item> items, int capacity) {
    int[][] dp = new int[items.size()][1+capacity];

    for (int r=0; r < items.size(); r++) {
      dp[r][0] = 0;
    }

    for (int c=1; c <= capacity; c++) {
      dp[0][c] = items.getFirst().weight <= c ? items.getFirst().value : 0;
    }

    for (int r=1; r < items.size(); r++) {
      Item item = items.get(r);
      for (int c=1; c <= capacity; c++) {
        int valueWithMe = item.weight <= c ? (item.value + dp[r-1][c-item.weight]) : dp[r-1][c];
        int valueWithoutMe = dp[r-1][c];
        dp[r][c] = Math.max(valueWithoutMe, valueWithMe);
      }
    }

    return dp[items.size()-1][capacity];
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Knapsack.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
