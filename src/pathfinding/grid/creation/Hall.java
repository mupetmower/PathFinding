package pathfinding.grid.creation;

import java.util.Random;

import pathfinding.algorithms.utils.Direction;

public class Hall {

	public int startXPos; // The x coordinate for the start of the corridor.
	public int startYPos; // The y coordinate for the start of the corridor.
	public int corridorLength; // How many units long the corridor is.
	public Direction direction = Direction.South; // Which direction the corridor is heading from it's room.
	private Random rng = new Random();
	
	
	public void CreateHall(IntRange length, int columns, int rows, boolean first, Hall lastHall) {
		 // Set a random direction (a random index from 0 to 3, cast to Direction).
        direction = direction.toDir(rng.nextInt(4));
        
        
        // Find the direction opposite to the one entering the room this corridor is leaving from.
        // Cast the previous corridor's direction to an int between 0 and 3 and add 2 (a number between 2 and 5).
        // Find the remainder when dividing by 4 (if 2 then 2, if 3 then 3, if 4 then 0, if 5 then 1).
        // Cast this number back to a direction.
        // Overall effect is if the direction was South then that is 2, becomes 4, remainder is 0, which is north.

        Direction oppositeDirection;
        
        if (first) {
        	direction = Direction.South;
        	oppositeDirection = Direction.North;
        } else {
        	
        	//Could have just made a simple if statement for this... if east, then west, if north then south, etc...
        	oppositeDirection = direction.toDir(((lastHall.direction.ordinal() + 2) % 4));
        }

        // If this is not the first corridor and the randomly selected direction is opposite to the previous corridor's direction...
        if (!first && direction == lastHall.direction)
        {
            // Rotate the direction 90 degrees clockwise (North becomes East, East becomes South, etc).
            // This is a more broken down version of the opposite direction operation above but instead of adding 2 we're adding 1.
            // This means instead of rotating 180 (the opposite direction) we're rotating 90.
            int directionInt = direction.ordinal();
            directionInt++;
            directionInt = directionInt % 4;
            direction = direction.toDir(directionInt);

        }

        // Set a random length.
        corridorLength = length.getRandom();

        // Create a cap for how long the length can be (this will be changed based on the direction and position).
        int maxLength = length.max;
        
        if (!first) {
	        switch (direction)
	        {
	            // If the chosen direction is North (up)...
	            case North:
	                // ... the starting position in the x axis can be random but within the width of the room.
	                startXPos = lastHall.EndPositionX();
	
	                // The starting position in the y axis must be the top of the room.
	                startYPos = lastHall.EndPositionY();
	
	                // The maximum length the corridor can be is the height of the board (rows) but from the top of the room (y pos + height).
	                maxLength = startYPos;
	                break;
	            case East:
	                startXPos = lastHall.EndPositionX();
	                startYPos = lastHall.EndPositionY();
	                maxLength = startXPos;
	                break;
	            case South:
	            	startXPos = lastHall.EndPositionX();
	                startYPos = lastHall.EndPositionY();
	                maxLength = rows - startYPos - length.min;
	                break;
	            case West:
	            	startXPos = lastHall.EndPositionX();
	                startYPos = lastHall.EndPositionY();
	                maxLength = columns - startXPos - length.min;
	                break;
        }
        } else {
        	startXPos = 0;
        	startYPos = 0;
        	maxLength = length.max;
        }
        // We clamp the length of the corridor to make sure it doesn't go off the board.
        corridorLength = Clamp(corridorLength, 1, maxLength);
	}

	public int Clamp(int want, int min, int max) {
		int result = want;
		
		if (result < min)
			result = min;
		
		if (result > max)
			result = max;
		
		return result;
		
	}

	public int EndPositionX() {
		if (direction == Direction.North || direction == Direction.South)
			return startXPos;
		if (direction == Direction.East)
			return startXPos - corridorLength;
		return startXPos + corridorLength;
	}

	public int EndPositionY() {
		if (direction == Direction.East || direction == Direction.West)
			return startYPos;
		if (direction == Direction.North)
			return startYPos - corridorLength;
		return startYPos + corridorLength;
	}

}

