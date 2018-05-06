package pathfinding.pathfinder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.border.LineBorder;

import pathfinding.algorithms.AStar;
import pathfinding.algorithms.utils.Direction;
import pathfinding.algorithms.utils.Path;
import pathfinding.algorithms.utils.Tile;
import pathfinding.algorithms.utils.TileType;
import pathfinding.grid.creation.GridCreator;

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