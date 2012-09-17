/**
 * Tony Majestro (tmajest)
 * 
 * Program gathers all of the available 'placements' on the board.
 * For each placement, it tries to find the word that fits at that
 * place.  
 * 
 * Only words that could fit in the placement are attempted.
 * 
 * If no word of the correct size can fit in the placement, we backtrack.
 * 
 * Program passes all sets of input in under 3 seconds except for the large
 * input set where it times out.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class Crossword3
{
	private static int trial = 1;
	private static char[][] goalPuzzle = null;
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String[] lineArgs = in.nextLine().split("\\s+");
			int m = Integer.parseInt(lineArgs[0]);
			int n = Integer.parseInt(lineArgs[1]);
			if (m == 0 && n == 0)
				break;
			
			goalPuzzle = null;
			HashMap<Integer, ArrayList<String>> words = new HashMap<Integer, ArrayList<String>>();
			char[][] puzzle = new char[n][];
			
			// Set up words
			for (int i = 0; i < m; i++) {
				String word = in.nextLine();
				int len = word.length();
				if (!words.containsKey(len)) {
					words.put(len, new ArrayList<String>());
				}
				words.get(len).add(word);
			}
			
			// Set up puzzle
			for (int i = 0; i < n; i++) {
				puzzle[i] = in.nextLine().toCharArray();
			}
			
			ArrayList<Placement> placements = getPlacements(puzzle, words, n);
			
			solve(words, new HashSet<String>(), placements, new HashSet<Placement>(), puzzle, m);
			if (goalPuzzle != null) {
				System.out.printf("Problem %d\n", trial);
				printBoard(goalPuzzle);
			}
			else {
				System.out.printf("Problem %d: No layout is possible.\n", trial);
			}
			trial++;
		}
	}
	
	
	public static char[][] solve(HashMap<Integer, ArrayList<String>> words, HashSet<String> usedWords,
			ArrayList<Placement> placements, HashSet<Placement> usedPlaces, char[][] puzzle, int totalWords)
	{
		if (usedWords.size() == totalWords && checkSolution(puzzle)) {
			goalPuzzle = puzzle;
			return puzzle;
		}
		
		for (Placement p : placements) {
			if (usedPlaces.contains(p))
				continue;
	
			boolean foundWord = false;
			for (String word : words.get(p.length)) {
				if (usedWords.contains(word))
					continue;
				
				if (p.canBePlaced(word, puzzle)) {
					char[][] newPuzzle = clonePuzzle(puzzle);
					p.place(word, newPuzzle);
					usedPlaces.add(p);
					usedWords.add(word);
					foundWord = true;
					
					newPuzzle = solve(words, usedWords, placements, usedPlaces, newPuzzle, totalWords);
					if (goalPuzzle != null)
						return newPuzzle;
					
					usedPlaces.remove(p);
					usedWords.remove(word);
				}
			}
			if (!foundWord) {
				return puzzle;
			}
		}
		
		return puzzle;
	}
	
	
	public static char[][] clonePuzzle(char[][] puzzle)
	{
		char[][] newPuzzle = new char[puzzle.length][];
		for (int i = 0; i < puzzle.length; i++) {
			newPuzzle[i] = puzzle[i].clone();
		}
		return newPuzzle;
	}
	
	
	public static boolean checkSolution(char[][] puzzle) {
		if (puzzle == null)
			return false;
		
		int columns = puzzle[0].length;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < columns; j++) {
				if (puzzle[i][j] == '.') {
					return false;
				}
			}
		}
		goalPuzzle = puzzle;
		return true;
	}
	
	public static void printBoard(char[][] puzzle) {
		int columns = puzzle[0].length;
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(puzzle[i][j]);
			}
			System.out.println();
		}
	}
	
	
	public static ArrayList<Placement> getPlacements(char[][] puzzle, HashMap<Integer, ArrayList<String>> words, int rows) 
	{
		ArrayList<Placement> horizontalPlacements = new ArrayList<Placement>();
		ArrayList<Placement> verticalPlacements = new ArrayList<Placement>();
		
		int columns = puzzle[0].length;
		for (int i = 0; i < rows; i++) {
			int column = 0;
			for (int j = 0; j < columns; j++) {
				if (puzzle[i][j] == '#' || j == columns - 1) {
					int len = (j == columns - 1 && puzzle[i][j] == '.') ? j - column + 1 : j - column;
					if (len > 1 && words.containsKey(len)) {
						horizontalPlacements.add(new Placement(i, column, HORIZONTAL, len));
					}
					column = j+1;
				}
			}
		}
		
		for (int j = 0; j < columns; j++) {
			int row = 0;
			for (int i = 0; i < rows; i++) {
				if (puzzle[i][j] == '#' || i == rows - 1) {
					int len = (i == rows - 1 && puzzle[i][j] == '.') ? i - row + 1 : i - row;
					if (len > 1 && words.containsKey(len)) {
						verticalPlacements.add(new Placement(row, j, VERTICAL, len));
					}
					row = i + 1;
				}
			}
		}
		
		horizontalPlacements.addAll(verticalPlacements);
		
		return horizontalPlacements;
	}
	
	
	private static class Placement
	{
		public int row, col;
		public int orientation;
		public int length;
		
		public Placement(int row, int col, int orientation, int length) {
			this.row = row; this.col = col; this.orientation = orientation; this.length = length;
		}
		
		public boolean canBePlaced(String word, char[][] puzzle) {
			if (word.length() != length)
				return false;
			
			int i = row;
			int j = col;
			int count = 0;
			while (count < length) {
				if (puzzle[i][j] != '.') {
					if (puzzle[i][j] != word.charAt(count))
						return false;
				}
				if (orientation == HORIZONTAL) j++;
				else i++;
				count++;
			}
			
			return true;
		}
		
		public void place(String word, char[][] puzzle) {
			int i = row;
			int j = col;
			int count = 0;
			while (count < length) {
				puzzle[i][j] = word.charAt(count);
				if (orientation == HORIZONTAL) j++;
				else i++;
				count++;
			}
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] { row, col, orientation });
		}
		
		@Override
		public boolean equals(Object o) {
			Placement other = (Placement) o;
			return row == other.row && col == other.col && orientation == other.orientation;
		}
		
	}
	
	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;
}






