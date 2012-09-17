import java.util.HashSet;


public class Test {
	public static void main(String[] args) {
	
		HashSet<A> set1 = new HashSet<A>();
		HashSet<A> set2 = new HashSet<A>();
		
		set1.add(new A(1, 2));
		set1.add(new A(3, 4));
		
		set2.add(new A(3, 4));
		set2.add(new A(1, 2));
		
		
		
		
		System.out.println(set1.hashCode());
		System.out.println(set2.hashCode());
	}
	
	static class A
	{
		int a;
		int b;
		
		public A(int a, int b) { this.a = a; this.b = b; }
		
		public int hashCode() {
			return a ^ b;
		}
		
		public boolean equals(Object o) {
			A other = (A) o;
			return a == other.a && b == other.b;
		}
	}
	
	
	
}
