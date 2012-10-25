/**
 * Recursive implementation of Knapsack 0/1 problem
 *
 * @author Godmar Back
 */
import java.util.*;

public class KnapsackArray {
    static class Item {
        int weight;
        int value;
        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
        @Override
        public String toString() { return String.format("(%d:%d)", weight, value); }
    }

    /**
     * Subproblem reduction:.
     *
     * The optimal way of packing a knapsack of capacity 'capacity'
     * using only items i_k ... i_n.  (k == nextItem)
     * is the better of including item i_k in the knapsack or not.
     */
    static int knapsack(int capacity, int nextItem, Item [] items) {
        // System.out.println(capacity + " " + nextItem);
        int maxCap = 0;
        if (nextItem < items.length) {
            Item item = items[nextItem];

            maxCap = knapsack(capacity, nextItem+1, items);
            if (item.weight <= capacity) {
                maxCap = Math.max(maxCap, item.value + knapsack(capacity - item.weight, nextItem+1, items));
            }
        }
        return maxCap;
    }

    public static void main(String []av) {
        Scanner s = new Scanner(System.in);
        while (s.hasNextInt()) {
            int C = s.nextInt();
            int N = s.nextInt();
            Item [] items = new Item[N];
            for (int i = 0; i < N; i++)
                items[i] = new Item(s.nextInt(), s.nextInt());
            
            System.out.println(knapsack(C, 0, items));
            /*
            for (int i = -20; i < 20; i++)
                System.out.printf("%d:%s%n", C+i, knapsack(C+i, 0, items));
            */
        }
    }
}