package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class NextPermutation {
  @EpiTest(testDataFile = "next_permutation.tsv")
  public static List<Integer> nextPermutation(List<Integer> perm) {
    int pos=perm.size()-1;
    while (pos > 0) {
      if (perm.get(pos-1) < perm.get(pos)) {
        break;
      }
      pos--;
    }

    List<Integer> res = new ArrayList<>();
    if (pos == 0) {
      return res;
    }




    return null;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NextPermutation.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
