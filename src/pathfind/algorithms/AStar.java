package pathfind.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.border.LineBorder;

import pathfind.algorithms.utils.Path;
import pathfind.algorithms.utils.Tile;
import pathfind.algorithms.utils.TileType;
import pathfind.grid.creation.GridCreator;

public class AStar {
	
	private ArrayList<Tile> tilesToSearch = new ArrayList<Tile>();
	private ArrayList<Tile> searchedTiles = new ArrayList<Tile>();
	
	private boolean allowDiag = false;
	
	private int turnTime;
	
	
	public AStar() {}
	
	public AStar(int turnTime) {
		this.turnTime = turnTime;
	}
	
	public AStar(boolean diag) {
		this.allowDiag = diag;
	}
	
	public AStar(int turnTime, boolean diag) {
		this.turnTime = turnTime;
		this.allowDiag = diag;
	}
	
	
	
	public Path aStar(int startX, int startY) {
		
		Tile target = null;
		
		tilesToSearch.clear();
		searchedTiles.clear();
		GridCreator.grid[startX][startY].cost = 0;
		GridCreator.grid[startX][startY].posInPath = 0;
		addToTilesToSearch(startX, startY);
		
		
		while (tilesToSearch.size() != 0) {
			
			Tile current = tilesToSearch.get(0);
			
			if (current.getType() == TileType.End) {
				target = current;
				tilesToSearch.clear();
				continue;
			}
			
			tilesToSearch.remove(current);
			searchedTiles.add(current);
			
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					
					if ((x == 0) && (y == 0)) {
						continue;
					}
					
					if (!allowDiag) {
						if ((x != 0) && (y != 0)) {
							continue;
						}
					}
					
					
					
					int currentNeighborX = current.x + x;
					int currentNeighborY = current.y + y;
					
					
					
					boolean isOffBoard = (currentNeighborX < 0 || currentNeighborY < 0 || currentNeighborX >= GridCreator.grid.length || currentNeighborY >= GridCreator.grid[currentNeighborX].length);
					
					if (!isOffBoard && GridCreator.grid[currentNeighborX][currentNeighborY].getType() != TileType.Wall) {
						
						Tile currentNeighbor = GridCreator.grid[currentNeighborX][currentNeighborY];
						
						if (currentNeighbor.getType() == TileType.End) {
							target = currentNeighbor;
							tilesToSearch.clear();
							
						}
							
						
						if (currentNeighbor.getType() == TileType.Start)
							continue;
						
						float nextStepCost = current.cost + 1;
						
						if (nextStepCost < currentNeighbor.cost) {
							if (tilesToSearch.contains(currentNeighbor)) {
								tilesToSearch.remove(currentNeighbor);
							}
							if (searchedTiles.contains(currentNeighbor)) {
								searchedTiles.remove(currentNeighbor);
							}
						}
						
						if (!(tilesToSearch.contains(currentNeighbor)) && !(searchedTiles.contains(currentNeighbor))) {
							currentNeighbor.cost = nextStepCost;
							currentNeighbor.heuristic = 1;
							currentNeighbor.setParent(current);
							addToTilesToSearch(currentNeighbor);
						} else {
							continue;
						}
						
						
						
						
						
						currentNeighbor.setBorder(new LineBorder(Color.yellow));
						
						try {
							Thread.sleep(turnTime);
						} catch (Exception ex) {
							
						}
						
						currentNeighbor.setBorder(new LineBorder(Color.black));
						currentNeighbor.setBackground(Color.LIGHT_GRAY);
						
						
						
					} else {
						continue;
					}
					
					
				}
			}
			
			
			
		}
		

		Path path = new Path();
		while (target != GridCreator.grid[startX][startY]) {
			path.prependStep(target.x, target.y);
			
			if (GridCreator.grid[target.x][target.y].getType() != TileType.End)
				GridCreator.grid[target.x][target.y].setBackground(Color.yellow);
			else
				GridCreator.grid[target.x][target.y].setBackground(Color.red);
				
			try {
				Thread.sleep(10);
			} catch (Exception ex) {
				
			}
			
			target = target.parent;
		}
		path.prependStep(startX, startY);
		
		return path;
		
	}
	
		
	
	public void addToTilesToSearch(Tile tile) {
		tilesToSearch.add(tile);
		Collections.sort(tilesToSearch);
	}
	
	public void addToTilesToSearch(int x, int y) {
		addToTilesToSearch(GridCreator.grid[x][y]);
	}
	
	public void addToSearched(int x, int y) {
		searchedTiles.add(GridCreator.grid[x][y]);
		GridCreator.grid[x][y].setSearched(true);
	}
	
	
	
	public ArrayList<Tile> getTilesToSearch() {
		return tilesToSearch;
	}

	public void setTilesToSearch(ArrayList<Tile> tilesToSearch) {
		this.tilesToSearch = tilesToSearch;
	}

	public ArrayList<Tile> getSearchedTiles() {
		return searchedTiles;
	}

	public void setSearchedTiles(ArrayList<Tile> searchedTiles) {
		this.searchedTiles = searchedTiles;
	}

	public boolean isAllowDiag() {
		return allowDiag;
	}

	public void setAllowDiag(boolean allowDiag) {
		this.allowDiag = allowDiag;
	}

	public int getTurnTime() {
		return turnTime;
	}

	public void setTurnTime(int turnTime) {
		this.turnTime = turnTime;
	}

}
