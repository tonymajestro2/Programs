import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class TrafficLight 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		ArrayList<Light> lights = new ArrayList<Light>();
		
		while (in.hasNextInt()) {
			int cycleTime = in.nextInt();
			if (cycleTime == 0) {
				if (lights.size() == 0) {
					break;
				}
				else {
					solve(lights);
					lights.clear();
				}
			}
			else {
				lights.add(new Light(cycleTime));
			}
		}
	}
	
	public static void solve(ArrayList<Light> lights)
	{
		// Add initial states to queue
		PriorityQueue<Light> queue = new PriorityQueue<Light>(lights);
		boolean changedToYellow = false;
		Light last = null;
		
		// Process queue
		while (true) {
			Light currLight = queue.poll();
			System.out.printf("%d, %d, %d\n", currLight.currTime, currLight.cycleTime, currLight.state);
			
			if (currLight.currTime > 18000) {  // 5 hours
				System.out.println("Signals fail to synchronise in 5 hours");
				return;
			}
			
			if (changedToYellow && allGreen(queue, currLight)) {
				System.out.printf("%d\n", last.currTime);
				return;
			}
			
			currLight.computeNextTime(true);
			changedToYellow = true;
			
			// Add updated light to end of queue
			queue.add(currLight);
			last = currLight;
		}
	}
	
	
	public static boolean allGreen(PriorityQueue<Light> lights, Light currLight) {
		if (currLight.state != GREEN)
			return false;
		
		for (Light l : lights) {
			if (l.state != GREEN)
				return false;
		}
		return true;
	}
	
	
	private static class Light implements Comparable<Light>
	{
		public int currTime;
		public int cycleTime;
		public int state;
		
		public Light(int cycleTime) {
			this.currTime = 0;
			this.cycleTime = cycleTime;
			this.state = GREEN; 
		}
		
		public int computeNextTime(boolean update) {
			int nextTime = -1;
			switch (state) {
				case GREEN: nextTime = currTime + cycleTime - 5; break;
				case YELLOW: nextTime = currTime + 5;  break;
				case RED: nextTime = currTime + cycleTime;  break;
			}
			
			if (update) {
				state = (state + 1) % 3;
				currTime = nextTime;
			}
			return nextTime;
		}

		@Override
		public int compareTo(Light other) {
			if (currTime == other.currTime) {
				return computeNextTime(false) - other.computeNextTime(false);
			}
			return computeNextTime(false) - other.currTime;
			
		}
	}
	
	public static final int GREEN = 0;
	public static final int YELLOW = 1;
	public static final int RED = 2;
}
