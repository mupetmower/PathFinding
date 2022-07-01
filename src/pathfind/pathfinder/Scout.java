package pathfind.pathfinder;

import java.util.ArrayList;

import pathfind.algorithms.AStar;
import pathfind.algorithms.utils.Direction;
import pathfind.algorithms.utils.Path;
import pathfind.algorithms.utils.Tile;

public class Scout {
	
	private Direction currentDir;
	Direction startOfLoopDir = currentDir;
	
	private ArrayList<Tile> tilesToSearch = new ArrayList<Tile>();
	private ArrayList<Tile> searchedTiles = new ArrayList<Tile>();
	
	private boolean allowDiag = false;
	
	private int turnTime = 25;
	
	
	public Scout() {		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	public Scout(boolean diag) {		
		allowDiag = diag;
		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	public Scout(int turnTime) {		
		this.turnTime = turnTime;
		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	public Scout(boolean diag, int turnTime) {	
		allowDiag = diag;
		this.turnTime = turnTime;
		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	
	public Path runAStar(int startX, int startY, int turnTime, boolean diag) {
		return new AStar(turnTime, diag).aStar(startX, startY);		
	}
	
	
}