/**
 * Tony Majestro (tmajest) 
 * Passes online judge
 */

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


public class TexasSummers 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int numTrees = in.nextInt();
		
		Point[] vertices = new Point[numTrees + 2];
		int[] dist = new int[numTrees + 2];
		int[] pred = new int[numTrees + 2];
		
		// Initialize vertex distances
		for (int i = 1; i < dist.length; i++) {
			dist[i] = Integer.MAX_VALUE;	
		}
		dist[0] = 0;
		
		// Get tree vertices
		for (int i = 0; i < numTrees; i++) {
			vertices[i+1] = new Point(in.nextInt(), in.nextInt());
		}
		
		// Get dorm and class vertices
		vertices[0] = new Point(in.nextInt(), in.nextInt());
		vertices[vertices.length - 1] = new Point(in.nextInt(), in.nextInt());
		
		shortestPath(vertices, dist, pred);
		printPath(pred);
	}
	
	
	/** Finds the shortest path from vertex 0 to vertex n-1 */
	static void shortestPath(Point[] vertices, int[] dist, int[] pred)
	{
		int goal = dist.length - 1;
		boolean[] visited = new boolean[dist.length];
		
		for (int i = 0; i < dist.length; i++) {
			int v = findMinVertex(dist, visited);
			if (v == goal) 
				return;
			
			visited[v] = true;
			int d = dist[v];
		
            // relax v with all other vertices    
			for (int w = 0; w < vertices.length; w++) {
				if (visited[w])
					continue;
				
				int newDistW = d + getDistanceSquared(vertices[v], vertices[w]);
				if (newDistW < dist[w]) {
					dist[w] = newDistW;
					pred[w] = v;
				}
			}
		}
	}
	
	
	/** Find unused vertex with the shortest distance from start */
	static int findMinVertex(int[] dist, boolean[] visited)
	{
		int minDist = Integer.MAX_VALUE, minIndex = -1;
		for (int i = 0; i < dist.length; i++) {
			int d = dist[i];
			if (!visited[i] && d < minDist) {
				minDist = d;
				minIndex = i;
			}
		}
		
		return minIndex;
	}
	
	
	static int getDistanceSquared(Point p1, Point p2) 
	{
		return ((p1.x - p2.x) * (p1.x - p2.x)) + ((p1.y - p2.y) * (p1.y - p2.y));
	}
	
	
	static void printPath(int[] pred) 
	{
		int goal = pred.length - 1;
		if (pred[goal] == 0) {
			System.out.println('-');
			return;
		}
		
		int curr = goal;
		Deque<Integer> reversed = new ArrayDeque<Integer>();
		while (pred[curr] != 0) {
			reversed.add(pred[curr]);
			curr = pred[curr];
		}
		
		while (!reversed.isEmpty()) 
			System.out.println(reversed.removeLast() - 1);
	}
}
