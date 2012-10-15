import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Targets 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			int numTargets = Integer.parseInt(in.nextLine());
			List<int[]> circles = new ArrayList<int[]>();
			List<int[]> rectangles = new ArrayList<int[]>();
			
			for (int i = 0; i < numTargets; i++) {
				String[] target = in.nextLine().split("\\s+");
				if (target[0].equalsIgnoreCase("rectangle")) {
					int x1 = Integer.parseInt(target[1]);
					int y1 = Integer.parseInt(target[2]);
					int x2 = Integer.parseInt(target[3]);
					int y2 = Integer.parseInt(target[4]);
					
					rectangles.add(new int[] {x1, x2, y1, y2});
				}
				else if (target[0].equalsIgnoreCase("circle")) {
					int x = Integer.parseInt(target[1]);
					int y = Integer.parseInt(target[2]);
					int r = Integer.parseInt(target[3]);
					circles.add(new int[] {x, y, r });
				}
			}
			
			int numShots = Integer.parseInt(in.nextLine());
			Point[] points = new Point[numShots];
			
			
			for (int i = 0; i < numShots; i++) {
				int numHits = 0;
				String[] pointStr = in.nextLine().split("\\s+");
				int x = Integer.parseInt(pointStr[0]);
				int y = Integer.parseInt(pointStr[1]);
				Point p = new Point(x, y);
				
				for (int[] rect : rectangles) {
					if (contains(rect[0], rect[1], rect[2], rect[3], p)) {
						numHits++;
					}
				}
				for (int[] circle : circles) {
					if (contains(circle[0], circle[1], circle[2], p)) {
						numHits++;
					}
				}
				System.out.println(numHits);
			}
		}
	}
	
	
	public static boolean contains(int x1, int x2, int y1, int y2, Point p) {
		return (p.x >= x1 && p.x <= x2) && (p.y >= y1 && p.y <= y2);
	}
	
	public static boolean contains(int x, int y, int r, Point p) {
		return ((p.x - x) * (p.x - x)) + ((p.y - y) * (p.y - y)) <= r * r;
	}
}
