package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class ThreeSum {
  @EpiTest(testDataFile = "three_sum.tsv")

  public static boolean hasThreeSum(List<Integer> a, int t) {
    Collections.sort(a);
    for (int i=0; i < a.size(); i++) {
      boolean res = findNumber(a, t-a.get(i));
      if (res) {
        return true;
      }
    }
    return false;
  }

  private static boolean findNumber(List<Integer> a, int target) {
    int l=0, r=a.size()-1;
    int s;

    while (l <= r) {
      s=a.get(l) + a.get(r);

      if (s == target) {
        return true;
      } else if (s < target) {
        l++;
      } else {
        r--;
      }
    }

    return false;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ThreeSum.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
