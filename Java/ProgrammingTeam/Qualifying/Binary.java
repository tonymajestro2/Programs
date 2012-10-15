import java.util.Scanner;


public class Binary 
{
	public static void main(String[] args)
	{
		int num = new Scanner(System.in).nextInt();
		int count = 0;
		int i = 1;
		while (count != num) {
			String bin = Integer.toBinaryString(i);
			
			if (isPalindrome(bin)) {
				count++;
				System.out.println(bin + '\t' + count+ '\t' + bin.length());
			}
			
			i += 2;
		}
		
		System.out.println(i - 2);
	}
	
	public static boolean isPalindrome(String s) {
		char[] arr = s.toCharArray();
		int len = arr.length;
		for (int i = 0; i <= len/2; i++) {
			if (arr[i] != arr[len - i - 1])
				return false;
		}
		return true;
	}
}
