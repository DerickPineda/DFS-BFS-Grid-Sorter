/*
 * This class is what will be placed inside the grid (Pixels), the variable label will be the seed and
 * the variable order is the order in which it was discovered 
 * Class will also be used to help in the search algorithms
 */
public class Pixel {

	//Initialize instance variables
	public static final int DEFAULT_ORDER = 0;
	
	private int label;
	private int order;
	
	
	
	
	//Constructors
	public Pixel(int label, int order) {
		this.label = label;
		this.order = order;
	}
	
	public Pixel(int label) {
		this(label, DEFAULT_ORDER);
	}
	
	
	
	//Method to set the label for pixel
	public void setLabel(int label) {
		this.label = label;
	}
	
	//Method to set the order for the pixel
	public void setOrder(int order) {
		this.order = order;
	}
	
	//Method to get pixel label
	public int getLabel() {
		return label;
	}
	
	//Method to get pixel order
	public int getOrder() {
		return order;
	}
	
	
	
	
	@Override
	public String toString() {
		
		//Done to make the grid look consistent
		String pixel = String.format("(%-2s,%2s)", label, order);
		return pixel;
	}
	
}
