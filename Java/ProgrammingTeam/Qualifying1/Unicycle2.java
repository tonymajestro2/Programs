import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class Unicycle2 
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
			
			Set<List<Integer>> visited = new HashSet<List<Integer>>();
			solve(marks, visited, len, 0);
		}
	}
	
	
	public static int solve(List<Integer> marks, Set<List<Integer>> visited,
			int len, int count)
	{
		if (visited.contains(marks)) {
			return -1;
		}
		else if (marks.size() == 0)
			return count;
		
		int min = -1;
		for (List<Integer> children : getChildren(marks, len)) {
			int curr = solve(children, visited, len, count + 1);
			if (curr > 0 && curr > min) {
				min = count;
			}
		}
		
		return min;
	}
	
	static Set<List<Integer>> getChildren(List<Integer> marks, int len) 
	{
		Set<List<Integer>> children = new HashSet<List<Integer>>();
		if (marks.size() == 0) {
			List<Integer> l = new ArrayList<Integer>();
			l.add(marks.get(0));
			children.add(l);
			return children;
		}
		
		int a = marks.get(0);
		for (int i = 1; i < marks.size(); i++) {
			int b = marks.get(i);
			if (canAdd(a, b, marks, len)) {
				children.add(add(a,b,marks,len));
			}
		}
		
		return children;
	}
	
	static List<Integer> add(int i, int j, List<Integer> marks, int len)
	{
		List<Integer> newMarks = new ArrayList<Integer>(marks);
		int dist = j - i;
		int curr = i;
		while (curr < len) {
			newMarks.remove(new Integer(curr));
			curr += dist;
		}
		
		return newMarks;
	}
	
	static boolean canAdd(int i, int j, List<Integer> marks, int len)
	{
		int dist = j - i;
		int curr = i;
		while (curr < len) {				
			if (!marks.contains(curr)) {
				return false;
			}
			curr += dist;
		}
		return true;
	}
}
