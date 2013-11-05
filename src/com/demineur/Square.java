package com.demineur;

public class Square {
	private boolean _isMine; // true if there is a mine
	private int _value; // number of mines in the neighbor squares
	private boolean _hidden; // true after left click or after a right click on a mined square
	private boolean _withFlag; // true after right click (when a flag appears)
	private int _positionX,_positionY; // position on the square on the grid
	
	public Square(){
		_isMine = false;
		_value = 0;
		_hidden = true;
	}
	
	public Square(int positionX, int positionY){
		this();
		_positionX = positionX;
		_positionY = positionY;
	}
	
	public int getX(){
		return _positionX;
	}
	
	public int getY(){
		return _positionY;
	}
	
	public void putMine(){
		_isMine = true;
	}
	
	public boolean isMined(){
		return _isMine;
	}
	
	public boolean isFlagged(){
		return _withFlag;
	}
	
	public void rightClick(){
		if (_withFlag)
			_withFlag = false;
		else
			_withFlag = true;
	}
	
	public boolean isHidden(){
		return _hidden;
	}
	
	public void show(){
		_hidden = false;
	}
	
	public void hide(){
		_hidden = true;
	}
	
	public void incrementValue(){
		_value++;
	}
	
	public int getValue(){
		return _value;
	}
	
}
