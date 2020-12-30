import java.util.Arrays;
import java.util.EmptyStackException;

/*
 * This class is for the Stack data structure which will be used in Depth First Search Algorithm
 */
public class Stack<E>{

	public static final int DEFUALT_CAPACITY = 10;
	
	//Initialize instance variables
	private Object[] stack;
	private int capacity;
	private int top = 0;
	
	
	
	
	
	//Constructors
	public Stack(int capacity) {
		this.capacity = capacity; 
		stack = new Object[capacity];
	}

	public Stack(){
		this(DEFUALT_CAPACITY);
	}
	
	
	
	
	
	
	//Method to check if stack is empty
	public boolean isEmpty() {
		return (top <= 0);
	}
	
	//Method to "push" or add something onto the stack
	public boolean push(E object) {
		
		//Check if the stack is full, if so then call doubleStack() to increase the size
		if(top == capacity) {
			doubleStack();
		} 
		
		//Add the object onto the stack and increment top
		//Note that top will always be one ahead of the "top" of the stack 
		stack[top] = object;
		top += 1;
		return true;
		
	
	}
	
	//Method to "pop" or remove something from the stack
	public Object pop() {
		
		//Check if the stack is empty before trying to remove anything, if it is throw an exception
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		
		//If stack is not empty, then decrement top and remove the object, then return the object
		top -= 1;
		Object obj = stack[top];
		stack[top] = null;
		return obj;
		
	}
	
	//Method to "peek" the top of the stack; will return what is at the top of the stack
	public Object peek() {
		
		//Return object at top - 1 because top is always one ahead of "top" of stack
		return stack[top - 1];
	}
	
	//Method to increase the size of the array
	private void doubleStack() {
		
		//Initialize  new stack and change capacity
		capacity *= 2;
		Object[] biggerStack = new Object[capacity];
		
		//Transfer over everything from the old stack onto the new one 
		for(int i = 0; i < stack.length; i++) {
			biggerStack[i] = stack[i];
		}
		
		//Make stack "equal" to biggerStack; doing this makes stack have a reference that points to biggerStack
		stack = biggerStack;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return Arrays.toString(stack);
	}
}
