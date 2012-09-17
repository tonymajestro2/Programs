/**
 * Tony Majestro (tmajest)
 * 
 * Solves small and big input
 * 
 * Big input takes 5 seconds, which doesn't pass on ICPC.
 */

import java.util.Scanner;


public class Permutations 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			if (n == 0) {
				break;
			}
			
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = in.nextInt(); 
			}
			
			solve(arr, new StringBuilder());
		}
		
	}
	
	private static void solve(int[] arr, StringBuilder sb)
	{
		int len = arr.length;
		int[] permutation = new int[len];
		for (int i = 0; i < len; i++) {
			int a = arr[i];
			int numEmptySpaces = 0, pos = 0;
			while (numEmptySpaces < a) {
				if (permutation[pos] == 0) {
					numEmptySpaces++;
				}
				pos++;
			}
			// Make sure the 0 cases end on an empty position
			while (permutation[pos] != 0) pos++;
			permutation[pos] = i+1;
		}
		
		sb.append(permutation[0]);
		for (int i = 1; i < len; i++) {
			sb.append(',');
			sb.append(permutation[i]);
		}
		System.out.println(sb.toString());
	}
}
