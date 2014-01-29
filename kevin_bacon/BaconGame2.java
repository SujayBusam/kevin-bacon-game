package kevin_bacon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

import net.datastructures.*;

/**
 * CS10: PS-4 problem 2
 * @author Sujay Busam
 *
 * Given input files for actors, movies, and movies-actors, creates an undirected
 * adjacency list map graph of actors and their common movies
 */

public class BaconGame2 {
	// File instance variables
	private String actorsFile, moviesFile, movieActorsFile;
	
	// Map instance variables
	private HashMap<String, String> idToActor, idToMovie;
	private HashMap<String, Set<String>>movieIdToActorsIds;
	
	// Final map of actors and movies
	AdjacencyListGraphMap<String, String> baconGraph = new AdjacencyListGraphMap<String, String>();
	
	/**
	 * Puts up a fileChooser and gets path name for file to be opened.
	 * Returns an empty string if the user clicks "cancel".
	 * @return path name of the file chosen	
	 * @author Professor Grigoryan
	 */
	public static String getFilePath() {
		//Create a file chooser
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)  {
			File file = fc.getSelectedFile();
			String pathName = file.getAbsolutePath();
			return pathName;
		}
		else
			return "";
	}
	
	
	/**
	 * Constructor
	 * @param actorsFile File that maps actors to their IDs
	 * @param moviesFile File that maps movies to their IDs
	 * @param movieActorsFile File that maps actor IDs to movie IDs
	 * @throws IOException 
	 */
	public BaconGame2(String actorsFile, String moviesFile, String movieActorsFile) throws IOException {
		// Initialize file instance vars
		this.actorsFile = actorsFile;
		this.moviesFile = moviesFile;
		this.movieActorsFile = movieActorsFile;
		
		// Initialize hashmaps
		idToActor = new HashMap<String, String>();
		idToMovie = new HashMap<String, String>();
		movieIdToActorsIds = new HashMap<String, Set<String>>();
		
		// Execute methods to build final graph
		this.mapIdToActor();
		this.mapIdToMovie();
		this.mapMovieToActor();
		this.createBaconGraph();
	}
	
	/**
	 * Maps IDs from file to corresponding actor
	 * @throws IOException 
	 */
	private void mapIdToActor() throws IOException {
		try {
			BufferedReader input = new BufferedReader(new FileReader(actorsFile));
			
			String currentLine = input.readLine();
			
			// Keep reading lines until none remain
			while (currentLine != null) {
				String[] splitList = currentLine.split("\\|");
				
				// Add ID and actor to map
				idToActor.put(splitList[0], splitList[1]);
				
				// Add actor vertex to graph
				baconGraph.insertVertex(splitList[1]);
				
				currentLine = input.readLine();
			}
		}
		 
		catch (FileNotFoundException ex) {
			System.out.println(ex + " Pick another file.");
		}
		
		catch (IOException ex) {
			System.out.println(ex + " Actor file read error.");
		}
	}
	
	
	/**
	 * Maps IDs from file to corresponding movie
	 * @throws IOException 
	 */
	private void mapIdToMovie() throws IOException {
		try {
			BufferedReader input = new BufferedReader(new FileReader(moviesFile));
			
			String currentLine = input.readLine();
			
			// Keep reading lines until none remain
			while (currentLine != null) {
				String[] splitList = currentLine.split("\\|");
				
				// Add ID and actor to map
				idToMovie.put(splitList[0], splitList[1]);
				
				currentLine = input.readLine();
			}
		}
		 
		catch (FileNotFoundException ex) {
			System.out.println(ex + " Pick another file.");
		}
		
		catch (IOException ex) {
			System.out.println(ex + " Movies file read error.");
		}
	}
	
	/**
	 * Maps movie IDs to corresponding actor IDs
	 * @throws IOException 
	 */
	private void mapMovieToActor() throws IOException {
		try {
			BufferedReader input = new BufferedReader(new FileReader(movieActorsFile));
			
			String currentLine = input.readLine();
			
			// Keep reading lines until none remain
			while (currentLine != null) {
				String[] splitList = currentLine.split("\\|");
				
				// Check if map already contains movie ID
				if (movieIdToActorsIds.containsKey(splitList[0])) {
					// Add actor to existing set
					movieIdToActorsIds.get(splitList[0]).add(splitList[1]);
				}
				
				// Otherwise, create Set and add movie ID
				else {
					// Set of actors
					Set<String> actorSet = new HashSet<String>();
					// Add actor
					actorSet.add(splitList[1]);
					
					// Put the movie ID and set
					movieIdToActorsIds.put(splitList[0], actorSet);
				}
				
				currentLine = input.readLine();
			}
		}
		 
		catch (FileNotFoundException ex) {
			System.out.println(ex + " Pick another file.");
		}
		
		catch (IOException ex) {
			System.out.println(ex + " File read error.");
		}
	}	
	
	/**
	 * Create the final graph of actors and movies
	 */
	private void createBaconGraph() {
		// Run through set of movies		
		for (String movieID: this.movieIdToActorsIds.keySet()) {
			
			// Run through set of actors associated with movie ID
			for (String currentActorID: this.movieIdToActorsIds.get(movieID)) {
				
				// Run through again, creating edge between current and next
				for (String nextActorID: this.movieIdToActorsIds.get(movieID)) {
					// Get actor strings
					String currentActor = this.idToActor.get(currentActorID);
					String nextActor = this.idToActor.get(nextActorID);
					
					// Test to make sure edge doesn't already exist and next and current don't reference
					// same actor (set of one)
					if (!baconGraph.areAdjacent(currentActor, nextActor) && !currentActor.equals(nextActor)) {
						// Create the edge
						baconGraph.insertEdge(currentActor, nextActor, idToMovie.get(movieID));
					}
				}
			}
		}
	}
	
	// For testing only
	public static void main (String[] args) throws IOException {
		// Get input files from user
		System.out.println("Select an actors file.");
		String actorsFile = getFilePath();
		
		System.out.println("Select a movies file.");
		String moviesFile = getFilePath();
		
		System.out.println("Select a movie-actors file.");
		String movieActorsFile = getFilePath();
		
		// Create the object and test
		BaconGame2 baconGame2 = new BaconGame2(actorsFile, moviesFile, movieActorsFile);
		System.out.println(baconGame2.baconGraph);
		
		
	}
}