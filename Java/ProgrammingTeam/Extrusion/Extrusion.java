/**
 * Tony Majestro (tmajest)
 * Passes ICPC online judge.
 */

import java.awt.geom.Point2D;
import java.util.Scanner;


public class Extrusion 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int n = in.nextInt();
			if (n < 3)
				break;
			
			// Get cross section points
			Point2D.Double[] polygon = new Point2D.Double[n];
			for (int i = 0; i < n; i++) {
				polygon[i] = new Point2D.Double(in.nextDouble(), in.nextDouble());
			}
			
			// Get volume of metal
			double V = in.nextDouble();
			
			// Calculate area of cross section
			double area = areaPolygon(polygon);
			
			System.out.printf("BAR LENGTH: %.2f\n", V / area);
		}
	}
	
	static double areaPolygon(Point2D.Double[] p) 
	{
		double area = 0.0;
		int n = p.length;
		for (int i = 0; i < n; i++) {
			area += p[i].x * p[(i+1) % n].y - p[i].y * p[(i+1) % n].x;
		}
		return Math.abs(area/2.0);
	}
}
