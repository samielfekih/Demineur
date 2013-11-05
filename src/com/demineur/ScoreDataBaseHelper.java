package com.demineur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreDataBaseHelper extends SQLiteOpenHelper {

	private static String DB_NAME = "scoredb.sqlite";
	public SQLiteDatabase myDataBase;
	private final Context myContext;
	
	private static final String TABLE_SCORE_CREATE = "CREATE TABLE "
			+ ScoreDataBaseExtractor.TABLE_SCORE + "("
			+ ScoreDataBaseExtractor.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
			+ ScoreDataBaseExtractor.COL_NAME + ","
			+ ScoreDataBaseExtractor.COL_LEVEL + " ,"
			+ ScoreDataBaseExtractor.COL_DURATION + " );";

	private static final String TABLE_CUSTOM_LEVEL_SCORE_CREATE = "CREATE TABLE "
			+ ScoreDataBaseExtractor.TABLE_CUSTOM_SCORE + "("
			+ ScoreDataBaseExtractor.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
			+ ScoreDataBaseExtractor.COL_NAME + ","
			+ ScoreDataBaseExtractor.COL_LEVEL + " ,"
			+ ScoreDataBaseExtractor.COL_DURATION + " ,"
			+ ScoreDataBaseExtractor.COL_SCORE + " ,"
			+ ScoreDataBaseExtractor.COL_ROWS + " ,"
			+ ScoreDataBaseExtractor.COL_COLS + " ,"
			+ ScoreDataBaseExtractor.COL_MINE_NUMBER + " );";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */

	public ScoreDataBaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_SCORE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}