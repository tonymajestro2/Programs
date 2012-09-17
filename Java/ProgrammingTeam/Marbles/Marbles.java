/**
 * Tony Majestro (tmajet)
 * 
 * Passes ICPC online judge.
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;


public class Marbles 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
			
			if ((a + b + c) % 3 != 0) {
				System.out.printf("%d %d %d\n", a, b, c);
				System.out.println("============");
				continue;
			}
			
			State initState = new State(a, b, c);
			solve(initState);
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
				
				if (!pred.containsKey(child))
					pred.put(child, s);
				
				if (child.isFinal()) {
					outputState(child, pred);
					return;
				}
				
				if (!dist.containsKey(child)) {
					dist.put(child, n+1);
					bfs.offer(child);
				}
			}
		}
		
		System.out.printf("%d %d %d\n", initState.a, initState.b, initState.c);
		System.out.println("============");
	}
	
	
	private static void outputState(State finalState, Map<State, State> pred)
	{
		Stack<State> reversedPath = new Stack<State>();
		State s = finalState;
		while (pred.containsKey(s)) {
			reversedPath.add(s);
			s = pred.get(s);
		}
		reversedPath.add(s);
		
		StringBuffer sb = new StringBuffer();
		while (!reversedPath.isEmpty()) {
			State st = reversedPath.pop();
			sb.append(st.a);
			sb.append(' ');
			sb.append(st.b);
			sb.append(' ');
			sb.append(st.c);
			sb.append('\n');
			//System.out.printf("%d %d %d\n", st.a, st.b, st.c);
		}
		//System.out.println("============");
		sb.append("============");
		System.out.println(sb.toString());
	}
	
	
	private static class State
	{
		int a, b, c;
		
		public State(int a, int b, int c) {
			this.a = a; this.b = b; this.c = c;
		}
		
		public List<State> getChildren() {
			List<State> children = new ArrayList<State>();
			if (a >= b) {
				children.add(new State(a - b, b + b, c));
			}
			if (b >= a) {
				children.add(new State(a + a, b - a, c));
			}
			if (a >= c) {
				children.add(new State(a - c, b, c + c));
			}
			if (c >= a) {
				children.add(new State(a + a, b, c - a));
			}
			if (b >= c) {
				children.add(new State(a, b - c, c + c));
			}
			if (c >= b) {
				children.add(new State(a, b + b, c - b));
			}
			return children;
		}
		
		public boolean isFinal() {
			return a == b && b == c;
		}
		
		@Override
		public boolean equals(Object o) {
			State other = (State) o;
			return a == other.a && b == other.b && c == other.c;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] {a, b, c});
		}
	}
}

