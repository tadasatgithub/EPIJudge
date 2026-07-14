package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatrixEnclosedRegions {

  public static void fillSurroundedRegions(List<List<Character>> board) {
    for (int r=1; r < board.size()-1; r++) {
      for (int c=1; c < board.getFirst().size()-1; c++) {
        if (board.get(r).get(c).equals('W')) {
            if (bfs(board, r, c)) { // True means bounded by Black
              board.get(r).set(c, 'B');
            }
        }
      }
    }
  }

  private static boolean bfs(List<List<Character>> board, int r, int c) {
    boolean[][] visited = new boolean[board.size()][board.getFirst().size()];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{r, c});
    int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0 ,1}};

    while (!queue.isEmpty()) {
      int[] cur = queue.poll();
      for (int[] d : dirs) {
        int nx = cur[0] + d[0];
        int ny = cur[1] + d[1];

        if (nx < 0 || nx >= board.size() || ny < 0 || ny >= board.getFirst().size()) {
          return false; // not bounded by black
        }

        if (visited[nx][ny] || board.get(nx).get(ny).equals('B')) {
          continue;
        }
        visited[nx][ny] = true;
        queue.offer(new int[]{nx, ny});
      }
    }

    return true; // bounded by black
  }

  /**
   * [
   * ["W", "W", "W", "W", "B", "W", "W", "B", "B", "W"],
   * ["B", "W", "W", "B", "W", "B", "W", "B", "B", "W"],
   * ["B", "W", "W", "W", "B", "B", "B", "B", "B", "W"],
   * ["B", "B", "B", "B", "W", "B", "W", "B", "W", "B"],
   * ["W", "W", "B", "B", "W", "B", "B", "W", "B", "B"],
   * ["W", "W", "W", "B", "B", "B", "B", "W", "W", "W"],
   * ["B", "B", "W", "B", "W", "B", "B", "W", "W", "B"],
   * ["B", "W", "W", "W", "B", "W", "B", "W", "B", "W"],
   * ["W", "W", "W", "W", "B", "W", "W", "W", "B", "B"],
   * ["W", "W", "W", "W", "B", "B", "W", "B", "B", "B"],
   * ["B", "B", "W", "B", "B", "B", "W", "B", "W", "B"],
   * ["B", "W", "W", "B", "B", "B", "W", "W", "B", "B"],
   * ["B", "B", "W", "B", "W", "W", "B", "W", "W", "B"],
   * ["B", "B", "B", "W", "B", "B", "B", "B", "W", "W"],
   * ["W", "B", "W", "B", "B", "B", "W", "B", "B", "B"]]
   * @param r
   * @param c
   * @param rinc
   * @param cinc
   * @param board
   * @return
   */

  private static boolean fillSurroundedRegions_helper(int r, int c, int rinc, int cinc, List<List<Character>> board) {
    if (r < 0 ||  r >= board.size() || c < 0 || c >= board.getFirst().size()) {
      return false;
    }

    if (board.get(r).get(c).equals('B')) {
      return true;
    }

    return fillSurroundedRegions_helper(r + rinc,  c + cinc, rinc, cinc, board);
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
