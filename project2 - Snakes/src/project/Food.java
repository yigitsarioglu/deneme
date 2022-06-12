package project;

import game.Direction;
import game.Drawable;
import simulator.Action;
import simulator.LocalInformation;
import ui.GridPanel;
import java.awt.Color;
import java.util.LinkedList;

/**
 * Class representing a Food.
 * This class is the child class of the Creature class and implements Drawable interface.
 * @author yiðit
 *
 */
public class Food extends Creature implements Drawable{
	
	private Cell cell_food;
	
	/**
	 * Constructs food object.
	 * @param food is the cell where the food will occur.
	 */
	public Food(Cell food) {
		this.cell_food=food;

	}
	
	
	/**
	 * Draws the object on the grid panel (with Yellow color for Food)
	 * @param panel grid panel to draw on
	 */
	@Override
	public void draw(GridPanel panel) {

		panel.drawSquare(cell_food.getX(), cell_food.getY(), Color.YELLOW);
	}

	
	/**
	 * Returns Cell occupied by the Food.
	 * @return cell where food is.
	 */
	public Cell getCell_food() {
		return this.cell_food;
	}
	
	
	/**
	 * Setter for Cell of the Food.
	 * @param cell_food is the cell where the food will be set.
	 */
	public void setCell_food(Cell cell_food) {
		this.cell_food = cell_food;
	}

	
	/**
	 *  Returns x-coordinate of the Cell occupied by the Food.
	 */
	public int getX() {
		return this.cell_food.getX();
	}
	
	/**
	 * Returns y-coordinate of the Cell occupied by the Food.
	 */
	public int getY() {
		return this.cell_food.getY();
	}
	
	
	
// Below methods are not needed for Food,they do not make any actions. 
//Below methods are added because the parent class contains them as abstract method.
	
	
	/**
	 * Food does not have an action,so method returns null.
	 */
	@Override
	public Action chooseAction(LocalInformation createLocalInformationForCreature) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	/**
	 * Food contains only one cell,so it does not crash with itself.
	 * So method returns false every time.
	 */
	public boolean checkCrash(Cell nextCell) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	/**
	 * Food does not move,so method returns null.
	 */
	public Cell move(Direction direction) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Food does not need stay method,it stays always.
	 */
	@Override
	public void stay() {
		
	}

	
	@Override
	/**
	 * Food does not reproduce
	 */
	public Creature reproduce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Food does not attack.
	 */
	public void attack(Creature attackedCreature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * Food contains only one cell,it does not have a cell list.
	 * So method return null.
	 */
	public LinkedList<Cell> getBody() {
		// TODO Auto-generated method stub
		return null;
	}

}