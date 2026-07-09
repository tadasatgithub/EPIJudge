package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.List;
public class CalendarRendering {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Event {
    public int start, finish;

    public Event(int start, int finish) {
      this.start = start;
      this.finish = finish;
    }
  }

  private static class Endpoint {
    public int time;
    public boolean isStart;

    Endpoint(int time, boolean isStart) {
      this.time = time;
      this.isStart = isStart;
    }
  }

  @EpiTest(testDataFile = "calendar_rendering.tsv")

  public static int findMaxSimultaneousEvents(List<Event> A) {
    // TODO - you fill in here.
    Endpoint[] endPoints = new Endpoint[A.size()*2];
    int pos=-1;
    for (Event e : A) {
      endPoints[++pos] = new Endpoint(e.start, true);
      endPoints[++pos] = new Endpoint(e.finish, false);
    }

    Arrays.sort(endPoints, (ep1, ep2) -> {
      if (ep1.time == ep2.time) {
        return !ep1.isStart ? 1 : !ep2.isStart ? -1 : 0;
      }

      return ep1.time - ep2.time;
    });

    int count=0;
    int max=Integer.MIN_VALUE;

    for (Endpoint e : endPoints) {
      if (e.isStart) {
        count++;
        max = Math.max(count, max);
      } else {
        count--;
      }
    }
    return max;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CalendarRendering.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
