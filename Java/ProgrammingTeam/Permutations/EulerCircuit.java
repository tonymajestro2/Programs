import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EulerCircuit 
{
	static void hierholzer(List<Vertex> vertices)
	{
		Node<Vertex> tour = new Node<Vertex>(vertices.get(0));
		
		for (int i = 0; i < vertices.size(); i++) {
			
		}
		Node<Vertex> vertUnusedEdges;
		Node<Edge> unusedEdges;
		
		while (vertUnusedEdges != null) {
			
		}
	}
	
	
	static class Node<E>
	{
		E data;
		Node<E> prev;
		Node<E> next;
		
		Node(E data, Node<E> prev, Node<E> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
		Node (E data) {
			this.data = data;
		}
	}
	
	static class List<E>
	{
		Node<E> head;
		Node<E> tail;
		Node<E> curr;
		
		List() {
			head = new Node<E>(null);
			tail = new Node<E>(null);
			curr = head;
		}
		
		void append(E data) {
			Node<E> node = new Node<E>(data, tail.prev, tail);
			tail.prev.next = node;
			tail.prev = node;
		}
		
		void add(E data) {
			Node<E> node = new Node<E>(data, curr, curr.next);
		}
		
	}
	
	static class Edge
	{
		int v;
		int w;
		
		Edge(int v, int w) { 
			this.v = v; 
			this.w = w; 
		}
	}
	
	static class Vertex
	{
		Set<Node<Edge>> edges;
		
		Vertex() {
			edges = new HashSet<Node<Edge>>();
		}
	}
}
