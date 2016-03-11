package assignment09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PathFinder {

	public static void solveMaze(String inputFile, String outputFile)
			throws IOException {
		Graph graph = new Graph(inputFile);

		// you can disregard these loops, i was just using it to check if nodes in the graph
		// were being assigned their correct neighbors. used on tinyMaze.txt
		for (MazeNode[] array : graph.nodes) {
			for (MazeNode node : array) {
				//node.toString returns this node's data followed by a
				// space, followed by all of its neighbors
				System.out.println(node.toString());
			}
		}
		//this loop prints out the maze as represented in the graph as a 2D array
		for (MazeNode[] array : graph.nodes) {
			String row ="";
			for (MazeNode node : array) {
				row+=node.data;
			}
			System.out.println(row);
		}

	}

	// used for testing
	public static void main(String[] args) throws IOException {
		solveMaze("tinyMaze.txt", "output");
	}

	/**
	 * Graph class stores all of the characters in the maze as nodes in a 2D
	 * array.
	 *
	 */
	private static final class Graph {
		MazeNode[][] nodes;

		// Constructor makes a 2D MazeNode array from the maze file.
		Graph(String inputFile) throws IOException {
			// Creates a reader that will read the text file by line
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			// Grabs the first line which will always be the dimensions of the
			// maze
			String[] dimensions = input.readLine().split(" ");
			int height = Integer.parseInt(dimensions[0]);
			int width = Integer.parseInt(dimensions[1]);
			// Initializes a 2D array of nodes with the correct height and width
			// of the maze
			nodes = new MazeNode[height][width];
			int row = 0;
			int nodeNumber = 0; // unique number to be assigned to each node
			// Creates a 2D array of nodes, row by row
			while (row < height) {
				// convert one row into a string
				String mazeRow = input.readLine();
				// converts this string to a char array
				char[] charArray = mazeRow.toCharArray();
				int column = 0;
				// for every character, creates a node and adds it to its
				// correct row and column
				for (char nodeChar : charArray) {
					MazeNode mazeNode = new MazeNode(nodeChar); // creates the
																// node
					mazeNode.nodeNum = nodeNumber; // assigns its number
					// adds the node to its correct spot relating to the maze
					nodes[row][column] = mazeNode;
					nodeNumber++;
					column++;
				}
				row++;
			}
			// Assigns every node in the 2D array its neighbors
			assignNeighbors();
		}

		// After the 2D array of Nodes is created, the nodes need to be assigned
		// their neighbors based on where they are in the 2D array
		public void assignNeighbors() {
			// Traverses the 2D array by each row
			for (int rowNum = 0; rowNum < nodes.length; rowNum++) {
				MazeNode[] row = nodes[rowNum];
				// Picks out each node in the row
				for (int colNum = 0; colNum < row.length; colNum++) {
					MazeNode node = nodes[rowNum][colNum];
					ArrayList<MazeNode> adjacent = new ArrayList<MazeNode>();
					// Adds all possible neighbors to the above list, if edge
					// case does nothing
					try {
						adjacent.add(nodes[rowNum - 1][colNum]);
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						adjacent.add(nodes[rowNum][colNum + 1]);
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						adjacent.add(nodes[rowNum + 1][colNum]);
					} catch (IndexOutOfBoundsException e) {
					}
					try {
						adjacent.add(nodes[rowNum][colNum - 1]);
					} catch (IndexOutOfBoundsException e) {
					}
					// Adds all of these nodes to the current node's neighbor
					// list
					node.neighbors.addAll(adjacent);
				}
			}

		}

	}

	/**
	 * Node Class that stores elements in a maze.
	 *
	 * @param <E>
	 */
	private static final class MazeNode {
		int cameFrom; // Indicates the nodeNum from which this node came from
		boolean visited;
		int nodeNum; // Number to identify each node. Unique for each node in a
						// maze/graph
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
			this.neighbors = new ArrayList<MazeNode>();
		}

		// using this for testing, will return this node's data followed by a
		// space, followed by all of its neighbors
		@Override
		public String toString() {
			String result = data + " ";
			for (MazeNode n : neighbors) {
				result += n.data;
			}
			return result;
		}
	}

}
