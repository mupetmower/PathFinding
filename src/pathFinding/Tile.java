package pathfinding;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class Tile extends JButton implements Comparable {

	private TileType type;
	private boolean searched = false;
	
	public int x;
	public int y;
	
	public int posInPath = 0;
	public Tile parent;
	public float cost;
	public float heuristic;
	
	public Tile() {
		setSearched(false);
	}
	
	public Tile(int _x, int _y) {
		setSearched(false);
		x = _x;
		y = _y;
	}

	public Tile(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public Tile(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Tile(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public Tile(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}
	
	
	public void setType(TileType t) {
		type = t;
	}
	public TileType getType() {
		return type;
	}
	
	public void setSearched(boolean s) {
		searched = s;
	}
	public boolean getSearched() {
		return searched;
	}
	
	public int setParent(Tile p) {
		parent = p;
		posInPath = parent.posInPath + 1;
				
		return posInPath;
	}
	
	@Override
	public int compareTo(Object other) {
		Tile o = (Tile) other;
		
		float f = heuristic + cost;
		float of = o.heuristic + o.cost;
		
		if (f < of) {
			return -1;
		} else if (f > of) {
			return 1;
		} else {
			return 0;
		}
	}
	
	

}
