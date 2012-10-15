import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Ls 
{
	public static void main(String[] args) throws IOException
	{
		char[] letters = "abcdefghijklmnopqrstuvwxyz.".toCharArray();
		Scanner in = new Scanner(System.in);
		String pattern = in.nextLine();
		
		State firstState = new State();
		State lastState = firstState;
		
		for (char c : pattern.toCharArray()) {	
			if (c == '*') {
				// For wildcard characters, add transition from all letters
				// back to original state
				for (char letter : letters) {
					lastState.addTransition(letter, lastState);
				}
			}
			else {
				// For non-wildcard letters, add transition from letter
				// to the new state
				State newState = new State();
				lastState.addTransition(c, newState);
				lastState = newState;
			}
		}
		
		int n = Integer.parseInt(in.nextLine());
		for (int i = 0; i < n; i++) {
			// Simulate NFA for each filename
			String fileName = in.nextLine();
			Set<State> activeStates = new HashSet<State>();
			activeStates.add(firstState);
			
			for (char c : fileName.toCharArray()) {
				Set<State> newStates = new HashSet<State>();
				
				// For each of the active states, transition to the
				// next state using the current filename letter
				for (State s : activeStates) {
					if (s.transitions.containsKey(c)) {
						for (State transition : s.transitions.get(c)) {
							newStates.add(transition);
						}
					}
				}
				activeStates = newStates;
			}
			
			// After simulating the NFA, if the active state set contains the 
			// original last state object, then the pattern matches the filename
			if (activeStates.contains(lastState)) {
				System.out.println(fileName);
			}
		}
		
		in.close();
	}
	
	
	private static class State
	{
		Map<Character, List<State>> transitions;
		
		public State() 
		{
			transitions = new HashMap<Character, List<State>>();
		}
		
		void addTransition(char c, State s) 
		{
			if (!transitions.containsKey(c)) {
				transitions.put(c, new ArrayList<State>());
			}
			transitions.get(c).add(s);
		}
		
		@Override
		public int hashCode() 
		{
			return transitions.keySet().hashCode();
		}
	}
}
