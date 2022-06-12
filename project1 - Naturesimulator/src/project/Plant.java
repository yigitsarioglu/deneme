package project;

import java.awt.Color;
import java.util.List;

import game.Direction;
import naturesimulator.Action;
import naturesimulator.LocalInformation;
import ui.GridPanel;

public class Plant extends Creature {
	
	private static final double MAX_HEALTH=1.0; 
	Direction directionToMove;
	
	public Plant(int x, int y) {
		super(x, y, MAX_HEALTH/2);
		// TODO Auto-generated constructor stub
	}
	
	public Plant(int x, int y,double health) {
		super(x, y, health);
		
	}

	@Override
	public void draw(GridPanel panel) {
		panel.drawSquare(this.getX(), this.getY(), Color.GREEN);
		
	}

	
	/**
	 * if Plant health is larger than or equal to 0.75, and there is an empty cell(direction) around it will reproduce..
	 *or it will stay
	 */
	@Override
	public Action chooseAction(LocalInformation createLocalInformationForCreature) {
		List <Direction> freeDirections=createLocalInformationForCreature.getFreeDirections();
		directionToMove =LocalInformation.getRandomDirection(freeDirections);

		//Reproduce
		if(this.getHealth()>=0.75 &&  !freeDirections.isEmpty() ) {
			return new Action(Action.Type.REPRODUCE,directionToMove);

		}

		//Stay
		else {
			return new Action(Action.Type.STAY);
		}
	}
	
	
	
	
	
	@Override
	/**
	 * This action increases the Plant's health by 0.05.
	 * Plant max health should be 1.00
	 */
	public void stay() {
		if(this.getHealth()<=0.95)  {
			this.setHealth(this.getHealth() + 0.05);
		}
		else {
			this.setHealth(MAX_HEALTH);
		}
	}
	
	
	/**
	 * The newly plant have 10% of its parents health
	 * The parents health reduced to 70% of its original health
	 */
	@Override
	public Creature reproduce(Direction direction) {
		double parent_health=this.getHealth();
		double child_health=0.1*parent_health;
		this.setHealth(parent_health*0.7);
		
		if(direction==Direction.UP) {
			return new Plant(this.getX(),this.getY()-1,child_health) ;
		}
		else if(direction==Direction.DOWN) {
			return new Plant(this.getX(),this.getY()+1,child_health) ;
			
		}
		else if(direction==Direction.RIGHT) {
			return new Plant(this.getX()+1,this.getY(),child_health) ;
			
		}
		else {
			return new Plant(this.getX()-1,this.getY(),child_health) ;
		}
			
		
	}


}
