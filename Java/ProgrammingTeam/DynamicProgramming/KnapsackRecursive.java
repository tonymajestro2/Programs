import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class KnapsackRecursive 
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int c = in.nextInt();
			int n = in.nextInt();
			Set<Item> items = new HashSet<Item>(n);
			for (int i = 0; i < n; i++) {
				items.add(new Item(in.nextInt(), in.nextInt()));
			}
			
			System.out.println(knapsack(c, items));
		}
	}
	
	
	static int knapsack(int capacity, Set<Item> items) 
	{
		System.out.println(capacity + " " + items);
		int max = 0;
		for (Item item : items) {
			if (item.weight <= capacity) {
				Set<Item> newItems = new HashSet<Item>(items);
				newItems.remove(item);
				max = Math.max(max, item.value + knapsack(capacity - item.value, newItems));
			}
		}
		return max;
	}
	
	
	static class Item
	{
		int weight;
		int value;
		
		Item(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
		
		@Override
		public String toString() { 
			return String.format("(%d:%d)", weight, value); 
		}
	}
	
	
}
