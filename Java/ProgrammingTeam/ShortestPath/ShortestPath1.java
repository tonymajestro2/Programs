import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class ShortestPath1 
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("in.txt"));
		
		while (in.hasNextInt()) {
			int numVertices = in.nextInt();
			int numEdges = in.nextInt();
			
			Graph g = new Graph(numVertices);
			int[] dist = new int[numVertices];
			int[] pred = new int[numVertices];
			boolean[] visited = new boolean[numVertices];
			
			for (int i = 0; i < numVertices; i++) {
				dist[i] = Integer.MAX_VALUE;
				pred[i] = Integer.MAX_VALUE;
				g.edges.add(new HashMap<Integer, Edge>());
			}
			
			for (int i = 0; i < numEdges; i++) {
				int v = in.nextInt();
				int w = in.nextInt();
				int weight = in.nextInt();
				g.addEdge(new Edge(v, w, weight));
				g.addEdge(new Edge(w, v, weight));
			}
			
			dist[0] = 0;
			shortestPath(g, numVertices, dist, pred, visited);
			printPath(pred, dist[numVertices-1]);
		}
		
		in.close();
	}
	
	static void shortestPath(Graph g, int goal, int[] dist, 
			int[] pred, boolean[] visited)
	{
		for (int i = 0; i < visited.length; i++) {
			// Find unused vertex with shortest distance from start
			int v = findMinVertex(dist, visited);
			if (v == goal) {
				return;
			}
			
			visited[v] = true;
			
			int d = dist[v];
			for (Edge e : g.edges.get(v).values()) {
				int newDist = d + e.weight;
				if (newDist < dist[e.v]) {
					dist[e.v] = newDist;
					pred[e.v] = v; 
				}
			}
		}
	}
	
	static int findMinVertex(int[] dist, boolean[] visited)
	{
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < dist.length; i++) {
			int d = dist[i];
			if (d < min && !visited[i]) {
				min = d;
				index = i;
			}
		}
		return index;
	}
	
	static void printPath(int[] pred, int dist) {
		int i = pred.length-1;
		Deque<Integer> reversed = new ArrayDeque<Integer>();
		reversed.add(pred.length-1);
		while (pred[i] != Integer.MAX_VALUE) {
			reversed.add(pred[i]);
			i = pred[i];
		}
		
		System.out.print("Path: ");
		while (!reversed.isEmpty()) {
			System.out.print(reversed.removeLast() + " ");
		}
		System.out.println("\nDistance: " + dist);
	}

	static class Graph
	{
		int size;
		int[] vertices;	
		List<Map<Integer, Edge>> edges;
		
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
		int u; int v;
		int weight;
		
		Edge(int u, int v, int weight) {
			this.u = u; this.v = v; this.weight = weight;
		}
	}
}
