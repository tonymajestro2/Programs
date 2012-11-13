/**
 * Tony Majestro (tmajest)
 * Times out on judge data
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Arabia 
{
	static Map<Integer, Integer> track_cache = new HashMap<Integer, Integer>();
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			int m = in.nextInt();
			if (n == 0 && m == 0)
				break;
			
			int[] depots = new int[n];
			for (int i = 0; i < n; i++) {
				depots[i] = in.nextInt();
			}
			
			track_cache.clear();
			System.out.println(solve(depots, new boolean[n-1], 0, m));
		}
	}
	
	
	static int solve(int[] depots, boolean[] cut_tracks, int num_cut, int m)
	{
		int hash_code = Arrays.hashCode(cut_tracks);
		if (track_cache.containsKey(hash_code)) {
			return track_cache.get(hash_code);
		}
		else if (num_cut >= m) {
			return value(depots, cut_tracks);
		}
		
		int min = -1;
		for (int i = 0; i < cut_tracks.length; i++) {
			if (cut_tracks[i]) {
				continue;
			}
			
			boolean[] new_cut_tracks = cut_tracks.clone();
			new_cut_tracks[i] = true;
			int curr_value = solve(depots, new_cut_tracks, num_cut + 1, m);
			
			if (min == -1 || curr_value < min)
				min = curr_value;
		}
	
		min = (min == -1) ? 0 : min;
		track_cache.put(hash_code, min);
		return min;
	}
	
	
	static int value(int[] depots, boolean[] cut_tracks)
	{
		int value = 0;
		for (int i = 0; i < depots.length - 1; i++) {
			for (int j = i + 1; j < depots.length; j++) {
				if (cut_tracks[j-1])
					break;
				
				value += depots[i] * depots[j];
			}
		}
		return value;
	}
}
