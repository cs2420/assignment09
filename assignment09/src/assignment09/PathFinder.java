package assignment09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PathFinder {

	public static void solveMaze(String inputFile, String outputFile)
			throws IOException {
		Graph graph = new Graph(inputFile);
		// Queue used for path finding
		Queue<MazeNode> queue = new LinkedList<MazeNode>();
		// Contains nodes representing the shortest path from S to G
		LinkedList<MazeNode> nodePath = new LinkedList<MazeNode>();
		// Starts the search from the 'S' node obtained from the graph of the maze
		graph.start.visited = true;
		queue.add(graph.start);
		while (!queue.isEmpty()) {
			// current node becomes the first node in the queue
			MazeNode current = queue.poll();
			// if this node matches the goal, constructs the pathway from where
			// it came and breaks from the loop
			if (current.data == 'G') {
				// last node before the goal
				MazeNode step = current.cameFrom;
				// loop used to construct the entire path, goes until it finds
				// the starting point again
				while (step.data != 'S') {
					// adds the step to a list later used to create the path
					// dots
					nodePath.add(step);
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
		// After the shortest path has been found, changes the chars in the
		// nodes along the shortest path to '*'
		for (MazeNode n : nodePath) {
			n.data = '*';
		}

		// this loop prints out the maze as represented in the graph as a 2D
		// array. using this println statement for testing.
		for (MazeNode[] array : graph.nodes) {
			String row = "";
			for (MazeNode node : array) {
				row += node.data;
			}
			System.out.println(row);
		}

	}

	// used for testing
	public static void main(String[] args) throws IOException {
		solveMaze("classic.txt", "output");
	}

	/**
	 * Graph class stores all of the characters in the maze as nodes in a 2D
	 * array.
	 *
	 */
	private static final class Graph {
		MazeNode[][] nodes;
		MazeNode start; // The node containing 'S' data

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
		MazeNode cameFrom; // Indicates the Node from which this node came
							// from
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
