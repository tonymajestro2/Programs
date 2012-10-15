/**
 * Tony Majestro (tmajest)
 * Passes online judge
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MazeMovement 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		
		// Set up vertices, source, sink
		Graph g = new Graph(n);
		int source = -1, sink = -1;
		int sourceMin = Integer.MAX_VALUE, sinkMax = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			int v = in.nextInt();
			if (v < sourceMin) {
				sourceMin = v;
				source = i;
			}
			else if (v > sinkMax) {
				sinkMax = v;
				sink = i;
			}
			g.vertices[i] = v;
			g.edges.add(new HashMap<Integer, Edge>());
		}
		
		// Set up edges
		for (int u = 0; u < n - 1; u++) {
			int uRoom = g.vertices[u];
			for (int v = u + 1; v < n; v++) {
				int vRoom = g.vertices[v];
				int gcd = gcd(uRoom, vRoom);
				if (gcd == 1)
					continue;
				
				g.addEdge(new Edge(u, v, gcd));	// current flow is 0
				g.addEdge(new Edge(v, u, gcd));	// capacity is gcd(u, v)
			}
		}
		
		System.out.println(maxFlow(g, source, sink));
	}
	
	
	static int maxFlow(Graph g, int source, int sink)
	{
		int maxFlow = 0;
		int[] pred = new int[g.vertices.length];
		
		while (true) {
			int currFlow = bfs(g, pred, source, sink);
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
		
		return maxFlow;
	}
	
	
	static int bfs(Graph g, int[] pred, int source, int sink)
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
	
	static int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a % b);
	}
}
