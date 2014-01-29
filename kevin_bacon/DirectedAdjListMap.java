package kevin_bacon;


import net.datastructures.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * An realization of a graph according to adjacency list structure.
 * Modified to use a map from vertex names to vertices instead of a 
 *   NodePositionList to hold the vertices.  
 * Assumes that vertex values are unique.
 * 
 * This directed graph subclass written for SA-10.
 *
 * @author Sujay Busam 
 */

public class DirectedAdjListMap<V,E> extends AdjacencyListGraphMap<V,E> {

	protected Object EDGE_TYPE;
	protected Object DIRECTED;

	/**
	 * Default constructor that creates an empty graph 
	 */
	public DirectedAdjListMap() {
		super();
		EDGE_TYPE = new Object();
		DIRECTED = new Object();
	}


	/**
	 * Is the given edge directed?
	 * @param e the edge to test
	 * @return true if e is directed
	 */
	public boolean isDirected(Edge<E> e){
		return e.get(EDGE_TYPE).equals(DIRECTED);
	}

	/**
	 * Insert a directed edge into this graph
	 * @param v - the source vertex
	 * @param w - the destination vertex
	 * @param label - the edge label
	 * @return the new edge
	 */
	public Edge<E> insertDirectedEdge(Vertex<V> v, Vertex<V> w, E label) {
		Edge<E> myEdge = super.insertEdge(v, w, label);
		myEdge.put(EDGE_TYPE, DIRECTED);
		return myEdge;
	}

	/**
	 * Get all incident edges with v as destination
	 * @param v the destination vertex
	 * @return collection of incident edges with v as destination
	 */
	public Iterable<Edge<E>> incidentEdgesIn(Vertex<V> v) {
		Iterable<Edge<E>> myEdgesIn = new ArrayList<Edge<E>>();

		Iterable<Edge<E>> edgeList = super.incidentEdges(v);
		for(Edge<E> edge: edgeList) {
			if (super.endVertices(edge)[1] == v) {
				((ArrayList<Edge<E>>) myEdgesIn).add(edge);
			}
		}

		return myEdgesIn;
	}

	/**
	 * Get all incident edges with v as source
	 * @param v the source vertex
	 * @return collection of incident edges with v as source
	 */
	public Iterable<Edge<E>> incidentEdgesOut(Vertex<V> v) {
		Iterable<Edge<E>> myEdgesOut = new ArrayList<Edge<E>>();

		Iterable<Edge<E>> edgeList = super.incidentEdges(v);
		for(Edge<E> edge: edgeList) {
			if (super.endVertices(edge)[0] == v) {
				((ArrayList<Edge<E>>) myEdgesOut).add(edge);
			}
		}

		return myEdgesOut;
	}

	/**
	 * Get the in degree of a vertex
	 * @param v the vertex
	 * @return the in degree of v
	 */
	public int inDegree(Vertex<V> v) {
		return ((ArrayList<Edge<E>>) this.incidentEdgesIn(v)).size();
	}

	/**
	 * Get the out degree of a vertex
	 * @param v the vertex
	 * @return the out degree of v
	 */
	public int outDegree(Vertex<V> v) {
		return ((ArrayList<Edge<E>>) this.incidentEdgesOut(v)).size();
	}



	// Overloaded Methods



	/**
	 * Insert a directed edge into this graph
	 * @param v - the source vertex
	 * @param w - the destination vertex
	 * @param label - the edge label
	 * @return the new edge
	 */
	public Edge<E> insertDirectedEdge(V v, V w, E label) {
		return this.insertDirectedEdge(super.getVertex(v), super.getVertex(w), label);
	}

	/**
	 * Get all incident edges with v as destination
	 * @param v the destination vertex
	 * @return collection of incident edges with v as destination
	 */
	public Iterable<Edge<E>> incidentEdgesIn(V v) {
		return this.incidentEdgesIn(super.getVertex(v));
	}

	/**
	 * Get all incident edges with v as source
	 * @param v the source vertex
	 * @return collection of incident edges with v as source
	 */
	public Iterable<Edge<E>> incidentEdgesOut(V v) {
		return this.incidentEdgesOut(super.getVertex(v));
	}

	/**
	 * Get the in degree of a vertex
	 * @param v the vertex
	 * @return the in degree of v
	 */
	public int inDegree(V v) {
		return this.inDegree(super.getVertex(v));
	}

	/**
	 * Get the out degree of a vertex
	 * @param v the vertex
	 * @return the out degree of v
	 */
	public int outDegree(V v) {
		return this.outDegree(super.getVertex(v));
	}

	public static void main(String [] args) {
		DirectedAdjListMap<String, String> baconGraph = 
			new DirectedAdjListMap<String, String>();

		baconGraph.insertVertex("Kevin Bacon");
		baconGraph.insertVertex("Laura Linney");
		baconGraph.insertVertex("Tom Hanks");
		baconGraph.insertVertex("Liam Neeson");
		baconGraph.insertDirectedEdge("Laura Linney","Kevin Bacon", "Mystic River");
		baconGraph.insertEdge("Liam Neeson", "Laura Linney", "Kinsey");
		baconGraph.insertDirectedEdge( "Tom Hanks", "Kevin Bacon", "Apollo 13");

		System.out.println("\nDegree of Laura Linney = " + 
				baconGraph.degree("Laura Linney"));

		System.out.println("\nInDegree of Laura Linney = " + 
				baconGraph.inDegree("Laura Linney"));

		System.out.println("\nOutDegree of Laura Linney = " +
				baconGraph.outDegree("Laura Linney"));

		System.out.println("\nEdges into to Laura Linney:");
		for(Edge<String> edge : baconGraph.incidentEdgesIn("Laura Linney")) 
			System.out.println(edge);

		System.out.println("\nEdges out of to Laura Linney:");
		for(Edge<String> edge : baconGraph.incidentEdgesOut("Laura Linney")) 
			System.out.println(edge); 

		System.out.println("The entire graph:");
		for(Vertex<String> vertex : baconGraph.vertices()) {
			System.out.println("\nEdges adjacent to " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdges(vertex)) 
				System.out.println(edge); 

			System.out.println("\nEdges into " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdgesIn(vertex)) 
				System.out.println(edge); 

			System.out.println("\nEdges out of " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdgesOut(vertex)) 
				System.out.println(edge); 
		}

		System.out.println("\nRenaming Laura Linney to L. Linney");
		baconGraph.replace("Laura Linney", "L. Linney");
		System.out.println("\nGetting Laura Linney: " + 
				baconGraph.getVertex("Laura Linney"));

		for(Vertex<String> vertex : baconGraph.vertices()) {
			System.out.println("\nEdges adjacent to " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdges(vertex)) 
				System.out.println(edge);   		
		}

		System.out.println("\nRemoving L. Linney");
		baconGraph.removeVertex("L. Linney");

		System.out.println("\nThe entire graph:");
		for(Vertex<String> vertex : baconGraph.vertices()) {
			System.out.println("\nEdges adjacent to " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdges(vertex)) 
				System.out.println(edge);   		

			System.out.println("\nEdges into " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdgesIn(vertex)) 
				System.out.println(edge); 

			System.out.println("\nEdges out of " + vertex + ":");
			for(Edge<String> edge : baconGraph.incidentEdgesOut(vertex)) 
				System.out.println(edge); 
		}  		
	}
}