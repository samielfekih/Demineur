package com.demineur;

public class Score {
	private String _name;
	private int _level;
	private long _duration;
	private boolean _custom;
	private int _rows;
	private int _cols;
	private int _mineNumber;
	
	// score for classic game (level easy, medium and hard)
	public Score(String name, int level, long duration){
		_name = name;
		_level =level;
		_duration = duration;
		_custom = false;
	}
	
	
	// score for custom grid (chosen rows number, columns number and mines number)
	public Score(String name, int level, long duration, int rows, int cols, int mineNumber){
		this(name,level,duration);
		_custom = true;
		_rows = rows;
		_cols = cols;
		_mineNumber = mineNumber;
	}

	

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public int getLevel() {
		return _level;
	}

	public void setLevel(int level) {
		this._level = level;
	}

	public long getDuration() {
		return _duration;
	}

	public void setD_duration(long duration) {
		this._duration = duration;
	}

	public boolean isCustom() {
		return _custom;
	}

	public void setCustom(boolean _custom) {
		this._custom = _custom;
	}

	public int getRows() {
		return _rows;
	}

	public void setRows(int _rows) {
		this._rows = _rows;
	}

	public int getCols() {
		return _cols;
	}

	public void setCols(int _cols) {
		this._cols = _cols;
	}

	public int getMineNumber() {
		return _mineNumber;
	}

	public void setMineNumber(int _mineNumber) {
		this._mineNumber = _mineNumber;
	}
}
