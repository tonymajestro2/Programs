
public class TryCatch 
{
	public static void main(String[] args)
	{
		boolean[] arr = new boolean[100000000];
		test(arr);
	}
	
	
	public static void test(boolean[] arr) {
		int i = 0;
		try {
			while (true) {
				System.out.println(arr[i++]);
			}
		}
		catch (IndexOutOfBoundsException e) {
			
		}
	}
	
	public static void test2(boolean[] arr) {
		int i = 0;
		while (i < arr.length) { 
			System.out.println(arr[i++]);
		}
	}
}
