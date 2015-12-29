/**
* @author Kurt Dorflinger
* 18 Feb 2015
* TaskListTester.java
* Tests DoubleLinkedListRefExtra with 10 different tests.
*/
public class DoubleLinkTesterExtra {
	/**
     * Test the order of the priority of the tasks to make sure they're in the correct order.
     * Tasks should be in order of decreasing priority.
     * @param list DoubleLinkedListRefExtra to have its order checked.
     */
	private static void testOrder(DoubleLinkedListRefExtra list) {
		System.out.println("\n-------------------------------Testing Order-------------------------------\n");
		Task temp;
		int prevPriority, prevNumber, tempPriority, tempNumber;
		temp = list.retrieve(0);
		prevPriority = temp.getPriority();
		prevNumber = temp.getNumber();
		int i = 1;

		for(; i < list.getLength(); i++) {
			temp = list.retrieve(i);
			//Verify that an element was retrieved
			if(temp != null) {
				tempPriority = temp.getPriority();
				//System.out.format("Task (%1$d, %2$d).\n", tempPriority, temp.getNumber());
				//Verify that the current priority is equal to or lesser than the previous
				if(tempPriority <= prevPriority) {
					prevPriority = tempPriority;
				} else {
					System.out.format("TEST FAILED.\nTask (%1$d, %2$d) is not ordered correctly with the previous task (%3$d, %4$d).\n", 
									   tempPriority, temp.getNumber(), prevPriority, prevNumber);
					break;
				}
			} else {
				System.out.format("TEST FAILED.\nRetrieve failed to retrieve at index %1$d.\n", i);
				break;
			}
		}

		//If the loop reached the end then all checks were passed
		if(i == list.getLength()) {
			System.out.println("TEST PASSED.");
		}
	}

	/**
     * Removes all tasks from the list using both remove and removeHead methods.
     * Removes tasks from index 1 until there are no more, and then removes the head.
     * @param list DoubleLinkedListRefExtra to be cleared.
     */
	private static void testClear(DoubleLinkedListRefExtra list) {
		System.out.println("\n-------------------------------Testing Clearing List-------------------------------\n");
		Task temp;
		int listLength = list.getLength() - 2;

		//Remove every element except the first from the 1st index
		for(int i = 0; i < listLength; i++) {
			temp = list.remove(list.retrieve(1));
			//If no task was received then the remove failed
			if(temp == null) {
				System.out.format("TEST FAILED.\nList returned null on a valid removal at index %1$d.\n", i);
				break;
			}
		}

		list.removeTail();
		list.removeHead();
		//Verify that the length agrees with the emptiness
		if(list.isEmpty() && list.getLength() == 0) {
			System.out.println("TEST PASSED.");
		} else {
			System.out.println("TEST FAILED.\nList is not empty or size > 0 after removing every element and the head. Size: " + list.getLength());
		}
	}

	/**
     * Retrieves each task and compares it to the expected 
     * Removes tasks from index 1 until there are no more, and then removes the head.
     * @param list DoubleLinkedListRefExtra to be cleared.
     */
	private static void testRetrieve(DoubleLinkedListRefExtra list, int[] expectedPriorities, int[] expectedNumbers) {
		System.out.println("\n-------------------------------Testing Retrieve-------------------------------\n");
		Task temp;
		int i = 0;
		int length = list.getLength();

		//Check that there is the same amount of list nodes to expected nodes
		if(length != expectedPriorities.length) {
			System.out.println("TEST FAILED. List length: " + length + " expected priorities: " + expectedPriorities.length + '.');
			//Set length and i to -1 to stop the test
			length = -1;
			i = -1;
		}

		//Verify that all tasks are in the expected orders
		for(; i < length; i++) {
			temp = list.retrieve(i);
			if(temp != null) {
				if(temp.getPriority() != expectedPriorities[i] || temp.getNumber() != expectedNumbers[i]) {
					System.out.format("TEST FAILED.\nRetrieve failed to retrieve expected task (%1$d, %2$d). Got (%3$d, %4$d) instead.\n", 
									  expectedPriorities[i], expectedNumbers[i], temp.getPriority(), temp.getNumber());
					break;
				}
			} else {
				System.out.format("TEST FAILED.\nRetrieve failed to retrieve at index %1$d.\n", i);
				break;
			}
		}

		//If the loop reached the end then all checks were passed
		if(i == list.getLength()) {
			System.out.println("TEST PASSED.");
		}
	}

	private static void printTasks(DoubleLinkedListRefExtra list) {
		Task temp;
		System.out.println("----------------");
		for(int i = 0; i < list.getLength(); i++) {
			temp = list.retrieve(i);
			System.out.format("Task (%1$d, %2$d).\n", temp.getPriority(), temp.getNumber());
		}
		System.out.println("----------------");
	}

	public static void main(String[] args) {
		DoubleLinkedListRefExtra list = new DoubleLinkedListRefExtra();
		list.insert(new Task(4, 100));
		//printTasks(list);
		list.insert(new Task(3, 22));
		//printTasks(list);
		list.insert(new Task(1, 11));
		//printTasks(list);
		list.insert(new Task(3, 13));
		//printTasks(list);
		list.insert(new Task(1, 21));
		//printTasks(list);
		list.insert(new Task(3, 12));
		//printTasks(list);
		int[] expectedPrioritiesS1 = {4, 3, 3, 3, 1, 1};
		int[] expectedNumbersS1 = {100, 22, 13, 12, 11, 21};

		System.out.println("=============================================Test Set 1=============================================");

		//Test 1
		testRetrieve(list, expectedPrioritiesS1, expectedNumbersS1);
		//Test 2
		testOrder(list);
		//Test 3
		testClear(list);

		//If the list is cleared then other tests may proceed
		if(list.isEmpty()) {
			System.out.println("\n\n=============================================Test Set 2=============================================");
			list.insert(new Task(10, 1790));
			//printTasks(list);
			list.insert(new Task(100, 1060));
			//printTasks(list);
			list.insert(new Task(100, 10740));
			//printTasks(list);
			list.insert(new Task(30, 29));
			//printTasks(list);
			list.insert(new Task(31, 28));
			list.insert(new Task(30, 1600));
			list.insert(new Task(30, 238));
			list.insert(new Task(100, 7100));
			list.insert(new Task(100, 100));
			list.insert(new Task(100, 18060));
			list.insert(new Task(100, 107780));
			list.insert(new Task(100, 949));
			list.insert(new Task(100, 929));
			list.insert(new Task(100, 998));

			int[] expectedPrioritiesS2 = {100, 100, 100, 100, 100, 100, 100, 100, 100, 31, 30, 30, 30, 10};
			int[] expectedNumbersS2 = {1060, 10740, 7100, 100, 18060, 107780, 949, 929, 998, 28, 29, 1600, 238, 1790};

			//Test 4
			testRetrieve(list, expectedPrioritiesS2, expectedNumbersS2);
			testClear(list);
		} else {
			System.out.println("TEST FAILED. List was not empty for test set 2.");
		}

		if(list.isEmpty()) {
			System.out.println("\n\n=============================================Test Set 3=============================================");

			list.insert(new Task(100, 1001));
			list.insert(new Task(100, 1002));
			list.insert(new Task(100, 1003));

			int[] expectedPrioritiesS3 = {100, 100, 100};
			int[] expectedNumbersS3 = {1001, 1002, 1003};

			//Test 5
			testRetrieve(list, expectedPrioritiesS3, expectedNumbersS3);

			System.out.println("\n-------------------------------Testing Remove Head-------------------------------\n");
			
			//Test 6
			list.removeHead();
			if(list.getLength() == 2) {
				System.out.println("TEST PASSED.");
			} else {
				System.out.println("TEST FAILED.");
			}
			list.removeHead();

			testClear(list);
		} else {
			System.out.println("TEST FAILED. List was not empty for test set 3.");
		}

		if(list.isEmpty()) {
			System.out.println("\n\n=============================================Test Set 4=============================================");
			list.insert(new Task(1, 60));
			list.insert(new Task(1, 31));
			list.insert(new Task(2, 750));
			list.insert(new Task(3, 70));
			list.insert(new Task(3, 165));
			testOrder(list);
			list.insert(new Task(4, 23));
			list.remove(list.retrieve(3));
			list.remove(list.retrieve(2));

			int[] expectedPrioritiesS4 = {4, 3, 1, 1};
			int[] expectedNumbersS4 = {23, 70, 60, 31};
			//Test 7
			testRetrieve(list, expectedPrioritiesS4, expectedNumbersS4);

			testClear(list);
		} else {
			System.out.println("TEST FAILED. List was not empty for test set 4.");
		}

		if(list.isEmpty()) {
			System.out.println("\n\n=============================================Test Set 5=============================================");
			list.insert(new Task(5, 360));
			list.insert(new Task(3, 351));
			list.insert(new Task(2, 35750));
			list.removeHead();
			list.insert(new Task(7, 50));
			list.insert(new Task(8, 355));
			list.insert(new Task(8, 254));
			list.remove(list.retrieve(3));
			list.remove(list.retrieve(2));

			int[] expectedPrioritiesS5 = {8, 8, 2};
			int[] expectedNumbersS5 = {355, 254, 35750};
			
			//Test 8
			testRetrieve(list, expectedPrioritiesS5, expectedNumbersS5);

			testClear(list);
		} else {
			System.out.println("TEST FAILED. List was not empty for test set 5.");
		}

		if(list.isEmpty()) {
			System.out.println("\n\n=============================================Test Set 6=============================================");
			list.insert(new Task(10, 100));
			list.insert(new Task(10, 324));
			list.insert(new Task(10, 224));
			list.insert(new Task(10, 284));
			list.insert(new Task(10, 824));
			list.insert(new Task(10, 282));
			//Remove tasks and then insert them again
			list.insert(list.remove(list.retrieve(2)));
			list.insert(list.remove(list.retrieve(4)));
			list.insert(list.remove(list.retrieve(3)));

			int[] expectedPrioritiesS6 = {10, 10, 10, 10, 10, 10};
			int[] expectedNumbersS6 = {100, 324, 284, 224, 282, 824};
			
			//Test 9
			testRetrieve(list, expectedPrioritiesS6, expectedNumbersS6);

			list.insert(list.remove(list.retrieve(3)));
			list.insert(list.remove(list.retrieve(2)));
			list.insert(list.remove(list.retrieve(3)));
			list.insert(list.remove(list.retrieve(2)));

			int[] newOrder = {100, 324, 224, 284, 824, 282};
			
			//Test 10
			testRetrieve(list, expectedPrioritiesS6, newOrder);

			testClear(list);
		} else {
			System.out.println("TEST FAILED. List was not empty for test set 6.");
		}
	}
}