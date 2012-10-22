import java.util.Arrays;
import java.util.Scanner;

/**
 * Tony Majestro (tmajest)
 * 
 * 2010 F: Roller Coaster
 * http://midatl.radford.edu/docs/pastProblems/10contest/MidAtlantic2010.pdf
 */

public class RollerCoaster 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			int k = in.nextInt();
			int l = in.nextInt();
			if (n == 0 && k == 0 && l == 0)
				break;
			
			int maxFun = 0;
			int[] funLevels = new int[n];
			int[] dizzinessLevels = new int[n];
			for (int i = 0; i < n; i++) {
				int f = in.nextInt();
				funLevels[i] = f;
				maxFun += f;
				dizzinessLevels[i] = in.nextInt();
			}
			
			solve(maxFun, k, l, funLevels, dizzinessLevels);
		}
	}
	
	
	static void solve(int maxFun, int k, int maxDizziness, int[] funLevels, int[] dizLevels)
	{
		int[][] fun = new int[maxFun+1][funLevels.length+1];
		int[][] diz = new int[maxFun+1][funLevels.length+1];
			
		for (int f = 0; f <= maxFun; f++) {
			for (int i = 0; i < funLevels.length; i++) {
				int currFun = funLevels[i];
				int currDiz = dizLevels[i];
				fun[f][i+1] = fun[f][i];
				diz[f][i+1] = (diz[f][i] - k <= 0) ? 0 : diz[f][i] - k; 
				if (currFun <= f) {
					int funTaken = fun[f - currFun][i] + currFun; 
					int dizTaken = diz[f - currFun][i] + currDiz;
					if (dizTaken <= maxDizziness && 
							compareSection(funTaken, dizTaken, fun[f][i+1], diz[f][i+1]) > 0) {
						fun[f][i+1] = funTaken;
						diz[f][i+1] = dizTaken;
					}
				}
			}
			//printArray(best);
		}
		
		int max = 0;
		for (int i = 0; i <= maxFun; i++) {
			max = Math.max(max, fun[i][funLevels.length]);
		}
		
		System.out.println(max);
	}
	
	
	static int compareSection(int f1, int d1, int f2, int d2) {
		if (f1 == f2) {
			return d2 - d1;
		}
		return f1 - f2;
	}
}
