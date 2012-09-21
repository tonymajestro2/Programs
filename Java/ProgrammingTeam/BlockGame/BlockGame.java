/**
 * Tony Majestro (tmajest)
 * 
 * Passes IMPC online judge
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class BlockGame 
{
	public static final int BOARD_LEN = 6;
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			char goalChar = in.nextLine().charAt(0);
			if (goalChar == '*')
				break;
			
			char[][] board = new char[BOARD_LEN][];
			for (int i = 0; i < 6; i++) {
				board[i] = in.nextLine().toCharArray();
			}
			
			Block goalBlock = null;
			List<Block> blocks = getBlocks(board);
			for (Block b : blocks) {
				if (b.c == goalChar) {
					goalBlock = b;
					break;
				}
			}
			
			solve(new State(blocks, board), goalBlock);
		}
	}
	
	private static void solve(State initState, Block goal)
	{
		// Check if init state is final state
		if (initState.isFinal(goal.row, goal.c)) {
			System.out.println(1);
			return;
		}
		
		Set<State> visited = new HashSet<State>();
		Map<State, State> pred = new HashMap<State, State>();
		Map<State, Integer> dist = new HashMap<State, Integer>();
		Deque<State> bfs = new ArrayDeque<State>();
		
		bfs.offer(initState);
		dist.put(initState, 0);
			
		while (bfs.size() > 0) {
			State s = bfs.poll();
			int n = dist.get(s);
			visited.add(s);
			
			for (State child : s.getChildren()) {
				if (visited.contains(child))
					continue;
				
				if (!pred.containsKey(child))
					pred.put(child, s);
				
				if (!dist.containsKey(child)) {
					dist.put(child, n+1);
					bfs.offer(child);
				}
				
				if (child.isFinal(goal.row, goal.c)) {
					System.out.println(dist.get(child));
					return;
				}
			}
		}
		System.out.println("-1");
	}
	
	private static List<Block> getBlocks(char[][] board)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		int i = 0;
		int j = 0;
		
		// Get horizontal blocks
		while (i < BOARD_LEN) {
			while (j < BOARD_LEN) {
				char c = board[i][j];
				if (c != '.') {
					int len = 0;
					do {
						len++;
						j++;
					} while (j < BOARD_LEN && board[i][j] == c);
					if (len > 1) {
						blocks.add(new Block(i, j - len, len, HORIZONTAL, c));
					}
				}
				else {
					j++;
				}
			}
			j = 0;
			i++;
		}
		
		// Get vertical blocks
		i = j = 0;
		while (j < BOARD_LEN) {
			while (i < BOARD_LEN) {
				char c = board[i][j];
				if (c != '.') {
					int len = 0;
					do {
						len++;
						i++;
					} while (i < BOARD_LEN && board[i][j] == c);
					if (len > 1) {
						blocks.add(new Block(i - len, j, len, VERTICAL, c));
					}
				}
				else {
					i++;
				}
			}
			i = 0;
			j++;
		}
		
		return blocks;
	}
	
	
	private static class State
	{
		List<Block> blocks;
		char[][] board;
		
		public State(List<Block> blocks, char[][] board) {
			this.blocks = blocks; this.board = board;
		}
		
		// Clone a State using an updated block;
		// Update the block list and board to reflect
		// the new block's location
		public State(State s, Block oldBlock, Block newBlock) {
			this(s.deepCopyBlocks(), s.deepCopyBoard());
			blocks.remove(oldBlock);
			blocks.add(newBlock);
			oldBlock.put(board, false);
			newBlock.put(board, true);
			
		}
		
		public boolean isFinal(int goalRow, int goalChar) {
			return board[goalRow][BOARD_LEN-1] == goalChar;
		}
		
		public List<State> getChildren() {
			List<State> children = new ArrayList<State>();
			for (Block b : blocks) {
				for (Block newBlock : b.getMovements(board)) {
					children.add(new State(this, b, newBlock));
				}
			}
			return children;
		}
		
		@Override
		public int hashCode() {
			return Arrays.deepHashCode(board);
		}
		
		@Override
		public boolean equals(Object o) {
			return Arrays.deepEquals(board, ((State)o).board);
		}
		
		private List<Block> deepCopyBlocks() {
			List<Block> newBlocks = new ArrayList<Block>();
			for (Block b : blocks) {
				newBlocks.add(new Block(b.row, b.col, b.len, b.orientation, b.c));
			}
			return newBlocks;
		}
		
		private char[][] deepCopyBoard() {
			char[][] newBoard = new char[BOARD_LEN][];
			for (int i = 0; i < BOARD_LEN; i++) {
				newBoard[i] = board[i].clone();
			}
			return newBoard;
		}
	}
	
	
	private static class Block
	{
		int row;
		int col;
		int len;
		int orientation;
		char c;
		
		public Block(int row, int col, int len, int o, char c) {
			this.row = row; this.col = col; this.len = len; 
			this.orientation = o;this.c = c;
		}
		
		public List<Block> getMovements(char[][] board) {
			List<Block> blocks = new ArrayList<Block>();
			int xs = 1, ys = 0;
			if (orientation == HORIZONTAL) {
				xs = 0;
				ys = 1;
			}
			
			// Get all negative movements (left or up)
			int i = row - xs, j = col - ys;
			while (i >= 0 && j >= 0) {
				// Check for block collisions
				if (board[i][j] != '.') {
					break;
				}
				
				blocks.add(new Block(i, j, len, orientation, c));
				i -= xs;
				j -= ys;
			}
		
			i = row + xs;
			j = col + ys;
			
			// Get all positive movements (right or down)
			while (true) {
				// Get boundary position of block
				int xBounds = i + len - 1, yBounds = j;
				if (orientation == HORIZONTAL) {
					xBounds = i;
					yBounds = j + len - 1;
				}
				
				// Check for out of bounds or block collisions
				if (xBounds >= BOARD_LEN || yBounds >= BOARD_LEN ||
						board[xBounds][yBounds] != '.') {	
					break;
				}
				
				blocks.add(new Block(i, j, len, orientation, c));
				i += xs;
				j += ys;
			}
			
			return blocks;
		}
		
		// For the given block's position and length, place
		// or unplace it on the board
		public void put(char[][] board, boolean place) {
			char c = (place) ? this.c : '.';
			int x = row, y = col, count = 0;
			int xs = 1, ys = 0;
			if (orientation == HORIZONTAL) {
				xs = 0; 
				ys = 1;
			}
			while (count++ < len) {
				board[x][y] = c;
				x += xs;
				y += ys;
			}
		}
		
		@Override
		public boolean equals(Object o) {
			return c == ((Block)o).c;
		}
	}
}
