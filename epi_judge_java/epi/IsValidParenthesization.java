package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsValidParenthesization {
  @EpiTest(testDataFile = "is_valid_parenthesization.tsv")

  public static boolean isWellFormed(String s) {
    char[] stack = new char[s.length()];
    int top=-1;

    char[] sa = s.toCharArray();

    for (int pos=0; pos < sa.length; pos++) {
      char cur = sa[pos];

      if (cur == ')' || cur == '}' || cur == ']') {
        if (top < 0) {
          return false;
        }
        char tope = stack[top];
        if ((cur == ')' && tope != '(') || (cur == '}' && tope != '{') || (cur == ']' && tope != '[')) {
          return false;
        }
        --top;
      } else {
        stack[++top] = cur;
      }
    }

    return top < 0;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsValidParenthesization.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
