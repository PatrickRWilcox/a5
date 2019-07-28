package a5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import a4.Heap;
import a4.NotImplementedException;
import common.NotImplementedError;
import graph.Edge;
import graph.Node;
import graph.LabeledEdge;

/** We've provided depth-first search as an example; you need to implement Dijkstra's algorithm.
 */
public class GraphAlgorithms  {
	/** Return the Nodes reachable from start in depth-first-search order */
	public static <N extends Node<N,E>, E extends Edge<N,E>>
	List<N> dfs(N start) {
		
		Stack<N> worklist = new Stack<N>();
		worklist.add(start);
		
		Set<N>   visited  = new HashSet<N>();
		List<N>  result   = new ArrayList<N>();
		while (!worklist.isEmpty()) {
			// invariants:
			//    - everything in visited has a path from start to it
			//    - everything in worklist has a path from start to it
			//      that only traverses visited nodes
			//    - nothing in the worklist is visited
			N next = worklist.pop();
			visited.add(next);
			result.add(next);
			for (N neighbor : next.outgoing().keySet())
				if (!visited.contains(neighbor))
					worklist.add(neighbor);
		}
		return result;
	}
	
	/**
	 * Return a minimal path from start to end.  This method should return as
	 * soon as the shortest path to end is known; it should not continue to search
	 * the graph after that. 
	 * 
	 * @param <N> The type of nodes in the graph
	 * @param <E> The type of edges in the graph; the weights are given by e.label()
	 * @param start The node to search from
	 * @param end   The node to find
	 */
	public static <N extends Node<N,E>, E extends LabeledEdge<N,E,Integer>>
	List<N> shortestPath(N start, N end) {
		/*Heap<N,Integer> heap = new Heap(Comparator.reverseOrder());
		heap.add(start, 0);
		for(N n: start.outgoing().keySet()) {
			heap.add(n, start.outgoing().get(n).label());
		}
		
		List<N> worklist = new ArrayList<N>();
		
		List<N> result = new ArrayList<N>();
		
		while(worklist.size() > 0) {
			for(N n: start.outgoing().keySet()) {
				heap.add(n, start.outgoing().get(n).label());
			}*/
		
		
   
		
		
		
		throw new NotImplementedException();
	}
	
	public static <N extends Node<N,E>, E extends LabeledEdge<N,E,Integer>> 
	List<N> generateGraph(N start, N end){
		N node = start;
		List<N> worklist = new ArrayList<N>();
		List<N> visited = new ArrayList<N>();
		worklist.add(node);
		
		while(worklist.size() > 0) {
			visited.add(node);
			node = worklist.remove(0);
			if(node.equals(end)) {
				visited.add(node);
				return visited;
			}
			for(N n: node.outgoing().keySet()) {
				if(!visited.contains(n))
					worklist.add(n);
			}
		}
		
		return null;
	}
	
}
