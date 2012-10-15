import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Babelfish 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		Map<String, String> dict = new HashMap<String, String>();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.length() == 0) {
				break;
			}
			
			String[] words = line.split("\\s+");
			dict.put(words[1].trim(), words[0].trim());
		}
		
		while (in.hasNextLine()) {
			String key = in.nextLine().trim();
			if (dict.containsKey(key)) {
				System.out.println(dict.get(key));
			}
			else {
				System.out.println("eh");
			}
		}
	}
}
