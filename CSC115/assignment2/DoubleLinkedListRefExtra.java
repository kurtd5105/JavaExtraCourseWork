/**
* @author Kurt Dorflinger
* 18 Feb 2015
* DoubleLinkedListRefExtra.java
* Double linked list implementation of TaskList interface using DoubleLinkedNodeExtra. Functionality as
* described in TaskList.
*/

public class DoubleLinkedListRefExtra implements TaskList{
	private int size = 0;
	private DoubleLinkedNodeExtra head = null;
	private DoubleLinkedNodeExtra tail = null;

	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		return false;
	}

	public int getLength() {
		return size;
	}

	public Task removeHead() {
		if(isEmpty()) {
			return null;
		//If there is only 1 task we can remove the head and tail (same thing)
		} else if(size == 1) {
			DoubleLinkedNodeExtra temp = head;
			head = null;
			tail = null;
			size--;
			return temp.getTask();
		}
		DoubleLinkedNodeExtra temp = head;
		head = head.next;
		size--;
		return temp.getTask();
	}

	public Task removeTail() {
		if(isEmpty()) {
			return null;
		//If there is only 1 task we can remove the head and tail (same thing)
		} else if(size == 1) {
			DoubleLinkedNodeExtra temp = tail;
			tail = null;
			head = null;
			size--;
			return temp.getTask();
		}
		DoubleLinkedNodeExtra temp = tail;
		tail = tail.prev;
		size--;
		return temp.getTask();
	}

	public Task remove(Task t) {
		if(isEmpty()) {
			return null;
		//Check if the task is the head
		} else if (head.getPriority() == t.getPriority() && head.getNumber() == t.getNumber()) {
			if(size == 1) {
				tail = null;
			}
			return removeHead();
		//Check if the task is the tail
		} else if (tail.getPriority() == t.getPriority() && tail.getNumber() == t.getNumber()) {
			if(size == 1) {
				head = null;
			}
			return removeTail();
		}

		DoubleLinkedNodeExtra curr = head.next;
		DoubleLinkedNodeExtra before = head;
		while(curr.next != null) {
			//Check if we have passed what we are trying to remove, since tasks are sorted the task does not exist
			if(curr.getPriority() < t.getPriority()) {
				return null;
			}
			//If a match is found we remove and return it
			if(curr.getPriority() == t.getPriority() && curr.getNumber() == t.getNumber()) {
				before.next = curr.next;
				DoubleLinkedNodeExtra temp = before.next;
				temp.prev = before;
				size--;
				return curr.getTask();
			} else {
				before = curr;
				curr = curr.next;
			}
		}
		//Once we have reached the last task we check if it is the task to be removed otherwise 
		if(curr.getPriority() == t.getPriority() && curr.getNumber() == t.getNumber()) {
			before.next = curr.next;
			DoubleLinkedNodeExtra temp = before.next;
			temp.prev = before;
			size--;
			return curr.getTask();
		}
		return null;
	}

	public void insert(Task t) {
		if(isEmpty()) {
			head = new DoubleLinkedNodeExtra(t);
			tail = head;
		} else {
			//Place the task at the head if it has a greater priority
			if(t.getPriority() > head.getPriority()) {
				//If there is only one other task in the list head and tail must be manipulated
				if(size == 1) {
					head = new DoubleLinkedNodeExtra(t);
					head.next = tail;
					tail.prev = head;
				} else {
					DoubleLinkedNodeExtra temp = head;
					head = new DoubleLinkedNodeExtra(t);
					head.next = temp;
					temp.prev = head;
				}
			//Place the task at the tail if it has a lesser priority
			} else if(tail.getPriority() > t.getPriority()) {
				//If there is only one other task in the list head and tail must be manipulated
				if(size == 1) {
					tail = new DoubleLinkedNodeExtra(t);
					head.next = tail;
					tail.prev = head;
				} else {
					DoubleLinkedNodeExtra newNode = new DoubleLinkedNodeExtra(t);
					tail.next = newNode;
					newNode.prev = tail;
					tail = newNode;
				}
			} else {
				DoubleLinkedNodeExtra curr = head.next;
				int tPriority = t.getPriority();
				
				//While the task priority is less than or equal to the current node priority move down the list
				while(curr != null && curr.getPriority() >= tPriority) {
					curr = curr.next;
				}
				
				//Insert the task at the point that was reached, if curr is null then at the end of the list
				if(curr == null) {
					DoubleLinkedNodeExtra temp = tail;
					tail = new DoubleLinkedNodeExtra(t);
					temp.next = tail;
					tail.prev = temp;
				} else {
					DoubleLinkedNodeExtra newNode = new DoubleLinkedNodeExtra(t);
					newNode.next = curr;
					newNode.prev = curr.prev;
					curr.prev.next = newNode;
					curr.prev = newNode;
				}
			}
		}
		size++;
	}

	public Task retrieve(int i) {
		//If the index given is out of bounds or negative
		if(i > size - 1 || i < 0) {
			return null;
		}
		DoubleLinkedNodeExtra curr;
		int n = 0;
		//If the task is in the second half of the list start transversal from tail
		if(i > size/2) {
			curr = tail;
			//Reverse i to get amount of transversals from the end of the list rather than the front
			i = (size - 1) - i;
			//Transverse the list in reverse
			while(n++ < i) {
				curr = curr.prev;
			}
		} else {
			curr = head;
			//Transverse the list
			while(n++ < i) {
				curr = curr.next;
			}
		}
		return curr.getTask();
	}	
}