/* HeapSort.java
   CSC 225 - Fall 2015
   Assignment 3 - Template for Heap Sort
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java HeapSort

   To conveniently test the algorithm with a large input, create a 
   text file containing space-separated integer values and run the program with
	java HeapSort file.txt
   where file.txt is replaced by the name of the text file.

   M. Simpson & B. Bird - 11/16/2015
   Implementation: Kurt Dorflinger V00820907
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

//Do not change the name of the HeapSort class
public class HeapSort{
	/* HeapSort(A)
		Sort the array A using heap sort.
		You may add additional functions (or classes) if needed, but the entire program must be
		contained in this file. 

		Do not change the function signature (name/parameters).
	*/
	//repairHeap written by Kurt D
	public static void repairHeap(int[] A, int start, int end){
		int top = start;
		int child, swap;
		//While there is a child to compare to
		while((top * 2) + 1 <= end){
			//Set the child to the top node's left node
			child = (top * 2) + 1;
			swap = top;
			//If the child is larger then set it to the swap
			if(A[swap] < A[child]){
				swap = child;
			}
			//If the right child is greater than the left node then set the right node to the swap
			if(child + 1 <= end && A[swap] < A[child + 1]){
				swap = child + 1;
			}
			//If the swap node isn't the top node then swap them and continue swapping otherwise
			//it is already in order
			if(swap == top){
				break;
			} else {
				int temp = A[top];
				A[top] = A[swap];
				A[swap] = temp;
				top = swap;
			}
		}
	}
	
	//sortToHeap written by Kurt D
	public static void sortToHeap(int[] A){
		//Repair the heap below a certain node and keep moving up the tree until the 
		//top node in the tree is repaired
		for(int i = (int)Math.floor((A.length - 2)/2); i >= 0; i--){
			repairHeap(A, i, A.length - 1);
		}
	}

	//HeapSort written by Kurt D
	public static void HeapSort(int[] A){
		//Start by changing the input array so that it has the heap property
		//The value at the top of the tree is the largest
	    sortToHeap(A);
	    int end = A.length - 1;
	    //While there are still elements left to be sorted
	    while(end > 0){
	    	//Swap the top node (largest) to the end of the current unsorted range in the array
	    	//aka removeMin
	    	int temp = A[0];
	    	A[0] = A[end];
	    	A[end] = temp;
	    	//Decrement the unsorted range
	    	end--;
	    	//Repair the heap, because now the node that was at the bottom of the heap is now on top
	    	repairHeap(A, 0, end);
	    }
	}

	/* main()
	   Contains code to test the HeapSort function. Nothing in this function 
	   will be marked. You are free to change the provided code to test your 
	   implementation, but only the contents of the HeapSort() function above 
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);

		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.currentTimeMillis();

		HeapSort(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		//Don't print out the values if there are more than 100 of them
		if (array.length <= 100){
			System.out.println("Sorted values:");
			for (int i = 0; i < array.length; i++)
				System.out.printf("%d ",array[i]);
			System.out.println();
		}

		//Check whether the sort was successful
		boolean isSorted = true;
		for (int i = 0; i < array.length-1; i++)
			if (array[i] > array[i+1]){
				System.out.println(array[i] + " > " + array[i+1] + " at i: " + i);
				isSorted = false;
			}

		System.out.printf("Array %s sorted.\n",isSorted? "is":"is not");
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}
