/**
 * Tony Majestro (tmajest)
 * Wrong answer on ICPC online judge
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Raggedy
{
	static Map<Integer, Pair<String, Integer>> cache = 
			new HashMap<Integer, Pair<String, Integer>>();
	
	static final Pair<String, Integer> empty_pair = 
			new Pair<String, Integer>("", 0);


	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);	
		while (in.hasNextLine()) {
			int L = Integer.parseInt(in.nextLine());
			if (L == 0)
				break;
			
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.nextLine()).length() > 0) {
				sb.append(line.trim());
				sb.append(' ');
			}
			
			line = sb.toString();
			String[] words = line.split("\\s+");
			cache.clear();
			
			String best = solve(L, 0, words).item1;
			System.out.println(best.substring(0, best.length() - 1)); // delete trailing newline
			System.out.println("===");
			
			//for (Pair<String, Integer> p: cache.values()) {
			//	System.out.println(p.item1 + ": " + p.item2 + "\n");
			//}
		}
	}
	
	
	static Pair<String, Integer> solve(int L, int curr, String[] words)
	{
		if (cache.containsKey(curr)) {
			return cache.get(curr);
		}
		else if (curr == words.length) {
			return empty_pair;
		}
		
		int min_value = Integer.MAX_VALUE;
		String best_line = "";
		String temp = "";
		
		// Find minimum value for the row, given the remaining words
		for (int i = curr; i < words.length; i++) {
			if (words[i].length() == 0) {
				i++;
				continue;
			}
				
			temp += (temp.length() == 0) ? words[i] : String.format(" %s", words[i]);
			if (temp.length() > L)
				break;
			
			Pair<String, Integer> curr_best = solve(L, i + 1, words);
			int curr_value = curr_best.item2 + value(L, temp, i, words.length);
			
			if (curr_value < min_value) {
				min_value = curr_value;
				best_line = String.format("%s\n%s", temp, curr_best.item1);
			}
		}
		
		// Cache best line and its value
		Pair<String, Integer> best = new Pair<String, Integer>(best_line, min_value);
		cache.put(curr, best);
		return best;
	}
	
	
	static int value(int L, String P, int curr, int num_words) {
		if (curr == num_words - 1) {
			return 0;
		}
		
		return (L - P.length()) * (L - P.length());
	}
	
	
	static class Pair<T1, T2>
	{
		T1 item1;
		T2 item2;
		
		Pair(T1 item1, T2 item2) {
			this.item1 = item1; this.item2 = item2;
		}
	}
	
	
}
