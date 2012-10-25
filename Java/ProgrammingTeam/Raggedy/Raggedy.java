import java.util.Scanner;



public class Raggedy
{
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
				sb.append(line);
				sb.append(' ');
			}
			
			line = sb.toString();
			String[] words = line.split("\\s+");
			System.out.println(solve(L, 1, words[0], words));
		}
	}
	
	
	static int solve(int L, int curr, String P, String[] words)
	{
		int min = 0;
		if (curr < words.length) {
			String newP = P + " " + words[curr];
			if (newP.length() <= L) {
				System.out.println(newP);
				min = solve(L, curr + 1, "", words);
				min = Math.min(min, solve(L, curr + 1, newP, words));
			}
			else {
				min = Integer.MAX_VALUE;
			}
		}
		
		return min;
	}
	
	static int value(int L, String P) {
		return (L - P.length()) * (L - P.length());
	}
}