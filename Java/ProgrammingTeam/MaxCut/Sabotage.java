import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Sabotage 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			int m = in.nextInt();
			if (n == 0 && m == 0)
				break;
			
			Graph g = new Graph(n);
			for (int i = 0; i < n; i++) {
				g.edges.add(new HashMap<Integer, Edge>());
			}
			
			for (int i = 0; i < m; i++) {
				int u = in.nextInt() - 1;
				int v = in.nextInt() - 1;
				int capacity = in.nextInt();
				g.addEdge(new Edge(u, v, capacity));
				g.addEdge(new Edge(v, u, capacity));
			}
			
			minCut(g, 0, 1);
		}
	}
	
	static void minCut(Graph g, int source, int sink)
	{
		int maxFlow = 0;
		int[] pred = new int[g.vertices.length];
		
		while (true) {
			int currFlow = bfs(g, pred, source, sink, false);
			if (currFlow == 0)
				break;
			
			maxFlow += currFlow;
			
			int v = sink;
			while (v != source) {
				int u = pred[v];
				// Update total flow and available capacity for edge
				Edge uv = g.getEdge(u, v);
				uv.flow += currFlow;
				uv.capacity -= currFlow;
				v = u;
			}
		}
		
		bfs(g, pred, source, sink, true);
	}
	
	
	static int bfs(Graph g, int[] pred, int source, int sink, boolean minCut)
	{
		// Initialize flow to each node from source
		int[] m = new int[g.vertices.length];
		m[source] = Integer.MAX_VALUE;
		
		// Initialize predecessor array
		for (int i = 0; i < pred.length; i++) {
			pred[i] = -1;
		}
		pred[source] = -2;
		
		Deque<Integer> Q = new ArrayDeque<Integer>();
		Q.add(source);
		
		while (!Q.isEmpty()) {
			int u = Q.poll();
			for (int v: g.edges.get(u).keySet()) {
				// If there is available capacity and v is unlabeled, push 
				// available flow through uv
				int capacity = g.getEdge(u, v).capacity;
				if (!minCut) {
					if (capacity > 0 && pred[v] == -1) {
						pred[v] = u;
						m[v] = Math.min(m[u], capacity);
						
						if (v == sink) {
							return m[sink];
						}
						else {
							Q.add(v);
						}
					}
				}
				else {
					if (capacity == 0 || pred[v] > 0) {
						System.out.printf("%d %d\n", u+1, v+1);
					}
					else {
						Q.add(v);
					}
				}
			}
		}
		
		return 0;
	}
	
	
	static class Graph
	{
		List<Map<Integer, Edge>> edges;
		int[] vertices;
		int size;
		
		Graph(int n) { 
			this.vertices = new int[n];
			this.edges = new ArrayList<Map<Integer, Edge>>();
			this.size = n;
		}
		
		Edge getEdge(int u, int v) {
			return edges.get(u).get(v);
		}
		
		void addEdge(Edge e) {
			this.edges.get(e.u).put(e.v, e);
		}
	}
	
	static class Edge
	{
		int u;
		int v;
		int flow;
		int capacity;
		
		Edge(int u, int v, int capacity) {
			this.u = u; this.v = v; this.flow = 0; this.capacity = capacity;
		}
	}
}
