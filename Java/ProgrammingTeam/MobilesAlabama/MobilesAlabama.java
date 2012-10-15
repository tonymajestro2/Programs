/**
 * Passes ICPC online judge
 */


import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;


public class MobilesAlabama 
{
    final static String MATCH_BEFORE_OR_AFTER = "((?<=%1$s)|(?=%1$s))";
    
    final static String delim = "\\p{javaWhitespace}+|" + 
    		String.format(MATCH_BEFORE_OR_AFTER, "[\\(\\)]");
    
    
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(System.in).useDelimiter(delim);
		Deque<String> tokens = new ArrayDeque<String>();
		int pCount = 0;
		
		while (in.hasNext()) {
			String nextToken = in.next();
			
			// Keep count of parentheses.  If count is 0, we have
			// closed all parantheses and can solve the problem
			if (nextToken.equals("("))
				pCount++;
			else if (nextToken.equals(")"))
				pCount--;
			
			tokens.add(nextToken);
			
			if (pCount == 0) {
				if (tokens.size() == 2)
					break;
				
				solve(tokens);
				tokens.clear();
			}
		}
	}
	
    /** Balance top bar to recursively balance remaining bars */
    private static void solve(Deque<String> tokens)
    {
    	tokens.poll();  // Consume (
    	List<Bar> bars = new ArrayList<Bar>();
    	Bar topBar = new Bar(tokens);
    	topBar.balance(bars);
    	
    	Collections.sort(bars);
    	
    	for (Bar b : bars) {
    		System.out.printf("Bar %d must be tied %.1f from one end.\n", b.num, b.balancedLength);
    	}
    }
    
    /**
     * Each ASTNode has a weight and can balance itself.
     * 
     * Each ASTNode is responsible for parsing itself and consuming
     * its trailing parenthesis.  The ASTNode's parent will consume
     * the child's leading parenthesis.
     */
    private static abstract class ASTNode 
    {
    	double weight;
    	abstract void balance(List<Bar> bars);
    	abstract double getWeight();
    }
    
    
    private static class Bar extends ASTNode implements Comparable<Bar>
    {
    	private int num;
		private double length;
		private double balancedLength;
    	private ASTNode left;
    	private ASTNode right;
    	
		public Bar(Deque<String> tokens) {
			tokens.poll();	// Consume 'B'
			num = Integer.parseInt(tokens.poll());
			length = Double.parseDouble(tokens.poll());
			
			tokens.poll();  // Consume (
			left = (tokens.peek().equals("B")) ? new Bar(tokens) : new Decoration(tokens);
			
			tokens.poll();  // Consume (
			right = (tokens.peek().equals("B")) ? new Bar(tokens) : new Decoration(tokens);
			
			tokens.poll();  // Consume )
		}
    
    	@Override
		void balance(List<Bar> bars) {
    		left.balance(bars);
    		right.balance(bars);
    		
			double leftWeight = left.getWeight();
			double rightWeight = right.getWeight();
			
			double leftLen = (rightWeight * length) / (leftWeight + rightWeight);
			double rightLen = length - leftLen;
			balancedLength = Math.min(leftLen,  rightLen);
			bars.add(this);
		}

		@Override
		double getWeight() {
			return weight = left.getWeight() + right.getWeight();
		}

		@Override
		public int compareTo(Bar other) {
			return num - other.num;
		}
    }
    
    
    private static class Decoration extends ASTNode
    {
    	public Decoration(Deque<String> tokens) {
    		tokens.poll();  // Consume 'D'
    		this.weight = Double.parseDouble(tokens.poll());
    		tokens.poll();  // Consume )
    	}

    	/** Do nothing to balance a Decoration */
		@Override
		void balance(List<Bar> sortedBars) {}

		@Override
		double getWeight() {
			return weight;
		}
    }    
}
