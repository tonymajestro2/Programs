import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;


public class Compression 
{
	final static String MATCH_AFTER = "(?<=%1$s)";
	
    public static void main(String[] args) throws IOException
    {
    	Scanner in = new Scanner(new File("in.txt"));
    	
    	while (in.hasNextLine()) {
    		String compressed = in.nextLine();
    		solve(compressed);
    	}
    }
    
    
    static void solve(String compressed)
    {
    	Deque<String> tokens = new ArrayDeque<String>();
    	String temp = compressed;
    	for (int i = 0; i < compressed.length() / 2; i++) {
    		tokens.add(temp.substring(0, 2));
    		temp = temp.substring(2);
    	}
    	PictureElement elem = parsePictureElement(tokens);
    	elem.scale();
    	return;
    }
	
	
	static PictureElement parsePictureElement(Deque<String> tokens)
	{
		PictureElement elem = null;
		String next = tokens.poll();
		
		if (next.equals("00")) {
			elem = new Pixel(true);
		}
		else if (next.equals("11")) {
			elem = new Pixel(false);
		}
		else if (next.equals("01")) {
			elem = new VerticalSplit(parsePictureElement(tokens), parsePictureElement(tokens));
		}
		else if (next.equals("10")) {
			elem = new HorizontalSplit(parsePictureElement(tokens), parsePictureElement(tokens));
		}
		
		return elem;
	}
	
	
	static abstract class PictureElement
	{
		Tuple dim;
		abstract void scale();
		abstract Tuple getMultipliers();
	}
	
	
	static class Pixel extends PictureElement
	{
		boolean black;
		
		Pixel(boolean black) 
		{ 
			this.black = black;
			this.dim = new Tuple(1, 1);
		}
		
		@Override
		void scale() {}

		@Override
		Tuple getMultipliers() { return this.dim; }
	}
	
	
	static abstract class Split extends PictureElement
	{
		PictureElement left;
		PictureElement right;
		
		@Override
		void scale() {
			left.scale();
			right.scale();
			
			Tuple leftDim = left.dim;
			Tuple rightDim = right.dim;
			
			Tuple multipliers = getMultipliers();
			int lMult = multipliers.a, rMult = multipliers.b;
			
			leftDim.a *= lMult;
			leftDim.b *= lMult;
			rightDim.a *= rMult;
			rightDim.b *= rMult;
			
			this.dim = new Dimension()
		}
	}
	
	
	static class HorizontalSplit extends Split
	{
		public HorizontalSplit(PictureElement left, PictureElement right) 
		{
			this.left = left;
			this.right = right;
		}
		
		@Override
		Tuple getMultipliers() 
		{
			Tuple leftDim = left.dim;
			Tuple rightDim = right.dim;
			
			int lcm = gcd(leftDim.b, rightDim.b) / (leftDim.b * rightDim.b);
			int lMult = lcm / leftDim.b;
			int rMult = lcm / rightDim.b;
			
			return new Tuple(lMult, rMult);
		}
	}
	
	
	static class VerticalSplit extends Split
	{
		public VerticalSplit(PictureElement left, PictureElement right) 
		{
			this.left = left;
			this.right = right;
		}
		
		@Override
		Tuple getMultipliers() 
		{
			Tuple leftDim = left.dim;
			Tuple rightDim = right.dim;
			
			int lcm = gcd(leftDim.a, rightDim.a) / (leftDim.a * rightDim.a);
			int lMult = lcm / leftDim.a;
			int rMult = lcm / rightDim.a;
			
			return new Tuple(lMult, rMult);
		}
	}
	
	
	static class Tuple
	{
		int a; int b;
		Tuple (int a, int b) { this.a = a; this.b = b; }
	}
	
	static int gcd(int a, int b) 
	{
		if (b == 0)
			return a;
		
		return gcd(b, a % b);
	}
	
}
