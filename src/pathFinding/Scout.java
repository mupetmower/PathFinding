package pathfinding;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.border.LineBorder;

public class Scout {
	
	private Point startPos = new Point();	
	private int startX;
	private int startY;

	private Point currentPos = new Point();
	private int currentX;
	private int currentY;
	
	private static int lastX = 0;
	private static int lastY = 0;
	
	private static int searchX;
	private static int searchY;
	
	private Tile currentTile;
	private Tile lastTile;
	
	private Direction lastDir;
	private Direction currentDir;
	Direction startOfLoopDir = currentDir;
	
	private ArrayList<Tile> tilesToSearch = new ArrayList<Tile>();
	private ArrayList<Tile> searchedTiles = new ArrayList<Tile>();
	
	private boolean allowDiag = false;
	
	private int turnTime = 25;
	
	
	public Scout(Point _start) {
		setStartPos(_start);
		setCurrentPos(_start);
		
		startX = _start.x;
		startY = _start.y;
		
		currentX = startX;
		currentY = startY;
		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	public Scout(int _startX, int _startY) {
		startX = _startX;
		startY = _startY;
		
		currentX = startX;
		currentY = startY;
		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	public Scout(int _startX, int _startY, boolean diag) {
		startX = _startX;
		startY = _startY;
		
		currentX = startX;
		currentY = startY;
		
		allowDiag = diag;
		
		tilesToSearch.clear();
		searchedTiles.clear();
	}
	
	public Scout(int _startX, int _startY, int _turnTime) {
		startX = _startX;
		startY = _startY;
		
		currentX = startX;
		currentY = startY;
		
		turnTime = _turnTime;
		
		tilesToSearch.clear();
		searchedTiles.clear();
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
	
	
	public boolean search3(int turn) {
		//boolean endFound = false;
		int searchX = startX;
		int searchY = startY;
		if (turn == 1) {
			currentX = startX;
			currentY = startY;
			searchX = startX;
			searchY = startY;
			//lastDir = Direction.West;
			currentDir = Direction.West;
		} else {
			//searchX = currentX;
			//searchY = currentY;
			//currentDir = nextDir();
		}		
		
		
		
		return CheckNeighbors(searchX, searchY);
		
		
		
				
	}
	
	
	public boolean CheckNeighbors(int x, int y) {
		
		
		
		for (int i = 0; i < 8; i++) {
			if (!endFound) {
				checkTile(0, 0, lastX, lastY);
				try {
					Thread.sleep(10);
				} catch (Exception ex) {
					System.out.print(ex.getMessage());
				}
				
				int searchX = x;
				int searchY = y;
				
				lastX = searchX;
				lastY = searchY;
								
				
				switch (i) 
				{
					case 0:
						//searchX -= 1;
						//searchY += 1;
						break;
					case 1:
						searchX -= 0;
						searchY += 1;
						break;
					case 2:
						//searchX += 1;
						//searchY += 1;
						break;
					case 3:
						searchX += 1;
						searchY += 0;
						break;
					case 4:
						//searchX += 1;
						//searchY -= 1;
						break;
					case 5:
						searchX -= 0;
						searchY -= 1;
						break;
					case 6:
						//searchX -= 1;
						//searchY -= 1;
						break;
					case 7:
						searchX -= 1;
						searchY += 0;
						break;					
					default:						
						break;
																
				}
				
				if (searchX < 0 || searchY < 0 || searchX >= GridCreator.grid.length || searchY >= GridCreator.grid[searchX].length) {
					endFound = false;
					checkTile(0, 0, lastX, lastY);
					continue;			
				}
				if (GridCreator.grid[searchX][searchY].getSearched()) {
					endFound = false;
					continue;
				}
				
				
				int resultType = checkTile(searchX, searchY, lastX, lastY);
				
				if (resultType == -1) {
					endFound = false;
					checkTile(0, 0, lastX, lastY);
					continue;
				}
				
				if (resultType == 0) {
					endFound = false;
					checkTile(0, 0, lastX, lastY);
					continue;
				}
				
				if(resultType == 1) {
					MarkTileSearched(searchX, searchY);
					CheckNeighbors(searchX, searchY);
				}
				if (resultType == 2) {
					endFound = true;
					return endFound;
				}
								
			} else {
				return true;
			}
			
			
		} 
		
		return true;
		
	}
	
	public boolean search2(int turn) {
		boolean endFound = false;
		
		
		//int searchX = 0;
		//int searchY = 0;
		
		
		if (turn == 1) {
			currentX = startX;
			currentY = startY;
			searchX = startX;
			searchY = startY;
			//lastDir = Direction.West;
			currentDir = Direction.West;
		} else {
			//searchX = currentX;
			//searchY = currentY;
			//currentDir = nextDir();
		}
		
		searchX -= 1;
		searchY += 1;
		
		
		for (int i = 0; i < 4; i++) {
			if (!search2Loop(0, turn, currentDir)) {
//				if (i == 0) {
//					searchX = searchX + (turn * 2);
//				}
//				
//				if (i == 1) {
//					searchY = searchY - (turn * 2);
//				}
//				
//				if (i == 2) {
//					searchX = searchX + (turn * 2);
//				}
//				
//				if (i == 3) {
//					searchY = searchY + (turn * 2);
//				}
			
				nextDir();
			} else
				return true;
		}
		
		
		return endFound;
	}
	boolean endFound = false;
	public boolean search2Loop(int loopCount, int turn, Direction dir) {
		
		if (loopCount < turn * 2) {
			
			lastX = searchX;
			lastY = searchY;
			
			switch (dir)
			{
				case North:
					searchY -= 1;
					break;
				case East:
					searchX -= 1;
					break;				
				case South:
					searchY += 1;
					break;
				case West:
					searchX += 1;
					break;
			}
			
			if (searchX < 0 || searchY < 0 || searchX >= GridCreator.grid.length || searchY >= GridCreator.grid[searchX].length) {
				endFound = false;
				return search2Loop(loopCount + 1, turn, dir);				
			}
			if (GridCreator.grid[searchX][searchY].getSearched()) {
				endFound = false;
				return search2Loop(loopCount + 1, turn, dir);
			}
			
			
		
			
			int resultType = checkTile(searchX, searchY, lastX, lastY);
			
//			if (resultType == -1) {
//				endFound = false;
//				return search2Loop(loopCount + 1, turn, dir);
//			}
			
			if (resultType == 0) {
				endFound = false;
				return search2Loop(loopCount + 1, turn, dir);
			}
			
			if(resultType == 1) 
				MarkTileSearched(searchX, searchY);
			
			if (resultType == 2) {
				endFound = true;
				return endFound;
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
				System.out.print(ex.getMessage());
			}
			
			return search2Loop(loopCount + 1, turn, dir);
			
			
		}
		
		return endFound;
	}
	
	
	public boolean search(int turn) {
		boolean endFound = false;
		
		int searchX = 0;
		int searchY = 0;
		
		
		
		if (turn == 0) {
			currentX = startX;
			currentY = startY;
			searchX = startX;
			searchY = startY;
			lastDir = Direction.West;
			currentDir = Direction.South;
		} else {
			searchX = currentX;
			searchY = currentY;
			currentDir = nextDir();
		}
		
		switch (currentDir)
		{
			case North:
				searchY -= 1;
				break;
			case East:
				searchX -= 1;
				break;				
			case South:
				searchY += 1;
				break;
			case West:
				searchX += 1;
				break;
		}
		
		
		lastX = currentX;
		lastY = currentY;
		
		currentX = searchX;
		currentY = searchY;
		
		if (searchX < 0 || searchY < 0 || searchX >= GridCreator.grid.length || searchY >= GridCreator.grid[searchX].length) {
			endFound = false;
			return endFound;
		}
		if (GridCreator.grid[searchX][searchY].getSearched()) {
			endFound = false;
			
		}
	
	
		int resultType = checkTile(searchX, searchY, 0, 0);
		
		if (resultType == -1) {
			endFound = false;
			return false;
		}
		
		if (resultType == 0) {
			endFound = false;
			return false;
		}
		
		if(resultType == 1) 
			MarkTileSearched(searchX, searchY);
		
		if (resultType == 2) {
			endFound = true;
			return endFound;
		}
		
		
		try {
			Thread.sleep(1000);
		} catch (Exception ex) {
			System.out.print(ex.getMessage());
		}
		
		
		return endFound;
		
	}
	
	private void MarkTileSearched(int x, int y) {
		GridCreator.grid[x][y].setSearched(true);
	}
	
	private int checkTile(int x, int y, int lx, int ly) {
		int type = 0;
				
		if (!(lx < 0 || ly < 0 || lx >= GridCreator.grid.length || ly >= GridCreator.grid[lx].length)) {
			GridCreator.grid[lx][ly].setBorder(new LineBorder(Color.black, 1));
		}
		
		GridCreator.grid[x][y].setBorder(new LineBorder(Color.yellow, 1));
		
		if (GridCreator.grid[x][y].getSearched())
			type = -1;
		
		if (GridCreator.grid[x][y].getType() == TileType.Floor) {
			type = 1;
			GridCreator.grid[x][y].setBackground(Color.LIGHT_GRAY);
			MarkTileSearched(x, y);
			
		}
		
		if (GridCreator.grid[x][y].getType() == TileType.End)
			type = 2;
		
		return type;
	}
	
	
	
	public Direction nextDir() {
		lastDir = currentDir;
		currentDir = currentDir.nextDir(currentDir);
		
		return currentDir;
	}
	
	
	
	public void setStartPos(Point s) {
		startPos = s;
	}
	
	public void setCurrentPos(Point cur) {
		currentPos = cur;
	}

}
