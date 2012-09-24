import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Mobiles 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		List<ASTNode> nodes = new ArrayList<ASTNode>();
		while (in.hasNextLine()) {
			String[] nodeParams = in.nextLine().split("\\s+");
			int num = Integer.parseInt(nodeParams[0]);
			if (num <= 0)
				break;
			
			if (nodeParams[1].equals("B")) {
				nodes.add(e)
			}
			
		}
	}
	
	public static abstract class ASTNode
	{
		double weight;
		abstract void balance(double weight);
	}
	
	public static class Bar extends ASTNode
	{
		double length;
		
		public Bar(double weight, double length) {
			this.weight = weight;
			this.length = length;
		}

		@Override
		void balance(double weight) {
			
		}
	}
	
	public static class Decoration extends ASTNode
	{
		public Decoration(double weight) { this.weight = weight; }

		@Override
		void balance(double weight) {
			// TODO Auto-generated method stub
			
		}
	}
}
