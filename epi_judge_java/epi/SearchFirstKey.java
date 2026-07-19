package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class SearchFirstKey {
  @EpiTest(testDataFile = "search_first_key.tsv")

  public static int searchFirstOfK(List<Integer> A, int k) {
    int l=0, r=A.size()-1;
    int index=-1;
    while (l <= r) {
      int mid = l + (r-l)/2;

      if (A.get(mid) == k) {
        index=mid;
        r = mid-1;
      } else if (A.get(mid) > k) {
        r = mid-1;
      } else {
        l = mid+1;
      }
    }
    return index;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFirstKey.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
