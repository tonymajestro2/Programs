
public class UnionFind2 
{
	public static void main(String[] args)
	{
		UnionFind uf = new UnionFind(4);
		
		uf.union(0, 1);
		System.out.println(uf.find(0));
		System.out.println(uf.find(1));
		System.out.println(uf.find(2));
		System.out.println(uf.find(3) + "\n");
		
		uf.union(0, 2);
		System.out.println(uf.find(0));
		System.out.println(uf.find(1));
		System.out.println(uf.find(2));
		System.out.println(uf.find(3) + "\n");
		
		
	}
	
	static class UnionFind
	{
		int[] uf;
		int[] size;
		
		UnionFind(int n) {
			this.uf = new int[n];
			this.size = new int[n];
			for (int i = 0; i < n; i++) {
				uf[i] = i;
				size[i] = 1;
			}
		}
		
		int find(int x) {
			while (x != uf[x]) {
				uf[x] = uf[uf[x]];
				x = uf[x];
			}
			return x;
		}
		
		
		void union(int x, int y) {
			int px = find(x);
			int py = find(y);
			if (size[px] > size[py]) {
				uf[py] = px;
				size[px] += size[py];
			}
			else {
				uf[px] = py;
				size[py] += size[px];
			}
		}
	}
}
