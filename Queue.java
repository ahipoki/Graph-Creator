import java.util.ArrayList;

public class Queue {

	ArrayList<String> queue = new ArrayList<String>();
	
	public void enqueue(String s)
	{//Add an item to the queue
		queue.add(s);
		//Add an item to the queue
	}
	
	public String dequeue()
	{//Remove an item from the queue
		String s = queue.get(0);
		//Get the item at spot 0 in the queue
		queue.remove(0);
		//Remove it
		return s;
		//Return the item removed
	}
	
	public boolean isEmpty()
	{//Queue is empty
		return queue.isEmpty();
		//Return the empty queue
	}
}
