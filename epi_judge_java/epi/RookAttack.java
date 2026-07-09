package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class RookAttack {

  public static void rookAttack(List<List<Integer>> A) {
    // TODO - you fill in here.
    List<int[]> rookPositions = new ArrayList<>();

    int rc=A.size();
    int cc=A.getFirst().size();

    for (int r=0; r < rc; r++) {
      for (int c=0; c < cc; c++) {
        if (A.get(r).get(c) == 0) {
          rookPositions.add(new int[] {r,c});
        }
      }
    }

    for (int[] rook : rookPositions) {
      int rookRow = rook[0];
      int rookCol = rook[1];

      A.get(rookRow).replaceAll(ignored -> 0);

      for (List<Integer> integers : A) {
        integers.set(rookCol, 0);
      }
    }

    return;
  }
  @EpiTest(testDataFile = "rook_attack.tsv")
  public static List<List<Integer>> rookAttackWrapper(List<List<Integer>> A) {
    List<List<Integer>> aCopy = new ArrayList<>(A);
    rookAttack(aCopy);
    return aCopy;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "RookAttack.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
