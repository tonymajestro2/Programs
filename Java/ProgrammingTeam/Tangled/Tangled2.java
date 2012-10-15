/**
 * Tony Majestro (tmajest)
 * Passes ICPC online judge
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Tangled2
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextDouble()) {
			double totalCable = in.nextDouble();
			int n = in.nextInt();
			
			// Get vertices
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < n; i++) {
				map.put(in.next(), i);
			}
			
			// Get edges
			Edge[] edges = new Edge[in.nextInt()];
			for (int i = 0; i < edges.length; i++) {
				Edge e = new Edge(map.get(in.next()), map.get(in.next()), in.nextDouble());
				edges[i] = e;
			}
			
			// Get MST length
			double cableLen = 0;
			for (Edge e : kruskals(n, edges)) {
				cableLen += e.weight;
			}
			
			if (Double.compare(totalCable, cableLen) < 0) {
				System.out.println("Not enough cable");
			}
			else {
				System.out.printf("Need %.1f miles of cable\n", cableLen);
			}
		}
	}
	
	
	/** Returns the edges in the MST */
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
	
	
	static class UnionFind
	{
		int[] uf;
		int[] size;
		
		UnionFind(int n) {
			this.uf = new int[n];
			this.size = new int[n];
			for (int i = 0; i < n; i++) {
				uf[i] = i;
				size[i] = 1;
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
			int px = find(x);
			int py = find(y);
			if (size[px] > size[py]) {
				uf[py] = px;
				size[px] += size[py];
			}
			else {
				uf[px] = py;
				size[py] += size[px];
			}
		}
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
}
