package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.*;

public class MinimumPointsCoveringIntervals {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Interval {
    public int left, right;

    public Interval(int l, int r) {
      this.left = l;
      this.right = r;
    }
  }

  @EpiTest(testDataFile = "minimum_points_covering_intervals.tsv")

  public static Integer findMinimumVisits(List<Interval> intervals) {
    if (intervals.isEmpty()) {
      return 0;
    }
    intervals.sort((interval1, interval2) -> {
        if (interval1.right == interval2.right) {
            return interval1.left - interval2.left;
        }

        return interval1.right - interval2.right;
    });

    int count=1;
    Interval lastInterval=intervals.getFirst();

    for (int pos=1; pos < intervals.size(); pos++) {
      Interval curInterval = intervals.get(pos);
      //System.out.println(curInterval.left + " : " + curInterval.right);
      if (curInterval.left > lastInterval.right) {
        lastInterval = curInterval;
        count++;
      }
    }

    return count;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MinimumPointsCoveringIntervals.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
