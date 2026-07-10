package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class OnlineMedian {
  public static List<Double> onlineMedian(Iterator<Integer> sequence) {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    List<Double> res = new ArrayList<>();
    int count=0;

    while (sequence.hasNext()) {
      int val = sequence.next();
      count++;
      if (maxHeap.isEmpty()) {
        maxHeap.offer(val);
      } else {
        if (val > maxHeap.peek()) {
          minHeap.offer(val);
          if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
          }
        } else {
          maxHeap.offer(val);

          if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
          }
        }
      }

      if (maxHeap.size() == minHeap.size() && !maxHeap.isEmpty() && !minHeap.isEmpty()) {
        res.add((maxHeap.peek() + minHeap.peek()) / 2.0);
      } else {
        res.add(Double.valueOf(maxHeap.peek()));
      }
    }

    return res;
  }
  @EpiTest(testDataFile = "online_median.tsv")
  public static List<Double> onlineMedianWrapper(List<Integer> sequence) {
    return onlineMedian(sequence.iterator());
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "OnlineMedian.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
