package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class TreeLevelOrder {
  @EpiTest(testDataFile = "tree_level_order.tsv")

  public static List<List<Integer>>
  binaryTreeDepthOrder(BinaryTreeNode<Integer> tree) {
    List<BinaryTreeNode> queue = new ArrayList<>();
    List<Integer> levels = new ArrayList<>();
    int level=0;
    if (tree != null) {
      queue.addLast(tree);
      levels.add(level);
    }

    List<List<Integer>> res = new ArrayList<>();

    while (!queue.isEmpty()) {
      BinaryTreeNode<Integer> cur = queue.removeFirst();
      int curLevel = levels.removeFirst();

      List<Integer> curLevelValues;
      if (res.isEmpty() || res.size() <= curLevel) {
        curLevelValues = new ArrayList<>();
        res.add(curLevel, curLevelValues);
      } else {
        curLevelValues = res.get(curLevel);
      }
      curLevelValues.add(cur.getData());

      if (cur.getLeft() != null) {
        queue.addLast(cur.getLeft());
        levels.addLast(curLevel + 1);
      }

      if (cur.getRight() != null) {
        queue.addLast(cur.getRight());
        levels.addLast(curLevel + 1);
      }
    }

    return res;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeLevelOrder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
