package simulator;

import game.Direction;
import project.Creature;
import project.Food;

import java.util.HashMap;
import java.util.List;


/**
 * Class representing the information a creature has about its surroundings.
 * Automatically created and passed by the game to each creature at each timer tick.
 */
public class LocalInformation {

    private int gridWidth;
    private int gridHeight;

    private List<Direction> freeDirections;
    private Food foods;
    /**
     * Constructs the local information for a creature
     * @param gridWidth width of the grid world
     * @param gridHeight height of the grid world
     * @param creatures mapping of directions to neighbor creatures
     * @param freeDirections list of free directions
     * @param food the food object on the grid.
     */
    LocalInformation(int gridWidth, int gridHeight, List<Direction> freeDirections ,Food food) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.freeDirections = freeDirections;
        this.foods=food;
    }

    /**
     * Getter for the width of the grid world.
     * Can be used to assess the boundaries of the world.
     * @return number of grid squares along the width
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Getter for the height of the grid world.
     * Can be used to assess the boundaries of the world.
     * @return number of grid squares along the height
     */
    public int getGridHeight() {
        return gridHeight;
    }
    
    /**
     * Returns the Food's x-coordinate
     * @return x-coordinate of food.
     */
    public int getFoodX() {
    	return this.foods.getX();
    }
    
    /**
     * Returns the Food's y-coordinate
     * @return y-coordinate of food.
     */
    public int getFoodY() {
    	return this.foods.getY();
    }
    
    /**
     * Returns the list of free directions around the current position.
     * The list does not contain directions out of bounds or containing a creature.
     * Can be used to determine the directions available to move or reproduce.
     * @return creature or null if no creature exists
     */
    public List<Direction> getFreeDirections() {
        return freeDirections;
    }

    /**
     * Utility function to get a randomly selected direction among multiple directions.
     * The selection is uniform random: All directions in the list have an equal chance to be chosen.
     * @param possibleDirections list of possible directions
     * @return direction randomly selected from the list of possible directions
     */
    public static Direction getRandomDirection(List<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return null;
        }
        int randomIndex = (int)(Math.random() * possibleDirections.size());
        return possibleDirections.get(randomIndex);
    }

}