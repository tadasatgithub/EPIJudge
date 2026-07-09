package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class LeftRightJustifyText {
  @EpiTest(testDataFile = "left_right_justify_text.tsv")

  public static List<String> justifyText(List<String> words, int L) {
    List<String> lines = new ArrayList<>();
    int[] pos = new int[1];
    do {
      lines.add(buildLine(words, L, pos).toString());
    } while (pos[0] < words.size());

    return lines;
  }

  private static StringBuilder buildLine(List<String> words, int l, int[] pos) {
    int curPos = pos[0];
    StringBuilder sb = new StringBuilder();
    List<String> res = new ArrayList<>();
    int runLength=0;

    while (curPos < words.size()) {
      String curStr = words.get(curPos);

      if (curStr.length() + (runLength > 0 ? runLength + 1 : runLength) < l) {
        runLength += curStr.length() + 1;
        res.add(curStr);
        curPos++;
      } else {
        break;
      }
    }

    if (curPos == words.size()) {
      // last line
      for (String re : res) {
          sb.append(re);
          sb.append(" ");
      }
      int blankLen=l-sb.length();
      sb.append(" ".repeat(Math.max(0, blankLen)));
    } else {
      int[] spaceDistribution = new int[res.size() > 1 ? res.size()-1 : 1 ];
      int totalSpace = l - runLength - (res.size()-1);
      int len = spaceDistribution.length;

      while (totalSpace > 0) {
        int s = totalSpace/len;
        for (int p=0; p < len; p++) {
          spaceDistribution[p] += s;
        }
        totalSpace = totalSpace % len;
        len--;
      }

      for (int i=0; i < res.size(); i++) {
        sb.append(res.get(i));
        for (int j=0; i < spaceDistribution.length && j < spaceDistribution[i]; j++) {
          sb.append(" ");
        }
      }
    }

    pos[0] = curPos;
    return sb;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LeftRightJustifyText.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
