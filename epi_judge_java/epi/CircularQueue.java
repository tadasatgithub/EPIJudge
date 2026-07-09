package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.Arrays;
import java.util.List;
public class CircularQueue {

  public static class Queue {
    private int[] queue;
    private int max;
    private int front, rear;

    public Queue(int capacity) {
      //System.out.println("capacity : " + capacity);
      max = capacity;
      queue = new int[max];
      front = rear = -1;
    }

    private boolean isFull() {
      if (front == 0 && rear == max-1) {
        return true;
      }

      return rear+1 == front;
    }

    private boolean isEmpty() {
      return front == -1;
    }

    public void enqueue(Integer x) {
      //System.out.println(" x : " + x + " max : " + max);
      if (isFull()) {
        queue = Arrays.copyOf(queue, 2*max);
        //System.out.println("queue : " + queue.length);
        int oldMax = max;
        max = 2*max;

        if (!(front==0 && rear == oldMax-1)) {
          // copy over
          int d=oldMax;
          for (; d <= oldMax + (rear); d++) {
            queue[d] = queue[d-oldMax];
          }
          rear = d-1;
        }
      }

      if (isEmpty()) {
        front = rear = 0;
        queue[rear] = x;
      } else {
        rear = rear+1;
        if (rear == max) {
          rear = 0;
        }
        queue[rear] = x;
      }
    }
    public Integer dequeue() {
      if (isEmpty()) {
        return 0;
      }
      int value = queue[front];
      if (front == rear) {
        front = -1;
        rear = -1;
      } else {
        front = front+1;
        if (front == max) {
          front = 0;
        }
      }
      return value;
    }
    public int size() {
      if (isEmpty()) return 0;
      if (isFull()) return max;
      // System.out.println("front : " + front + " rear : " + rear + " max : " + max);
      if (rear >= front) {
        return rear-front+1;
      } else {
        return (max-front+rear+1);
      }

    }
    @Override
    public String toString() {
      return super.toString();
    }
  }
  @EpiUserType(ctorParams = {String.class, int.class})
  public static class QueueOp {
    public String op;
    public int arg;

    public QueueOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }

    @Override
    public String toString() {
      return op;
    }
  }

  @EpiTest(testDataFile = "circular_queue.tsv")
  public static void queueTester(List<QueueOp> ops) throws TestFailure {
    Queue q = new Queue(1);
    int opIdx = 0;
    for (QueueOp op : ops) {
      switch (op.op) {
      case "Queue":
        q = new Queue(op.arg);
        break;
      case "enqueue":
        q.enqueue(op.arg);
        break;
      case "dequeue":
        int result = q.dequeue();
        if (result != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, result);
        }
        break;
      case "size":
        int s = q.size();
        if (s != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, s);
        }
        break;
      }
      opIdx++;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CircularQueue.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
