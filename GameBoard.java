// Final Project by Ryan Winter rw15e for COP3252 Spring 17 5-3-17
import java.awt.*;
import java.util.*;

public class GameBoard {
	private BoardBlock[][] gameBoard; // array that holds board
	private int tempRow;
    private int tempCol; 
	private int row = 8; // 8 row
    private int col = 8; // 8 col
    private boolean darkBlock;
    
    public GameBoard() {
    	gameBoard = new BoardBlock[row][col];
    	for(int i = 0; i < row; i++) { // loop through entire board
    		for(int j = 0; j < col; j++) {
    			if(darkBlock == true)
    				gameBoard[i][j] = new BoardBlock(BoardBlock.BoardColor.DARKBLOCK, i, j);
    			else if(darkBlock == false) // if not a dark block, draw a light block
    				gameBoard[i][j] = new BoardBlock(BoardBlock.BoardColor.LIGHTBLOCK, i, j);
    			darkBlock = !darkBlock; // alternate colors for j loop
    		}
    		darkBlock = !darkBlock; // alternate colors for i loop
    	}  
    	
    	for(int row = 0; row < 3; row++) // populate the game board with checkers
			for(int col = 0; col < 8; col++)
				if(getBlock(row, col).getBoardColor() == BoardBlock.BoardColor.DARKBLOCK)
					getBlock(row,col).setChecker(new Checker(Color.BLACK, false, row, col));
		for(int row = 5; row < 8; row++)
			for(int col = 0; col < 8; col++)
				if(getBlock(row, col).getBoardColor() == BoardBlock.BoardColor.DARKBLOCK)
					getBlock(row,col).setChecker(new Checker(Color.RED, true, row, col));
    }      
   
    public BoardBlock getBlock(int r, int c) { // checks if row/col is valid, if it is then it returns the gameboard
        if(isValidMove(r, c) == true)
        	return gameBoard[r][c];
        else
        	return null; // invalid move
    }
   
	public Vector<BoardBlock> getPossibleMoves(Checker c) {
		Vector<BoardBlock> possibleMoves = new Vector<BoardBlock>();
		Color tempColor = c.getColor();
		tempRow = c.getRow();
		tempCol = c.getCol();
		if(c.color == Color.RED){
			for(int i = 0; i < 8; i++)
					if(c.getRow() == 0 && c.getCol() == i)
						c.King = true;
		}
		if(c.color == Color.BLACK){
			for(int i = 0; i < 8; i++)
					if(c.getRow() == 7 && c.getCol() == i)
						c.King = true;
		}
		if(c.King == true){ // if Checker is a king it needs to be able to move up and down GameBoard
			for(int i = 1; i < 2; i++){
				if(GameBoard.isValidMove(tempRow+i, tempCol+i) == true) { // check bottom right
					if(this.getBlock(tempRow+i, tempCol+i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow+i, tempCol+i));
					else // check jump
						if(GameBoard.isValidMove(tempRow+(i+1), tempCol+(i+1)) == true && this.getBlock(tempRow+(i+1), tempCol+(i+1)).isTaken() == false)
								possibleMoves.add(this.getBlock(tempRow+(i+1), tempCol+(i+1)));
				}
				if(GameBoard.isValidMove(tempRow-i, tempCol+i) == true) { // check top right
					if(this.getBlock(tempRow-i, tempCol+i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow-i, tempCol+i));
					else // check jump
						if(GameBoard.isValidMove(tempRow-(i+1), tempCol+(i+1)) == true && this.getBlock(tempRow-(i+1), tempCol+2).isTaken() == false)
								possibleMoves.add(this.getBlock(tempRow-(i+1), tempCol+(i+1)));
				}
				if(GameBoard.isValidMove(tempRow+i, tempCol-i) == true) { // check bottom left
					if(this.getBlock(tempRow+i, tempCol-i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow+i, tempCol-i));
					else // check jump
						if(GameBoard.isValidMove(tempRow+(i+1), tempCol-(i+1)) == true && this.getBlock(tempRow+(i+1), tempCol-(i+1)).isTaken() == false)
								possibleMoves.add(this.getBlock(tempRow+(i+1), tempCol-(i+1)));
				}
				if(GameBoard.isValidMove(tempRow-i, tempCol-i) == true) { // check top left
					if(this.getBlock(tempRow-i, tempCol-i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow-i, tempCol-i));
					else // check jump
						if(GameBoard.isValidMove(tempRow-(i+1), tempCol-(i+1)) == true && this.getBlock(tempRow-(i+1), tempCol-(i+1)).isTaken() == false)
								possibleMoves.add(this.getBlock(tempRow-(i+1), tempCol-(i+1)));
				}
			}			
		} // end of if king
		else { // if not a king
			for(int i = 1; i < 2; i++){
				if(GameBoard.isValidMove(tempRow+i, tempCol+i) == true && tempColor == Color.BLACK) { // check bottom right
					if(this.getBlock(tempRow+i, tempCol+i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow+i, tempCol+i));
					else // check jump
						if(GameBoard.isValidMove(tempRow+(i+1), tempCol+(i+1)) == true && this.getBlock(tempRow+(i+1), tempCol+(i+1)).isTaken() == false && this.getBlock(tempRow+i, tempCol+i).getPiece().getColor() == Color.RED)
								possibleMoves.add(this.getBlock(tempRow+(i+1), tempCol+(i+1)));
				}
				if(GameBoard.isValidMove(tempRow-i, tempCol+i) == true && tempColor == Color.RED) { // check top right
					if(this.getBlock(tempRow-i, tempCol+i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow-i, tempCol+i));
					else // check jump
						if(GameBoard.isValidMove(tempRow-(i+1), tempCol+(i+1)) == true && this.getBlock(tempRow-(i+1), tempCol+2).isTaken() == false && this.getBlock(tempRow-i, tempCol+i).getPiece().getColor() == Color.BLACK)
								possibleMoves.add(this.getBlock(tempRow-(i+1), tempCol+(i+1)));
				}
				if(GameBoard.isValidMove(tempRow+i, tempCol-i) == true && tempColor == Color.BLACK) { //check moves to the bottom-left of this Checker
					if(this.getBlock(tempRow+i, tempCol-i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow+i, tempCol-i));
					else // check jump
						if(GameBoard.isValidMove(tempRow+(i+1), tempCol-(i+1)) == true && this.getBlock(tempRow+(i+1), tempCol-(i+1)).isTaken() == false && this.getBlock(tempRow+i, tempCol-i).getPiece().getColor() == Color.RED)
								possibleMoves.add(this.getBlock(tempRow+(i+1), tempCol-(i+1)));
				}	
				if(GameBoard.isValidMove(tempRow-i, tempCol-i) == true && tempColor == Color.RED) { //Check moves to the top-left of this Checker
					if(this.getBlock(tempRow-i, tempCol-i).isTaken() == false)
						possibleMoves.add(this.getBlock(tempRow-i, tempCol-i));
					else // check jump
						if(GameBoard.isValidMove(tempRow-(i+1), tempCol-(i+1)) == true && this.getBlock(tempRow-(i+1), tempCol-(i+1)).isTaken() == false && this.getBlock(tempRow-i, tempCol-i).getPiece().getColor() == Color.BLACK)
								possibleMoves.add(this.getBlock(tempRow-(i+1), tempCol-(i+1)));
				}
			}
		} // end of not king
		return possibleMoves;	
	}
	
    public static boolean isValidMove(int r, int c) { // makes sure that the row/col passed in is a valid move
    	if(r > -1 && c > -1 && r < 8 && c < 8)	
    		return true;
    	else 
    		return false; // invalid move
    }
 
	public void loadMoveVector(Checker c) { 
		Vector<BoardBlock> possibleMoves = getPossibleMoves(c);
		for(BoardBlock highlight : possibleMoves)
			highlight.selectBlock();
	}
	
	public void clearMoveVector(Checker c) {
		Vector<BoardBlock> possibleMoves = getPossibleMoves(c);
		for(BoardBlock moves : possibleMoves)
			moves.clearBlock();
	}
	
	public boolean moveChecker(BoardBlock start, BoardBlock end) {
		Checker tempChecker = start.getPiece();
		BoardBlock jumpBlock;
		int jumpRow;
		int jumpCol;
		boolean jump = false;
		int oldRow = start.getRow();
		int newRow = end.getRow();
		int oldCol = start.getCol();
		int newCol = end.getCol();
		start.setChecker(null);
		tempChecker.placeChecker(end.getRow(), end.getCol());
		end.setChecker(tempChecker);	
		if(Math.abs(oldRow - newRow) > 1 || Math.abs(oldCol - newCol) > 1){
			jump = true;	
			jumpRow = (oldRow + newRow) / 2;
			jumpCol = (oldCol + newCol) / 2;
			jumpBlock = getBlock(jumpRow, jumpCol);
			jumpBlock.setChecker(null);
			jumpBlock.update(jumpBlock.getGraphics());
		}
		start.update(start.getGraphics());
		end.update(end.getGraphics());
		return jump;
	}   
}