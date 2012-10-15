import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CFG 
{
	public static void main(String[] args)
	{
		
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
