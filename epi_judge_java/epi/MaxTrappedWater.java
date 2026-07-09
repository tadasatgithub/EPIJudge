package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class MaxTrappedWater {
  @EpiTest(testDataFile = "max_trapped_water.tsv")

  public static int getMaxTrappedWater(List<Integer> h) {
    int maxWater=0;

    int b=0, e=h.size()-1;

    while (b < e) {
      int left = h.get(b);
      int right = h.get(e);
      int min = Math.min(left, right);
      maxWater = Math.max(maxWater, min*(e-b));

      if (left == right) {
        b++; e--;
      } else if (left < right) {
        b++;
      } else {
        e--;
      }
    }

    return maxWater;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxTrappedWater.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
