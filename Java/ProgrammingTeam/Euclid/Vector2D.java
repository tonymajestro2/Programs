import java.awt.geom.Point2D;

public class Vector2D 
{
	final Point2D.Double p0; // start point
	final Point2D.Double p1; // end point
	final double vx, vy; // direction vector
	final double length;

	// Create a new vector from two Points
	Vector2D(Point2D.Double start, Point2D.Double end) {
		this.p0 = start;
		this.p1 = end;
		this.vx = end.x - start.x;
		this.vy = end.y - start.y;
		this.length = start.distance(end);
	}

	// Create a new Vector beginning at the initial point and parallel
	// to the given Vector
	Vector2D(Point2D.Double start, Vector2D parallelVector) {
		this.p0 = start;
		this.p1 = new Point2D.Double(start.x + parallelVector.vx, start.y
				+ parallelVector.vy);
		this.vx = parallelVector.vx;
		this.vy = parallelVector.vy;
		this.length = p0.distance(p1);
	}

	// Returns a new Vector with the same initial point and direction
	// having been scaled to the specified length
	Vector2D resize(double newLength) {
		double scale = newLength / length;
		Point2D.Double newP0 = new Point2D.Double(p0.x, p0.y);
		Point2D.Double newP1 = new Point2D.Double(p0.x + vx * scale, p0.y + vy
				* scale);
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
