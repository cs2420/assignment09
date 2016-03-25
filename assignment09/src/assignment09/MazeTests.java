package assignment09;

import static org.junit.Assert.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

/**
 * Tests pathfinding several types of mazes
 * 
 * @author Connor Ottenbacher and Doug Garding
 */

public class MazeTests {

	//helper method counts the dots (steps in path) in the specified text file
	private static int countDots(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		int result = 0;
		while (sc.hasNext()) {
			String row = sc.next();
			char[] charArray = row.toCharArray();
			for (char ch : charArray) {
				if (ch == '.') {
					result++;
				}
			}
		}
		sc.close();
		return result;
	}

	@Test
	public void testBigMaze() throws IOException {
		PathFinder.solveMaze("mazes/bigMaze.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/bigMazeSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testClassic() throws IOException {
		PathFinder.solveMaze("mazes/classic.txt", "mazes/output.txt");
		assertEquals(14, countDots("mazes/output.txt"));
	}
	
	@Test
	public void testDemoMaze() throws IOException {
		PathFinder.solveMaze("mazes/demoMaze.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/demoMazeSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testMediumMaze() throws IOException {
		PathFinder.solveMaze("mazes/mediumMaze.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/mediumMazeSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testRandomMaze() throws IOException {
		PathFinder.solveMaze("mazes/randomMaze.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/randomMazeSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testStraight() throws IOException {
		PathFinder.solveMaze("mazes/straight.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/straightSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testTinyMaze() throws IOException {
		PathFinder.solveMaze("mazes/tinyMaze.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/tinyMazeSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testTinyOpen() throws IOException {
		PathFinder.solveMaze("mazes/tinyOpen.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/tinyOpenSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testTurn() throws IOException {
		PathFinder.solveMaze("mazes/turn.txt", "mazes/output.txt");
		assertEquals(countDots("mazes/turnSol.txt"), countDots("mazes/output.txt"));
	}
	
	@Test
	public void testUnsolvable() throws IOException {
		PathFinder.solveMaze("mazes/unsolvable.txt", "mazes/output.txt");
		assertEquals(0, countDots("mazes/output.txt"));
	}

}
