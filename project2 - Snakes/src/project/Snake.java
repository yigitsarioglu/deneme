package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game.Direction;
import game.Drawable;
import simulator.Action;
import simulator.LocalInformation;
import ui.GridPanel;

/**
 * Creates new snake in the game.
 * @author yiðit
 *
 */
public class Snake extends Creature implements Drawable{

	private LinkedList <Cell> snakePartList =new LinkedList <Cell> ();
	private Cell head; 
	Direction directionToMove;

	/**
	 * Constructs new snake.
	 * I do not use  this constructor,but I want to show another version of snake creation.
	 * @param initPos cell which is added to snake cell list.
	 */
	public Snake(Cell initPos) {
		head = initPos;
		snakePartList.add(head);
	}

	/**
	 * Constructs new snake.
	 * Initially it should contain four cell in the list.
	 * @param cells cell list which newly born snake would contain.
	 */
	public Snake(LinkedList <Cell> cells) {
		this.snakePartList=cells;
	}


	/**
	 * Returns the body(list of cells) of the snake.
	 * @return cell list of snakes.
	 */
	public LinkedList<Cell> getBody() {
		return this.snakePartList;
	}


	/**
	 * Returns the head of snake
	 * @return cell which is head of snake 
	 */
	public Cell getHead() {
		return snakePartList.getFirst();
	}

	/**
	 * Returns the tail of the snake
	 * @param tail
	 * @return
	 */
	public Cell addTail(Cell tail) {
		this.snakePartList.addLast(tail);
		return tail;
	}

	/**
	 * Checks whether the snake crash with its own body
	 * @param nextCell cell which this method will control,and if it is false, snake could move
	 * @return false, if snake will not crash with its own body.  
	 */
	public boolean checkCrash(Cell nextCell) {
		for (Cell cell : snakePartList) {
			if (cell.getX() == nextCell.getX() && cell.getY() == nextCell.getY()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Moves to adjacent empty cell.
	 * Snake will move intelligently, they will look for foods.
	 * @param direction is a free direction,which snake can move.
	 * @return the cell,which is removed from the tail of snake after move.
	 */
	public Cell move(Direction direction) {
		Cell node=null;
		int headX=this.snakePartList.getFirst().getX();
		int headY=this.snakePartList.getFirst().getY();

		switch(direction) {
		case UP :
			node = new Cell(headX, headY - 1);
			break;
		case RIGHT :
			node = new Cell(headX + 1, headY);
			break;
		case DOWN :
			node = new Cell(headX, headY + 1);
			break;
		case LEFT :
			node = new Cell(headX - 1, headY);
			break;
		}
		this.snakePartList.addFirst(node);
		return snakePartList.removeLast();

	}

	/**
	 * Stay motionless
	 * Snake will stay at its own position when it could not do other actions. 
	 */
	public void stay() {

	}

	/**
	 * Eats the food at the adjacent cell.
	 * When a snake consumes that food, it will grow by one cell, added to snakepartList.
	 * The attackedCell will be the snake's head.
	 * @param attackedCreature Creature(Food) which snakes attack/eat.
	 */
	public void attack(Creature attackedCreature) {
		Cell node=null;

		int posX=attackedCreature.getX();
		int posY=attackedCreature.getY();

		node = new Cell(posX,posY);
		this.snakePartList.addFirst(node);



	}

	/**
	 * Creates a new snake.
	 * When snake reaches size 8, it will divide into two snakes of size 4.
	 * The newly born snake's head will be the old snake's tail.
	 * @return newly born Creature(snake)
	 */
	public Creature reproduce() {
		LinkedList<Cell> list=new LinkedList();
		LinkedList<Cell> newList=new LinkedList();

		list=this.getBody();

		//old snake's last 4 cell will be added to newList.
		for(int i=0 ; i<4 ; i++) {
			//old snake's tail will be head of the newly created snake
			newList.add(list.getLast());
			list.removeLast();
		}
		//removes the last four cell of the old snake
		this.snakePartList.removeAll(newList);

		//then the new snake will be build by newList.
		return new Snake(newList);


	}


	@Override
	/**
	 * Returns the chosen action
	 * @param createLocalInformationForCreature provides informations for each creature
	 * @return chosen Action
	 */
	public Action chooseAction(LocalInformation createLocalInformationForCreature) {
		List <Direction> freeDirections=createLocalInformationForCreature.getFreeDirections();


		ArrayList <Direction> directionToFood=new ArrayList <Direction> ();
		ArrayList <Direction> directionToGo=new ArrayList <Direction> ();

		int foodcorX=createLocalInformationForCreature.getFoodX();
		int foodcorY=createLocalInformationForCreature.getFoodY();

		////////////////////////////////////////////

		//This logic will check, if the snake could eats a food.
		int xhead=this.snakePartList.getFirst().getX();
		int yhead=this.snakePartList.getFirst().getY();
		Direction directionToAttack=null;
		boolean testAttack=false;

		if(xhead==foodcorX && yhead==foodcorY+1) {
			directionToAttack=Direction.UP;
			testAttack=true;
		}
		if(xhead==foodcorX && yhead==foodcorY-1) {
			directionToAttack=Direction.DOWN;
			testAttack=true;
		}
		if(xhead==foodcorX+1 && yhead==foodcorY) {
			directionToAttack=Direction.LEFT;
			testAttack=true;
		}
		if(xhead==foodcorX-1 && yhead==foodcorY) {
			directionToAttack=Direction.RIGHT;
			testAttack=true;
		}
		////////////////////////////////////////////


		// If a snake reaches size 8, it will divide(reproduce) 
		if(snakePartList.size()==8) {
			return new Action(Action.Type.REPRODUCE);
		}

		// If a food exist on the adjacent cell, snake should eat the food.
		else if(testAttack && directionToAttack!=null) {

			return new Action(Action.Type.ATTACK, directionToAttack );

		}

		//if snake could not divide and it has free directions to move, the snake should move.
		else if(snakePartList.size()<8 && !freeDirections.isEmpty()) {

			// This is a AI algorithm, it will find the food, and moves to it.

			//Learn the food coordinates
			int headX=this.snakePartList.getFirst().getX();
			int headY=this.snakePartList.getFirst().getY();

			//it will adds the directions,which snake should move,to find the food
			if(headX < foodcorX ) {
				directionToFood.add(Direction.RIGHT);
			}

			if(headX > foodcorX ) {
				directionToFood.add(Direction.LEFT);
			}


			if(headY < foodcorY) {
				directionToFood.add(Direction.DOWN);
			}

			if(headY > foodcorY) {
				directionToFood.add(Direction.UP);
			}

			/*
			 * if directionToFood list and freeDirections list has same directions in the list, 
			 * we will take this directions to another list called directionToGo.
			 */
			for(int i=0; i<directionToFood.size() ;i++) {
				if(freeDirections.contains(directionToFood.get(i)) ){
					directionToGo.add(directionToFood.get(i));
				}
			}

			//if directionToGo list has one or more element in the list,we will select the direction randomly by getRandomDirection method.
			if(!directionToGo.isEmpty()) {
				directionToMove =LocalInformation.getRandomDirection(directionToGo);
			}
			// if directionToGo list is empty, snake will move only to freeDirection.
			else{
				directionToMove =LocalInformation.getRandomDirection(freeDirections);
			}

			return new Action(Action.Type.MOVE,directionToMove);
		}


		//if snake could not move ,it will wait at the same position.
		else {

			return new Action(Action.Type.STAY);
		}
	}


	/**
	 * Draws the object on the grid panel 
	 * Red color for Snake's head.
	 * Blue color for Snake's body.
	 * @param panel grid panel to draw on
	 */
	@Override
	public void draw(GridPanel panel) {
		for(Cell cell : snakePartList) {
			if( snakePartList.getFirst()==cell) {
				panel.drawSquare(cell.getX(), cell.getY(), Color.RED);
			}
			else  {
				panel.drawSquare(cell.getX(), cell.getY(), Color.BLUE);
			}


		}

	}


	/**
	 * Returns the head coordinate(x-coordinate) of snake.
	 * @return x-coordinate of the snake's head.
	 */
	public int getX() {
		return this.snakePartList.getFirst().getX();
	}

	/**
	 * Returns the head coordinate(y-coordinate) of snake.
	 * @return y-coordinate of the snake's head.
	 */
	public int getY() {
		return this.snakePartList.getFirst().getY();
	}

	/**
	 * Sets the head's x coordinate
	 * @param x head's x coordinate
	 */
	public void setX(int x) {
		this.snakePartList.getFirst().setX(x);
	}

	/**
	 * Sets the head's y coordinate
	 * @param y head's y coordinate
	 */
	public void setY(int y) {
		this.snakePartList.getFirst().setY(y) ;
	}



}