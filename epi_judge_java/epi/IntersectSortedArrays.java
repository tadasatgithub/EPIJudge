package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class IntersectSortedArrays {
  @EpiTest(testDataFile = "intersect_sorted_arrays.tsv")

  public static List<Integer> intersectTwoSortedArrays(List<Integer> A,
                                                       List<Integer> B) {
    // TODO - you fill in here.
    int alen = A.size();
    int blen = B.size();
    List<Integer> res = new ArrayList<>();

    int f=0, s=0;

    while (f < alen && s < blen) {

      while (f < alen && A.get(f) < B.get(s)) {
        f++;
      }

      while (s < blen && f < alen && B.get(s) < A.get(f)) {
        s++;
      }

      if (f < alen && s < blen && A.get(f).equals(B.get(s))) {
        if (res.isEmpty() || !res.getLast().equals(A.get(f))) {
          if (f < alen)
            res.add(A.get(f));
          else if (s < blen)
            res.add(B.get(s));
        }
        f++;
        s++;
      }
    }

    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntersectSortedArrays.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
