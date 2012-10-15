import java.math.BigInteger;
import java.util.Scanner;


public class Stringer 
{
	private static char[] letterMap = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int n = Integer.parseInt(in.next());
			BigInteger k = new BigInteger(in.next());
			
			if (n == 0 && k.equals(BigInteger.ZERO))
				break;
			
			int[] letterCounts = new int[n];
			for (int i = 0; i < n; i++) {
				letterCounts[i] = in.nextInt();
			}
			
			StringBuilder sb = new StringBuilder();
			solve(letterCounts, BigInteger.ZERO, k, sb);
			System.out.println(sb.toString());
		}
		in.close();
	}
	
	
	private static void solve(int[] letterCounts, BigInteger curr, BigInteger k,
			StringBuilder sb)
	{
		if (allZero(letterCounts)) {
			return;
		}
		else if (curr.equals(k)) {
			getRemainingLetters(letterCounts, sb);
			return;
		}
		
		int pos = updateLetterCount(letterCounts, 0);
		BigInteger temp = curr.add(numPermutations(letterCounts));
		BigInteger prev = curr.add(BigInteger.ZERO);
		while (temp.compareTo(k) <= 0) {
			prev = temp;
			letterCounts[pos]++;
			pos = updateLetterCount(letterCounts, pos+1);
			temp = temp.add(numPermutations(letterCounts));
		}
		
		sb.append(letterMap[pos]);
		solve(letterCounts, prev, k, sb);
		
	}
	
	private static boolean allZero(int[] arr) {
		for (int i : arr) {
			if (i != 0) {
				return false;
			}
		}
		return true;
	}
	
	
	private static BigInteger factorial(int n) 
	{
		BigInteger r = BigInteger.ONE;
		for (int i = 1; i <= n; i++) {
			r = r.multiply(BigInteger.valueOf(i));
		}
		return r;
	}
	
	
	private static BigInteger numPermutations(int[] letterCounts)
	{	
		int numerator = 0;
		BigInteger denominator = BigInteger.ONE;
		for (int count : letterCounts) {
			numerator += count;
			denominator = denominator.multiply(factorial(count));
		}
		return factorial(numerator).divide(denominator);
	}
	
	
	private static int updateLetterCount(int[] letterCounts, int pos) 
	{
		for (int i = pos; i < letterCounts.length; i++) {
			if (letterCounts[i] != 0) {
				letterCounts[i] -= 1;
				return i;
			}
		}
		return -1;
	}
	
	public static void getRemainingLetters(int[] letterCounts, StringBuilder sb)
	{
		for (int i = 0; i < letterCounts.length; i++) {
			for (int j = 0; j < letterCounts[i]; j++) {
				sb.append(letterMap[i]);
			}
		}
	}
}
