import java.util.Scanner;


public class Blocks 
{

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNextDouble()) {
			double[] b = new double[] {
					in.nextDouble(),
					in.nextDouble(),
					in.nextDouble(),
					in.nextDouble()
			};
			
			double[] t = new double[] {
					in.nextDouble(),
					in.nextDouble(),
					in.nextDouble(),
					in.nextDouble()
			};
			
			solve(b, t);
			
			
		}
	}
	
	public static void solve(double[] b, double[] t)
	{
		double[] x1 = quad(b[3] * 3, b[2] * 2, b[1]);
		double[] x2 = quad(t[3] * 3, t[2] * 2, t[1]);
		
		double by = b[0] + (b[1] * x1) + (b[2] * Math.pow(x1,  2)) +
				(b[3] * Math.pow(x1,  3));
		
		double ty = t[0] + (t[1] * x2) + (t[2] * Math.pow(x2,  2)) +
				(t[3] * Math.pow(x2,  3));
		
		System.out.println(ty - by);
		
	}
	
	public static double[] quad(double a, double b, double c) {
		double det = Math.sqrt((b * b) - (4 * a * c));
		double ans1 = ((b * -1) + det) / (2 * a);
		double ans2 = ((b * -1) + det) / (2 * a);
		return new double[] {ans1, ans2};
	}
}
