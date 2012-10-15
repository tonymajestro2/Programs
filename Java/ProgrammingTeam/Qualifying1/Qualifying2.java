import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Qualifying2 
{
	public static void main(String[] args)
	{ 
		Scanner in = new Scanner(System.in);
		ArrayList<String> lines = new ArrayList<String>();
		int maxLength = -1;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.length() > maxLength) {
				maxLength = line.length();
			}
			lines.add(line);
		}
		
		int rag = 0;
		for (int i = 0; i < lines.size() - 1; i++) {
			int len = lines.get(i).length();
			rag += (maxLength - len) * (maxLength - len);
		}
		
		System.out.println(rag);
		
	}
}
