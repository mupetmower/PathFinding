package pathfinding.grid.creation;

import java.util.Random;

public class IntRange {
	
	Random rng = new Random();
	public int min;
	public int max;
	
	
	public IntRange(int _min, int _max) throws IllegalArgumentException {
		if (_min > 0)
			min = _min;
		else
			throw new IllegalArgumentException("Min must be greater than 1");
		if (_max >= _min && _max < 1000)
			max = _max;
		else
			throw new IllegalArgumentException("Max must be greater than min and less than 1000");
	}

	
	public int getRandom() {
		return (min + rng.nextInt(max - min + 1));
	}
}
