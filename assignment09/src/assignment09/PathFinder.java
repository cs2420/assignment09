package assignment09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class contains methods used for solving a text file maze and writing the
 * solved maze to a new output file. Contains nested classes for representing
 * the maze as a graph, characters in the maze as a node in that graph
 * 
 * @author Connor Ottenbacher and Doug Garding
 */
public class PathFinder {

	/**
	 * Takes in a text file containing a maze, solves the maze representing the
	 * shortest path with '.' characters, and writes the solved maze to a new
	 * file
	 * 
	 * @param inputFile
	 *            the file name that contains the text maze
	 * @param outputFile
	 *            file name/ location for the solved maze file
	 */
	public static void solveMaze(String inputFile, String outputFile)
			throws IOException {
		Graph graph = new Graph(inputFile);
		Queue<MazeNode> queue = new LinkedList<MazeNode>();
		// Begins the breadth-first search using the 'S' node obtained from the
		// graph
		graph.start.visited = true;
		queue.add(graph.start);
		while (!queue.isEmpty()) {
			// current node becomes the first node in the queue
			MazeNode current = queue.poll();
			// if this node matches the goal, constructs the pathway from where
			// it came and breaks from the loop
			if (current.data == 'G') {
				MazeNode step = current.cameFrom;
				// loop used to construct the entire path, goes until it finds
				// the starting point again
				while (step.data != 'S') {
					// Changes the character in each node on the shortest path
					// to '.'
					step.data = '.';
					step = step.cameFrom;
				}
				// Shortest path was found, stops searching
				break;
			}
			// runs through the list of the current node's neighbors adding them
			// to the queue if they
			// are not walls and have not already been visited
			for (MazeNode neighbor : current.neighbors) {
				if (neighbor.data != 'X' && neighbor.visited == false) {
					neighbor.visited = true;
					neighbor.cameFrom = current;
					queue.add(neighbor);
				}
			}
		}

		// Writes the new maze with the solution to the outputFile
		writeToFile(graph, outputFile);

	}

	/**
	 * Helper method turns the graph's 2D array into a file with a certain file
	 * name
	 *
	 * @param graph
	 *            graph containing the solved maze, represented with a 2D array
	 * @param outputFile
	 *            the user specified file name
	 */
	private static void writeToFile(Graph graph, String outputFile) {
		// Turns the graph into one string representing the maze
		String solutionString = graph.dimensions[0] + " " + graph.dimensions[1]
				+ '\n';
		for (MazeNode[] array : graph.nodes) {
			String row = "";
			for (MazeNode node : array) {
				row += node.data;
			}
			row += '\n';
			solutionString += row;
		}

		// writes the solution string to a file
		try {
			File file = new File(outputFile);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(solutionString);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Graph class stores all of the characters in the maze as nodes in a 2D
	 * array.
	 */
	private static final class Graph {
		MazeNode[][] nodes;
		MazeNode start; // The node containing 'S' data
		String[] dimensions; // Contains the dimensions of the maze

		/**
		 * Constructor for a graph, creates a graph of MazeNodes backed by a 2D
		 * array from a text file maze
		 * 
		 * @param inputFile
		 *            the file name that contains the text maze
		 */
		Graph(String inputFile) throws IOException {
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			// Grabs the first line which will always be the dimensions of the
			// maze
			dimensions = input.readLine().split(" ");
			int height = Integer.parseInt(dimensions[0]);
			int width = Integer.parseInt(dimensions[1]);
			// Initializes a 2D array of nodes with the correct height and width
			// of the maze
			nodes = new MazeNode[height][width];
			int row = 0;
			// Creates a 2D array of nodes, row by row
			while (row < height) {
				// converts the row into a char array
				String mazeRow = input.readLine();
				char[] charArray = mazeRow.toCharArray();
				int column = 0;
				// for every character, creates a node and adds it to its
				// correct row and column
				for (char nodeChar : charArray) {
					MazeNode mazeNode = new MazeNode(nodeChar);
					// if the maze node is S, assigns it as the starting node
					if (mazeNode.data == 'S') {
						start = mazeNode;
					}
					// adds the node to its correct spot relating to the maze
					nodes[row][column] = mazeNode;
					column++;
				}
				row++;
			}
			input.close();
			// Assigns every node in the 2D array its neighbors
			assignNeighbors();
		}

		/**
		 * Helper method assigns the nodes in the graph's 2D array its neighbors
		 * based upon where their location in the 2D array
		 */
		private void assignNeighbors() {
			// Traverses the 2D array by each row
			for (int rowNum = 0; rowNum < nodes.length; rowNum++) {
				MazeNode[] row = nodes[rowNum];
				// Picks out each node in the row
				for (int colNum = 0; colNum < row.length; colNum++) {
					MazeNode node = nodes[rowNum][colNum];
					// Adds all possible neighbors, if edge case does nothing
					try {
						node.neighbors.add(nodes[rowNum - 1][colNum]);
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						node.neighbors.add(nodes[rowNum][colNum + 1]);
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						node.neighbors.add(nodes[rowNum + 1][colNum]);
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						node.neighbors.add(nodes[rowNum][colNum - 1]);
					} catch (IndexOutOfBoundsException e) {
					}
				}
			}

		}

	}

	/**
	 * Node Class that stores elements in a maze.
	 */
	private static final class MazeNode {
		MazeNode cameFrom; // Indicates the Node from which this node came
		boolean visited;
		Character data; // The element contained in the node. Either space, X,
						// S, or G.
		ArrayList<MazeNode> neighbors; // list of up to four adjacent nodes

		/**
		 * Default constructor for a node.
		 * 
		 * @param data
		 *            the list element
		 */
		MazeNode(Character data) {
			this.data = data;
			neighbors = new ArrayList<MazeNode>();
			visited = false;
		}

		@Override
		public String toString() {
			return data + "";
		}
	}

}
