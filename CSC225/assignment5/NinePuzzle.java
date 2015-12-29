/* NinePuzzle.java
   CSC 225 - Fall 2015
   Assignment 5 - Template for the 9-puzzle
   
   This template includes some testing code to help verify the implementation.
   Input boards can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java NinePuzzle
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. boards.txt), run the program with
    java NinePuzzle boards.txt
	
   The input format for both input methods is the same. Input consists
   of a series of 9-puzzle boards, with the '0' character representing the 
   empty square. For example, a sample board with the middle square empty is
   
    1 2 3
    4 0 5
    6 7 8
   
   And a solved board is
   
    1 2 3
    4 5 6
    7 8 0
   
   An input file can contain an unlimited number of boards; each will be 
   processed separately.
  
   B. Bird    - 07/11/2014
   M. Simpson - 11/07/2015
*/
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

public class NinePuzzle{

	//The total number of possible boards is 9! = 1*2*3*4*5*6*7*8*9 = 362880
	public static final int NUM_BOARDS = 362880;


	/*  SolveNinePuzzle(B)
		Given a valid 9-puzzle board (with the empty space represented by the 
		value 0),return true if the board is solvable and false otherwise. 
		If the board is solvable, a sequence of moves which solves the board
		will be printed, using the printBoard function below.
	*/
	//SolveNinePuzzle written by Kurt D
	public static boolean SolveNinePuzzle(int[][] B){
		//There are NUM_BOARDS and max 4 possible moves from any node
		int[][] G = new int[NUM_BOARDS][4];

		//Initialize all values in G to -1
		for(int i = 0; i < NUM_BOARDS; i++){
			for(int k = 0; k < 4; k++){
				G[i][k] = -1;
			}
		}
		generatePuzzleGraph(G, 9);

		LinkedList<Integer> path = new LinkedList<Integer>();
		boolean result = false;
		result = runBFS(G, B, path);

		if(result){
			//Print out all nodes in path
			Iterator<Integer> it = path.descendingIterator();
			while (it.hasNext()) {
				printBoard(getBoardFromIndex(it.next()));
			}
			return true;
		}
		
		return false;
		
	}

	//runBFS written by Kurt D
	public static boolean runBFS(int[][] G, int[][] B, LinkedList<Integer> path){
		int[][] solution = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		boolean[] visited = new boolean[NUM_BOARDS];
		int[] from = new int[NUM_BOARDS];
		Queue<Integer> q = new LinkedList<Integer>();

		//Initialize all values
		for(int i = 0; i < NUM_BOARDS; i++){
			visited[i] = false;
			from[i] = -1;
		}
		int u = getIndexFromBoard(B);
		int start = u;
		int v = getIndexFromBoard(solution);
		q.add(u);
		//While there is something in the queue to visit
		while(q.peek() != null){//!q.isEmpty()){
			//Remove the number from the queue and set it to visited
			u = q.remove();
			visited[u] = true;
			//If the number is the solution then add the path taken and return true
			if(u == v){
				while(u != start){
					path.add(u);
					u = from[u];
				}
				path.add(start);
				return true;
			}
			//Add all adjacent boards to the queue and set them to be from this node
			for(int i = 0; i < 4; i++){
				if(G[u][i] != -1 && !visited[G[u][i]]){
					q.add(G[u][i]);
					from[G[u][i]] = u;
				}
			}
		}
		return false;
	}

	//generatePuzzleGraph written by Kurt D
	public static void generatePuzzleGraph(int[][] G, int N){
		int n = (int)Math.sqrt(N);
		int[][] B = new int[n][n];
		int[][] Bp = new int[n][n];
		int i = 0;
		int j = 0;
		boolean found = false;
		int displayed = 0;
		
		for(int a = 0; a < NUM_BOARDS; a++){
			B = getBoardFromIndex(a);

			//Find the column and row the 0 is in
			for(i = 0, found = false; i < n; i++){
				for(j = 0; j < n; j++){
					if(B[i][j] == 0){
						found = true;
						break;
					}
				}
				if(found)
					break;
			}
			//Construct the graph by swapping the 0 space to adjacent spots
			if(i > 0){
				swapCopy(B, Bp, i, j, i - 1, j, n);
				G[a][0] = getIndexFromBoard(Bp);
			}
			if(i < n - 1){
				swapCopy(B, Bp, i, j, i + 1, j, n);
				G[a][1] = getIndexFromBoard(Bp);
			}
			if(j > 0){
				swapCopy(B, Bp, i, j, i, j - 1, n);
				G[a][2] = getIndexFromBoard(Bp);
			}
			if(j < n - 1){
				swapCopy(B, Bp, i, j, i, j + 1, n);
				G[a][3] = getIndexFromBoard(Bp);
			}	
		}
	}

	//swapCopy written by Kurt D
	public static void swapCopy(int[][] source, int[][] dest, int iStart, int jStart, int iEnd, int jEnd, int n){
		//Copy the board
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				dest[i][j] = source[i][j];
			}
		}
		//Swap the two spots
		dest[iStart][jStart] = source[iEnd][jEnd];
		dest[iEnd][jEnd] = source[iStart][jStart];
	}
	
	/*  printBoard(B)
		Print the given 9-puzzle board. The SolveNinePuzzle method above should
		use this method when printing the sequence of moves which solves the input
		board. If any other method is used (e.g. printing the board manually), the
		submission may lose marks.
	*/
	public static void printBoard(int[][] B){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++)
				System.out.printf("%d ",B[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	
	/* Board/Index conversion functions
	   These should be treated as black boxes (i.e. don't modify them, don't worry about
	   understanding them). The conversion scheme used here is adapted from
		 W. Myrvold and F. Ruskey, Ranking and Unranking Permutations in Linear Time,
		 Information Processing Letters, 79 (2001) 281-284. 
	*/
	public static int getIndexFromBoard(int[][] B){
		int i,j,tmp,s,n;
		int[] P = new int[9];
		int[] PI = new int[9];
		for (i = 0; i < 9; i++){
			P[i] = B[i/3][i%3];
			PI[P[i]] = i;
		}
		int id = 0;
		int multiplier = 1;
		for(n = 9; n > 1; n--){
			s = P[n-1];
			P[n-1] = P[PI[n-1]];
			P[PI[n-1]] = s;
			
			tmp = PI[s];
			PI[s] = PI[n-1];
			PI[n-1] = tmp;
			id += multiplier*s;
			multiplier *= n;
		}
		return id;
	}
		
	public static int[][] getBoardFromIndex(int id){
		int[] P = new int[9];
		int i,n,tmp;
		for (i = 0; i < 9; i++)
			P[i] = i;
		for (n = 9; n > 0; n--){
			tmp = P[n-1];
			P[n-1] = P[id%n];
			P[id%n] = tmp;
			id /= n;
		}
		int[][] B = new int[3][3];
		for(i = 0; i < 9; i++)
			B[i/3][i%3] = P[i];
		return B;
	}
	

	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */
		/*int[][] test = new int[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				test[i][j] = 0;
			}
		}
		boolean isSolvable = SolveNinePuzzle(test);*/

		
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read boards until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for (int i = 0; i < 3 && s.hasNextInt(); i++){
				for (int j = 0; j < 3 && s.hasNextInt(); j++){
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < 9){
				System.out.printf("Board %d contains too few values.\n",graphNum);
				break;
			}
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			if (isSolvable)
				System.out.printf("Board %d: Solvable.\n",graphNum);
			else
				System.out.printf("Board %d: Not solvable.\n",graphNum);
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n Average Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:0);

	}

}
