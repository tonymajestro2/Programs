import java.util.Random;
import java.util.Scanner;

public class Cells 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextDouble()) {
			int n = in.nextInt();
			if (n == 0)
				break;
			
			Circle center = new Circle(in.nextDouble(), in.nextDouble(), in.nextDouble());
			Circle[] towers = new Circle[n - 1];
			for (int i = 0; i < n -1; i++) {
				towers[i] = new Circle(in.nextDouble(), in.nextDouble(), in.nextDouble());
			}
			
			System.out.printf("%.2f\n", getCoverage(center, towers));
		}
		in.close();
	}
	
	
	public static double getCoverage(Circle c, Circle[] towers)
	{
		int count = 0, total = 0;
		Random r = new Random();
		for (int i = 0;i < 100000; i++) {
			// Generate random point
			double x = (r.nextDouble() * c.radius * 2) - c.radius + c.x;
			double y = (r.nextDouble() * c.radius * 2) - c.radius + c.y;
			
			// Check if the point's coordinates are in the town circle
			if (!isInCircle(c, x, y))
				continue;
			
			// Find a tower whose coverage includes the point
			for (Circle tower : towers) {
				if (isInCircle(tower, x, y)) {
					count++;
					break;
				}
			}
			total++;
		}
		
		return (count * 1.0) / total;
	}
	
	
	public static boolean isInCircle(Circle c, double x, double y)
	{
		return Math.sqrt(Math.pow(c.x - x, 2) + Math.pow(c.y - y, 2)) <= c.radius;
	}
	
	
	public static class Circle
	{
		public double x;
		public double y;
		public double radius;
		
		public Circle(double x, double y, double radius) {
			this.x = x; this.y = y; this.radius = radius;
		}
	}
}
