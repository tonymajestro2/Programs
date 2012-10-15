import java.math.BigInteger;
import java.util.Scanner;


public class BasePalindrome
{
	public static void main(String [] args)
	{
		//Scanner in = new Scanner(System.in);
		//int M = in.nextInt();
		//for (int M = 1; M <= 50000; ++M)
		//{
		int M = 5;
		int sum = 0;
		int i = 1;
		for (i = 1; sum < M; i++)
			sum += Math.pow(2, (i - 1) / 2);
		int numBits = i - 1;
		sum -= Math.pow(2, (numBits - 1) / 2);
		BigInteger bigInt = BigInteger.valueOf((numBits - 1) * (numBits - 1));
		for (; ; bigInt = bigInt.add(BigInteger.ONE))
		{
			if (isPalindrome(bigInt))
			{
				sum++;
				if (sum == M)
				{
					System.out.println(bigInt);
					break;
				}
			}
		}
		//}
		//in.close();
	}
	
	private static boolean isPalindrome(BigInteger bigInt)
	{
		int numBits = bigInt.bitLength();
		for (int i = 0; i < numBits; i++)
			if (bigInt.testBit(i) != bigInt.testBit(numBits - i - 1))
				return false;
		return true;
	}
}
