/**
 * Tony Majestro (tmajest)
 * Lazy version passes online judge
 */

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class TexasSummers2 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt() + 2;
		
		final int[] dist = new int[n];
		int[] pred = new int[n];
		Point[] points = new Point[n];
		
		Queue<Integer> Q = new PriorityQueue<Integer>(n, new Comparator<Integer>() {
			@Override
			public int compare(Integer v, Integer w) {
				return dist[v] - dist[w]; 
			}
		});
		
		// Initialize vertex distances
		for (int i = 1; i < dist.length; i++) {
			dist[i] = Integer.MAX_VALUE;	
		}
		dist[0] = 0;
		
		// Get vertices
		for (int i = 1; i < n - 1; i++) {
			points[i] = new Point(in.nextInt(), in.nextInt());
		}
		points[0] = new Point(in.nextInt(), in.nextInt());
		points[n-1] = new Point(in.nextInt(), in.nextInt());
		Q.add(0);
		
		shortestPath(Q, points, dist, pred);
		printPath(pred);
	}
	
	
	/** Finds the shortest path from vertex 0 to vertex n-1 */
	static void shortestPath(Queue<Integer> Q, Point[] points, int[] dist, int[] pred)
	{
		int goal = dist.length - 1;
		boolean[] visited = new boolean[dist.length];
		
		while (!Q.isEmpty()) {
			int v = Q.poll();
			if (v == goal) {
				return;
			}
			else if (visited[v]) {
				continue;
			}
			
			visited[v] = true;
			int d = dist[v];
		
            // relax v with all other vertices    
			for (int w = 0; w < points.length; w++) {
				if (visited[w])
					continue;
				
				int newDistW = d + getDistanceSquared(points[v], points[w]);
				if (newDistW < dist[w]) {
					dist[w] = newDistW;
					pred[w] = v;
					
					// Uncomment for eager implmentation
					// Performs slowly due to the O(n) removal in PriorityQueue
					/*if (Q.contains(w)) {
						Q.remove(w);
					}*/
					Q.add(w);
				}
			}
		}
	}
	
	
	static int getDistanceSquared(Point p1, Point p2) {
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

