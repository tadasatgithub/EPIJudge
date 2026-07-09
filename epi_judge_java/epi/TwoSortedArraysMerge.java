package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class TwoSortedArraysMerge {

  public static void mergeTwoSortedArrays(List<Integer> A, int m,
                                          List<Integer> B, int n) {
    int pos=m+n-1;
    int i=m-1, j=n-1;
    while (i>=0 && j>=0) {
      if (A.get(i) > B.get(j)) {
        A.set(pos, A.get(i));
        i--;
        pos--;
      } else if (A.get(i) < B.get(j)) {
        A.set(pos, B.get(j));
        j--;
        pos--;
      } else {
        A.set(pos, A.get(i));
        i--;
        pos--;

        A.set(pos, B.get(j));
        j--;
        pos--;
      }
    }

    while (j >= 0) {
      A.set(pos, B.get(j));
      j--;
      pos--;
    }
    return;
  }
  @EpiTest(testDataFile = "two_sorted_arrays_merge.tsv")
  public static List<Integer>
  mergeTwoSortedArraysWrapper(List<Integer> A, int m, List<Integer> B, int n) {
    mergeTwoSortedArrays(A, m, B, n);
    return A;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TwoSortedArraysMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
