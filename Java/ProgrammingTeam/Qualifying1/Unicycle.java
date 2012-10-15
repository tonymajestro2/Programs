import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Unicycle 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int len = in.nextInt();
			int numMarks = in.nextInt();
			List<Integer> marks = new ArrayList<Integer>();
			for (int i = 0; i < numMarks; i++) {
				marks.add(in.nextInt());
			}
			
			solve(new State(marks, len));
		}
	}
	
	private static void solve(State initState)
	{	
		Set<State> visited = new HashSet<State>();
		Map<State, State> pred = new HashMap<State, State>();
		Map<State, Integer> dist = new HashMap<State, Integer>();
		Deque<State> bfs = new ArrayDeque<State>();
		
		bfs.offer(initState);
		dist.put(initState, 0);
			
		while (bfs.size() > 0) {
			State s = bfs.poll();
			int n = dist.get(s);
			visited.add(s);
			
			for (State child : s.getChildren()) {
				if (visited.contains(child))
					continue;
				
				//printState(child);
				
				if (!pred.containsKey(child))
					pred.put(child, s);
				
				if (!dist.containsKey(child)) {
					dist.put(child, n+1);
					bfs.offer(child);
				}
				
				if (child.isFinal()) {
					System.out.println(dist.get(child));
					return;
				}
			}
		}
	}
	
	static class State
	{
		List<Integer> marks;
		int len;
		
		public State(List<Integer> marks, int len) {
			this.marks = marks;
			this.len = len;
		}
		
		public boolean isFinal() {
			return marks.size() == 0;
		}
		
		public Set<State> getChildren()
		{
			Set<State> states = new HashSet<State>();
			for (int i = 0; i < marks.size(); i++) {
				int x = marks.get(i);
				for (int j = i+1; j < marks.size(); j++) {
					
					int y = marks.get(j);
					if (canAdd(x, y)) {
						add(x, y, states);
					}
				}
			}
			
			return states;
		}
		
		private boolean canAdd(int i, int j)
		{
			int dist = j - i;
			if (dist < i)
				return false;
			
			int curr = i;
			while (curr < len) {				
				if (!marks.contains(curr)) {
					return false;
				}
				curr += dist;
			}
			return true;
		}
		
		private void add(int i, int j, Set<State> states)
		{
			List<Integer> newMarks = new ArrayList<Integer>(marks);
			int dist = j - i;
			int curr = i;
			while (curr < len) {
				newMarks.remove(new Integer(curr));
				curr += dist;
			}
			
			states.add(new State(newMarks, len));
		}
		
		@Override
		public int hashCode() {
			return marks.hashCode();
		}
		
		@Override
		public boolean equals(Object o)
		{
			return ((State)o).marks.equals(marks);
		}
	}
	
	public static void printState(State s) {
		for (int i : s.marks) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
