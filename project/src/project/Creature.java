package project;

import game.Direction;
import game.Drawable;
import naturesimulator.Action;
import naturesimulator.LocalInformation;

public abstract class Creature implements Drawable {
	private int x;
	private int y;
	private double health;
	
	
	public Creature(int x, int y, double health) {
		this.x = x;
		this.y = y;
		this.health = health;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public double getHealth() {
		return health;
	}


	public void setHealth(double health) {
		this.health = health;
	}


	public abstract Action chooseAction(LocalInformation createLocalInformationForCreature) ;

	
	

	public void attack(Creature attackedCreature) {
		// TODO Auto-generated method stub
		
	}


	public void move(Direction direction) {
	
		
	}


	public abstract Creature reproduce(Direction direction);
	
	


	public abstract void stay();
	
	
	
	
	
	
	

}
