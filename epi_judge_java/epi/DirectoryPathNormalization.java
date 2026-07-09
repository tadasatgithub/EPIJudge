package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class DirectoryPathNormalization {
  @EpiTest(testDataFile = "directory_path_normalization.tsv")

  public static String shortestEquivalentPath(String path) {
    String[] stack = new String[path.length()];
    int top=-1;

    StringBuilder sb = new StringBuilder();

    char[] patha = path.toCharArray();

    for (char cur : patha) {
        if (cur == '/') {
            if (!sb.isEmpty()) {
                String curStr = sb.toString();
                //System.out.println(curStr);
                sb = new StringBuilder();

                if (curStr.equals(".") || curStr.isEmpty()) {
                } else if (curStr.equals("..")) {
                    if (top >= 0 && !stack[top].equals("..")) {
                        --top;
                    } else {
                      stack[++top] = curStr;
                    }
                } else {
                    stack[++top] = curStr;
                }
            }
        } else {
          sb.append(cur);
        }
    }

    if (!sb.isEmpty()) {
      String curStr = sb.toString();
      if (curStr.equals(".") || curStr.isEmpty()) {
      } else if (curStr.equals("..")) {
        if (top >= 0 && !stack[top].equals("..")) {
          --top;
        } else {
          stack[++top] = curStr;
        }
      } else {
        stack[++top] = curStr;
      }
    }

    StringBuilder pathRes = new StringBuilder();

    if (path.charAt(0) == '/') {
      pathRes.append("/");
    }

    for (int pos=0; pos <= top; pos++) {
      pathRes.append(stack[pos]);
      if (pos < top) {
        pathRes.append("/");
      }
    }

    return pathRes.toString();
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DirectoryPathNormalization.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
