import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class KnapsackRecursiveWithBag
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
			
			Set<Item> bag = new HashSet<Item>();
			System.out.println(knapsack(c, items, bag));
			System.out.println(bag);
		}
	}
	
	
	static int knapsack(int capacity, Set<Item> items, Set<Item> bag) 
	{
		//System.out.println(capacity + " " + items);
		int max = 0;
		Set<Item> bestBag = null;
		for (Item item : items) {
			if (item.weight <= capacity) {
				Set<Item> newItems = new HashSet<Item>(items);
				Set<Item> newBag = new HashSet<Item>();
				newItems.remove(item);
				
				int currMax = item.value + knapsack(capacity - item.value, newItems, newBag);
				if (currMax > max) {
					max = currMax;
					newBag.add(item);
					bestBag = newBag;
				}
			}
		}
		
		if (bestBag != null) {
			bag.addAll(bestBag);
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
