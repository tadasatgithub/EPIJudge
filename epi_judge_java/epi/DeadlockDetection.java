package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
public class DeadlockDetection {

  public static class GraphVertex {
    public List<GraphVertex> edges;

    public GraphVertex() { edges = new ArrayList<>(); }
  }
  
  // This is BFS
  // public static boolean isDeadlocked(List<GraphVertex> graph) {
  //   Queue<GraphVertex> queue = new LinkedList<>();
  //   Set<GraphVertex> visited = new HashSet<>();
  //   Set<GraphVertex> visiting;
    
  //   for (GraphVertex vertex : graph) {
  //     if (visited.contains(vertex)) {
  //       continue;
  //     }

  //     visiting = new HashSet<>();
  //     visiting.add(vertex);
  //     queue.offer(vertex);
    
  //     while (!queue.isEmpty()) {
  //         GraphVertex vert = queue.poll();

  //         for (GraphVertex conn : vert.edges) {
  //           if (visiting.contains(conn)) {
  //             return true;
  //           }
  //           queue.offer(conn);
  //           visiting.add(conn);
  //         }

  //         visited.add(vert);
  //     }
  //   }
  //   return false;
  // }

  // This is DFS. You comment this and the following method and uncomment the above method to do BFS.
  public static boolean isDeadlocked(List<GraphVertex> graph) {
    Set<GraphVertex> visited = new HashSet<>();
    Set<GraphVertex> visiting = new HashSet<>();
    
    for (GraphVertex vert : graph) {
      if (visited.contains(vert)) {
        continue;
      }
      visiting.add(vert);
      boolean res = isDeadlocked_helper(vert, visiting, visited);
      visited.add(vert);
      visiting.remove(vert);
      if (res) return res;
    }

    return false;

  }
  public static boolean isDeadlocked_helper(GraphVertex vert, Set<GraphVertex> visiting, Set<GraphVertex> visited) {
  
    for (GraphVertex conn : vert.edges) {
      if (visiting.contains(conn)) {
        return true;
      }
      
      visiting.add(conn);
      boolean res = isDeadlocked_helper(conn, visiting, visited);
      visiting.remove(conn);
      if (res) return res;
    }
      
    return false;
  }


  @EpiUserType(ctorParams = {int.class, int.class})
  public static class Edge {
    public int from;
    public int to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  @EpiTest(testDataFile = "deadlock_detection.tsv")
  public static boolean isDeadlockedWrapper(TimedExecutor executor,
                                            int numNodes, List<Edge> edges)
      throws Exception {
    if (numNodes <= 0) {
      throw new RuntimeException("Invalid numNodes value");
    }
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < numNodes; i++) {
      graph.add(new GraphVertex());
    }
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= numNodes || e.to < 0 || e.to >= numNodes) {
        throw new RuntimeException("Invalid vertex index");
      }
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    return executor.run(() -> isDeadlocked(graph));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DeadlockDetection.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
