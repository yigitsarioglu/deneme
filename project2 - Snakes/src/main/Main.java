package main;

import simulator.NatureSimulator;
import project.Cell;
import project.Snake;
import project.Food;
import ui.ApplicationWindow;

import java.awt.*;
import java.util.LinkedList;


/**
 * The main class that can be used as a playground to test.
 *
 */
public class Main {

	/**
	 * Main entry point for the application.
	 *
	 * @param args application arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				// Create game 
				//You can change the speed of game here...initally 20.
				//naturesimulator (width , height ,size of each grid square ,  speed of game )
				// You can change the world width and height, size of each grid square in pixels or the game speed
				NatureSimulator game = new NatureSimulator(50, 50,10, 50);  // width , height ,size , speed

				
				//Creates initial snake at initial position 
				// New cells created and added to linked list
				LinkedList<Cell> list=new LinkedList <Cell> ();
				list.add(new Cell(4,1));
				list.add(new Cell(3,1));
				list.add(new Cell(2,1));
				list.add(new Cell(1,1));
				//Snake expects linkedlist parameter to create its object
				game.addCreature(new Snake(list));

				//Initial food created and added.
				game.createFood();
				


				// Create application window that contains the game panel
				ApplicationWindow window = new ApplicationWindow(game.getGamePanel());
				window.getFrame().setVisible(true);

				// Start game
				game.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}