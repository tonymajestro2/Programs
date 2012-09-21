import java.awt.Point;
import java.util.*;

public class Quzzle 
{
	private static final int BOARD_WIDTH = 4;
	private static final int BOARD_LENGTH = 5;
	private static final int NUM_BLOCKS = 9;
	
	public static void main(String[] args)
	{
		Block[] blocks = getBlocks();
		char[][] board = getBoard();
		solve(new State(blocks, board));
	}
	
	private static void solve(State initState)
	{	
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
				
				if (child.isFinal()) {
					printSolution(child, pred);
					return;
				}
			}
		}
	}
	
	private static class State
	{
		Block[] blocks;
		char[][] board;
		
		public State(Block[] blocks, char[][] board) {
			this.blocks = blocks; this.board = board;
		}
		
		// Clone a State using an updated block;
		// Update the block list and board to reflect
		// the new block's location
		public State(State s, Block oldBlock, Block newBlock) {
			this(s.deepCopyBlocks(), s.deepCopyBoard());
			blocks[oldBlock.num-'0'] = newBlock;
			oldBlock.put(board, false);
			newBlock.put(board, true);
			
		}
		
		public boolean isFinal() {
			return board[0][BOARD_WIDTH-1] == '0';
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
		
		private Block[] deepCopyBlocks() {
			Block[] newBlocks = new Block[NUM_BLOCKS];
			for (int i = 0; i < NUM_BLOCKS; i++) {
				newBlocks[i] = (Block)(blocks[i].clone());
			}
			return newBlocks;
		}
		
		private char[][] deepCopyBoard() {
			char[][] newBoard = new char[BOARD_LENGTH][];
			for (int i = 0; i < BOARD_LENGTH; i++) {
				newBoard[i] = board[i].clone();
			}
			return newBoard;
		}
	}
	
	
	private static class Block
	{
		Point[] points;
		char num;
		
		public Block(Point[] points, char num) {
			this.points = points;
			this.num = num;
		}
		
		public void put(char[][] board, boolean place)
		{
			char c = (place) ? num : '.';
			for (Point p : points) {
				board[p.x][p.y] = c;
			}
		}
		
		public Set<Block> getMovements(char[][] board) {
			Set<Block> visited = new HashSet<Block>();
			getMovements(board, this, visited);
			visited.remove(this);
			return visited;
		}
		
		/** * Backtrack to get all possible moves */
		private void getMovements(char[][] board, Block currBlock, Set<Block> visited) 
		{	
			if (visited.contains(currBlock)) {
				return;
			}
			else if (currBlock.outOfBoundsOrCollision(board)) {
				return;
			}
			
			visited.add(currBlock);
			
			Block left = new Block(currBlock.getNewPoints(0, -1), num);
			Block right = new Block(currBlock.getNewPoints(0, 1), num);
			Block up = new Block(currBlock.getNewPoints(-1, 0), num);
			Block down = new Block(currBlock.getNewPoints(1, 0), num);
			
			getMovements(board, left, visited);
			getMovements(board, right, visited);
			getMovements(board, up, visited);
			getMovements(board, down, visited);
		}
		
		/** Checks if the block has a point that is out of bounds or
		 *  collides with another block on the board */
		private boolean outOfBoundsOrCollision(char[][] board) {
			for (Point p : points) {
				if (p.x < 0 || p.x >= BOARD_LENGTH || p.y < 0 || p.y >= BOARD_WIDTH) {
					return true;
				}
				
				char c = board[p.x][p.y]; 
				if (c != '.' && c != num) {
					return true;
				}
			}
			return false;
		}
		
		private Point[] getNewPoints(int xs, int ys) {
			Point[] newPoints = new Point[points.length];
			for (int i = 0; i < points.length; i++) {
				Point currPoint = points[i];
				newPoints[i] = new Point(currPoint.x + xs, currPoint.y + ys);
			}
			return newPoints;
		}
		
		@Override
		public boolean equals(Object o) {
			Block b = (Block) o;
			return num == b.num && Arrays.deepEquals(points, b.points);
		}
		
		@Override
		public int hashCode() {
			return Arrays.deepHashCode(points) * num;
		}
		
		@Override
		public Object clone() {
			Point[] newPoints = new Point[points.length];
			for (int i = 0; i < points.length; i++) {
				Point old = this.points[i];
				newPoints[i] = new Point(old.x, old.y);
			}
			return new Block(newPoints, num);
		}
		
	}
	
	private static Block[] getBlocks()
	{
		Block goal = new Block(
			new Point[] {
				new Point(0, 0), new Point(0, 1),
				new Point(1, 0), new Point(1, 1)}, 
			'0');
		
		Block block1 = new Block(
			new Point[] {
				new Point(0, 2), new Point(0, 3)}, 
			'1');
	
		Block block2 = new Block(
			new Point[] {
				new Point(1, 2), 
				new Point(2, 2)}, 
			'2');
		
		Block block3 = new Block(
			new Point[] {
				new Point(1, 3), 
				new Point(2, 3)}, 
			'3');
		
		Block block4 = new Block(
			new Point[] {
				new Point(3, 0), 
				new Point(4, 0)}, 
			'4');
		
		Block block5 = new Block(
			new Point[] {
				new Point(3, 1), new Point(3, 2) }, 
			'5');
		
		Block block6 = new Block(
			new Point[] {
				new Point(3, 3) },
			'6');
		
		Block block7 = new Block(
			new Point[] {
				new Point(4, 1), new Point(4, 2)},
			'7');
		
		Block block8 = new Block(
			new Point[] {
				new Point(4, 3) },
			'8');
		
		return new Block[] {
				goal, block1, block2, block3, block4,
				block5, block6, block7, block8
		};
	}
	
	private static char[][] getBoard() {
		return new char[][] {
				new char[] {'0', '0', '1', '1' },
				new char[] {'0', '0', '2', '3' },
				new char[] {'.', '.', '2', '3' },
				new char[] {'4', '5', '5', '6' },
				new char[] {'4', '7', '7', '8' }
		};
	}
	
	private static void printSolution(State finalState, Map<State, State> pred) {
		Deque<State> reversed = new ArrayDeque<State>();
		State s = finalState;
		while (s != null) {
			reversed.add(s);
			s = pred.get(s); 
		}
		
		while (!reversed.isEmpty()) {
			printBoard(reversed.removeLast().board);
			System.out.println();
		}
	}
	
	private static void printBoard(char[][] board) {
		for (int i = 0; i < BOARD_LENGTH; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
}