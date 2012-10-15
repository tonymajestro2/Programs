/**
 * Tony Majestro (tmajest)
 * 
 * I was annoyed that the judge data included mobile specifications
 * that did not have a decoration with an unknown weight when the 
 * problem clearly stated that exactly one decoration per spec will
 * have an unknown weight.  
 * 
 * My solution passes all cases for judge input data 0 and 1 except
 * for the case where there are no unknowns and the mobile is balanced.
 * I didn't know which decoration's weight to report - the one that the
 * judge chose to output seemed arbitrary.
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Mobiles 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		List<ASTNode> nodes = new ArrayList<ASTNode>();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] nodeParams = line.split("\\s+");
			
			int num = Integer.parseInt(nodeParams[0]);
			if (num <= 0) {
				Bar top = assembleMobile(nodes);
				solve(top);
				nodes.clear();
				
				if (num < 0) 
					break;
			}
			
			else if (nodeParams[1].equals("B")) {
				double length = Double.parseDouble(nodeParams[2]);
				int leftIndex = Integer.parseInt(nodeParams[3]);
				int rightIndex = Integer.parseInt(nodeParams[4]);
				nodes.add(new Bar(num, length, leftIndex, rightIndex));
			}
			else { // Decoration
				double weight;
				if (nodeParams[2].equals("X")) {
					weight = -1;
				}
				else {
					weight = Double.parseDouble(nodeParams[2]);
				}
				nodes.add(new Decoration(num, weight));
			}
		}
		
		in.close();
	}
	
	
	private static Bar assembleMobile(List<ASTNode> nodes)
	{
		Set<Integer> availableNodes = new HashSet<Integer>();
		for (int i = 1; i <= nodes.size(); i++) {
			availableNodes.add(i);
		}
		
		for (ASTNode node : nodes) {
			if (node instanceof Bar) {
				Bar b = (Bar) node;
				b.left = nodes.get(b.leftIndex-1);
				b.right = nodes.get(b.rightIndex-1);
				availableNodes.remove(b.leftIndex);
				availableNodes.remove(b.rightIndex);
			}
		}
		
		return (Bar) nodes.get(availableNodes.iterator().next() - 1);
	}
	
	private static void solve(Bar top)
	{
		double leftWeight = top.left.getWeight();
		double rightWeight = top.right.getWeight();
		if (leftWeight > 0 && rightWeight > 0) {
			if (!top.balanced()) {
				// ...
			}
		}
		else {
			ASTNode known = leftWeight > 0 ? top.left : top.right;
			ASTNode unknown = known == top.left ? top.right : top.left;
			if (known.balanced()) {
				if (unknown.balance(known.getWeight())) {
					if (top.canSwingFreely()) {
						System.out.println("The mobile will swing freely.");
					}
					else {
						System.out.println("The mobile will not swing freely.");
					}
				}
			}
		}
	}
	
	
	public static abstract class ASTNode
	{
		int num;
		double weight;
		double length;
		abstract boolean balanced();
		abstract boolean balance(double weight);
		abstract boolean canSwingFreely();
		abstract double getWeight();
		abstract double getLength();
	}
	
	public static class Bar extends ASTNode
	{
		ASTNode left;
		ASTNode right;
		int num;
		int leftIndex;
		int rightIndex;
		
		public Bar(int num, double length, int leftIndex, int rightIndex) {
			this.num = num;
			this.length = length;
			this.leftIndex = leftIndex;
			this.rightIndex = rightIndex;
		}

		@Override
		boolean balance(double weight) {
			ASTNode known = left.getWeight() > 0 ? left : right;
			ASTNode unknown = known == left ? right : left;
			return unknown.balance(weight - known.getWeight());
		}

		@Override
		double getWeight() {
			double leftWeight = left.getWeight();
			double rightWeight = right.getWeight();
			if (leftWeight < 0 || rightWeight < 0) {
				return weight = -1;
			}
			return weight = leftWeight + rightWeight;
		}

		@Override
		boolean canSwingFreely() {
			return Double.compare(length, (left.getLength() + right.getLength()) / 2) > 0 &&
					left.canSwingFreely() && right.canSwingFreely();
		}

		@Override
		boolean balanced() {
			if (left.balanced() && right.balanced()) {
				if (left.getWeight() != right.getWeight()) {
					System.out.println("The mobile cannot be balanced.");
					return false;
				}
				return true;
			}
			
			System.out.println("The mobile cannot be balanced.");
			return false;
		}

		@Override
		double getLength() {
			return length + ((left.getLength() + right.getLength()) / 2);
		}
	}
	
	public static class Decoration extends ASTNode
	{
		public Decoration(int num, double weight) { 
			this.num = num;
			this.weight = weight; 
			this.length = 0;
		}

		@Override
		boolean balance(double weight) {
			if (Double.compare(weight, 0) <= 0) {
				System.out.println("The mobile cannot be balanced.");
				return false;
			}
			
			System.out.printf("Object %d must have weight %.2f\n", num, weight);
			return true;
		}

		@Override
		double getWeight() { 
			return weight; 
		}

		@Override
		boolean canSwingFreely() {
			return true;
		}

		@Override
		boolean balanced() {
			return true;
		}

		@Override
		double getLength() {
			return 0;
		}
	}
}
