/**
 * Tony Majestro (tmajest)
 * 
 * Passes test0, test1, and test2.
 * There were no solutions for test3 posted, so I'm not sure
 * if test3 is correct.
 * 
 * I doubt it passes ICPC because test3 takes longer than 3 seconds
 * to solve.  My attempts at doing dynamic programming failed to 
 * generate correct solutions.
 */

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;


public class WordStack
{
	public static void main(String[] args) throws IOException
	{	
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			int numWords = Integer.parseInt(in.nextLine());
			if (numWords == 0)
				break;
			
			String[] words = new String[numWords];
			for (int i = 0; i < numWords; i++) {
				words[i] = in.nextLine();
			}
			
			MaxWord max = solve(words, new HashSet<Integer>(), new MaxWord("", 0, 0));
			System.out.println(max.score);
		}
	}
	
	/** 
	 *  Get the word that yields the maximum score for the current word stack. 
     */
	public static MaxWord solve(String[] words, HashSet<Integer> wordStack, 
            MaxWord prevMax)
	{
		if (words.length == wordStack.size()) {
			return prevMax;
		}
		
		MaxWord max = null;
		for (int i = 0; i < words.length; i++) {
			// Skip words already used in the stack
			String currWord = words[i];
			if (wordStack.contains(i))
				continue;
		
			wordStack.add(i);
			
			// Get max orientation for current word given the previous word
			MaxWord maxOrientation = getMaxOrientation(currWord, prevMax);
			
			// Get max score for current word's max orientation
			MaxWord currMaxWord = solve(words, wordStack, maxOrientation);
			 
			if (max == null || (currMaxWord.score > max.score)) {
				max = currMaxWord;
			}
			
			wordStack.remove(i);
		}
		
		max.score += prevMax.score;
		return max;
	}
	
	
	public static MaxWord getMaxOrientation(String word, MaxWord prevWord) 
	{
		if (prevWord.word.length() == 0) {
			return new MaxWord(word, 0, 0);
		}
		
		String prevStr = prevWord.word;
		int spaces = prevWord.spaces;
		int maxScore = -1;
		int maxSpaces = 0;
		
		// i = number of spaces in front of current word
		for (int i = 0; i < prevStr.length() + spaces; i++) {
			int count = 0;
			for (int j = i; j < prevStr.length() + spaces; j++) {
				// prevStr out of bounds
				if (j - spaces < 0)  
					continue;
				// currWord out of bounds
				if (j >= prevStr.length() + spaces || j >= word.length() + i)
					continue;
				
				if (prevStr.charAt(j - spaces) == word.charAt(j - i)) {
					maxSpaces = i;
					count++;
				}
			}
			
			if (maxScore == -1 || count > maxScore)
				maxScore = count;
		}
		
		return new MaxWord(word, maxSpaces, maxScore);
	}
	
	
	public static class MaxWord
	{
		public String word;
		public int spaces;
		public int score;
		
		public MaxWord(String word, int spaces, int score) {
			this.word = word; this.spaces = spaces; this.score = score;
		}
	}
}
