import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class KnapsackBottomUp
{
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			int c = in.nextInt();
			int n = in.nextInt();
			Item [] items = new Item[n];
            for (int i = 0; i < n; i++)
                items[i] = new Item(in.nextInt(), in.nextInt());
			
			System.out.println(knapsack(c, items));
		}
	}
	
	
	static int knapsack(int capacity, Item[] items) 
	{
		int[][] best = new int[capacity+1][items.length+1];
		
		for (int c = 0; c <= capacity; c++) {
            for (int i = 0; i < items.length; i++) {
            	int w = items[i].weight;
                if (items[i].weight <= c) {
                    best[c][i+1] = Math.max(best[c][i], best[c-items[i].weight][i] + items[i].value);
                } else {
                    best[c][i+1] = best[c][i];
                }
            }
            
            printArray(best);     
        }
		
		return best[capacity][items.length];
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

    static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(Arrays.toString(array[i]));
        }    
        System.out.println();
    }
	
}
