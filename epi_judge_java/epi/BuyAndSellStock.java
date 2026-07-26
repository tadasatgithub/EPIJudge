package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class BuyAndSellStock {
  @EpiTest(testDataFile = "buy_and_sell_stock.tsv")
  public static double computeMaxProfit(List<Double> prices) {
    double maxProfit = 0;
    Double minValue = prices.getFirst();

    for (int i=1; i < prices.size(); i++) {
      maxProfit = Math.max(maxProfit, prices.get(i) - minValue);
      minValue = Math.min(minValue, prices.get(i));
    }
    return maxProfit;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStock.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
