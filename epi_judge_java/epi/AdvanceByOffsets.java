package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class AdvanceByOffsets {
  @EpiTest(testDataFile = "advance_by_offsets.tsv")
  public static boolean canReachEnd(List<Integer> maxAdvanceSteps) {
    int pos=0;
    int maxICanReach = 0;

    while (pos <= maxICanReach) {
      maxICanReach = Math.max(maxICanReach, pos + maxAdvanceSteps.get(pos));

      if (maxICanReach >= maxAdvanceSteps.size()-1) {
        return true;
      }

      pos++;
    }
    return false;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "AdvanceByOffsets.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
