/**
 * Tony Majestro (tmajest)
 * Passes IMPC online judge
 */

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;


public class AbstractExtract 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			if (line.equals("***")) {
				solve(sb.toString());
				sb = new StringBuilder();
				continue;
			}
			else if (line.equals("******")) {
				solve(sb.toString());
				break;
			}
			
			sb.append(line);
			sb.append("\n");
		}
		in.close();
	}
	
	public static void solve(String article) 
	{
		StringBuffer sb = new StringBuffer();
		String[] paragraphs = article.split("\n\n");
		
		for (String paragraph : paragraphs) {
			String delim = "(?<=[!.?])\\s*";
			String[] sentences = paragraph.split(delim);
			String topicSentence = getTopicSentence(sentences);
			if (topicSentence != null) {
				sb.append(topicSentence);
				sb.append('\n');
			}
		}
		
		// Print newline if no topic sentence was found
		if (sb.length() > 0) {
			System.out.println(sb.substring(0, sb.length()-1));
		}
		else {
			System.out.println();
		}
		
		System.out.println("======");
	}
	
	private static String getTopicSentence(String[] sentences)
	{
		int sentenceCount = 0;
		Pattern punct = Pattern.compile("[!.?]");
		String maxSentence = null;
		int maxDistinct = 0;
		
		for (int i = 0; i < sentences.length; i++) {
			// Ignore sentences without punctuation at the end
			String sentence = sentences[i];
			if (!punct.matcher(sentence).find()) {
				continue;
			}
			
			int distinctCount = getDistinctWords(i, sentences);
			if (distinctCount > maxDistinct || maxSentence == null) {
				maxSentence = sentence;
				maxDistinct = distinctCount;
			}
			
			sentenceCount++;
		}
		
		return (sentenceCount < 3) ? null : maxSentence;
	}
	
	/** 
	 * Get the number of distinct words in the sentence that also appear in
	 * following sentences.
	 */
	private static int getDistinctWords(int sentencePos, String[] sentences) {
		Set<String> distinctWords = new HashSet<String>();
		String[] words = sentences[sentencePos].toLowerCase().split("\\p{Space}");
		Pattern punct = Pattern.compile("[!.?]");
		Pattern non_word = Pattern.compile("\\W");
		
		// Get distinct words in the sentence
		for (String word : words) {
			// Ignore punctuation and non-alphabetic characters
			word = punct.matcher(word).replaceAll("");
			word = non_word.matcher(word).replaceAll("");
			if (word.length() == 0) {
				continue;
			}
			
			if (!distinctWords.contains(word)) {
				distinctWords.add(word);
			}
		}
		
		// Count number of distinct words appearing in following sentences
		int count = 0;
		for (String distinctWord : distinctWords) {
			// Find a following sentence containing the distinct word
			findWordLoop:
			for (int i = sentencePos + 1; i < sentences.length; i++) {
				String nextSentence = sentences[i];
				String[] nextWords = nextSentence.toLowerCase().split("\\p{Space}");
			
				for (String nextWord : nextWords) {
					nextWord = punct.matcher(nextWord).replaceAll("");
					if (nextWord.equals(distinctWord)) {
						count++;
						break findWordLoop;
					}
				}
			}
		}
		
		return count;
	}
}