package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class MinimumWaitingTime {
  @EpiTest(testDataFile = "minimum_waiting_time.tsv")

  public static int minimumTotalWaitingTime(List<Integer> serviceTimes) {
    if (serviceTimes.isEmpty()) return 0;
    Collections.sort(serviceTimes);
    int[] wt = new int[serviceTimes.size()];
    wt[0] = 0;

    int total=0;
    for(int i=1; i < serviceTimes.size(); i++) {
      wt[i] = wt[i-1] + serviceTimes.get(i-1);
      total += wt[i];
    }

    return total;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumWaitingTime.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
