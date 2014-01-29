package kevin_bacon;
import net.datastructures.*;


public class BaconGame1Driver {

	public static void main (String[] args) {
		// --------- Test Part 1 ---------------------

		// Create undirected graph
		AdjacencyListGraphMap<String, String> myGraph = new AdjacencyListGraphMap<String,String>();

		// Add vertices and edges
		myGraph.insertVertex("Kevin Bacon");
		myGraph.insertVertex("actor1");
		myGraph.insertVertex("actor2");
		myGraph.insertVertex("actor3");
		myGraph.insertVertex("actor4");
		myGraph.insertVertex("actor5");
		myGraph.insertVertex("actor6");
		myGraph.insertEdge("Kevin Bacon", "actor1", "movie1");
		myGraph.insertEdge("Kevin Bacon", "actor2", "movie1");
		myGraph.insertEdge("actor1", "actor2", "movie1");
		myGraph.insertEdge("actor1", "actor3", "movie2");
		myGraph.insertEdge("actor3", "actor2", "movie3");
		myGraph.insertEdge("actor3", "actor4", "movie4");
		myGraph.insertEdge("actor5", "actor6", "movie5");

		// Print this graph
		System.out.println("Undirected graph:");
		for(Vertex<String> vertex : myGraph.vertices()) {
			System.out.println("\nEdges adjacent to " + vertex + ":");
			for(Edge<String> edge : myGraph.incidentEdges(vertex)) 
				System.out.println(edge); 
		}

		// Tree created by BFS
		BaconGame1 baconGame1 = new BaconGame1(myGraph);
		DirectedAdjListMap<String,String> myTree = baconGame1.doBFS();

		// Print the tree
		System.out.println("\nBFS Tree:");
		for(Vertex<String> vertex : myTree.vertices()) {
			System.out.println("\nEdges adjacent to " + vertex + ":");
			for(Edge<String> edge : myTree.incidentEdges(vertex)) 
				System.out.println(edge); 

			System.out.println("\nEdges into " + vertex + ":");
			for(Edge<String> edge : myTree.incidentEdgesIn(vertex)) 
				System.out.println(edge); 

			System.out.println("\nEdges out of " + vertex + ":");
			for(Edge<String> edge : myTree.incidentEdgesOut(vertex)) 
				System.out.println(edge); 
		}
	}		
}