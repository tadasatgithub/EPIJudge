package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class IntervalsUnion {

  public static class Interval {
    public Endpoint left = new Endpoint();
    public Endpoint right = new Endpoint();

    private static class Endpoint {
      public boolean isClosed;
      public int val;
    }
  }

  public static List<Interval> unionOfIntervals(List<Interval> intervals) {
    // First get all the interval who does not have any operlap with the next interval;

    intervals.sort((interval1, interval2) -> {
      if (interval1.left.val != interval2.left.val) {
        return interval1.left.val - interval2.left.val;
      } else {
        if (interval1.left.isClosed == interval2.left.isClosed) {
          return 0;
        } else if (interval1.left.isClosed) {
          return -1;
        } else {
          return 1;
        }
      }
    });

    List<Interval> result = new ArrayList<>();

    Interval cur = intervals.getFirst();

    for (int pos=1; pos < intervals.size(); pos++) {
      Interval next = intervals.get(pos);

      boolean overLapping = hasOverlapping(next, cur);

      if (overLapping) {
        cur = merge(next, cur);
      } else {
        result.add(cur);
        cur = next;
      }
    }

    result.add(cur);

    return result;
  }

  private static Interval merge(Interval next, Interval cur) {
    Interval newInterval = new Interval();
    newInterval.left.val = Math.min(cur.left.val, next.left.val);
    newInterval.left.isClosed = cur.left.val < next.left.val ? cur.left.isClosed : (cur.left.val > next.left.val ? next.left.isClosed : (cur.left.isClosed || next.left.isClosed));

    newInterval.right.val = Math.max(cur.right.val, next.right.val);
    newInterval.right.isClosed = cur.right.val > next.right.val ? cur.right.isClosed : (cur.right.val < next.right.val ? next.right.isClosed : (cur.right.isClosed || next.right.isClosed));
    return newInterval;
  }
  private static boolean hasOverlapping(Interval next, Interval cur) {
    if (next.right.val > cur.left.val && cur.right.val > next.left.val) {
      return true;
    }

    if (next.left.val == cur.right.val && next.right.val > cur.left.val && (next.left.isClosed || cur.right.isClosed)) {
      return true;
    }

    if (next.right.val == cur.left.val && cur.right.val > next.left.val && (next.right.isClosed || cur.left.isClosed)) {
      return true;
    }

    return false;
  }

  @EpiUserType(
      ctorParams = {int.class, boolean.class, int.class, boolean.class})
  public static class FlatInterval {
    int leftVal;
    boolean leftIsClosed;
    int rightVal;
    boolean rightIsClosed;

    public FlatInterval(int leftVal, boolean leftIsClosed, int rightVal,
                        boolean rightIsClosed) {
      this.leftVal = leftVal;
      this.leftIsClosed = leftIsClosed;
      this.rightVal = rightVal;
      this.rightIsClosed = rightIsClosed;
    }

    public FlatInterval(Interval i) {
      if (i != null) {
        leftVal = i.left.val;
        leftIsClosed = i.left.isClosed;
        rightVal = i.right.val;
        rightIsClosed = i.right.isClosed;
      }
    }

    public Interval toInterval() {
      Interval i = new Interval();
      i.left.val = leftVal;
      i.left.isClosed = leftIsClosed;
      i.right.val = rightVal;
      i.right.isClosed = rightIsClosed;
      return i;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      FlatInterval that = (FlatInterval)o;

      if (leftVal != that.leftVal) {
        return false;
      }
      if (leftIsClosed != that.leftIsClosed) {
        return false;
      }
      if (rightVal != that.rightVal) {
        return false;
      }
      return rightIsClosed == that.rightIsClosed;
    }

    @Override
    public int hashCode() {
      int result = leftVal;
      result = 31 * result + (leftIsClosed ? 1 : 0);
      result = 31 * result + rightVal;
      result = 31 * result + (rightIsClosed ? 1 : 0);
      return result;
    }

    @Override
    public String toString() {
      return "" + (leftIsClosed ? "<" : "(") + leftVal + ", " + rightVal +
          (rightIsClosed ? ">" : ")");
    }
  }

  @EpiTest(testDataFile = "intervals_union.tsv")
  public static List<FlatInterval>
  unionIntervalWrapper(TimedExecutor executor, List<FlatInterval> intervals)
      throws Exception {
    List<Interval> casted = new ArrayList<>(intervals.size());
    for (FlatInterval in : intervals) {
      casted.add(in.toInterval());
    }

    List<Interval> result = executor.run(() -> unionOfIntervals(casted));

    intervals = new ArrayList<>(result.size());
    for (Interval i : result) {
      intervals.add(new FlatInterval(i));
    }
    return intervals;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntervalsUnion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
