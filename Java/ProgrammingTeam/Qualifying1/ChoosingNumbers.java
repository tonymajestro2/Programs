import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;


public class ChoosingNumbers 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			int[] nums = new int[n];
			for (int i = 0; i < n; i++) {
				nums[i] = in.nextInt();
			}
			
			Arrays.sort(nums);
			
			outer:
			for (int i = n-1; i >= 0; i--) {
				int a = nums[i];
				for (int j = 0; j < n; j++) {
					int b = nums[j];
					if (a == b)
						continue; 
					
					if (!BigInteger.valueOf(a).gcd(BigInteger.valueOf(b))
							.equals(BigInteger.ONE)) {
						continue outer;
					}
				}
				
				System.out.println(a);
				break;
			}
		}
	}
}
