package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class StackWithMax {

  public static class Stack {
    private java.util.Stack<int[]> stack;

    Stack() {
      stack = new java.util.Stack<>();
    }

    public boolean empty() {
      return stack.isEmpty();
    }

    public Integer max() {
      if (stack.empty()) {
        return 0;
      }

      return stack.peek()[1];
    }

    public Integer pop() {
      if (stack.empty()) {
        return 0;
      }
      int[] e = stack.pop();
      return e[0];
    }

    public void push(Integer x) {
      int[] e = new int[2];
      e[0] = x;
      e[1] = Math.max(x, stack.empty() ? Integer.MIN_VALUE : stack.peek()[1]);
      stack.push(e);
      return;
    }
  }


  @EpiUserType(ctorParams = {String.class, int.class})
  public static class StackOp {
    public String op;
    public int arg;

    public StackOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }
  }

  @EpiTest(testDataFile = "stack_with_max.tsv")
  public static void stackTester(List<StackOp> ops) throws TestFailure {
    try {
      Stack s = new Stack();
      int result;
      for (StackOp op : ops) {
        switch (op.op) {
        case "Stack":
          s = new Stack();
          break;
        case "push":
          s.push(op.arg);
          break;
        case "pop":
          result = s.pop();
          if (result != op.arg) {
            throw new TestFailure("Pop: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(result));
          }
          break;
        case "max":
          result = s.max();
          if (result != op.arg) {
            throw new TestFailure("Max: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(result));
          }
          break;
        case "empty":
          result = s.empty() ? 1 : 0;
          if (result != op.arg) {
            throw new TestFailure("Empty: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(s));
          }
          break;
        default:
          throw new RuntimeException("Unsupported stack operation: " + op.op);
        }
      }
    } catch (NoSuchElementException e) {
      throw new TestFailure("Unexpected NoSuchElement exception");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StackWithMax.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
