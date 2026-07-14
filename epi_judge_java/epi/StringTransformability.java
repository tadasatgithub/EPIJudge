package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class StringTransformability {
  @EpiTest(testDataFile = "string_transformability.tsv")
  public static int transformString(Set<String> D, String s, String t) {
    if (D.isEmpty() || s.isEmpty() || t.isEmpty() || !D.contains(s) || !D.contains(t)) {
      return -1;
    }

    if (s.equals(t)) {
      return 0;
    }

    //Map<String, List<String>> adjList = buildAdjacencyList(D);
    return findMinDistance(D, s, t);
  }

  private static int findMinDistance(Set<String> d, String s, String t) {
    Queue<Object[]> queue = new LinkedList<>();

    queue.offer(new Object[]{s, 0});
    d.remove(s);

    while (!queue.isEmpty()) {
      Object[] cur = queue.poll();

      String cs = (String) cur[0];
      Integer hop = (Integer) cur[1];

      if (cs.equals(t)) {
        return hop;
      }

      //char[] csa = cs.toCharArray();

      for (int i=0; i < cs.length(); i++) {
        char[] csa = cs.toCharArray();

        for (int c=0; c < 26; c++) {
          csa[i] = (char) (c+'a');
          String csStr = new String(csa);
          //System.out.println("csStr : " + csStr);
          if (d.remove(csStr)) {
            queue.offer(new Object[] {csStr, hop+1});
          }
        }
      }
    }

    return -1;
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringTransformability.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
