import java.util.Scanner;


public class NumberedSets 
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		for (int i = 0; i < n; i++) {
			solve(in.nextInt(), in.nextInt(), in.nextInt());
		}
	}
	
	static void solve(int a, int b, int p)
	{
		UnionFind uf = new UnionFind(b - a + 1);
		
		boolean[] used = new boolean[b];
		for (int i = 1; i <= Math.sqrt(b); i++) {
			if (used[i]) {
				int prev = i;
				for (int j = i * i; j < b; j++) {
					if (prev > i && )
					prev = j;
				}
			}
			used[i] = true;
		}
	}
	
	static class UnionFind
	{
		int[] uf;
		int n;
		
		UnionFind(int n) {
			this.n = n;
			this.uf = new int[n];
			for (int i = 0; i < n; i++) {
				uf[i] = i;
			}
		}
		
		int find(int x) {
			int parent = uf[x];
			if (x != parent)
				parent = find(parent);
			
			return uf[x] = parent;
		}
		
		
		void union(int x, int y) {
			int a = find(x);
			int b = find(y);
			if (a != b) {
				n--;
				uf[a] = b;
			}
			
		}
	}
}
