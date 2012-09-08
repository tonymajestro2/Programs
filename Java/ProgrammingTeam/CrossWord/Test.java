import java.util.Arrays;


public class Test {
	public static void main(String[] args) {
	
		char[][] matrix= new char[2][2];
		matrix[0] = new char[] {'a', 'b'};
		matrix[1] = new char[] {'c', 'd'};
		
		char[][] cloned = matrix.clone();
		
		cloned[0][0] = 'z';
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.println(matrix[i][j]);
			}
		}
		
		
		
		
		System.out.println("yo");
	}
	

}
