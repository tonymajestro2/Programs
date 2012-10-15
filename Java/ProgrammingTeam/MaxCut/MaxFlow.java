import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MaxFlow 
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("in.txt"));
		while (in.hasNextInt()) {
			int n = in.nextInt();
			Graph g = new Graph(n);
			
			// Init list of edges for each vertex
			for (int i = 0; i < n; i++) {
				g.edges.add(new HashMap<Integer, Edge>());
			}
			
			for (int u = 0; u < n; u++) {
				int numEdges = in.nextInt();
				for (int j = 0; j < numEdges; j++) {
					int v = in.nextInt();
					int capacity = in.nextInt();
					g.addEdge(new Edge(u, v, 0));
					g.addEdge(new Edge(v, u, capacity));
				}
			}
			
			System.out.println(maxFlow(g, 0, n - 1));
		}
	}
	
	
	static int maxFlow(Graph g, int source, int sink)
	{
		int maxFlow = 0;
		int[] pred = new int[g.numVertices];
		
		while (true) {
			int currFlow = bfs(g, pred, source, sink);
			if (currFlow == 0)
				break;
			
			maxFlow += currFlow;
			
			int v = sink;
			while (v != source) {
				int u = pred[v];
				// Update total flow and available capacity for edge
				Edge uv = g.edges.get(u).get(v);
				Edge vu = g.edges.get(v).get(u);
				uv.weight += currFlow;
				vu.weight -= currFlow;
				v = u;
			}
		}
		
		return maxFlow;
	}
	
	
	static int bfs(Graph g, int[] pred, int source, int sink)
	{
		// Initialize flow to each node from source
		int[] m = new int[g.numVertices];
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
				int capacity = g.edges.get(v).get(u).weight;
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
		}
		
		return 0;
	}
	
	
	static class Graph
	{
		List<Map<Integer, Edge>> edges;
		int numVertices;
		
		Graph(int n) { 
			this.numVertices = n;
			this.edges = new ArrayList<Map<Integer, Edge>>();
		}
		
		void addEdge(Edge e) {
			this.edges.get(e.u).put(e.v, e);
		}
	}
	
	static class Edge
	{
		int u;
		int v;
		int weight;
		
		Edge(int u, int v, int weight) {
			this.u = u; this.v = v; this.weight = weight;
		}
	}
}
