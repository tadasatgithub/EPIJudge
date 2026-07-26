package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.List;
public class LargestRectangleUnderSkyline {
  @EpiTest(testDataFile = "largest_rectangle_under_skyline.tsv")

  public static int calculateLargestRectangle(List<Integer> heights) {
    int[] stack = new int[heights.size()+1];
    int top = -1;
    stack[++top] = -1;

    for (int i=0; i < heights.size(); i++) {
        if (top <= 0 || heights.get(stack[top]) < heights.get(i)) {
          stack[++top] = i;
        } else {

        }
    }
    return 0;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LargestRectangleUnderSkyline.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
