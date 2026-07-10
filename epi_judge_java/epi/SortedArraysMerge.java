package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SortedArraysMerge {
  @EpiTest(testDataFile = "sorted_arrays_merge.tsv")

  public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {
    PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));

    int pos=0;
    for (List<Integer> a : sortedArrays) {
      if (!a.isEmpty())
        minHeap.offer(new int[]{a.getFirst(), 0, pos});
      ++pos;
    }

    List<Integer> result = new ArrayList<>();

    while (!minHeap.isEmpty()) {
      int[] cur = minHeap.poll();
      int value = cur[0];
      int epos = cur[1];
      int apos = cur[2];

      result.add(value);
      if (epos+1 < sortedArrays.get(apos).size()) {
        minHeap.add(new int[]{sortedArrays.get(apos).get(epos+1), epos+1, apos});
      }
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedArraysMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
