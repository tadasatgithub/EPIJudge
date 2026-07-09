package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class FindSalaryThreshold {
  @EpiTest(testDataFile = "find_salary_threshold.tsv")

  public static double findSalaryCap(int targetPayroll,
                                     List<Integer> currentSalaries) {
    Collections.sort(currentSalaries);
    int[] range = new int[currentSalaries.size()];
    int total=0;
    int pos=0;

    while (pos< range.length) {
      range[pos] = currentSalaries.get(pos) * (currentSalaries.size()-pos) + total;
      if (range[pos] > targetPayroll) {
        break;
      }
      total += currentSalaries.get(pos);
      pos++;
    }

    if (pos == range.length && total < targetPayroll) {
      return -1.0;
    }

    if (pos == range.length) {
      return currentSalaries.getLast();
    }

    return 1.0 * (targetPayroll - total) / (currentSalaries.size() - pos);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "FindSalaryThreshold.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
