package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FirstMissingPositiveEntry {
  @EpiTest(testDataFile = "first_missing_positive_entry.tsv")

  public static int findFirstMissingPositive(List<Integer> A) {
    int n=A.size();

    for (int i=0; i < n; ) {
      if (A.get(i) > 0 && A.get(i) <= n && !Objects.equals(A.get(A.get(i) - 1), A.get(i))) {
        Collections.swap(A, i, A.get(i)-1);
      } else {
        i++;
      }
    }

    for (int i=0; i < n; i++) {
      if (A.get(i) != i+1) {
        return i+1;
      }
    }
    return n+1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "FirstMissingPositiveEntry.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
