import java.util.Scanner;

/*
 * This class has methods to create two grid of Pixels and contains the DFS and BFS algorithms that will be used to label the grids
 * This class also contains the main method
 */
public class DFSBFSGrid {
	
	/* This method will welcome the user and ask for the size of the grid and return it*/
	private static int getUserSize() {
		
		//Declare local variables
		Scanner input = new Scanner(System.in);
		int size;
		
		//Prompt user for the grid size and validate that it is between 5 and 20
		System.out.println("Welcome! Give me a dimension for the grid between 5 and 20: ");
		size = Integer.parseInt(input.nextLine());
		while(size < 5 || size > 20) {
			System.out.print("Size must be between 5 and 20 try again: ");
			size = Integer.parseInt(input.nextLine());
		}
		
		//Add 2 to size to account for a wall that will be made around the grid and initialize both grids
		size += 2;
		
		return size;
			
	}//end getUserSize()
	
	
	
	private static double getUserDensity() {
		//Initialize local variables
		Scanner input = new Scanner(System.in);
		double density;
		
		// Prompt user for the density of the grid
		System.out.println("Give me a density for the grid between 0 and 1: ");
		density = Double.parseDouble(input.nextLine());
		
		//Validate density to make sure it's between 0 and 1
		while (density <= 0 || density >= 1.0) {
			System.out.print("The density must be between 0 and 1, try again: ");
			density = Double.parseDouble(input.nextLine());
		}
		
		//close the scanner
		input.close();
		
		
		return density;
		
	}//end getUserDensity();
	
	
	
	/*
	 * This method will take the information given by user and create two grids 
	 */
	private static void imageLabel(int size, double density, Pixel[][] dfsGrid, Pixel[][] bfsGrid) {
		/* Using the numbers from the user we will now make the grids
		 * 
		 We start from 1 because we need to make a wall around the grid, the wall will
	     be made after 
	     */
		for (int i = 1; i < size; i++) {
			for (int j = 1; j < size; j++) {

				/* Generate a random number between 0 and 1, if it is less than or equal to density,
				 * label that position as a 1, else make it a 0.
				 * Leave the order as 0 for now, that will be changed by the search algorithms
				 */
				double random = Math.random();
				if (random <= density) {
					dfsGrid[i][j] = new Pixel(1);
					bfsGrid[i][j] = new Pixel(1);
				} else {
					dfsGrid[i][j] = new Pixel(0);
					bfsGrid[i][j] = new Pixel(0);
				}
			}
		}

		// Make wall around the grids
		// Need to subtract 1 from size in order to avoid out of bounds
		for (int i = 0; i < size; i += (size - 1)) {
			for (int j = 0; j < size; j++) {
				dfsGrid[i][j] = new Pixel(0);
				bfsGrid[i][j] = new Pixel(0);
			}
		}

		// This loop is to make a wall in the front and back of the grids
		for (int i = 0; i < size; i++) {
			//Front of the grid
			dfsGrid[i][0] = new Pixel(0);
			bfsGrid[i][0] = new Pixel(0);
			
			//Back of the grid
			dfsGrid[i][size - 1] = new Pixel(0);
			bfsGrid[i][size - 1] = new Pixel(0);
		}

	}//end imageLabel()
	 
	
	
	//This method prints out the array
	public static void printArray(Pixel[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid.length; j++) {
				System.out.print(grid[i][j] + "  ");
			}
			System.out.println();
		}
		
		System.out.println();
	}//end printArray();
	
	
	
	
	/* This method is the implementation of the Depth First Search Algorithm 
	 * It uses stacks to find a path in a grid of Pixels labeling them in the order they were found  
	 * It searches by going down a single path for as long as it can, then backtracking once it hits a wall 
	 * and tries to find another path
	 */
	public static void labelUsingDFS(Pixel[][] grid, Stack<Pixel> stack, int seedCounter, int row, int column) {
		
		//Initialize local variables
		Pixel[] offset = new Pixel[4];
		Pixel position;
		Stack<Integer> optionStack = new Stack<Integer>();
		int option = 0, order = 1;;
		int r = 0, c = 0;
		
		//Offset will be used to change direction of scan
		offset[0] = new Pixel(0,1);   //right
		offset[1] = new Pixel(1,0);	  //down
		offset[2] = new Pixel(0, -1); //left
		offset[3] = new Pixel(-1, 0); //up
		
		
		//Push first seed position onto the stack and label it; increment the order 
		/* Also put 1 into optionStack as we assume you are going to find a seed to the right. 
		 * So when you come back to the bottom of the stack,
		 * you move onto the next search, which is down
		 */
		stack.push(new Pixel(row,column));
		optionStack.push(1);
		grid[row][column].setLabel(seedCounter);
		grid[row][column].setOrder(order);
		order++;
		
		
		//Loop to keep searching for more seeds until it either finds no more(empty stack) or hits a wall
		while(row != grid.length - 1 || column != grid.length - 1) {
			
			//Option is used to cycle through offset; offset is used to change direction of scan 
			while(option < 4) {
				
				/* Update r and c to scan around the seed
				 * getLabel is used to get the "row" value of the Pixel
				 * getOrder is used to get the "column" value of the Pixel
				 */
				r = row + offset[option].getLabel();
				c = column + offset[option].getOrder();
				
				//Check if there is a seed in this position
				if(grid[r][c].getLabel() == 1) {
					//If there is a seed  label it then exit the loop for now
					grid[r][c].setLabel(seedCounter);
					grid[r][c].setOrder(order);
					order++;
					break;
				}
				
				//Update option to continue looking around
				option++;
			}
			
			//Check if option is greater than 4, if it is then that means we did not find a seed
			if(option > 3) {
				
				//Check if stack is empty
				if(!stack.isEmpty()) {
				
				//Update row,column and option to last Pixel's row, column and option on the stack to continue searching 
				Pixel lastPixel = (Pixel) stack.pop();
				row = lastPixel.getLabel();
				column = lastPixel.getOrder();
				option = (int) optionStack.pop();
				
				
				} else { //Stack is empty 
					
					//If stack is empty then exit loop
					break;
				}
				
			} else { //If option < 4 we found a seed
				
				
				 /* update to the next seed's position, 
				  * save option onto another stack and keep searching
		          */
				
				//Push position onto the stack 
				position = new Pixel(r,c);
				stack.push(position);
				optionStack.push(option);
				
				row = r;
				column = c;
				option = 0;
			}
			
		}
		
		
	}//end labelUsingDFS()
				
			
		
	/*
	 * This method is the implementation of the Breadth First Search algorithm
	 * It finds a path of pixels by using a queue and labeling them as it goes along the queue
	 * It searches around each Pixel in "circles" 
	 */
	public static void labelUsingBFS(Pixel[][] grid, Queue<Pixel> queue, int seedCounter, int row, int column) {
		
		//Initialize local variables 
		Pixel[] offset = new Pixel[4];

		//Count is the order in which Pixels are found
		int count = 1;
		
		//r and c will act as row and column; they are used to not change row and column values
		int r = 0, c = 0;
		
		offset[0] = new Pixel(0,1);   //right
		offset[1] = new Pixel(1,0);	  //down
		offset[2] = new Pixel(0, -1); //left
		offset[3] = new Pixel(-1, 0); //up
	
		
		
		/* Update the first seed's label and order then add it into queue 
		 * Increment count
		 * The queue will hold the seed's position
		 */
		grid[row][column].setLabel(seedCounter);
		grid[row][column].setOrder(count);
		queue.add(new Pixel(row,column));
		count++;
		
		/*While the queue is not empty keep cycling through it, going through each pixel in it 
		 * and searching its neighbors
		 */
		while(!queue.isEmpty()) {
			
			/* Remove the next pixel position off the queue and set position equal to it 
			 * Get the row and column from position and set the int variables row and column accordingly
			 */
			Pixel position = queue.remove();
			
			//Use getLabel() to get the "row" value and getOrder() to get the "column" value 
			row = position.getLabel();
			column = position.getOrder();
			
			// Scan around the first pixel
			for (int i = 0; i < 4; i++) { 
				/* i must be less than 4 because there are 4 neighbors around the pixel
				 * we are using i to cycle though offset 
				 */

				/*
				 * Change direction of scan getLabel is used to get the "row" value of the Pixel
				 * getOrder is used to get the "column" value of the Pixel
				 */
				r = row + offset[i].getLabel();
				c = column + offset[i].getOrder();

				// Check if the neighbor is a seed
				if (grid[r][c].getLabel() == 1) {

					// if it is a seed, label it and add onto the queue
					grid[r][c].setLabel(seedCounter);
					grid[r][c].setOrder(count);
					queue.add(new Pixel(r,c));

					// Increment count
					count++;

				}

			}
		}
		
		
		
	}//end labelUsingBFS()
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		//Initialize variables
		Pixel[][] dfsGrid;
		Pixel[][] bfsGrid;
		
		Stack<Pixel> stack = new Stack<Pixel>();
		Queue<Pixel> queue = new Queue<Pixel>();
		
		int size;
		double density;
		int seedCount = 2;
		
		
		//Get the size and density
		/* Made two different methods because I could not use wrapper classes to get them both in one method */
		size = getUserSize();
		density = getUserDensity();
		 
		//Initialize both grids using the size obtained from user in the two methods above 
		dfsGrid = new Pixel[size][size];
		bfsGrid = new Pixel[size][size];
		
		//Label the grids 
		imageLabel(size, density, dfsGrid, bfsGrid);
		
		//Print grids before the search algorithms are performed 
		System.out.println("\nImage Before Depth First Search: ");
		printArray(dfsGrid);
		
		System.out.println("\nImage Before Breadth First Search: ");
		printArray(bfsGrid);
		
		
		//Scan through the grids and call the algorithm methods if a seed is found
		for(int row = 0; row < size; row++ ) {
			for(int column = 0; column < size; column++) {
				
				//Check if pixel is a seed
				if(dfsGrid[row][column].getLabel() == 1) {
					
					labelUsingDFS(dfsGrid, stack, seedCount, row, column);
					
					labelUsingBFS(bfsGrid, queue, seedCount, row, column);
					
					seedCount++;
					
				}
			}
		}
		
		
		System.out.println("\n\n\n\nImage After Depth First Search: ");
		printArray(dfsGrid);
		
		System.out.println("\nImage After Breadth First Search: ");
		printArray(bfsGrid);
			
		
	}//end main()
	
}
