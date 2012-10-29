/**
 * Generic State Space code
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


public class StateSpace 
{
	public static void main(String[] args) 
	{
		
	    State initState = new State();
	    solve(initState);
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
			
            if (s.isFinal()) {
				outputState(s, pred);
			    return;
		    }
			
            for (State child : s.getChildren()) {
				if (visited.contains(child))
					continue;
				
				if (!pred.containsKey(child))
					pred.put(child, s);
				
                if (!dist.containsKey(child)) {
					dist.put(child, n+1);
					bfs.offer(child);
				}
			}
		}
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
            sb.append(st.toString());
		}
		
        System.out.println(sb.toString());
	}
	
	
	private static class State
	{
        // Fields go here
		
		public State() {}
		
		public List<State> getChildren() {
			List<State> children = new ArrayList<State>();
            
            // Add logic for generating children here

           return children; 
        }
		
		public boolean isFinal() {
	        // isFinal logic here
            return false;    
		}
		
		@Override
		public boolean equals(Object o) {
			State other = (State) o;
            // equals logic here
            return true;
		}
		
		@Override
		public int hashCode() {
            // Hashcode logic here
            return 0;
		}
	}
}

