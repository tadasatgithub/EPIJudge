package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IntSquareRoot {
  @EpiTest(testDataFile = "int_square_root.tsv")

  public static int squareRoot(int k) {
    long l=0, r = 1;
    int value = 1;

    while ((r * r) < k) {
      r = r << 1;
    }

    while (l <= r) {
      long mid = l + (r-l)/2;

      if (mid*mid <= k) {
        value = (int) mid;
        l = mid+1;
      } else {
        r = mid-1;
      }
    }

    return value;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntSquareRoot.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
