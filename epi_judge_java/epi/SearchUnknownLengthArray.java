package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class SearchUnknownLengthArray {
  @EpiTest(testDataFile = "search_unknown_length_array.tsv")

  public static int binarySearchUnknownLength(List<Integer> A, int k) {
    int offset=0;

    while (true) {
      try {
        int idx=(1 << offset)-1;
        int val = A.get(idx);
        if (val == k) {
          return idx;
        } else if (val > k) {
          break;
        }
      } catch (IndexOutOfBoundsException e) {
       break;
      }
      offset++;
    }

    return findElement(A, Math.max(0, 1 << (offset-1)), (1 << offset)-2, k);
  }

  private static int findElement(List<Integer> a, int beg, int e, int k) {
    int m = beg + (e-beg)/2;

    while (beg <= e) {
      try {
        m = beg + (e-beg)/2;
        int item = a.get(m);

        if (item == k) {
          return m;
        } if (item > k) {
          e = m-1;
        } else {
          beg = m+1;
        }
      } catch (IndexOutOfBoundsException ex) {
        e = m-1;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchUnknownLengthArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
