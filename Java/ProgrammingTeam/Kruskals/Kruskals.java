import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Kruskals 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			int edgeCount = in.nextInt();
			Edge[] edges = new Edge[edgeCount];
			
			int count = 0;
			for (int u = 0; u < n; u++) {
				int numEdges = in.nextInt();
				for (int i = 0; i < numEdges; i++) {
					edges[count++] = new Edge(u, in.nextInt(), in.nextInt());
				}
			}
			
			int sum = 0;
			for (Edge e : kruskals(n, edges)) {
				sum += e.weight;
				System.out.printf("%c %c\n", (char)(e.u+'a'), (char)(e.v+'a'));
			}
			System.out.println("\n\nSum = " + sum);
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
		int weight;
		
		public Edge(int u, int v, int weight) {
			this.u = u; 
			this.v = v;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return this.weight - o.weight;
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
			int parent = uf[x];
			if (x != parent)
				parent = find(parent);
			
			return uf[x] = parent;
		}
		
		
		void union(int x, int y) {
			uf[find(x)] = find(y);
		}
	}
}
