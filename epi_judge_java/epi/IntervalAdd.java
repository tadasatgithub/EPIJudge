package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class IntervalAdd {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Interval {
    public int left, right;

    public Interval(int l, int r) {
      this.left = l;
      this.right = r;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Interval interval = (Interval)o;

      if (left != interval.left) {
        return false;
      }
      return right == interval.right;
    }

    @Override
    public String toString() {
      return "[" + left + ", " + right + "]";
    }
  }

  @EpiTest(testDataFile = "interval_add.tsv")

  public static List<Interval> addInterval(List<Interval> disjointIntervals,
                                           Interval newInterval) {
    List<Interval> result = new ArrayList<>();
    boolean foundOverlap = false;
    for (int pos=0; pos < disjointIntervals.size(); pos++) {
      Interval curInt = disjointIntervals.get(pos);

      if (isBefore(curInt, newInterval)) {
        result.add(curInt);
      } else {
        handleOverlap(disjointIntervals, pos, result, newInterval);
        foundOverlap = true;
        break;
      }
    }

    if (!foundOverlap) {
      result.add(newInterval);
    }
    return result;
  }

  private static void handleOverlap(List<Interval> dis, int pos, List<Interval> result, Interval newInt) {
    for (int i=pos; i < dis.size(); i++) {
      Interval cur = dis.get(i);

      if (hasOverLap(cur, newInt)) {
        newInt = new Interval(Math.min(cur.left, newInt.left), Math.max(cur.right, newInt.right));
      } else {
        result.add(newInt);
        newInt = cur;
      }
    }

    result.add(newInt);
  }

  private static boolean hasOverLap(Interval cur, Interval newInt) {
    return cur.right >= newInt.left && newInt.right >= cur.left;
  }

  private static boolean isBefore(Interval curI, Interval newI) {
    return curI.left < newI.left && curI.right < newI.left;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntervalAdd.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
