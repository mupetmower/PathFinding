package pathfinding;

public enum Direction {
	North,
	East,
	South,
	West;


	public Direction toDir(int i) {
		switch (i) {
			case 0:
				return North;
			case 1:
				return East;
			case 2:
				return South;
			case 3:
				return West;
			default:
				return null;
		}
		
	}
	
	public Direction nextDir(Direction currentDir) {
		switch (currentDir) {
			case North:
				return East;
			case East:
				return South;
			case South:
				return West;
			case West:
				return North;
			default:
				return null;
		}
	}

}
