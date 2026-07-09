package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.List;
public class RotateArray {

  public static void rotateArray(int rotateAmount, List<Integer> A) {
    int len = A.size();
    int k=rotateAmount % len;
    int[] t = new int[k];

    for (int i=0; i < k; i++) {
     t[i] = A.get(len-k+i);
    }

    for (int i=len-k-1; i >= 0; i--) {
      A.set(i+k, A.get(i));
    }

    for (int i=0; i < k; i++) {
      A.set(i, t[i]);
    }

    return;
  }
  @EpiTest(testDataFile = "rotate_array.tsv")
  public static List<Integer>
  rotateArrayWrapper(TimedExecutor executor, List<Integer> A, int rotateAmount)
      throws Exception {
    List<Integer> aCopy = new ArrayList<>(A);

    executor.run(() -> rotateArray(rotateAmount, aCopy));
    return aCopy;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "RotateArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
