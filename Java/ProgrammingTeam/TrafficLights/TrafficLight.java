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
		
		// Process queue
		while (true) {
			Light currLight = queue.poll();
			currLight.computeNextTime();
			
			if (currLight.currTime > 18000) {  // 5 hours
				System.out.println("Signals fail to synchronise in 5 hours");
				return;
			}
			
			if (changedToYellow && allGreen(queue, currLight)) {
				System.out.printf("%s\n", formatTime(currLight.currTime));
				return;
			}
			
			if (currLight.currState == YELLOW)
				changedToYellow = true;
			
			// Add updated light to end of queue
			queue.add(currLight);
		}
	}
	
	
	public static String formatTime(int seconds)
	{
		int minutes = seconds / 60;
		seconds %= seconds % 60;
		int hours = minutes / 60;
		minutes %= 60;
		
		return String.format("20d:20d:20", hours, minutes, seconds);
	}
	
	
	public static boolean allGreen(PriorityQueue<Light> lights, Light currLight) {
		if (currLight.currState != GREEN)
			return false;
		
		for (Light l : lights) {
			if (l.currState != GREEN)
				return false;
		}
		return true;
	}
	
	
	private static class Light implements Comparable<Light>
	{
		public int currTime;
		public int nextTime;
		public int currState;
		public int nextState;
		
		public int cycleTime;
		
		public Light(int cycleTime) {
			this.currTime = this.nextTime = 0;
			this.cycleTime = cycleTime;
			this.currState = GREEN;
			this.nextState = GREEN;
		}
		
		public void computeNextTime() {
			currTime = nextTime;
			
			switch (nextState) {
				case GREEN: nextTime = currTime + cycleTime - 5; break;
				case YELLOW: nextTime = currTime + 5;  break;
				case RED: nextTime = currTime + cycleTime;  break;
			}
			currState = nextState;
			nextState = (nextState+ 1) % 3;
		}

		@Override
		public int compareTo(Light other) {
			if (nextTime == other.nextTime) {
				return cycleTime - other.cycleTime;
			}
			return nextTime - other.nextTime;
		}
		
		@Override
		public boolean equals(Object o)
		{
			Light other = (Light) o;
			return currTime == other.currTime && nextTime == other.nextTime &&
					currState == other.currState && nextState == other.nextState;
		}
	}
	
	public static final int GREEN = 0;
	public static final int YELLOW = 1;
	public static final int RED = 2;
}
