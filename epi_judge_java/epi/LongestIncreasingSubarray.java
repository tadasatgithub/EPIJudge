package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class LongestIncreasingSubarray {

  // Represent subarray by starting and ending indices, inclusive.
  private static class Subarray {
    public Integer start;
    public Integer end;

    public Subarray(Integer start, Integer end) {
      this.start = start;
      this.end = end;
    }
  }

  public static Subarray findLongestIncreasingSubarray(List<Integer> A) {
    int start=0;
    int maxLen = Integer.MIN_VALUE;
    Subarray res = null;
    for (int pos=1; pos < A.size(); pos++) {
      if (A.get(pos) <= A.get(pos-1)) {
        if (pos-start > maxLen) {
          maxLen = pos-start;
          res = new Subarray(start, pos-1);
        }
        start = pos;
      }
    }

    if (A.size() - start > maxLen) {
      res = new Subarray(start, A.size()-1);
    }
    return res;
  }
  @EpiTest(testDataFile = "longest_increasing_subarray.tsv")
  public static int findLongestIncreasingSubarrayWrapper(List<Integer> A) {
    Subarray result = findLongestIncreasingSubarray(A);
    return result.end - result.start + 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LongestIncreasingSubarray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
