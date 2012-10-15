import java.util.Scanner;


public class BasePalindromeOld
{
	public static void main(String [] args)
	{
		//Scanner in = new Scanner(System.in);
		//int M = in.nextInt();
		for (int M = 1; M <= 1; M++)
		{
		int sum = 0;
		int i = 1;
		for (i = 1; sum < M; i++)
			sum += (int)Math.pow(2, (i - 1) / 2);
		int numBits = i - 1;
		sum -=((numBits - 1) * (numBits - 1)) / 4; // Math.pow(2, (numBits - 1) / 2);
		int l = (numBits - 1) * (numBits - 1); //(int)Math.pow(2, numBits - 1);
		for (; ; l++)
		{
			if (isPalindrome(l))
			{
				sum++;
				if (sum == M)
				{
					//System.out.println(l);
					break;
				}
			}
		}
		}
		//in.close();
	}
	
	private static boolean isPalindrome(int l)
	{
		int high = Integer.highestOneBit(l);
		int low = 1;
		int end = getLog(l) / 2;
		for (int i = 0; i < end; i++)
		{
			int top = high & l;
			int bottom = low & l;
			if ((top == 0 && bottom != 0) || (top != 0 && bottom == 0))
				return false;
			high >>= 1;
			low <<= 1;
		}
		return true;
	}

	private static int getLog(int i)
	{
		int count = 0;
		while (i != 0)
		{
			i = i >> 1;
			count++;
		}
		return count;
	}
}
