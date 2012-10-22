/**
 * Tony Majestro (tmajest)
 * Passes ICPC online judge
 * 
 * Problem 2009:A Euclid 
 * http://midatl.radford.edu/docs/pastProblems/09contest/MidAtlantic2009.pdf
 */

import java.awt.geom.Point2D;
import java.util.Scanner;

public class Euclid 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextDouble()) {
			Point2D.Double a = new Point2D.Double(in.nextDouble(), in.nextDouble());
			Point2D.Double b = new Point2D.Double(in.nextDouble(), in.nextDouble());
			Point2D.Double c = new Point2D.Double(in.nextDouble(), in.nextDouble());
			Point2D.Double d = new Point2D.Double(in.nextDouble(), in.nextDouble());
			Point2D.Double e = new Point2D.Double(in.nextDouble(), in.nextDouble());
			Point2D.Double f = new Point2D.Double(in.nextDouble(), in.nextDouble());
			
			if (a.x == 0 & a.y == 0 && b.x == 0 && b.y == 0 && c.x == 0 && c.y == 0 &&
					d.x == 0 && d.y == 0 && e.x == 0 && e.y == 0 && f.x == 0 && f.y == 0)
				break;
			
			solve(a, b, c, d, e, f);
		}
	}


    // Prints points H and G such that parallelogram abgh has the same area as
    // triangle def
	static void solve(Point2D.Double a, Point2D.Double b, Point2D.Double c,
			Point2D.Double d, Point2D.Double e, Point2D.Double f)
	{
		Vector2D ab = new Vector2D(a, b);
		Vector2D ac = new Vector2D(a, c);
		
		// Calculate length AH using sin(angle CAB) = height(abgh) * length AH
		double areaTriangle = triangle_area(d, e, f);
		double height = areaTriangle / ab.length;
		double cab_angle = ab.angleBetween(ac);
		double AH = height / Math.sin(cab_angle);
		
		// Get Vector ah and point H
		Vector2D ah = ac.resize(AH);
		Point2D.Double h = ah.p1;
		
		// Get Vector hg and point G (hg begins on h and is parallel to Vector ab)
		Vector2D hg = new Vector2D(h, ab);
		Point2D.Double g = hg.p1;
		
		System.out.printf("%.3f %.3f %.3f %.3f\n", g.x, g.y, h.x, h.y);
	}


	static double triangle_area(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
		return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0;
	}


	static class Vector2D
	{
		final Point2D.Double p0;  // start point
		final Point2D.Double p1;  // end point
		final double vx, vy;      // direction vector
		final double length;
	
        // Create a new vector from two Points    
		Vector2D(Point2D.Double start, Point2D.Double end) {
			this.p0 = start;
			this.p1= end;
			this.vx = end.x - start.x;
			this.vy = end.y - start.y;
			this.length = start.distance(end);
		}
        
        // Create a new Vector beginning at the initial point and parallel 
        // to the given Vector
		Vector2D(Point2D.Double start, Vector2D parallelVector) {
			this.p0 = start;
			this.p1 = new Point2D.Double(start.x + parallelVector.vx, start.y + parallelVector.vy);
			this.vx = parallelVector.vx;
			this.vy = parallelVector.vy;
			this.length = p0.distance(p1);
		}
	
        // Returns a new Vector with the same initial point and direction 
        // having been scaled to the specified length    
		Vector2D resize(double newLength) {
			double scale = newLength / length;
			Point2D.Double newP0 = new Point2D.Double(p0.x, p0.y);
			Point2D.Double newP1 = new Point2D.Double(p0.x + vx * scale, p0.y + vy * scale);
			return new Vector2D(newP0, newP1);
		}
	
        // Calculates the angle between two vectors    
		double angleBetween(Vector2D other) {
			double dotProduct = this.dot(other);
			return Math.acos(dotProduct / (length * other.length));
		}
		
		// Calculates the dot products of two vectors
		double dot(Vector2D other) {
			return vx * other.vx + vy * other.vy;
		}
	}
}
