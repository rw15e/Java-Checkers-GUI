// Final Project by Ryan Winter rw15e for COP3252 Spring 17 5-3-17
import java.awt.*;

public class Checker {
	private int row;
	private int col;
	public Color color;
	public boolean King;
	private int currentPlayer;
	private boolean playerTurn; 
	
	public Checker(Color b, boolean rOrB, int r, int c) { // true for red, false for black
		color = b;
		this.row = r;
		this.col = c;
		King = false;
		currentPlayer = 1;
		playerTurn = rOrB;
		
		if(b == Color.RED)
			currentPlayer = 1;
		else if(b == Color.BLACK)
			currentPlayer = 0;
		else 
			currentPlayer = 5;
	}
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
	public boolean getPlayerTurn(){
		return playerTurn;
	}
	
	public int getRow() {
		return row;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean getKing(){
		return this.King;
	}
	
	public void placeChecker(int row, int col) {
		this.row = row;
		this.col = col;
		//currentPlayer++;
		//playerTurn = !playerTurn;
	}
}