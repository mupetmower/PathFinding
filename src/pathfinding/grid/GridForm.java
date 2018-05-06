package pathfinding.grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import pathfinding.algorithms.utils.Path;
import pathfinding.grid.creation.GridCreator;
import pathfinding.grid.creation.IntRange;
import pathfinding.pathfinder.Scout;

public class GridForm extends JFrame {
	
	//private Timer timer;
	//private final int SEARCH_SPEED = 1000;
	private Scout scout;
	
	//private int turn = 1;
	
	//private int rows;
	//private int cols;
	
	boolean allowDiag = false;
	
	GridCreator gridCreator;
	JPanel gridHolder;
	
	private ArrayList<JPanel> menuPanels = new ArrayList<JPanel>();
	
	public GridForm() {
		Init();
	}
	
	
	
	
	private void Init() {

		this.setSize(1600, 820);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		//this.setLayout(new BorderLayout());
		gridCreator = new GridCreator();
		gridCreator.Init();
		gridHolder = gridCreator.getGridHolder();
		
		this.getContentPane().add(gridHolder);
		
		scout = new Scout();
		
		
//		ActionListener timerTask = new ActionListener() {
//		      public void actionPerformed(ActionEvent evt) {
//		          if (scout.search(turn)) {
//		        	  timer.stop();
//		          }
//		          turn++;
//		      }
//		};
//		
//		timer = new Timer(SEARCH_SPEED, timerTask);
//		timer.start();
		
//		for (int i = 0; i < GridCreator.grid.length; i++) {
//			for (int j = 0; j < GridCreator.grid.length; j++) {
//				scout.search(i, j, turn);
//				turn++;
//			}
//		}
		
		JPanel menuPanelHolder = new JPanel();
		menuPanelHolder.setLayout(new BoxLayout(menuPanelHolder, BoxLayout.Y_AXIS));
		menuPanelHolder.setBorder(new LineBorder(Color.black, 1));
		
		
		for (int i = 0; i < 40; i++) {
			menuPanels.add(new JPanel(new FlowLayout()));
		}
		
		
		
		JLabel lblNumHalls = new JLabel("Number Of Halls: ");
		JTextField txtNumHalls = new JTextField();
		txtNumHalls.setColumns(6);
		txtNumHalls.setToolTipText("Default = 300");
		
		JLabel lblSpace2 = new JLabel("                                   ");
		
		
		menuPanels.get(2).add(lblNumHalls);
		menuPanels.get(2).add(txtNumHalls);
		menuPanels.get(2).add(lblSpace2, FlowLayout.RIGHT);
		
		
		JLabel lblRows = new JLabel("Rows: ");
		JTextField txtRows = new JTextField();
		txtRows.setColumns(3);
		txtRows.setToolTipText("Default = 31");
		
		JLabel lblCols = new JLabel("Cols: ");
		JTextField txtCols = new JTextField();
		txtCols.setColumns(3);
		txtCols.setToolTipText("Default = 51");
		
		JLabel lblSpace3 = new JLabel("                                        ");
		
		
		menuPanels.get(3).add(lblRows);
		menuPanels.get(3).add(txtRows);
		menuPanels.get(3).add(lblCols);
		menuPanels.get(3).add(txtCols);
		menuPanels.get(3).add(lblSpace3);
		
		
		JLabel lblHallSizeMin = new JLabel("Min Hall Size: ");
		JTextField txtHallSizeMin = new JTextField();
		txtHallSizeMin.setColumns(3);
		txtHallSizeMin.setToolTipText("Default = 2");
		
		JLabel lblHallSizeMax = new JLabel("Max Hall Size: ");
		JTextField txtHallSizeMax = new JTextField();
		txtHallSizeMax.setColumns(3);
		txtHallSizeMax.setToolTipText("Default = 6");
		
		JLabel lblSpace4 = new JLabel("          ");
		
		
		menuPanels.get(4).add(lblHallSizeMin);
		menuPanels.get(4).add(txtHallSizeMin);
		menuPanels.get(4).add(lblHallSizeMax);
		menuPanels.get(4).add(txtHallSizeMax);
		menuPanels.get(4).add(lblSpace4);		
		
		
		JButton btnResetGrid = new JButton("New Grid");
		
		btnResetGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nh = txtNumHalls.getText();
				String c = txtCols.getText();
				String r = txtRows.getText();
				String hsmin = txtHallSizeMin.getText();
				String hsmax = txtHallSizeMax.getText();
				
				NewGridButtonPressed(nh, c, r, hsmin, hsmax);
			}
		});
		
		JLabel lblSpace6 = new JLabel("       ");
		
		
		menuPanels.get(6).add(btnResetGrid);
		menuPanels.get(6).add(lblSpace6);
		
		
		
		JLabel lblTurnTime = new JLabel("Turn Time: ");
		JTextField txtTurnTime = new JTextField();
		txtTurnTime.setColumns(6);
		txtTurnTime.setToolTipText("Default = 25");
		
		JLabel lblSpace8 = new JLabel("                                              ");		
		
		
		menuPanels.get(8).add(lblTurnTime);
		menuPanels.get(8).add(txtTurnTime);
		menuPanels.get(8).add(lblSpace8);
		
		
		JButton btnStartSearch = new JButton("Start Search");
		
		btnStartSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartButtonPressed(txtTurnTime.getText());				
			}
		});
		
		JLabel lblSpace10 = new JLabel("       ");
		
		menuPanels.get(10).add(btnStartSearch);
		menuPanels.get(10).add(lblSpace10);
		
		

		for (int i = 0; i < 40; i++) {
			menuPanelHolder.add(menuPanels.get(i));
		}
		
		
		this.getContentPane().add(menuPanelHolder, BorderLayout.EAST);

		
		
		  

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	
	Path returnedPath = new Path();
	
	private Path StartButtonPressed(String strTurnTime) {
		int turnTime = 25;
		
		if (strTurnTime.equals(""))
			turnTime = 25;
		else {
			try {
				turnTime = Integer.parseInt(strTurnTime);
				if (turnTime < 10)
					throw new Exception("Turn time was negative.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Turn time must be an integer greater than or equal to 10. Default is 25.\n" + ex.toString() );
				turnTime = 25;
			}
		}
		
		scout = new Scout(turnTime);
		final int time = turnTime;
		
		CompletableFuture<Path> findPath = new CompletableFuture<Path>();
		findPath.supplyAsync(() -> ScoutSearchAStar(gridCreator.getStartX(), gridCreator.getStartY(), time, allowDiag)).whenCompleteAsync((p, err) -> {
			if (p != null) {
				System.out.println("Found it!");
				returnedPath = p;
			}
			else
				System.out.println("Path not found..");
		});
		
		return returnedPath;
	}
	
	private void NewGridButtonPressed(String strNumHalls, String strCols, String strRows, String strHallSizeMin, String strHallSizeMax) {
		int numHalls = 300;
		int cols = 51;
		int rows = 31;
		int hallSizeMin = 2;
		int hallSizeMax = 6;
		IntRange hallSize = new IntRange(hallSizeMin, hallSizeMax);
		
				
		if (strNumHalls.equals(""))
			numHalls = 300;
		else {
			try {
				numHalls = Integer.parseInt(strNumHalls);
				if (numHalls <= 0)
					throw new Exception("Number of halls was negative.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Number of halls must be a positive integer. Default is 300.\n" + ex.toString() );
			}
		}
		
		if (strCols.equals(""))
			cols = 51;
		else {
			try {
				cols = Integer.parseInt(strCols);
				if (cols <= 0)
					throw new Exception("Columns was negative.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Columns must be a positive integer. Default is 51.\n" + ex.toString() );
			}
		}
		
		if (strRows.equals(""))
			rows = 31;
		else {
			try {
				rows = Integer.parseInt(strRows);
				if (rows <= 0)
					throw new Exception("Rows was negative.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Rows must be a positive integer. Default is 31.\n" + ex.toString() );
			}
		}
		
		if (strHallSizeMin.equals(""))
			hallSizeMin = 2;
		else {
			try {
				hallSizeMin = Integer.parseInt(strHallSizeMin);
				if (hallSizeMin <= 0)
					throw new Exception("Minimum Hall Size was negative.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Minimum Hall Size must be a positive integer. Default is 2.\n" + ex.toString() );
			}
		}
		
		if (strHallSizeMax.equals(""))
			hallSizeMax = 6;
		else {
			try {
				hallSizeMax = Integer.parseInt(strHallSizeMax);
				if (hallSizeMax <= 0)
					throw new Exception("Maximum Hall Size was negative.");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Maximum Hall Size must be a positive integer. Default is 6.\n" + ex.toString() );
			}
		}
		
		hallSize = new IntRange(hallSizeMin, hallSizeMax);
		
		ResetGrid(numHalls, cols, rows, hallSize);		
			
	}
	
	public Path ScoutSearchAStar(int startX, int startY, int turnTime, boolean diag) {
		return scout.runAStar(startX, startY, turnTime, diag);
		
	}
	
	public void ResetGrid(int numHalls, int cols, int rows, IntRange hallSize) {
				
		getContentPane().remove(gridHolder);
		
		gridCreator = new GridCreator();
		
		gridCreator.setNumHalls(numHalls);
		gridCreator.setCols(cols);
		gridCreator.setRows(rows);
		gridCreator.setHallSize(hallSize);
		
		gridCreator.Init();
		
		//gridCreator.Reset();
		gridHolder = gridCreator.getGridHolder();
		
		//scout = new Scout();
		
		
		getContentPane().add(gridHolder);
		this.getContentPane().validate();
		this.getContentPane().repaint();
		
		this.setVisible(true);
	}

}
