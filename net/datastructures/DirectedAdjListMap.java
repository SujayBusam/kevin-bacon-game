package net.datastructures;

import net.datastructures.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/** 
 * Methods for a directed graph
 * 
 * @author Delos Chang
 *
 */

public class DirectedAdjListMap<V,E> extends AdjacencyListGraphMap<V,E> { 
	protected static Object EDGE_TYPE;
	protected static Object DIRECTED;
	
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
		// insert an edge first
		Edge<E> newEdge = super.insertEdge(v, w, label);

		// now add decorator to mark directed
		newEdge.put(EDGE_TYPE, DIRECTED);
		return newEdge;
	}

	/**
	 * Overloaded Method for type V instead of vertex of type Vertex<V>
	 * @param v the first vertex identifier
	 * @param w the second vertex identifier
	 * @param o the label on the edge
	 * @return vertex object inserted
	 */
	public Edge<E> insertDirectedEdge(V v, V w, E label){
		return insertDirectedEdge(getVertex(v), getVertex(w), label);
	}


	/**
	 * Get all incident edges with v as destination
	 * @param v the destination vertex
	 * @return collection of incident edges with v as destination
	 */
	public Iterable<Edge<E>> incidentEdgesIn(Vertex<V> v) {
		MyVertex<V> vv = checkVertex(v);

		// grab the edge list
		Iterable<Edge<E>> allEdges = super.incidentEdges(vv);

		// the edge list to return
		ArrayList<Edge<E>> returnIncomingEdges = new ArrayList<Edge<E>>();

		for (Edge<E> edge : allEdges){
			// if edge is undirected, add in (two-way) 
			// if edge is directed, check destination
			if ((edge.get(EDGE_TYPE) == null) || 
					(edge.get(EDGE_TYPE) == DIRECTED && super.endVertices(edge)[1] == vv)){
				returnIncomingEdges.add(edge);
			}
		}

		return returnIncomingEdges;

	}

	/**
	 * Overloaded method for incident edges in 
	 * 
	 * @param v the destination vertex
	 * @return collection of incident edges with v as destination
	 */
	public Iterable<Edge<E>> incidentEdgesIn(V v) {
		return incidentEdgesIn(getVertex(v));
	}



	/**
	 * Get all incident edges with v as source
	 * @param v the source vertex
	 * @return collection of incident edges with v as source
	 */
	public Iterable<Edge<E>> incidentEdgesOut(Vertex<V> v) {
		MyVertex<V> vv = checkVertex(v);

		// grab the edge list
		Iterable<Edge<E>> allEdges = super.incidentEdges(vv);

		// the edge list to return
		ArrayList<Edge<E>> returnOutgoingEdges = new ArrayList<Edge<E>>();

		for (Edge<E> edge : allEdges){
			// if edge is undirected, add in (two-way) 
			// if edge is directed, check source
			if ((edge.get(EDGE_TYPE) == null) || 
					(edge.get(EDGE_TYPE) == DIRECTED && super.endVertices(edge)[0] == vv)){
				returnOutgoingEdges.add(edge);
			}
		}

		return returnOutgoingEdges;


	}

	/**
	 * Overloaded method for incident edges out 
	 * 
	 * @param v the destination vertex
	 * @return collection of incident edges with v as source
	 */
	public Iterable<Edge<E>> incidentEdgesOut(V v) {
		return incidentEdgesOut(getVertex(v));
	}


	/**
	 * Get the in degree of a vertex
	 * @param v the vertex
	 * @return the in degree of v
	 */
	public int inDegree(Vertex<V> v) {
		MyVertex<V> vv = checkVertex(v);
		int counter = 0;

		// count the undirected edges
		for (Edge<E> edge : super.incidentEdges(vv)){
			//                        System.out.println(edge);
			//                        System.out.println(edge.get(EDGE_TYPE) == null);
			if (edge.get(EDGE_TYPE) == null){
				counter += 1;
			}
		}

		return counter;
	}


	/**
	 * Overloaded method for inDegree of type v
	 * @param v the vertex identifier
	 * @return the in degree of v
	 */
	public int inDegree(V v) {
		return inDegree(getVertex(v));
	}


	/**
	 * Get the out degree of a vertex
	 * @param v the vertex
	 * @return the out degree of v
	 */
	public int outDegree(Vertex<V> v) {
		MyVertex<V> vv = checkVertex(v);
		int counter = 0;

		// count directed edges
		for (Edge<E> edge : super.incidentEdges(vv)){
			//                        System.out.println(edge);
			//                        System.out.println(edge.get(EDGE_TYPE) == DIRECTED);
			if (edge.get(EDGE_TYPE) == DIRECTED){
				counter +=1;
			}
		}

		return counter;
	}

	/**
	 * Overloaded method for outDegree of type v
	 * @param v the vertex identifier
	 * @return the out degree of v
	 */
	public int outDegree(V v) {
		return outDegree(getVertex(v));
	}


	// testing the code
	public static void main(String [] args) {
		

		DirectedAdjListMap<String, String> baconGraph = new DirectedAdjListMap<String, String>();

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