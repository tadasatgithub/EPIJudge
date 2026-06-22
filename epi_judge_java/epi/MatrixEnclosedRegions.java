package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
public class MatrixEnclosedRegions {

  public static void fillSurroundedRegions(List<List<Character>> board) {
    int maxr=board.size();
    int maxc=board.get(0).size();
    int[][] track = new int[maxr][maxc];

    for (int r=1; r < maxr-1; r++) {
      for (int c=1; c < maxc-1; c++) {
        if (board.get(r).get(c).equals('W') && track[r][c] == 0) {
          fillRegion(board, r, c, track);
        }
      }
    }
    return;
  }

  private static void fillRegion(List<List<Character>> board, int r, int c, int[][] track) {
      int maxr=board.size();
      int maxc=board.get(0).size();

      Queue<int[]> queue = new LinkedList<>();
      Queue<int[]> backup = new LinkedList<>();
      int[][] directions = new int[][] {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

      queue.offer(new int[] {r, c});
      boolean borderedByBlack = true;
      track[r][c] = 1;

      while (!queue.isEmpty()) {
        int[] cur = queue.poll();
        
        for (int[] dir : directions) {
          int nr = cur[0] + dir[0]; 
          int nc = cur[1] + dir[1];

          if (nr < 0 || nr >= maxr || nc < 0 || nc >= maxc) {
            System.out.println("cur[0] " + cur[0] + " cur[1] " + cur[1] + " nr : " + nr + " nc : " + nc);
            borderedByBlack = false;
            break;
          }

          if (track[nr][nc] != 0 || board.get(nr).get(nc).equals('B')) {
            continue;
          }

          track[nr][nc] = 1;
          queue.offer(new int[] {nr, nc});
        }

        if (!borderedByBlack) {
          break;
        }

        track[cur[0]][cur[1]] = 2;
        backup.offer(cur);
      }

       System.out.println("borderedByBlack : " + borderedByBlack);

      if (borderedByBlack) {
         while (!backup.isEmpty()) {
          int[] cur = backup.poll();
          board.get(cur[0]).set(cur[1], 'B');
         }
      }
  }

  @EpiTest(testDataFile = "matrix_enclosed_regions.tsv")
  public static List<List<Character>>
  fillSurroundedRegionsWrapper(List<List<Character>> board) {
    fillSurroundedRegions(board);
    return board;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixEnclosedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
