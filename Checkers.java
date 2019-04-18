// Final Project by Ryan Winter rw15e for COP3252 Spring 17 5-3-17
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Checkers implements ActionListener, MouseListener {
	private int blackCount; // counter for black checkers
	private int redCount; // counter for red checkers
	private int winValue = 5;
	private int currentPlayer; // will be a 0 if red and a 1 if black
	private GameBoard gameBoard; // gameboard that holds all data
	private BoardBlock clickedBlock;
	private boolean found; 
	private boolean jump;
	private JPanel gamePanel; 	// jpanel for game board
	private JLabel whosTurn; // lets user know whos turn it is
	private JMenuBar menubar; // menu bar
	private JMenu fileMenu; // main menu that holds other options
	private JMenuItem newGame; // start new game menu
	private JMenuItem saveGame; // save game menu
	private JMenuItem loadGame; // load game menu
	private JMenuItem exitGame; // quit menu
	private JFrame mainFrame; 	// the main frame that holds everything 

	public static void main(String[] args) {
		new Checkers(); // create new checkers object
		
	}
	public Checkers() {
		playGame(); // set up and play the game
		currentPlayer = 1; // start with red player
		redCount = 12;
		blackCount = 12;
	}
	
	public void playGame() { 
		currentPlayer = 1;
		redCount = 12; // each player starts with 12 checkers
		blackCount = 12;
		mainFrame = new JFrame("Checkers by Ryan Winter for COP3252 Spring 17");
		mainFrame.setLayout(new FlowLayout());
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		gamePanel = new JPanel(new GridLayout(8, 8)); // create the board, using a grid layout
		gameBoard = new GameBoard();
		whosTurn = new JLabel("Reds Turn!"); // start of with red players turn
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this); 
		saveGame = new JMenuItem("Save Game");
		newGame.addActionListener(this);
		loadGame = new JMenuItem("Load Game");
		loadGame.addActionListener(this);
		exitGame = new JMenuItem("Quit Game");
		exitGame.addActionListener(this);
		fileMenu.add(newGame);
		fileMenu.add(saveGame);
		fileMenu.add(loadGame);
		fileMenu.add(exitGame);
		menubar.add(fileMenu);
		
		for(int i = 0; i < 8; i++) { // loop through the full board creating blocks
			for(int j = 0; j < 8; j++) {
				JPanel ContainerPanel = new JPanel(new FlowLayout());
				BoardBlock block = gameBoard.getBlock(i, j); 
				block.addMouseListener(this);
				ContainerPanel.add(block); 
				gamePanel.add(ContainerPanel);
				if(block.getBoardColor() == BoardBlock.BoardColor.DARKBLOCK)
					ContainerPanel.setBackground(new Color(139,69,19)); // darker brown
				else if(block.getBoardColor() == BoardBlock.BoardColor.LIGHTBLOCK)
					ContainerPanel.setBackground(new Color(244,164,96)); // light brown 
			}
		}
		mainFrame.setJMenuBar(menubar);
		mainFrame.add(whosTurn);
		whosTurn.setHorizontalTextPosition(JLabel.CENTER);
		mainFrame.add(gamePanel);
		mainFrame.pack();
		mainFrame.setVisible(true); // pack it before setting visible for clearer look
	}
	public int checkWin(){ // returns 0 if red wins, and 1 if black wins, 5 if no winner
		if(blackCount == 0)
			return 0; 
		if(redCount == 0)
			return 1;
		return 5;
	}
	public void mouseClicked(MouseEvent e) {
		BoardBlock clicked = (BoardBlock)e.getComponent();
		if (clicked.isTaken() == true && clicked.getPiece().getCurrentPlayer() != currentPlayer) // 0 for black 1 for red
			return;

		if(clicked.isTaken() == true){	// highlight new block
			if(clickedBlock == null){
				clickedBlock = clicked;
				clickedBlock.selectBlock();
				gameBoard.loadMoveVector(clickedBlock.getPiece());
			}
			else if(clickedBlock != null){ // change block that is highlighted
				clickedBlock.clearBlock();
				gameBoard.clearMoveVector(clickedBlock.getPiece());
				clickedBlock = clicked;
				clickedBlock.selectBlock();
				gameBoard.loadMoveVector(clickedBlock.getPiece());
			}
		}
		else if(clicked.isTaken() == false) { // jump
			Vector<BoardBlock> oldPossibleMoves = gameBoard.getPossibleMoves(clickedBlock.getPiece());
			found = false;
			jump = false;	
			checkMove(clicked);
			if(found == true) {
				if(jump == true) {
					if(currentPlayer == 0)
						redCount--;
					else
						blackCount--;
				}
				for (BoardBlock unhighlight : oldPossibleMoves)
					unhighlight.clearBlock();
				clickedBlock.clearBlock(); 
				clickedBlock = null;
				playerTracking(); // swaps players, updates message indicating whos turn it is
				if(checkWin() == 0)
					winValue = JOptionPane.showConfirmDialog(mainFrame, "Would you like to play again?", "Game Over, Red Won!", JOptionPane.YES_NO_OPTION);
				else if(checkWin() == 1)
					winValue = JOptionPane.showConfirmDialog(mainFrame, "Would you like to play again?", "Game Over, Black Won!", JOptionPane.YES_NO_OPTION);
		
				if(winValue == JOptionPane.YES_OPTION){
					mainFrame.setVisible(false);
					mainFrame.dispose(); // get rid of old frame
					winValue = 5; // reset winValue so it doesnt loop creating new frame
					playGame();
					//currentPlayer = 1;
				}
				else if (winValue == JOptionPane.NO_OPTION)
					System.exit(0);
				else if (winValue == JOptionPane.CANCEL_OPTION)
					System.exit(0);
			}
			else 
				whosTurn.setText("Invalid move! Try again!");
		}
		else if(clicked.equals(clickedBlock)) { // clear clicked square
			clickedBlock.clearBlock();
			gameBoard.clearMoveVector(clickedBlock.getPiece());
			clickedBlock = null;
		}
	}
	public void playerTracking(){
		if(currentPlayer == 0)
			whosTurn.setText("Reds turn!");
		else
			whosTurn.setText("Blacks turn!");
		if(currentPlayer == 0)
			currentPlayer = 1;
		else if(currentPlayer == 1)
			currentPlayer = 0;
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public void checkMove(BoardBlock b) {
		Vector<BoardBlock> oldPossibleMoves = gameBoard.getPossibleMoves(clickedBlock.getPiece());
		for(BoardBlock selection : oldPossibleMoves) {
			if(selection.equals(b)) {
				jump = gameBoard.moveChecker(clickedBlock, b);
				found = true;
			}
		}	
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) {
			playGame();
			//currentPlayer = 1;
		}
		else if (e.getSource() == saveGame){
			SaveData data = new SaveData();
            data.savedBoard = gameBoard;
            data.savedCurrentPlayer = currentPlayer;
            try {
                ResourceManager.save(data, "1.save");
            }
            catch (Exception e2) {
                System.out.println("Failed to save: " + e2.getMessage());
            }
		}
		else if (e.getSource() == loadGame){
			try {
                SaveData data = (SaveData)ResourceManager.load("1.save");
                // gameBoard.setText(data.savedBoard);
                // currentPlayer.setValue(data.savedCurrentPlayer);
                // currentPlayer.setText(data.savedCurrentPlayer);
                //currentPlayer.(data.savedCurrentPlayer);
            }
            catch (Exception e3) {
                System.out.println("Failed to load: " + e3.getMessage());
            }
		}
		else if(e.getSource() == exitGame)
			System.exit(0);
	}
}