package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SearchFirstGreaterValueInBst {

  public static BstNode<Integer> findFirstGreaterThanK(BstNode<Integer> tree,
                                                       Integer k) {
    BstNode<Integer>[] res;
    res = new BstNode[1];
    inorder(tree, k, res);
    return res[0];
  }

  private static void inorder(BstNode<Integer> tree, Integer k, BstNode<Integer>[] res) {
    if (tree == null) {
      return;
    }

    if (tree.getData() > k && (res[0] == null || tree.getData() < res[0].getData())) {
      res[0] = tree;
    }

    inorder(tree.left, k, res);
    inorder(tree.right, k, res);
  }

  @EpiTest(testDataFile = "search_first_greater_value_in_bst.tsv")
  public static int findFirstGreaterThanKWrapper(BstNode<Integer> tree,
                                                 Integer k) {
    BstNode<Integer> result = findFirstGreaterThanK(tree, k);
    return result != null ? result.data : -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFirstGreaterValueInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
