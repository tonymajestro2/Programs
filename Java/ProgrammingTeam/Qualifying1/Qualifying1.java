import java.util.Scanner;


public class Qualifying1 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.toLowerCase().contains("problem")) {
				System.out.println("yes");
			}
			else {
				System.out.println("no");
			}
		}
	}
}
