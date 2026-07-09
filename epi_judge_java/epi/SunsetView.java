package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class SunsetView {
  public static List<Integer>
  examineBuildingsWithSunset(Iterator<Integer> sequence) {
    List<int[]> stack = new LinkedList<>();
    int pos=0;
    while (sequence.hasNext()) {
      if (stack.isEmpty()) {
        stack.addLast(new int[] {sequence.next(), pos});
      } else {
        int cur = sequence.next();
        if (stack.getLast()[0] > cur) {
          stack.addLast(new int[]{cur, pos});
        } else {
          while (!stack.isEmpty() && stack.getLast()[0] <= cur) {
            stack.removeLast();
          }
          stack.addLast(new int[]{cur, pos});
        }
      }
      pos++;
    }

    List<Integer> res = new ArrayList<>();
    for (int[] x : stack) {
      res.addFirst(x[1]);
    }
    return res;
  }
  @EpiTest(testDataFile = "sunset_view.tsv")
  public static List<Integer>
  examineBuildingsWithSunsetWrapper(List<Integer> sequence) {
    return examineBuildingsWithSunset(sequence.iterator());
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SunsetView.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
