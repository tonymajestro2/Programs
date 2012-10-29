import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class GraphStuff
{
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
