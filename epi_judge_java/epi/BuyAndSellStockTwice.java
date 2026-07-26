package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class BuyAndSellStockTwice {
  @EpiTest(testDataFile = "buy_and_sell_stock_twice.tsv")
  public static double buyAndSellStockTwice(List<Double> prices) {
    double[] firstprofits = new double[prices.size()];
    firstprofits[0] = 0.0;
    double maxProfit = 0;
    double minValue = prices.getFirst();

    for (int i=1; i < prices.size(); i++) {
      maxProfit = Math.max(maxProfit, prices.get(i) - minValue);
      minValue = Math.min(minValue, prices.get(i));
      firstprofits[i] = maxProfit;
    }

    double maxSellPrice = prices.getLast();
    maxProfit = 0.0;
    double[] secondprofits = new double[prices.size()];
    secondprofits[prices.size()-1] = 0.0;
    for (int i=prices.size()-2; i >= 0 ;i--) {
      maxProfit = Math.max(maxProfit, maxSellPrice - prices.get(i));
      maxSellPrice = Math.max(maxSellPrice, prices.get(i));
      secondprofits[i] = maxProfit;
    }

    maxProfit = 0.0;
    for(int p=0; p < firstprofits.length; p++) {
      firstprofits[p] += (p+1 < firstprofits.length) ? secondprofits[p+1] : 0;
      maxProfit = Math.max(maxProfit, firstprofits[p]);
    }

    return maxProfit;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStockTwice.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
