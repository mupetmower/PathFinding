package pathfinding.grid.creation;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import pathfinding.algorithms.utils.Tile;
import pathfinding.algorithms.utils.TileType;

public class GridCreator {
	
	public static Tile[][] grid;
	//public static TileType[][] gridTileTypes;
	
	private JPanel gridHolder;
	
	private int cols = 51;
	private int rows = 31;
	
	private int tileWidth = 25;
	private int tileHeight = 25;
	
	private IntRange hallSize = new IntRange(2, 6);
	private int numHalls = 300;
	private Hall[] halls;
		
	private int startX;
	private int startY;
	
	
	public GridCreator() {		
		Init();
	}
	
	public void Init() {
		try {
			//cols = new IntRange(20, 40).getRandom();
			//rows = new IntRange(15, 25).getRandom();
			
			halls = new Hall[numHalls];
			
			CreateEmptyGrid();
			PopulateGridHolder();
			CreateHalls();
			SetTilesValuesForHalls();
			InstantiateTiles();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage() + ex.toString());
			ex.printStackTrace();
		}
		
	}
	
	public void Reset() {
		grid = null;
		gridHolder = null;
		halls = null;
		
		Init();
		
	}
	
	private void CreateEmptyGrid() {
		grid = new Tile[cols][];
		for (int i = 0; i < cols; i++)
			grid[i] = new Tile[rows];
		
//		gridTileTypes = new TileType[cols][];
//		for (int i = 0; i < cols; i++)
//			gridTileTypes[i] = new TileType[rows];
		
		
		
	}
	
	private void PopulateGridHolder() {
		//GridLayout layout = new GridLayout();
		
		//layout.setColumns(cols);
		//layout.setRows(rows);
		//layout.setHgap(1);
		//layout.setVgap(1);
				
		gridHolder = new JPanel(null);
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				grid[x][y] = new Tile(x, y);
				grid[x][y].setSize(tileWidth, tileHeight);			
				//grid[x][y].setMinimumSize(new Dimension(tileWidth, tileHeight));
				//grid[x][y].setMaximumSize(new Dimension(tileWidth, tileHeight));
				grid[x][y].setBorder(new LineBorder(Color.BLACK, 1));
				//grid[x][y].setBackground(Color.WHITE);
				grid[x][y].setLocation(coordToTile(x, y));
				grid[x][y].setType(TileType.Wall);
				gridHolder.add(grid[x][y]);
			}
		}
	}
	
	private void CreateHalls() {
		//Point start = new Point(0, 0);
		
		halls[0] = new Hall();		
        halls[0].CreateHall(hallSize, cols, rows, true, null);

        for (int i = 1; i < numHalls; i++) {
        	halls[i] = new Hall();
        	halls[i].CreateHall(hallSize, cols, rows, false, halls[i - 1]);
        }
		
		
	}
	
	private void SetTilesValuesForHalls()
    {
        // Go through every corridor...
        for (int i = 0; i < halls.length; i++)
        {
            Hall currentCorridor = halls[i];

            // and go through it's length.
            for (int j = 0; j < currentCorridor.corridorLength; j++)
            {
                // Start the coordinates at the start of the corridor.
                int xCoord = currentCorridor.startXPos;
                int yCoord = currentCorridor.startYPos;

                // Depending on the direction, add or subtract from the appropriate
                // coordinate based on how far through the length the loop is.
                switch (currentCorridor.direction)
                {
                    case North:
                        yCoord -= j;
                        break;
                    case East:
                        xCoord -= j;
                        break;
                    case South:
                        yCoord += j;
                        break;
                    case West:
                        xCoord += j;
                        break;
                }

                // Set the tile at these coordinates to Floor.
                grid[xCoord][yCoord].setType(TileType.Floor);
                
                if (xCoord == 0 && yCoord == 0) {
                	grid[xCoord][yCoord].setType(TileType.Start);
                	startX = xCoord;
                	startY = yCoord;
                }
                
                if (i == halls.length - 1 && j == currentCorridor.corridorLength - 1)
                	grid[xCoord][yCoord].setType(TileType.End);
                	
            }
        }
    }
	
	private void InstantiateTiles()
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                SetTile(grid[i][j].getType(), i, j);
            }
        }
    }
	
	private void SetTile(TileType type, int x, int y) {
		switch (type)
		{
			case Floor:
				grid[x][y].setBackground(Color.white);
				break;
			case Wall:
				grid[x][y].setBackground(Color.gray);
				break;
			case Start:
				grid[x][y].setBackground(Color.blue);
				break;
			case End:
				grid[x][y].setBackground(Color.red);
				break;
		}
	}
	
	private Point coordToTile(int x, int y) {
		return new Point(tileWidth*x, tileHeight*y);
	}
	
	public JPanel getGridHolder() {
		return gridHolder;
	}
	
	
	
//	public JButton[][] getGrid() {
//		return grid;
//	}
//	
//	public TileType[][] getGridTileTypes() {
//		return gridTileTypes;
//	}
	
	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startX;
	}
	
	public void setNumHalls(int n) {
		numHalls = n;
	}
	public int getNumHalls() {
		return numHalls;
	}
	
	public void setCols(int c) {
		cols = c;
	}
	public int getCols() {
		return cols;
	}
	
	public void setRows(int r) {
		rows = r;
	}
	public int getRows() {
		return rows;
	}

	public void setHallSize(IntRange hs) {
		hallSize = hs;
	}
	public IntRange getHallSize() {
		return hallSize;
	}
	
}
