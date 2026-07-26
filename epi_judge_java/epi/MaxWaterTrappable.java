package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class MaxWaterTrappable {
  @EpiTest(testDataFile = "max_water_trappable.tsv")

  public static int calculateTrappingWater(List<Integer> heights) {
    int water=0;

    int[] max = new int[heights.size()];
    int maxSoFar = heights.getFirst();
    max[0] = maxSoFar;

    for (int pos=1; pos < heights.size(); pos++) {
      maxSoFar = Math.max(maxSoFar, heights.get(pos));
      max[pos] = maxSoFar;
    }

    maxSoFar = heights.getLast();

    for (int pos=heights.size()-2; pos >= 0; pos--) {
      water += heights.get(pos) < Math.min(maxSoFar, max[pos]) ? Math.min(maxSoFar, max[pos]) - heights.get(pos) : 0;
      maxSoFar = Math.max(maxSoFar, heights.get(pos));
    }
    return water;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxWaterTrappable.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
