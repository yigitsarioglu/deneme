package project;

/**
 * Creates new cells in the game.
 * @author yiðit
 * 
 */
public class Cell {
	private int x, y;

	/**
	 * Constructs the cell.
	 * @param x x-coordinate of the cell on the grid
	 * @param y y-coordinate of the cell on the grid
	 */
	public Cell(int x, int y) {
		this.x = x;
		this.y =y ;
	
	}
	
	/**
	 * Getter for the x-coordinate of the cell.
	 * @return x-coordinate of the cell.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Setter for the x-coordinate of the cell.
	 * @param x x-coordinate of the cell on the grid.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	
	/**
	 * Getter for the y-coordinate of the cell.
	 * @return y-coordinate of the cell.
	 */
	public int getY() {
		return this.y;
	}
	
	
	/**
	 * Setter for the y-coordinate of the cell.
	 * @param y y-coordinate of the cell on the grid.
	 */
	public void setY(int y) {
		this.y = y;
	}
}	
	