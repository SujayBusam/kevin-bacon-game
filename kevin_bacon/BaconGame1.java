package kevin_bacon;
import net.datastructures.*;

/**
 * CS10: PS-4 problem 1
 * @author Sujay Busam
 *
 * Given a movie data graph G, perform a breadth-first search (BFS)
 * Builds a shortest-path tree, which consists of all vertices of G that are
 * reachable from the root, Kevin Bacon
 */

public class BaconGame1 {
	// Define root
	public static final String ROOT = "Kevin Bacon";
	// Map instance var
	public AdjacencyListGraphMap<String, String> myGraph;
	
	
	// Contstructor
	public BaconGame1(AdjacencyListGraphMap<String, String> inputGraph) {
		this.myGraph = inputGraph;
	}
	
	/**
	 * Implement BFS algorithm using a queue. Also builds the shortest-path tree
	 * @return T the shortest-path tree constructed
	 */
	public DirectedAdjListMap<String, String> doBFS() {
		// Directed graph to return
		DirectedAdjListMap<String, String> T = new DirectedAdjListMap<String, String>();
		// Queue used to implement BFS
		NodeQueue<String> Q = new NodeQueue<String>();
		
		// Insert root into queue and into new directed graph
		Q.enqueue(ROOT);
		T.insertVertex(ROOT);
		
		// Until queue is empty
		while(!Q.isEmpty()) {
			// dequeue Q to get next vertex to process
			String v = Q.dequeue();
			
			// for each edge e that is incident to v in G
			for (Edge<String> e: myGraph.incidentEdges(v)) {
				// Let v' be the other end of the edge
				Vertex<String> vPrime = myGraph.opposite(v, e);
				
				// If v' is not in T
				if (!T.vertexInGraph(vPrime.toString())) {
					// Add v' to T
					T.insertVertex(vPrime.toString());
					
					// Add edge with same label as e from v' to v in T
					T.insertDirectedEdge(vPrime.toString(), v, e.element().toString());
					
					// Enqueue v' in Q
					Q.enqueue(vPrime.toString());
				}
			}
		}
		
		// Return the newly constructed shortest-path tree
		return T;
	}
	
	
	/**
	 * Method to look up given actor's Bacon number
	 * @param actor to look up
	 */
	public void lookUpActor(DirectedAdjListMap<String, String> T, String actor) {
		int baconNumber = 0;
		
		if (actor.equals("")) {
			System.out.println("Thanks for playing!");
			return;
		}
		
		// If user looks up Kevin Bacon
		if (actor.equals(ROOT)) {
			System.out.println(ROOT + "'s own bacon number is 0.");
		}
		
		// If there is no vertex for the actor, actor is not connected to Bacon
		if (!T.vertexInGraph(actor)) {
			System.out.println(actor + " has no connection to " + ROOT);
		}
		
		
		
		// Actor is there. Follow edges of T back to Bacon, printing edge labels and
		// actors along the way
		else {
			Vertex<String> currentActor = T.getVertex(actor);
			Vertex<String> oppositeActor = null;
			
			while(!currentActor.toString().equals(ROOT)) {
				// Only one incident edge with current source
				for (Edge<String> currentMovie: T.incidentEdgesOut(currentActor)) {
					oppositeActor = T.opposite(currentActor, currentMovie);
					// Print the relation
					System.out.println(currentActor.toString() + " appeared in " + currentMovie.element() + " with " + oppositeActor.toString());
				}
				
				baconNumber++;
				currentActor = oppositeActor;
			}
			
			// Print actor's Bacon number
			System.out.println(actor + "'s Bacon number is: " + baconNumber + "\n");
		}
	}
}