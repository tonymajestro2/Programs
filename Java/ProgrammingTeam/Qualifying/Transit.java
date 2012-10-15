import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Transit 
{
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		int numNodes = in.nextInt();
		int n = in.nextInt();
		
		Node[] nodes = new Node[numNodes];
		for (int i = 0; i < numNodes; i++) {
			nodes[i] = new Node(i);
		}
		
		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			nodes[x].children.put(y, nodes[y]);
		}
		
		List<List<Node>> cycles = new List<>
	}
	
	static void solve(Node[] nodes, Node curr, List<Node> visited, List<List<Node>> cycles)
	{
		if (visited.size() > 1 && curr.equals(visited.get(0))) {
			addCycle(visited, cycles);
			return;
		}
		
		visited.add(curr);
		
		for (int i : curr.children.keySet()) {
			solve(nodes, curr.children.get(i), visited, cycles);
		}
	}
	
	static void addCycle(List<Node> visited, List<List<Node>> cycles) {
		
		for (List<Node> cycle : cycles) {
			if (cycle.size() != visited.size())
				continue;
			
			for (int i = 0; i < visited.size(); i++) {
				int count = 0;
				for (int j = 0; j < visited.size(); j++) {
					if (visited.get(i + j % visited.size()).equals(cycle))
						count++;
				}
				if (count == visited.size())
					return;
			}
		}
		
		cycles.add(visited);
	}
	
	static class Node
	{
		Map<Integer, Node> children;
		int num;
		
		Node(int num) {
			this.num = num;
			this.children = new HashMap<Integer, Node>();
		}
		
		public int hashCode() {
			return num;
		}
		
		public boolean equals(Object o)
		{
			return this.num == ((Node)o).num;
		}
	}
	
}
