package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class KLargestValuesInBst {
  @EpiTest(testDataFile = "k_largest_values_in_bst.tsv")

  public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
    List<Integer> res = new ArrayList<>();
    inorder_rev(tree, k, res);
    return res;
  }
  public static void inorder_rev(BstNode<Integer> tree, int k, List<Integer> res) {
    if (tree == null) {
      return;
    }

    inorder_rev(tree.right, k, res);
    if (res.size() < k) {
      res.add(tree.getData());
    }

    if (res.size() == k) {
      return;
    }

    inorder_rev(tree.left, k, res);
  }

  @EpiTestComparator
  public static boolean comp(List<Integer> expected, List<Integer> result) {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KLargestValuesInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
