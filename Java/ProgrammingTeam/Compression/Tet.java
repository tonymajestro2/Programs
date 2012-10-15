import java.util.Scanner;


public class Tet {
	public static void main(String[] args) {
		
		String MATCH_AFTER = "(?<=%1$s)";
		String MATCH_BEFORE = "(?=%1$s)";
		String s = "abcdef";
		String delim = String.format(MATCH_BEFORE, "..");
		Scanner in = new Scanner(s).useDelimiter(delim);
		
		while (in.hasNext()) {
			System.out.println(in.next());
		}
		
	}
}
