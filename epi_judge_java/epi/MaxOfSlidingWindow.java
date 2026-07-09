package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.*;

public class MaxOfSlidingWindow {
  @EpiUserType(ctorParams = {int.class, double.class})

  public static class TrafficElement implements Comparable<TrafficElement> {
    public int time;
    public double volume;

    public TrafficElement(int time, double volume) {
      this.time = time;
      this.volume = volume;
    }

    @Override
    public int compareTo(TrafficElement o) {
      int volumeCmp = Double.compare(volume, o.volume);
      return volumeCmp != 0 ? volumeCmp : time - o.time;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      return compareTo((TrafficElement)o) == 0;
    }

    @Override
    public String toString() {
      return "[" + time + ", " + volume + ']';
    }
  }

  @EpiTest(testDataFile = "max_of_sliding_window.tsv")

  public static List<TrafficElement>
  computeTrafficVolumes(List<TrafficElement> A, int w) {
    Collections.sort(A, Comparator.comparingInt(te -> te.time));
    List<TrafficElement> q = new LinkedList<>();
    List<TrafficElement> res = new ArrayList<>();

    for (TrafficElement point : A) {
      if (q.isEmpty()) {
        q.add(point);
        res.add(point);
      } else {
        while (!q.isEmpty() && (point.time - w) > q.getFirst().time) {
          // The peek
          q.removeFirst();
        }

        while (!q.isEmpty() && q.getLast().volume <= point.volume) {
          q.removeLast();
        }

        q.add(point);
        TrafficElement te = new TrafficElement(point.time, q.getFirst().volume);
        res.add(te);
      }
    }

    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MaxOfSlidingWindow.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
