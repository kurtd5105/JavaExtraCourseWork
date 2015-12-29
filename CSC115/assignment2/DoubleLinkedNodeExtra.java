/**
* @author Kurt Dorflinger
* 18 Feb 2015
* DoubleLinkedNodeExtra.java
* Double linked node for a double linked list.
*/
public class DoubleLinkedNodeExtra{
    Task task;
    DoubleLinkedNodeExtra next;
    DoubleLinkedNodeExtra prev;

    public DoubleLinkedNodeExtra(Task task) {
        this.task = task;
        this.next = null;
        this.prev = null;
    }

    public int getPriority() {
    	return task.getPriority();
    }

    public int getNumber() {
    	return task.getNumber();
    }

	public Task getTask() {
    	return task;
    }

}