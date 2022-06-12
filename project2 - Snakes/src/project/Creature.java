package project;
import java.util.LinkedList;

import game.Direction;
import game.Drawable;
import simulator.Action;
import simulator.LocalInformation;

/**
 * Class representing a Creature.
 * This class is a abstract class for the child classes (snake,food). 
 * Implements the Drawable interface
 * @author yiðit
 *
 */
public abstract class Creature implements Drawable{
	
	/**
	 * Returns the chosen action
	 * @param createLocalInformationForCreature provides informations for each creature
	 * @return chosen Action
	 */
	public abstract Action chooseAction(LocalInformation createLocalInformationForCreature);
	
	
	/**
	 * Returns the head coordinate of snake
	 * @return x-coordinate of the snake's head.
	 */
	public abstract int getX();
	
	/**
	 * Returns the head coordinate of snake
	 * @return y-coordinate of the snake's head.
	 */
	public abstract int getY();
	
	/**
	 * Checks whether the snake crash with its own body
	 * @param nextCell cell which this method will control,and if it is false, snake could move
	 * @return false, if snake will not crash with its own body.  
	 */
	public abstract boolean checkCrash(Cell nextCell);
	
	
	/**
	 * Moves to adjacent empty cell.
	 * @param direction randomly selected free direction,which snake can move.
	 * @return the cell,which is removed from the tail of snake after move.
	 */
	public abstract Cell move(Direction direction);
	
	
	/**
	 * Stay at the same space.
	 */
	public abstract void stay();
	
	
	/**
	 * Creates a new snake.
	 * When snake reaches size 8, it will divide into two snakes of size 4.
	 * The newly born snake's head will be the old snake's tail.
	 * @return newly born Creature(snake)
	 */
	public abstract Creature reproduce();
	
	
	/**
	 * Eats the food at the adjacent cell.
	 * When a snake consumes that food, it will grow by one cell.
	 * Only snakes could eat.
	 * @param attackedCreature Creature(Food) which snakes attack/eat.
	 */
	public abstract void attack(Creature attackedCreature);
	
	
	/**
	 * Returns the cell list which snake contains in its body.
	 * @return list of cells in the snake body.
	 */
	public abstract LinkedList<Cell> getBody();

}
