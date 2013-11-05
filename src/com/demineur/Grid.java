package com.demineur;

import java.util.ArrayList;

import android.util.Log;

public class Grid {
	private int _rows,_cols,_mineNumber;
	private Square[][] _squares;
	private boolean _gameOver;
	
	
	
	public Grid(int rows, int cols){
		_rows = rows; //number of rows
		_cols = cols; //number of columns 
		_squares = new Square[rows][cols]; 
		_gameOver = false;
		for (int i =0; i< rows; i++) //  create the squares
			for (int j = 0; j< cols ; j++)
				_squares[i][j] = new Square(i,j);
	}
	
	public Grid(int rows,int cols,int mineNumber){
		this(rows,cols);
		_mineNumber = mineNumber; //number of mines in the grid
		Log.i("sq","created");
		this.minePositioning(); //  place the mines
		Log.i("sq","mined");
	}
	
	
	public  boolean isGameOver(){
		return _gameOver;
	}
	
	public Square getSquare(int positionX, int positionY){
		return _squares[positionX][positionY];
	}
	
	public int getRows(){
		return _rows;
	}
	
	public int getCols(){
		return _cols;
	}
	
	public int getMineNumber(){
		return _mineNumber;
	}
	
	
	
	
	public void minePositioning (){
		ArrayList<Square> neighbors;
		for (int i = 0; i< _mineNumber ; i++ ){ 
			int positionX,positionY;
			do { 
				// choose a random square to put the mine
				positionX = (int)(Math.random()*_rows); 
				positionY = (int)(Math.random()*_cols);
			} while ( _squares[positionX][positionY].isMined()); //check if there is already a mine, 
			_squares[positionX][positionY].putMine();  // put the mine
			// increment the values of neighbors
			neighbors = this.getNeighbors(_squares[positionX][positionY]);
			for (int j = 0; j< neighbors.size(); j++)
				neighbors.get(j).incrementValue();
			
		}
	}
	
	public ArrayList<Square> getNeighbors(Square square){
		ArrayList<Square> result = new ArrayList<Square>();
		int positionX = square.getX();
		int positionY = square.getY();
		
		//border cases have to be checked
		if (positionX>0){
			if (positionY>0)
				result.add(_squares[positionX-1][positionY-1]);
			if (positionY< _cols-1)
				result.add(_squares[positionX-1][positionY+1]);
			result.add(_squares[positionX-1][positionY]);
		}
		

		if (positionY>0)
			result.add(_squares[positionX][positionY-1]);
		if (positionY< _cols-1)
			result.add(_squares[positionX][positionY+1]);
		
		if (positionX< _rows -1){
			if (positionY>0)
				result.add(_squares[positionX+1][positionY-1]);
			if (positionY< _cols-1)
				result.add(_squares[positionX+1][positionY+1]);
			result.add(_squares[positionX+1][positionY]);
		}
		

		return result;
	}
	
	
	public void onLeftClick(Square square){
		boolean[][] isVisited = new boolean[_rows][_cols]; //containing booleans 
		for (int i =0; i< _rows; i++) 
			for (int j = 0; j< _cols ; j++){
				isVisited[i][j] = false;
			}
		onLeftClick(square,isVisited);
	}
	
	public void onLeftClick(Square square, boolean[][] isVisited){ //recursive method 
		ArrayList<Square> neighbors;
		isVisited[square.getX()][square.getY()]= true;
		if (square.isFlagged()) // if there is a flag on it, the flag is removed
			square.rightClick();
		else if (square.isMined()){ // if there is a mine, the game is over
			_gameOver = true;
			showAll();
		}
		else if (square.getValue()>0){ // if there is a mine in at least one of the neighbors, the square is uncovered
			square.show();
		}
		else{ // if there is no mine in the neighbors, the same process is applied on every neighbor
			square.show();
			neighbors = this.getNeighbors(square);
			for (int j = 0; j< neighbors.size(); j++)
				if (!isVisited[neighbors.get(j).getX()][neighbors.get(j).getY()])
					onLeftClick(neighbors.get(j),isVisited);
		}		
	}
	
	public void showAll(){ // in case of game over
		for (int i =0; i< _rows; i++) 
			for (int j = 0; j< _cols ; j++)
				_squares[i][j].show();
	}
	
	public boolean checkEnd(){
		if (_gameOver)
			return true; // in case a mine is uncovered
		for (int i = 0 ; i< _rows; i++) // checking if the game is over
			for (int j = 0; j<_cols ; j++){
				if (_squares[i][j].isHidden() && !_squares[i][j].isMined()) // if there is an uncovered and not mined square
					return false;
				if (_squares[i][j].isMined() && !_squares[i][j].isFlagged()) // if there is a mined square without a flag on it
					return false;
			}
		return true;
	}

	
	
	
}
