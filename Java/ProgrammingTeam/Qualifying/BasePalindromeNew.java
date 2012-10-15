import java.util.Scanner;


public class BasePalindromeNew
{
	public static void main(String [] args)
	{
		//Scanner in = new Scanner(System.in);
		//int M = in.nextInt();
		int M = 50000;
	//	for (int M = 1; M <= 50000; M++)
	//	{
		int sum = 0;
		int i = 1;
		for (i = 1; sum < M; i++)
			sum += Math.pow(2, (i - 1) / 2);
		int numBits = i - 1;
		sum -= Math.pow(2, (numBits - 1) / 2);
		long l = (long)Math.pow(2, numBits - 1);
		for (; ; l++)
		{
			if (isPalindrome(l))
			{
				sum++;
				if (sum == M)
				{
					System.out.println(l);
					break;
				}
			}
		}
	//	}
		//in.close();
	}
	
	private static boolean isPalindrome(long l)
	{
		long high = Long.highestOneBit(l);
		long low = 1L;
		int end = (int)((high + low) / 2);
		for (int i = 0; i < end; i++)
		{
			long top = high & l;
			long bottom = low & l;
			if ((top == 0 && bottom != 0) || (top != 0 && bottom == 0))
				return false;
			high >>= 1;
			low <<= 1;
		}
		return true;
	}
}
