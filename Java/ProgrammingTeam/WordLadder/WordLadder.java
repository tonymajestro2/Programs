import java.util.Scanner;


public class WordLadder 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			if (n == 0)
				break;
			
			// Get vertices
			String[] words = new String[n];
			for (int i = 0; i < n; i++) {
				words[i] = in.next();
			}
			
			// Get edges
			int[][] edges = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					if (hasEdge(words[i], words[j])) {
						edges[i][j] = 1;
						edges[j][i] = 1;
					}
					else {
						edges[i][j] = Integer.MAX_VALUE;
						edges[j][i] = Integer.MAX_VALUE;
					}
				}
			}
			
			// Get largest word ladder (which is equal to the largest path in
            // the all pairs shortest path distance matrix
			int max = 0;
			int[][] dist = floydWarshalls(edges);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (dist[i][j] != Integer.MAX_VALUE && dist[i][j] > max)	
						max = dist[i][j];
				}
			}
			
			System.out.println(max + 1);
		}
	}
	

    // Returns the all pairs shortest path matrix    
	static int[][] floydWarshalls(int[][] edges) 
	{
		// Get initial edge weights
		int[][] dist = new int[edges.length][];
		for (int i = 0; i < edges.length; i++) {
			dist[i] = (int[]) edges[i].clone();
		}
		
		// Path reconstructions
		for (int k = 0; k < edges.length; k++) {
			for (int i = 0; i < edges.length; i++) {
				for (int j = 0; j < edges.length; j++) {
					// Check that ik + ij does not overflow
					int ikj = dist[i][k] + dist[k][j];
					if (ikj > 0 && ikj < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}
		return dist;
	}
	
	
	// Two words have an edge if their distance is at most 1
	static boolean hasEdge(String word1, String word2)
	{
		String larger = (word1.length() > word2.length()) ? word1 : word2;
		String smaller = (larger == word1) ? word2 : word1;
		
		if (larger.length() - smaller.length() > 1) {
			return false;
		}
		
		// Change a letter
		int dist = 0;
		if (smaller.length() == larger.length()) {
			for (int i = 0; i < smaller.length(); i++) {
				if (smaller.charAt(i) != larger.charAt(i)) {
					dist++;
					if (dist == 2)
						return false;
				}
			}
			return true;
		}
		
		// Add/remove a letter
		else {
			int sIndex = 0;
			for (int i = 0; i < larger.length(); i++) {
				if (sIndex >= smaller.length() || smaller.charAt(sIndex) != larger.charAt(i)) {
					dist++;
					if (dist == 2)
						return false;
				}
				else {
					sIndex++;
				}
			}
			return true;
		}
	}
}
