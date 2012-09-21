/**
 * Tony Majestro (tmajest)
 * Passes IMPC online judge
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Witness 
{
	private final static String MATCH_AFTER = "(?<=%1$s)";
	
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		Set<String> wordList = new HashSet<String>();
		StringBuilder message = new StringBuilder();     
		boolean readingWordList = true;
		
		while (in.hasNextLine()) {
			String line = in.nextLine();
			
			// Check of end of list, message, or input
			if (line.equals("EndOfList")) {
				readingWordList = false;
				continue;
			}
			else if (line.equals("EndOfMsg")) {
				solve(wordList, message.toString());
				message = new StringBuilder();
				wordList.clear();
				readingWordList = true;
				continue;
			}
			else if (line.equals("EndOfInput")) {
				break;
			}
			
			// Add current line to word list or message
			if (readingWordList) {
				wordList.add(line.trim().toLowerCase());
			}
			else {
				message.append(line);
				message.append('\n');
			}
		}
		
		in.close();
	}
	
	
	private static void solve(Set<String> wordList, String message)
	{
		// Preserve punctuation after the sentence
		String delim = String.format(MATCH_AFTER, "[\\.!?]|\n\n");
		String[] sentences = message.split(delim);
		
		// Print each sentence, unless it contains one of the sensitive
		// words. If so, print the sentence replaced by @'s.
		sentenceLoop: 
		for (String sentence : sentences) {
			for (String word : sentence.split("\\W")) {
				if (wordList.contains(word.toLowerCase())) {
					System.out.print(sentence.replaceAll("[^\n]", "@"));
					continue sentenceLoop;
				}
			}
			
			System.out.print(sentence);
		}
		
		System.out.println("====");
	}
}
