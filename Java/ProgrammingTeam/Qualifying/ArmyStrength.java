import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class ArmyStrength 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int numCases = in.nextInt();
		for (int i = 0; i < numCases; i++) {
			int gArmySize = in.nextInt();
			int mArmySize = in.nextInt();
			Queue<Integer> gArmy = new PriorityQueue<Integer>();
			Queue<Integer> mArmy = new PriorityQueue<Integer>();
			
			for (int j = 0; j < gArmySize; j++) {
				gArmy.add(in.nextInt());
			}
			
			for (int j = 0; j < mArmySize; j++) {
				mArmy.add(in.nextInt());
			}
			
			solve(gArmy, mArmy);
		}
	}
	
	public static void solve(Queue<Integer> gArmy, Queue<Integer> mArmy)
	{
		while (gArmy.size() > 0 && mArmy.size() > 0) {
			int gWeak = gArmy.peek();
			int mWeak = mArmy.peek();
			
			if (gWeak < mWeak) {
				gArmy.poll();
			}
			else {
				mArmy.poll();
			}
		}
		
		if (mArmy.isEmpty()) {
			System.out.println("Godzilla");
		}
		else {
			System.out.println("MechaGodzilla");
		}
	}
}
