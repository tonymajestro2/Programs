import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;


public class ShortestPathTotalDistance
{
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("in.txt"));
		
		while (in.hasNextInt()) {
			int numVertices = in.nextInt();
			int numEdges = in.nextInt();
			
			Graph g = new Graph();
			int[] dist = new int[numVertices];
			MinNode[] minSpanTree = new MinNode[numVertices];
			
			for (int i = 0; i < numVertices; i++) {
				dist[i] = Integer.MAX_VALUE;
				g.adjacencyList.add(new HashMap<Integer, Edge>());
				minSpanTree[i] = new MinNode(i);
			}
			
			for (int i = 0; i < numEdges; i++) {
				int v = in.nextInt();
				int w = in.nextInt();
				int weight = in.nextInt();
				g.addEdge(v, w, new Edge(v, w, weight));
			}
			
			int start = 0; 
			shortestPath(g, start, -1, dist, minSpanTree);
			System.out.println(minSpanTreeDistance(g, minSpanTree, start));
		}
		
		in.close();
	}
	
	static void shortestPath(Graph g, int start, int goal, int[] dist,
			MinNode[] minSpanTree)
	{
		dist[start] = 0;
		int[] pred = new int[dist.length];
		boolean[] visited = new boolean[dist.length];
		
		for (int i = 0; i < visited.length; i++) {
			// Find unused vertex with shortest distance from start
			int v = findMinVertex(dist, visited);
			if (v == goal) {
				return;
			}
			
			visited[v] = true;
			
			// Add to min span tree graph
			if (v != start)
				minSpanTree[pred[v]].children.add(v);
			
			
			int d = dist[v];
			for (int w : g.adjacencyList.get(v).keySet()) {
				Edge e = g.adjacencyList.get(v).get(w);
				int newDist = d + e.weight;
				if (newDist < dist[e.w]) {
					dist[e.w] = newDist;
					pred[e.w] = v;  
				}
			}
		}
		System.out.println("Done");
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
	
	static int minSpanTreeDistance(Graph g, MinNode[] minSpanTree, int start) 
	{
		Stack<MinNode> nodes = new Stack<MinNode>();
		nodes.add(minSpanTree[start]);
		int distance = 0;	
		
		while (!nodes.isEmpty()) {
			MinNode curr = nodes.pop();
			for (int w : curr.children) {
			
				int temp = g.adjacencyList.get(curr.num).get(w).weight;
				System.out.println(curr.num + " " + w + ": " + temp); 
				distance += temp;
				
				nodes.push(minSpanTree[w]);
			}
		}
		
		return distance;
	}



	static class Graph
	{
		List<Map<Integer, Edge>> adjacencyList;
		
		Graph() { 
			this.adjacencyList = new ArrayList<Map<Integer, Edge>>(); 
		}
		
		void addEdge(int v, int w, Edge e) {
			this.adjacencyList.get(v).put(w, e);
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
	
	static class MinNode
	{
		List<Integer> children;
		int num;
		
		MinNode(int num) {
			this.children = new ArrayList<Integer>();
			this.num = num;
		}
	}
}