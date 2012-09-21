/**
 * Tony Majestro (tmajest)
 * 
 * Passes ICPC online judge
 */

import java.awt.Point;
import java.io.File;
import java.io.IOException;
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


public class BlockGame2
{
	private static final int BOARD_LEN = 6;
	private static int goalRow;
	private static char goalChar;
	
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			goalChar = in.nextLine().charAt(0);
			if (goalChar == '*')
				break;
			
			char[][] board = new char[BOARD_LEN][];
			for (int i = 0; i < 6; i++) {
				board[i] = in.nextLine().toCharArray();
			}
			
			List<Block> blocks = getBlocks(board);
			for (Block b : blocks) {
				if (b.c == goalChar) {
					goalRow = b.points[0].x;
					break;
				}
			}
			
			solve(new State(blocks, board));
		}
	}
	
	private static void solve(State initState)
	{
		// Check if init state is final state
		if (initState.isFinal()) {
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
				
				//printBoard(child.board);
				//System.out.println();
				
				if (!pred.containsKey(child))
					pred.put(child, s);
				
				if (!dist.containsKey(child)) {
					dist.put(child, n+1);
					bfs.offer(child);
				}
				
				if (child.isFinal()) {
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
					List<Point> points = new ArrayList<Point>();
					do {
						points.add(new Point(i, j));
						j++;
					} while (j < BOARD_LEN && board[i][j] == c);
					if (points.size() > 1) {
						blocks.add(new HorizontalBlock(points, c));
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
					List<Point> points = new ArrayList<Point>();
					do {
						points.add(new Point(i, j));
						i++;
					} while (i < BOARD_LEN && board[i][j] == c);
					if (points.size() > 1) {
						blocks.add(new VerticalBlock(points, c));
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
		
		public boolean isFinal() {
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
				newBlocks.add(b.createBlock(b.points, b.c));
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
	
	
	private static abstract class Block
	{
		Point[] points;
		char c;
		
		public Block(List<Point> points, char c) {
			Point[] newPoints = new Point[points.size()];
			for (int i = 0; i < points.size(); i++) {
				newPoints[i] = points.get(i);
			}
			this.points = newPoints;
			this.c = c;
		}
		
		public Block(Point[] points, char c) {
			this.points = points;
			this.c = c;
		}
		
		
		public Set<Block> getMovements(char[][] board) {
			Set<Block> visited = new HashSet<Block>();
			getMovements(board, this, visited);
			visited.remove(this);
			return visited;
		}
		
		private void getMovements(char[][] board, Block currBlock, Set<Block> visited) 
		{	
			if (visited.contains(currBlock)) {
				return;
			}
			else if (currBlock.outOfBoundsOrCollision(board)) {
				return;
			}
			
			visited.add(currBlock);
			
			Block negative = createBlock(currBlock.getNewPoints(-1), c);
			Block positive = createBlock(currBlock.getNewPoints(1), c);
			
			getMovements(board, negative, visited);
			getMovements(board, positive, visited);
		}
		
		private boolean outOfBoundsOrCollision(char[][] board) {
			for (Point p : points) {
				if (p.x < 0 || p.x >= BOARD_LEN || p.y < 0 || p.y >= BOARD_LEN) {
					return true;
				}
				
				char piece = board[p.x][p.y]; 
				if (piece != '.' && piece != c) {
					return true;
				}
			}
			return false;
		}
		
		public void put(char[][] board, boolean place)
		{
			char piece = (place) ? this.c : '.';
			for (Point p : points) {
				board[p.x][p.y] = piece;
			}
		}
		
		@Override
		public boolean equals(Object o) {
			Block b = (Block) o;
			return c == b.c && Arrays.deepEquals(points, b.points);
		}
		
		@Override
		public int hashCode() {
			return Arrays.deepHashCode(points) * c;
		}
		
		@Override
		public Object clone() {
			Point[] newPoints = new Point[points.length];
			for (int i = 0; i < points.length; i++) {
				Point old = this.points[i];
				newPoints[i] = new Point(old.x, old.y);
			}
			return createBlock(newPoints, c);
		}
		
		abstract Point[] getNewPoints(int direction);
		abstract Block createBlock(Point[] pts, char c);
	}
	
	private static class HorizontalBlock extends Block
	{
		public HorizontalBlock(Point[] points, char c) {
			super(points, c);
		}
		
		public HorizontalBlock(List<Point> points, char c) {
			super(points, c);
		}
		
		@Override
		Point[] getNewPoints(int direction) {
			Point[] newPoints = new Point[points.length];
			for (int i = 0; i < points.length; i++) {
				newPoints[i] = new Point(points[i].x, points[i].y + direction);
			}
			return newPoints;
		}

		@Override
		Block createBlock(Point[] pts, char c) {
			return new HorizontalBlock(pts, c);
		}
	}
	
	private static class VerticalBlock extends Block
	{
		public VerticalBlock(Point[] points, char c) {
			super(points, c);
		}
		
		public VerticalBlock(List<Point> points, char c) {
			super(points, c);
		}
		
		@Override
		Point[] getNewPoints(int direction) {
			Point[] newPoints = new Point[points.length];
			for (int i = 0; i < points.length; i++) {
				newPoints[i] = new Point(points[i].x + direction, points[i].y);
			}
			return newPoints;
		}

		@Override
		Block createBlock(Point[] pts, char c) {
			return new VerticalBlock(pts, c);
		}
	}
	
	private static void printBoard(char[][] board) {
		for (int i = 0; i < BOARD_LEN; i++) {
			for (int j = 0; j < BOARD_LEN; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
}
