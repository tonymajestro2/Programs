/**
 * Tony Majestro (tmajest)
 * Passes ICPC online judge
 */

import java.awt.geom.Point2D;
import java.util.Scanner;


public class LineOfSight 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextDouble()) {
			double w1 = in.nextDouble();
			double h1 = in.nextDouble();
			double w2 = in.nextDouble();
			double h2 = in.nextDouble();
			double x  = in.nextDouble();
			double y  = in.nextDouble();
			if (Double.compare(w1, 0) == 0)
				break;
			
			solve(w1, h1, w2, h2, x, y);
		}
	}
	
	
	static void solve(double w1, double h1, double w2, double h2, double x, double y)
	{
		Point2D.Double factoryBotLeft = new Point2D.Double(0, 0);
		Point2D.Double factoryBotRight = new Point2D.Double(w1, 0);
		Point2D.Double boxTopLeft = new Point2D.Double(x - (w2/2), y + (h2/2));
		Point2D.Double boxTopRight = new Point2D.Double(x + (w2/2), y + (h2/2));
		
		// Find the point of intersection of (factoryLeft, boxLeft) and (factoryRight, boxRight)
		Point2D.Double intersection = 
				intersects(factoryBotLeft, boxTopLeft, factoryBotRight, boxTopRight);
		
		double hiddenArea = triangle_area(intersection, boxTopLeft, boxTopRight);
		
		// Special case where intersection is above factory height;
		// subtract the area of the triangle above the factory height from hidden area
		if (Double.compare(intersection.y, h1) > 0) {
			Point2D.Double factoryTopLeft = new Point2D.Double(0, h1);
			Point2D.Double factoryTopRight = new Point2D.Double(w1, h1);
		
            // Bottom points of triangle above factory    
			Point2D.Double pLeft = 
					intersects(boxTopLeft, intersection, factoryTopLeft, factoryTopRight);
			
			Point2D.Double pRight =
					intersects(boxTopRight, intersection, factoryTopLeft, factoryTopRight);
			
			// Subtract smaller triangle area from larger triangle area
			double smallerTriangleArea = triangle_area(pLeft, intersection, pRight);
			hiddenArea -= smallerTriangleArea;
		}
		
		double openArea = (w1 * h1) - (w2 * h2);
		System.out.printf("%.1f%%\n", hiddenArea / openArea * 100.0);
	}


	static Point2D.Double intersects(Point2D.Double p1, Point2D.Double p2,
			Point2D.Double p3, Point2D.Double p4) 
	{
		double d = det(p1.x - p2.x, p1.y - p2.y, p3.x - p4.x, p3.y - p4.y);
		double x12 = det(p1.x, p1.y, p2.x, p2.y);
		double x34 = det(p3.x, p3.y, p4.x, p4.y);

		double x = det(x12, p1.x-p2.x, x34, p3.x-p4.x) / d;
		double y = det(x12, p1.y-p2.y, x34, p3.y-p4.y) / d;
		return new Point2D.Double(x, y);
	}
	
	
	static double triangle_area(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
		return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0;
	}
   

    static double det(double x1, double y1, double x2, double y2) {
		return x1 * y2 - y1 * x2;
	}
}
