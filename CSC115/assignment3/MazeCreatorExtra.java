/**
* @author Kurt Dorflinger
* 4 Mar 2015
* MazeCreatorExtra.java
* Maze creator that allows you to create mazes more easily. Automatically handles the size, start and finish locations.
* That is the hardest part of making the mazes, so it's nice that it's automated. It also helps you make less mistakes 
* while making a maze. The reason for the maze only being able to end at the bottom is because RunSolver trims trailing
* spaces, making the right side impossible and therefore the other side should logically be impossible as well.
*/

import java.io.*;
import java.util.*;

public class MazeCreatorExtra {
	/**
	* Checks if the line entered is a valid line of the maze
	* @return true if there is something wrong, false if there is no problem found.
	*/
	private static boolean checkProblems(String mazeLine, int currRow, int rows, int cols) {
		if(mazeLine == null) {
			return true;
		}
		int length = mazeLine.length();
		int spaceCount = 0;
		char k;
		if(length != cols) {
			System.out.format("Character input count mismatch. Expected %1$d, got %2$d.\n", cols, length);
			return true;
		}
		//If something other than a space or star is found then there is a problem
		for(int i = 0; i < cols; i++) {
			k = mazeLine.charAt(i);
			if(k != 32 && k != 42) {
				System.out.format("Character '%1$c' found that was not a space or star at index %2$d.\n", k, i);
				return true;
			}
			//Keep track of the amount of spaces
			if(k == 32){
				spaceCount++;
			}
		}
		//If the first or last character on the maze is a space then there is an issue
		if(mazeLine.charAt(0) == 32 || mazeLine.charAt(length - 1) == 32) {
			System.out.println("A space was found at the start or end of the input. All maze rows must begin and end with a wall.");
			return true;
		}
		//The first and last lines must have only 1 space, if they don't then there is a problem
		if(currRow == 0 || currRow == rows - 1) {
			if(spaceCount != 1) {
				System.out.println("Maze needs one open space at the start and end of the maze.");
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Incorrect amount of arguments (need output filename).");
			System.exit(1);
		}

		PrintWriter outputFile = null;
		try {
			outputFile = new PrintWriter(args[0], "UTF-8");
		

			int rows = 0;
			int cols = 0;
			Scanner inputStream = new Scanner(System.in);
			System.out.println("How many rows would you like your maze to have?");
			//Get a number between 3 and 100
			while(rows < 3 || rows > 100) {
				try {
					rows = inputStream.nextInt();
					if(rows < 3 || rows > 100) {
						System.out.println("Please enter an integer greater than 2 but lesser than 101.");
					}
				} catch (InputMismatchException error) {
					System.out.println("Please enter an integer.");
				}
			}
			System.out.println("How many columns would you like your maze to have?");
			//Get a number between 3 and 100
			while(cols < 3 || cols > 100) {
				try {
					cols = inputStream.nextInt();
					if(cols < 3 || cols > 100) {
						System.out.println("Please enter an integer greater than 2 but lesser than 101.");
					}
				} catch (InputMismatchException error) {
					System.out.println("Please enter an integer.");
				}
			}
			String[] maze = new String[rows];
			System.out.format("Enter '*' for a wall, or a space for a path.\nEnter %1$d characters, each beginning and ending with a wall.\nFor the first and last lines, only leave 1 open space for the start and end of the maze.\n", cols);
			inputStream.nextLine();

			//Get each line of the maze
			for(int i = 0; i < rows; i++) {
				System.out.format("Row %1$d of %2$d: ", i + 1, rows);
				do {
					try {
						maze[i] = inputStream.nextLine();
					} catch (InputMismatchException error) {
						System.out.println("Please enter a string consisting of '*' for walls and spaces for empty space.");
					} 
				} while(checkProblems(maze[i], i, rows, cols));
			}

			//Begin writing the maze
			outputFile.format("%1$d %2$d\n", rows, cols);
			for(int i = 0; i < cols; i++) {
				if(maze[0].charAt(i) == 32) {
					outputFile.format("0 %1$d\n", i);
					break;
				}
			}
			for(int i = 0; i < cols; i++) {
				if(maze[rows - 1].charAt(i) == 32) {
					outputFile.format("%1$d %2$d\n", rows - 1, i);
					break;
				}
			}
			for(int i = 0; i < rows; i++) {
				outputFile.println(maze[i]);
			}
		} catch (IOException error) {
			System.out.println("Error creating file.");
			System.exit(1);
		} finally {
			if(outputFile != null) {
				outputFile.close();
			}
		}
	}
}