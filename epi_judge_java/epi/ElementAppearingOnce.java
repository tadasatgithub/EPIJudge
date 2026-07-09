package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class ElementAppearingOnce {
  @EpiTest(testDataFile = "element_appearing_once.tsv")

  public static int findElementAppearsOnce(List<Integer> A) {
    int[] count=new int[32];

    for (Integer x : A) {
      for (int p=0; p < 32; p++) {
        if ((x & (1 << p)) == (1 << p)) {
          count[p]++;
        }
      }
    }

    int single=0;
    for (int p=0; p < 32; p++) {
      if (count[p] % 3 != 0) {
        single = single | (1 << p);
      }
    }
    return single;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ElementAppearingOnce.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
