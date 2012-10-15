/**
 * Tony Majestro (tmajest)
 * Passes ICPC online judge
 */

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class SignalStrength 
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(System.in);
		int count = 1;
		while (in.hasNext()) {
			int n = in.nextInt();
			if (n == 0)
				break;
			
			Graph g = new Graph(n);
			for (int i = 0; i < n; i++) {
				g.vertices[i] = in.nextDouble();
				g.edges.add(new HashMap<Integer, Edge>());
				
				int k = in.nextInt();
				for (int j = 0; j < k; j++) {
					g.addEdge(new Edge(i, in.nextInt(), in.nextDouble()));	
				}
			}
			
			double maxSignal = solve(g, 0, g.size - 1);
			System.out.printf("Network %d: %.2f\n", count++, Math.abs(maxSignal));
		}
		
		in.close();
	}
	
	/** Output the length of the maximum path through the graph */
	static double solve(Graph g, int start, int goal) 
	{
		boolean visited[] = new boolean[g.size];
		double maxSignal[] = new double[g.size];
		maxSignal[start] = g.vertices[start];
		Deque<Integer> bfs = new ArrayDeque<Integer>(); 
		
		bfs.offer(start);
		visited[start] = true;
		
		while (bfs.size() > 0) {
			int u = bfs.poll();
            for (int v : g.edges.get(u).keySet()) {
            	
            	// Check if new output signal is greater than the current output signal.
            	// If so, update the max output signal.  Otherwise, we've encountered
            	// a cycle, so don't update the queue
            	double outputSignal = maxSignal[u] * g.getEdge(u, v).weight * g.vertices[v];
            	if (maxSignal[v] >= outputSignal) {
					continue;
				}
			
				maxSignal[v] = outputSignal;
				bfs.offer(v);
			}
		}
		
		return maxSignal[goal];
	}
	
	
	static class Graph
	{
		int size;
		double[] vertices;	// amplification for each vertex
		List<Map<Integer, Edge>> edges;
		
		Graph(int n) { 
			this.vertices = new double[n];
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
		int u; int v;
		double weight;
		
		Edge(int u, int v, double weight) {
			this.u = u; this.v = v; this.weight = weight;
		}
	}
}
