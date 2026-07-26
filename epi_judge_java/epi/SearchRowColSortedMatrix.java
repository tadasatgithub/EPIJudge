package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class SearchRowColSortedMatrix {
  @EpiTest(testDataFile = "search_row_col_sorted_matrix.tsv")

  public static boolean matrixSearch(List<List<Integer>> A, int x) {
    int r=0, c=A.getFirst().size()-1;

    while (r < A.size() && c >= 0) {
      int v=A.get(r).get(c);

      if (v == x) {
        return true;
      } else if (v < x) {
        r++;
      } else {
        c--;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchRowColSortedMatrix.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
