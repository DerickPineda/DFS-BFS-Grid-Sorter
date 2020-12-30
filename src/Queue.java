import java.util.*;

/*
 * This class is for the Queue data structure
 * This class will be used to help implement the Breadth First Search algorithm
 */
public class Queue<E> {

	//Initialize instance variables
	List<E> list = new LinkedList<E>();
	
	
	
	
	
	//Method to add an object to the beginning of the queue
	public boolean add(E object) {
		return list.add(object);
	}
	
	//Method to remove an object from the queue
	public E remove() {
		
		//Check if queue is empty, if so then throw exception
		if(list.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		return list.remove(0);
	}
	
	//Method to check if queue is empty
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	
	
	
	
	@Override
	public String toString() {
		return list.toString();
	}
}
