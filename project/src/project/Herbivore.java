package project;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import game.Direction;
import naturesimulator.Action;
import naturesimulator.LocalInformation;
import ui.GridPanel;

public class Herbivore extends Creature{

	private static final double MAX_HEALTH=20.0; 
	Direction directionToMove;
	List <Direction> freeDirections;
	List <Direction> filledDirections;
	Direction directionToAttack;

	public Herbivore(int x, int y) {
		super(x, y, MAX_HEALTH/2);

	}

	public Herbivore(int x, int y, double health) {
		super(x, y, health);


	}

	@Override
	public void draw(GridPanel panel) {

		panel.drawSquare(this.getX(), this.getY(), Color.RED);

	}

	/**
	 * 
	 * 
	 */
	@Override
	public Action chooseAction(LocalInformation createLocalInformationForCreature) {

		freeDirections=createLocalInformationForCreature.getFreeDirections();
		directionToMove =LocalInformation.getRandomDirection(freeDirections);
		
		
		boolean left=createLocalInformationForCreature.getCreatureLeft() instanceof Plant;
		boolean right=createLocalInformationForCreature.getCreatureRight() instanceof Plant;
		boolean up=createLocalInformationForCreature.getCreatureUp() instanceof Plant;
		boolean down=createLocalInformationForCreature.getCreatureDown() instanceof Plant;
		
		
		//////////////////////////////////////////////////////////

		//Reproduce
		if(this.getHealth()==MAX_HEALTH && ( ! freeDirections.isEmpty() ) ) {
			
			return new Action(Action.Type.REPRODUCE,directionToMove);

		}

		//Attack
		else if(  left ) {
			// directionToAttack =LocalInformation.getRandomDirection(filledDirections);
			
			return new Action(Action.Type.ATTACK, Direction.LEFT);
		}
		
		else if(  right ) {
			// directionToAttack =LocalInformation.getRandomDirection(filledDirections);
			
			return new Action(Action.Type.ATTACK, Direction.RIGHT);
		}
		
		else if(  up ) {
			// directionToAttack =LocalInformation.getRandomDirection(filledDirections);
			
			return new Action(Action.Type.ATTACK, Direction.UP);
		}
		else if(  down ) {
			// directionToAttack =LocalInformation.getRandomDirection(filledDirections);
			
			return new Action(Action.Type.ATTACK, Direction.DOWN);
		}


		//Move
		else if(!freeDirections.isEmpty() && this.getHealth()>1.0 ){
			
			return new Action(Action.Type.MOVE, directionToMove);
		}

		//Stay
		else {
			
			return new Action(Action.Type.STAY);
		}
		
		
	}





	/**
	 * The newly plant have 20% of its parents health
	 * The parents health reduced to 40% of its original health
	 */
	@Override
	public Creature reproduce(Direction direction) {
		double parent_health=this.getHealth();
		double child_health=0.2*parent_health;
		this.setHealth(parent_health*0.4);	

		if(direction==Direction.UP) {
			System.out.println("new herbivore");
			return new Herbivore(this.getX(),this.getY()-1,child_health) ;
		}
		else if(direction==Direction.DOWN) {
			System.out.println("new herbivore");
			return new Herbivore(this.getX(),this.getY()+1,child_health) ;
			
		}
		else if(direction==Direction.RIGHT) {
			System.out.println("new herbivore");
			return new Herbivore(this.getX()+1,this.getY(),child_health) ;

		}
		else {
			System.out.println("new herbivore");
			return new Herbivore(this.getX()-1,this.getY(),child_health) ;
		}



	}

	public void attack(Creature attackedCreature) {

		double plant_health=attackedCreature.getHealth();
		int posX=attackedCreature.getX();
		int posY=attackedCreature.getY();

		double herb_health=this.getHealth()+plant_health;

		attackedCreature.setHealth(0.0);
		System.out.println("atttacking occured");
		this.setX(posX);
		this.setY(posY);
		if(herb_health<= MAX_HEALTH) {
			this.setHealth(herb_health);
		}
		else {
			this.setHealth(MAX_HEALTH);
		}


	}




	@Override
	/**
	 * This action causes the Herbivore to lose 1.0 health.
	 */
	public void move(Direction direction) {

		this.setHealth(this.getHealth() -1.0);
		if(direction==Direction.UP) {
			this.setY(this.getY()-1);
		}
		else if(direction==Direction.DOWN) {
			this.setY(this.getY()+1);
		}
		else if(direction==Direction.RIGHT) {
			this.setX(this.getX()+1);
		}
		else {
			this.setX(this.getX()-1);
		}
	}


	@Override
	/**
	 * This action causes the Herbivore to lose 0.1 health.
	 */
	public void stay() {

		this.setHealth(this.getHealth()- 0.1);

	}



}
