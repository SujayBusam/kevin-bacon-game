package kevin_bacon;
import java.io.IOException;
import java.util.Scanner;

import net.datastructures.*;

/**
 * CS10: PS-4 problem 3
 * @author Sujay Busam
 *
 * Final implementation of Bacon Game
 */

public class BaconGame3 {
	
	public static void main (String[] args) throws IOException{
		
		// Get input files from user
		System.out.println("Select an actors file.");
		String actorsFile = BaconGame2.getFilePath();
		
		System.out.println("Select a movies file.");
		String moviesFile = BaconGame2.getFilePath();
		
		System.out.println("Select a movie-actors file.\n");
		String movieActorsFile = BaconGame2.getFilePath();
		
		// Create the object that creates the undirected graph of actors and movies
		BaconGame2 baconGame2 = new BaconGame2(actorsFile, moviesFile, movieActorsFile);
		
		// Perform BFS on the graph and save the BFS tree
		BaconGame1 baconGame1 = new BaconGame1(baconGame2.baconGraph);
		DirectedAdjListMap<String,String> myTree = baconGame1.doBFS();
		
		String userInput = null;
		Scanner input = new Scanner(System.in);
		
		System.out.println("To quit the program, type return in answer to a question.");
		
		do {
			// Get user input
			System.out.println("Enter the name of an actor: ");
			userInput = input.nextLine();
			
			// Print output
			baconGame1.lookUpActor(myTree, userInput);
			
		} while (!userInput.equals(""));
	}
}