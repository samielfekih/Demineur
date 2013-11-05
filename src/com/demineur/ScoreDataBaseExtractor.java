package com.demineur;

import java.io.IOException;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScoreDataBaseExtractor {
	Context context;
	ScoreDataBaseHelper dbHelper;
	private SQLiteDatabase db;
	
	public static final String TABLE_SCORE = "score";
	public static final String TABLE_CUSTOM_SCORE = "customScore";
	
	public static final String COL_ID = "id" ;
	public static final String	COL_NAME = "name" ;
	public static final String	COL_LEVEL = "level" ;
	public static final String	COL_DURATION = "duration" ;
	public static final String COL_SCORE = "score" ;
	public static final String COL_ROWS = "rows";
	public static final String COL_COLS = "rows";
	public static final String COL_MINE_NUMBER = "mineNumber";	
	
	public static final int NUM_COL_ID = 0 ;
	public static final int	NUM_COL_NAME = 1;
	public static final int	NUM_COL_LEVEL = 2;
	public static final int	NUM_COL_DURATION = 3 ;
	public static final int NUM_COL_SCORE = 4;
	public static final int NUM_COL_ROWS = 5;
	public static final int NUM_COL_COLS = 6;
	public static final int NUM_COL_MINE_NUMBER = 7;
	
	public static final int HIGH_SCORES_LIST_SIZE = 10; //number of high scores per level displayed in score activity

	
	public ScoreDataBaseExtractor(Context context) {
		this.context = context;
		dbHelper = new ScoreDataBaseHelper(context);
	}
	
	public ScoreDataBaseExtractor open(){
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public SQLiteDatabase getBDD(){
		return db;
	}
	
	
	public void insertScore(Score score){
		ContentValues values = new ContentValues();
		values.put(COL_NAME, score.getName());
		values.put(COL_LEVEL, score.getLevel());
		values.put(COL_DURATION, score.getDuration());
		db.insert(TABLE_SCORE, null, values);
	}
	
	// gives number of stored scores
	public int getScoreNumber(int level) {
		Cursor cursor = db.query(TABLE_SCORE, null, COL_LEVEL + "=" + level, null, null, null, null);
		return cursor.getCount();
	}
	
	
	private Score cursorToScore(Cursor c){	
		if (c.getCount() == 0)
			return null;
		String name = c.getString(NUM_COL_NAME);
		int duration = c.getInt(NUM_COL_DURATION);
		int level = c.getInt(NUM_COL_LEVEL);
		Score result = new Score(name,level,duration);
		return result;
	}
	
	// gives the last score in the high scores list
	public Score getLastHighScore(int level){
		Cursor cursor = db.query(TABLE_SCORE, null, COL_LEVEL + "=" + level, null, null, null,COL_DURATION);
		Score highScore = null;
		if (cursor.moveToPosition(HIGH_SCORES_LIST_SIZE - 1)) 
				highScore = cursorToScore(cursor);
		cursor.close();
		return highScore;
	}
	
	
	public Score[] getHighScores(int level){
		int j = 0;	
		Cursor cursor = db.query(TABLE_SCORE, null, COL_LEVEL + "=" + level, null, null, null,COL_DURATION);
		Score[] highScores = new Score [HIGH_SCORES_LIST_SIZE];
		for (int i= 0; i< HIGH_SCORES_LIST_SIZE; i++){
			if (cursor.moveToPosition(i)) {
				highScores[j] = cursorToScore(cursor);
				j++;	
			}
		}
		cursor.close();
		return highScores;
	}
	
	
	public void deleteHighScores(int level){
		db.delete(TABLE_SCORE,COL_LEVEL + " = " + String.valueOf(level), null);
	}

}