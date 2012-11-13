import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Hexagram 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			List<Integer> nums = new ArrayList<Integer>();
			boolean all_zeroes = true;
			for (int i = 0; i < 12; i++) {
				int num = in.nextInt();
				if (num != 0)
					all_zeroes = false;
				nums.add(num);
			}
			
			if (all_zeroes)
				break;
			
			System.out.println(solve(nums, new int[12], 0, -1) / 12);
		}
	}
	
	
	static int solve(List<Integer> nums, int[] placed, int curr, int curr_sum)
	{
		if (curr == 12) {
			//System.out.println(Arrays.toString(placed));
			return 1;
		}
	
		int total = 0;
		for (int i = 0; i < nums.size(); i++) {
			int num = nums.get(i);
			
			// Set first sum
			if (curr == 3) {
				curr_sum = placed[0] + placed[1] + placed[2] + num;
			}
			
			// Check sum at corner points
			else if (curr == 6 || curr == 8 || curr == 10 || curr == 11) {
				if (!correct_sum(placed, curr, num, curr_sum)) {
					continue;
				}
			}
			
			placed[curr] = num;
			List<Integer> copy_nums = new ArrayList<Integer>(nums);
			copy_nums.remove(i);
			total += solve(copy_nums, placed, curr + 1, curr_sum);
		}
		
		return total;
	}
	
	static boolean correct_sum(int[] placed, int curr, int num, int curr_sum)
	{
		switch (curr) {
			case 6:  return placed[3] + placed[4] + placed[5] + num == curr_sum;
			case 8:  return placed[6] + placed[7] + placed[0] + num == curr_sum;
			case 10: return placed[9] + placed[8] + placed[1] + num == curr_sum;
			case 11: return placed[10] + placed[2] + placed[4] + num == curr_sum &&
						placed[9] + placed[7] + placed[5] + num == curr_sum;
			default: return false; 
		}
	}
}
