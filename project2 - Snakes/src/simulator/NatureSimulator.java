package simulator;

import game.Direction;
import game.GridGame;
import simulator.Action;
import simulator.LocalInformation;
import project.Creature;
import project.Cell;
import project.Food;
import project.Snake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class that implements the game logic for Simulator.
 */
public class NatureSimulator extends GridGame {
	public Food foods;
	private List<Creature> creatures;
	private Creature[][] creaturesMap;
	public LinkedList <Cell> body;

	/**
	 * Creates a new Nature Simulator game instance
	 * @param gridWidth number of grid squares along the width
	 * @param gridHeight number of grid squares along the height
	 * @param gridSquareSize size of a grid square in pixels
	 * @param frameRate frame rate (number of timer ticks per second)
	 */
	public NatureSimulator(int gridWidth, int gridHeight, int gridSquareSize, int frameRate) {
		super(gridWidth, gridHeight, gridSquareSize, frameRate);

		creatures = new ArrayList<>();
		creaturesMap = new Creature[gridWidth][gridHeight];
	}
	
	
	/**
	 * Determines and execute actions for all creatures.
	 * Each timerTick, new action is executed for each Snake object
	 */
	@Override
	protected void timerTick() {
		// Determine and execute actions for all creatures
		ArrayList<Creature> creaturesCopy = new ArrayList<>(creatures);
		for (Creature creature : creaturesCopy) {

			//updates Map
			updateCreaturesMap(creature.getX(), creature.getY(), null);

			// Choose action
			Action action = creature.chooseAction(createLocalInformationForCreature(creature));

			// Execute action
			if (action != null) {
				if (action.getType() == Action.Type.STAY) {
					// STAY
					creature.stay();
				}
				else if (action.getType() == Action.Type.REPRODUCE) {
					addCreature(creature.reproduce());

				}

				else if (action.getType() == Action.Type.MOVE) {
					creature.move(action.getDirection());
				}

				else if (action.getType() == Action.Type.ATTACK) {
					// ATTACK

					Creature attackedCreature = (Creature) foods;
					if (attackedCreature != null && (creature instanceof Snake)) {

						Snake s= ( Snake) creature;

						creature.attack(attackedCreature);
						removeCreature(attackedCreature);    //will remove food object on the grid

						createFood();          //new food will be created.



					}
				}

			}  
		}    
	}	

	/**
	 * Adds a new creature to the game
	 * @param creature creature to be added
	 * @return boolean indicating the success of addition
	 */
	public boolean addCreature(Creature creature) {
	
		if(creature instanceof Food) {
			foods=(Food) creature;
		}

		if (isPositionInsideGrid(creature.getX(), creature.getY())) {
			if (creaturesMap[creature.getX()][creature.getY()] == null) {
				creatures.add(creature);
				addDrawable(creature);
				creaturesMap[creature.getX()][creature.getY()] = creature;
				return true;
			}
			else {    
				return false;
			}
		}	

		else {
			return false;
		}	
	}




	/**
	 * Returns LocalInformation object for Snakes.
	 * Using this informations,snakes will move intelligently.
	 * @param creature the creature which information is owned by.
	 * @return localinformation object.
	 */
	private LocalInformation createLocalInformationForCreature(Creature creature) {
		int x = creature.getX();
		int y = creature.getY();

		//This was an AI algorithm which prevents snakes move over another snakes.
		LinkedList <Cell> cellList=new LinkedList ();
		for( Creature snakes :this.creatures ) {
			//add the all snakes(in the grid) list of cells(body) to the cellList
			if(snakes instanceof Snake) {
				cellList.addAll(snakes.getBody());

			}
		}
		
		boolean upfield=true;
		boolean downfield=true;
		boolean rightfield=true;
		boolean leftfield=true;
		//And checks whether this cells are occupied by another snake
		for(int i=0 ; i<cellList.size() ; i++ ) {
			if(cellList.get(i).getX()==x && cellList.get(i).getY()==y-1) {
				upfield=false;
			}
			if(cellList.get(i).getX()==x && cellList.get(i).getY()==y+1) {
				downfield=false;
			}

			if(cellList.get(i).getX()==x-1 && cellList.get(i).getY()==y) {
				leftfield=false;
			}
			if(cellList.get(i).getX()==x+1 && cellList.get(i).getY()==y) {
				rightfield=false;
			}

		}
		///////////////////////////////////////////////////////////
		
		//This algorithm will select the freeDirections, which snake could move (not move its own body or over another snake)
		

		ArrayList<Direction> freeDirections = new ArrayList<>();
		if ( isPositionInsideGrid(x, y - 1) && !creature.checkCrash(new Cell(x,y-1)) && upfield ) {
			freeDirections.add(Direction.UP);
		}
		if (  isPositionInsideGrid(x, y + 1) && !creature.checkCrash(new Cell(x,y+1)) && downfield ) {
			freeDirections.add(Direction.DOWN);
		}
		if ( isPositionInsideGrid(x - 1, y) && !creature.checkCrash(new Cell(x-1,y)) && leftfield  ) {
			freeDirections.add(Direction.LEFT);
		}
		if ( isPositionInsideGrid(x + 1, y) && !creature.checkCrash(new Cell(x+1,y)) && rightfield  ) {
			freeDirections.add(Direction.RIGHT);
		}

		return new LocalInformation(getGridWidth(), getGridHeight(), freeDirections, foods);
	}


	
	/**
	 * Checks whether position is inside the grid
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return true if position is inside the grid
	 */
	private boolean isPositionInsideGrid(int x, int y) {
		return (x >= 0 && x < getGridWidth()) && (y >= 0 && y < getGridHeight());
	}
	
	/**
	 * Updates the creature map 
	 * @param x x-coordinate of the grid
	 * @param y y-coordinate of the grid
	 * @param creature
	 */
	private void updateCreaturesMap(int x, int y, Creature creature) {
		if (isPositionInsideGrid(x, y)) {
			creaturesMap[x][y] = creature;
		}
	}


	
	/**
	 * Returns true if the position is free
	 * @param x food's x coordinate
	 * @param y food's y coordinate
	 * @return true if that position is free for food creation
	 */
	private boolean isPositionOk(int x,int y) {
		LinkedList <Cell> cellList=new LinkedList ();
		for( Creature snakes :this.creatures ) {

			if(snakes instanceof Snake) {
				cellList.addAll(snakes.getBody());

			}

		}
		boolean position=true;

		for(int i=0 ; i<cellList.size() ; i++ ) {
			if(cellList.get(i).getX()==x && cellList.get(i).getY()==y) {
				position=false;

			}
		}
		
		return position;
	}	


	
	
	/**
	 * Removes creature(food) from the grid,also removes from the creature list.
	 * @param creature which should be removed
	 */
	private void removeCreature(Creature creature) {
		if (creature != null) {
			creatures.remove(creature);
			
			
			removeDrawable(creature);
			if (isPositionInsideGrid(creature.getX(), creature.getY())) {
				creaturesMap[creature.getX()][creature.getY()] = null;
			}
		}
	}


	/**
	 * Creates new food at the start of the game and also when snake ate old food.
	 */
	public void createFood() {

		int x,y;
		
		//Makes random positions/coordinates
		do {
			x = new Random( ).nextInt(this.getGridWidth());
			y = new Random( ).nextInt(this.getGridHeight());
		} while ( isPositionOk(x,y)==false);   //Check whether that coordinates are free.
		foods = new Food(new Cell(x,y));
		
		addCreature(foods);  // add this food 
		
	}	



}