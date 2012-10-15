/**
 * Tony Majestro (tmajest)
 * Passes ICPC online judge 
 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class UndergroundCables 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			if (n == 0)
				break;
			
			// Get pole vertices
			Point[] vertices = new Point[n];
			for (int i = 0; i < n; i++) {
				vertices[i] = new Point(in.nextInt(), in.nextInt());
			}
			
			// Get edge weights
			int numEdges = (n * (n-1)) / 2;
			Edge[] edges = new Edge[numEdges];
			int count = 0;
			for (int i = 0; i < n - 1; i++) {
				for (int j = i+1; j < n; j++) {
					double weight = distance(vertices[i], vertices[j]);
					edges[count++] = new Edge(i, j, weight);
				}
			}
			
			// Get MST length
			double len = 0;
			for (Edge e : kruskals(n, edges)) {
				len += e.weight;
			}
			
			System.out.printf("%.2f\n", len);
		}
	}
	
	
	static List<Edge> kruskals(int n, Edge[] edges) 
	{
		Arrays.sort(edges);
		List<Edge> MST = new ArrayList<Edge>();
		UnionFind uf = new UnionFind(n);
		
		for (Edge e : edges) {
			int x = uf.find(e.u);
			int y = uf.find(e.v);
			if (x != y) {
				uf.union(x, y);
				MST.add(e);
			}
		}
		
		return MST;
	}
	
	
	static class Edge implements Comparable<Edge>
	{
		int u, v;
		double weight;
		
		public Edge(int u, int v, double weight) {
			this.u = u; 
			this.v = v;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return Double.compare(this.weight, o.weight);
		}
	}
	
	
	static class UnionFind
	{
		int[] uf;
		
		UnionFind(int n) {
			this.uf = new int[n];
			for (int i = 0; i < n; i++) {
				uf[i] = i;
			}
		}
		
		int find(int x) {
			while (x != uf[x]) {
				uf[x] = uf[uf[x]];
				x = uf[x];
			}
			return x;
		}
		
		
		void union(int x, int y) {
			uf[find(x)] = find(y);
		}
	}
	
	/** Returns distance between two points */
	static double distance(Point a, Point b) {
		int xDiff = a.x - b.x;
		int yDiff = a.y - b.y;
		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
	}
}
