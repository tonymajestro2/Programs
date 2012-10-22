import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Resistors 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			if (n == 0)
				break;
			
			Graph g = new Graph();
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < n; i++) {
				int u = getIndex(in.next(), map);
				int v = getIndex(in.next(), map);
				int weight = in.nextInt();
				g.addEdge(new Edge(u, v, weight));
				g.addEdge(new Edge(v, u, weight));
			}
			
			solve(g);
		}
	}
	
	
	static void solve(Graph g)
	{
		while (true) {
			int i = 0;
			while (i < g.edges.size() && g.edges.size() > 1) {
				Map<Integer, List<Edge>> edges = g.edges.get(i);
				boolean reduced = reduceParallel(i, edges) || reduceSeries(i, edges);
				if (!reduced) {
					i++;
				}
			}
		}
	}
	
	
	static boolean reduceParallel(int u, Map<Integer, List<Edge>> edgeMap) 
	{
		for (Integer v : edgeMap.keySet()) {
			List<Edge> edges = edgeMap.get(v);
			while (edges.size() > 1) {
				Edge e1 = edges.remove(edges.size()-1);
				Edge e2 = edges.remove(edges.size()-1);
				double w1 = e1.weight, w2 = e2.weight;
				edges.add(new Edge(u, v, 1.0 / ((1.0/w1) + (1.0/w2))));
			}
		}
	}
	
	
	static boolean reduceSeries(int u, List<Edge> edges)
	{
		
	}
	
	
	static class Graph
	{
		List<Map<Integer, List<Edge>>> edges;
		
		Graph() { 
			this.edges = new ArrayList<Map<Integer, List<Edge>>>(); 
		}
		
		void addEdge(Edge e) {
			if (e.u > edges.size()) {
				edges.add(new HashMap<Integer, List<Edge>>();
			}
			
			if (edges.get(e.u).containsKey(e.v)) {
				edges.get(e.u).get(e.v).add(e);
			}
			else {
				List<Edge> newEdges = new ArrayList<Edge>();
				newEdges.add(e);
				edges.get(e.u).put(e.v, newEdges);
			}
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
	
	static int getIndex(String v, Map<String, Integer> map) 
	{
		if (map.containsKey(v)) {
			return map.get(v);
		}
		else {
			int index = map.size();
			map.put(v, index);
			return index;
		}
	}
}
