import java.util.Arrays;


public class Test {

	public static void main(String[] args) {
		boolean[] arr1 = new boolean[] { true, false, false, true};
		boolean[] arr2 = new boolean[] { true, false, false, true};
		
		System.out.println(Arrays.hashCode(arr1));
		System.out.println(Arrays.hashCode(arr2));
	}
}
