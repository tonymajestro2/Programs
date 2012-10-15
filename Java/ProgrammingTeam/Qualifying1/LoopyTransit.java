import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class LoopyTransit 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int numNodes = in.nextInt();
			int numConnections = in.nextInt();
			
			Node[] nodes = new Node[numNodes];
			for (int i = 0; i < numNodes; i++) {
				nodes[i] = new Node(i);
			}
			
			for (int i = 0; i < numConnections; i++) {
				int x1 = in.nextInt();
				int x2 = in.nextInt();
				
				nodes[x1].neighbors.add(nodes[x2]);
			}
			
			Set<List<Node>> cycles = new HashSet<List<Node>>();
			for (int i = 0; i < numNodes; i++) {
				solve(nodes[i], new ArrayList<Node>(), cycles);
			}
			
			System.out.println(cycles.size());
		}
		
		in.close();
	}
	
	
	static void solve(Node curr, List<Node> visited, Set<List<Node>> cycles)
	{
		if (visited.contains(curr)) {
			visited.add(curr);
			tryAddCycle(visited, cycles);
			return;
		}
		
		for (Node neighbor : curr.neighbors) {
			List<Node> newVisited = new ArrayList<Node>(visited);
			newVisited.add(curr);
			solve(neighbor, newVisited, cycles);
		}
	}
	
	static void tryAddCycle(List<Node> visited, Set<List<Node>> cycles)
	{
		int index = visited.indexOf(visited.get(0));
		List<Node> currCycle = new ArrayList<Node>();
		
		
		
		for (List<Node> cycle : cycles) {
			int len = visited.size();
			if (cycle.size() != len) {
				continue;
			}
			
			outer:
			for (int i = 0; i < len; i++) {
				for (int j = 0; j < len; j++) {
					int a = visited.get((i + j) % len).num;
					int b = cycle.get(j).num;
					
					//System.out.println(a + " " + b);
					if (!visited.get((i + j) % len).equals(cycle.get(j))) {
						continue outer;
					}
				}
				
				return false;
			}
		}
		
		return true;
	}
	

	static class Node
	{
		int num;
		List<Node> neighbors;
		
		public Node(int num) {
			this.num = num;
			neighbors = new ArrayList<Node>();
		}
		
		@Override 
		public boolean equals(Object o) {
			return num == ((Node)o).num;
		}
		
		@Override
		public int hashCode() {
			return num;
		}
		
	}
}
