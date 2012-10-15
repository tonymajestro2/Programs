import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;


public class ShortestPath1 
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("in.txt"));
		
		while (in.hasNextInt()) {
			int numVertices = in.nextInt();
			int numEdges = in.nextInt();
			
			Graph g = new Graph();
			int[] dist = new int[numVertices];
			int[] pred = new int[numVertices];
			boolean[] visited = new boolean[numVertices];
			
			for (int i = 0; i < numVertices; i++) {
				dist[i] = Integer.MAX_VALUE;
				pred[i] = Integer.MAX_VALUE;
				g.adjacencyList.add(new ArrayList<Edge>());
			}
			
			for (int i = 0; i < numEdges; i++) {
				int v = in.nextInt();
				int w = in.nextInt();
				int weight = in.nextInt();
				g.addEdge(v, new Edge(v, w, weight));
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
			for (Edge e : g.adjacencyList.get(v)) {
				int newDist = d + e.weight;
				if (newDist < dist[e.w]) {
					dist[e.w] = newDist;
					pred[e.w] = v; 
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
		List<List<Edge>> adjacencyList;
		
		Graph() { 
			this.adjacencyList = new ArrayList<List<Edge>>(); 
		}
		
		void addEdge(int v, Edge e) {
			this.adjacencyList.get(v).add(e);
		}
	}
	
	static class Edge
	{
		int v;
		int w;
		int weight;
		
		Edge(int v, int w, int weight) {
			this.v = v; this.w = w; this.weight = weight;
		}
	}
}