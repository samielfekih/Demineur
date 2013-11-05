package com.demineur;


import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Grid grid;
	
	int rows;
	int cols;
	int mineNumber;
	int level;
	int duration = 0;
	boolean chronoOn;
	

	// level parameters
	final int[] rowsByLevel = {6,10,14};
	final int[] colsByLevel = {9,15,20};
	final int[] mineNumberByLevel = {8,25,60};
	
	// graphical elements
	Button[][] squaresView;
	Chronometer chrono;
	TextView mineNumberView;
	Dialog dialog;
	TextView dialogTitle, dialogContent;
	EditText nameEditText;
	Button leaveButton,cancelButton;
	Button restartButton,saveButton;
	
	
	ScoreDataBaseExtractor extractor;
	
	// graphical element's dimensions for a 10'1 Tablet (1280 * 752)
	final float gridWidthReference = 996 ,gridHeightReference = 534 ;
	final float gridLeftMarginReference  = 256, gridTopMarginReference  = 188;
	
	final float chronoWidthReference = 120 ,chronoHeightReference = 60 ;
	final float chronoLeftMarginReference  = 55, chronoTopMarginReference  = 315;
	
	final float mineNumberViewWidthReference = 120 ,mineNumberViewHeightReference = 60 ;
	final float mineNumberViewLeftMarginReference  = 55, mineNumberViewTopMarginReference  = 600;
	
	final float dialogButtonWidthReference = 200 ,dialogButtonHeightReference = 70 ;
	final float dialogWidthReference = 800 ,dialogHeightReference = 500;
	final float screenWidthReference = 1280, screenHeightReference = 752;
	final float dialogTitleHeightReference = 200;
	final float dialogContentHeightReference = 200, dialogContentTopMarginReference = 200;
	final float leaveButtonLeftMarginReference  = 500, restartButtonLeftMarginReference  = 100;
	final float leaveButtonTopMarginReference  = 400, restartButtonTopMarginReference  = 400;
	
	final float[] textSizesReference = {24,18,8};
	int[] textSizes = new int[3];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //set activity layout
		requestWindowFeature(Window.FEATURE_NO_TITLE); // no title bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen mode
		setContentView(R.layout.activity_main);
		
		// get level
		level = this.getIntent().getIntExtra(MenuActivity.LEVEL,2);
		// set level parameters
		rows = rowsByLevel[level - 1];
		cols = colsByLevel[level - 1];
		mineNumber = mineNumberByLevel[level - 1];
		
		// create new grid
		grid = new Grid(rows,cols,mineNumber);
		
		mineNumberView = (TextView) findViewById(R.id.mine_number);
		mineNumberView.setText(String.valueOf(mineNumber));
		mineNumberView.setTextSize(MenuActivity.textSize);
		chrono = new Chronometer(this);
		chrono = (Chronometer) findViewById(R.id.chrono);
		chrono.setTextSize(MenuActivity.textSize);
		chronoOn = false;
		
		extractor = new ScoreDataBaseExtractor(this);
		
		
		// get screen size
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels; 
		int screenHeight = displayMetrics.heightPixels;
		float screenWidthRatio = screenWidth/screenWidthReference;
		float screenHeightRatio = screenHeight/screenHeightReference;

		// calculate dimensions and margins of graphical objects
		// grid and squares
		int gridWidth = (int) (gridWidthReference*screenWidthRatio);
		int gridHeight = (int) (gridHeightReference*screenHeightRatio);
		int gridLeftMargin= (int) (gridLeftMarginReference*screenWidthRatio);
		int gridTopMargin = (int) (gridTopMarginReference*screenHeightRatio);
		int squareWidth = (int) gridWidth/(cols);
		int squareHeight = (int) gridHeight/(rows);
		// chronometer
		int chronoWidth = (int) (chronoWidthReference*screenWidthRatio);
		int chronoHeight = (int) (chronoHeightReference*screenHeightRatio);
		int chronoLeftMargin= (int) (chronoLeftMarginReference*screenWidthRatio);
		int chronoTopMargin = (int) (chronoTopMarginReference*screenHeightRatio);
		// textView containing mines number
		int mineNumberViewWidth = (int) (mineNumberViewWidthReference*screenWidthRatio);
		int mineNumberViewHeight = (int) (mineNumberViewHeightReference*screenHeightRatio);
		int mineNumberViewLeftMargin= (int) (mineNumberViewLeftMarginReference*screenWidthRatio);
		int mineNumberViewTopMargin = (int) (mineNumberViewTopMarginReference*screenHeightRatio);
		
		int dialogButtonWidth = (int) (dialogButtonWidthReference*screenWidthRatio);
		int dialogButtonHeight = (int) (dialogButtonHeightReference*screenHeightRatio);
		int dialogWidth = (int) (dialogWidthReference*screenWidthRatio);
		System.out.println(dialogWidth);
		int dialogHeight = (int) (dialogHeightReference*screenHeightRatio);
		int leaveButtonLeftMargin= (int) (leaveButtonLeftMarginReference*screenWidthRatio);
		int leaveButtonTopMargin = (int) (leaveButtonTopMarginReference*screenHeightRatio);
		int restartButtonLeftMargin= (int) (restartButtonLeftMarginReference*screenWidthRatio);
		int restartButtonTopMargin = (int) (restartButtonTopMarginReference*screenHeightRatio);
		int dialogTitleHeight = (int) (dialogTitleHeightReference*screenHeightRatio);
		int dialogContentHeight = (int) (dialogContentHeightReference*screenHeightRatio);
		int dialogContentTopMargin = (int) (dialogContentTopMarginReference*screenHeightRatio);
		
		textSizes[0] = (int) (textSizesReference[0]*screenHeightRatio);
		textSizes[1] = (int) (textSizesReference[1]*screenHeightRatio);
		textSizes[2] = (int) (textSizesReference[2]*screenHeightRatio);
		System.out.println("sizez"+textSizes[2]);
		
		// define dialog elements
		dialog = new Dialog(MainActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		//dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialog.setContentView(R.layout.dialog_layout);
		leaveButton = (Button) dialog.findViewById(R.id.leave_button);
		restartButton = (Button) dialog.findViewById(R.id.restart_button);
		cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
		saveButton = (Button) dialog.findViewById(R.id.save_button);
		dialogContent = (TextView) dialog.findViewById(R.id.dialog_content);
		LinearLayout dialogContentLayout = (LinearLayout) dialog.findViewById(R.id.dialog_content_layout);
		dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
		nameEditText = (EditText) dialog.findViewById(R.id.name);
		
		// resize dialog elements
		// resize dialog layout
		RelativeLayout dialogLayout = (RelativeLayout) dialog.findViewById(R.id.dialog_layout);
		FrameLayout.LayoutParams dialogSizeParams = new FrameLayout.LayoutParams(dialogWidth,dialogHeight);
		dialogLayout.setLayoutParams(dialogSizeParams);				
		// resize buttons
		RelativeLayout.LayoutParams dialogElementsParams = new RelativeLayout.LayoutParams(dialogButtonWidth,dialogButtonHeight);
		dialogElementsParams.setMargins(leaveButtonLeftMargin, leaveButtonTopMargin, 0, 0);
		leaveButton.setLayoutParams(dialogElementsParams);
		cancelButton.setLayoutParams(dialogElementsParams);
		leaveButton.setTextSize(MenuActivity.textSize);
		cancelButton.setTextSize(MenuActivity.textSize);
		dialogElementsParams = new RelativeLayout.LayoutParams(dialogButtonWidth,dialogButtonHeight);
		dialogElementsParams.setMargins(restartButtonLeftMargin, restartButtonTopMargin, 0, 0);
		restartButton.setLayoutParams(dialogElementsParams);
		saveButton.setLayoutParams(dialogElementsParams);
		restartButton.setTextSize(MenuActivity.textSize);
		saveButton.setTextSize(MenuActivity.textSize);
		// resize title and content
		dialogElementsParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,dialogTitleHeight);
		dialogTitle.setLayoutParams(dialogElementsParams);
		dialogTitle.setTextSize(MenuActivity.textSize*2);
		dialogElementsParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,dialogContentHeight);
		dialogElementsParams.setMargins(0, dialogContentTopMargin, 0, 0);
		dialogContentLayout.setLayoutParams(dialogElementsParams);
		dialogContent.setTextSize(MenuActivity.textSize);
		nameEditText.setTextSize(MenuActivity.textSize);
		
		
		// define onClickListeners
		OnClickListener leaveOnClickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent restartIntent = new Intent(MainActivity.this,MenuActivity.class);
				dialog.dismiss();
				finish();
				startActivity(restartIntent);
			}
			
		};
		leaveButton.setOnClickListener(leaveOnClickListener);
		
		OnClickListener restartOnClickListener = new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent restartIntent = new Intent(MainActivity.this,MainActivity.class);
				restartIntent.putExtra(MenuActivity.LEVEL, level);
				dialog.dismiss();
				finish();
				startActivity(restartIntent);
			}
		};
		restartButton.setOnClickListener(restartOnClickListener);
		
		OnClickListener cancelOnClickListener = new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				dialogContent.setText(R.string.want_restart);
				nameEditText.setVisibility(View.GONE);
				restartButton.setVisibility(View.VISIBLE);
				leaveButton.setVisibility(View.VISIBLE);
				saveButton.setVisibility(View.GONE);
				cancelButton.setVisibility(View.GONE);
			}
		};
		cancelButton.setOnClickListener(cancelOnClickListener);
		
		OnClickListener saveOnClickListener = new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				dialogContent.setText(R.string.want_restart);
				nameEditText.setVisibility(View.GONE);
				restartButton.setVisibility(View.VISIBLE);
				leaveButton.setVisibility(View.VISIBLE);
				saveButton.setVisibility(View.GONE);
				cancelButton.setVisibility(View.GONE);
				String name = nameEditText.getText().toString();
				extractor.open();
				extractor.insertScore(new Score(name,level,duration));
				extractor.close();
			}	
		};
		saveButton.setOnClickListener(saveOnClickListener);
		
		class OnSquareClickListener implements OnClickListener {
			private Square square;
			private Grid grid;
			
			public OnSquareClickListener (Grid grid ,Square square){
				this.square = square;
				this.grid = grid;
			}
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				grid.onLeftClick(square);
				if (!chronoOn){
					chrono.setBase(SystemClock.elapsedRealtime());
					chrono.start();
					chronoOn = true;
				}
				viewUpdate();
			}
		}
		
		class OnSquareLongClickListener implements OnLongClickListener {
			private Square square;
			
			public OnSquareLongClickListener (Square square){
				this.square = square;
			}

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				square.rightClick();
				viewUpdate();
				return true;
			}
		}
		
		
		
		// create graphical grid 
		LinearLayout gridView = (LinearLayout) findViewById(R.id.grid);
		
		RelativeLayout.LayoutParams gridViewParams = new RelativeLayout.LayoutParams(gridWidth,gridHeight);
		gridViewParams.setMargins(gridLeftMargin, gridTopMargin, 0, 0);
		gridView.setLayoutParams(gridViewParams);	
		
		RelativeLayout.LayoutParams chronoViewParams = new RelativeLayout.LayoutParams(chronoWidth,chronoHeight);
		chronoViewParams.setMargins(chronoLeftMargin, chronoTopMargin, 0, 0);
		chrono.setLayoutParams(chronoViewParams);	
		
		RelativeLayout.LayoutParams mineNumberViewViewParams = new RelativeLayout.LayoutParams(mineNumberViewWidth,mineNumberViewHeight);
		mineNumberViewViewParams.setMargins(mineNumberViewLeftMargin, mineNumberViewTopMargin, 0, 0);
		mineNumberView.setLayoutParams(mineNumberViewViewParams);	
		
		
		squaresView = new Button[rows][cols];
		OnSquareClickListener onClickListener;
		OnSquareLongClickListener onLongClickListener;
		LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(squareWidth,squareHeight);
		for (int i =0; i< rows; i++) {
			LinearLayout line = new LinearLayout(this); // create a row
			//set row graphical parameters
			LinearLayout.LayoutParams parametreslignes = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			line.setLayoutParams(parametreslignes);
			line.setOrientation(LinearLayout.HORIZONTAL);
			line.setGravity(Gravity.CENTER);
			for (int j = 0; j< cols ; j++){
				squaresView[i][j] = new Button (this); // create a square
				//set square graphical parameters
				squaresView[i][j].setLayoutParams(layoutParams);
				squaresView[i][j].setBackgroundResource(R.drawable.button_gray);
				line.addView(squaresView[i][j]); // add square to the row
				// set onClickListeners 
				onClickListener = new OnSquareClickListener(grid,grid.getSquare(i,j));
				onLongClickListener = new OnSquareLongClickListener(grid.getSquare(i,j));
				squaresView[i][j].setOnClickListener(onClickListener);
				squaresView[i][j].setOnLongClickListener(onLongClickListener);
			}
			
			gridView.addView(line); // add row to the grid
			
		}
		
		

	}
	
	public void viewUpdate(){ //method displaying all changes, this method is called every click
		int remainingMinesNumber = mineNumber; // the remaining mines number displayed
		for (int i =0; i< rows; i++) {
			for (int j = 0; j< cols ; j++){
				if (!this.grid.getSquare(i,j).isHidden()){ 
					if (!this.grid.getSquare(i,j).isMined()){
						if (grid.getSquare(i,j).getValue()>0){
								squaresView[i][j].setBackgroundResource(R.drawable.button_blue); //uncovered square with at least a mine around
								squaresView[i][j].setText(String.valueOf(grid.getSquare(i,j).getValue())); // display number of mines around
								squaresView[i][j].setTextColor(Color.WHITE);
								squaresView[i][j].setTextSize(textSizes[level-1]);
						}
						else
								squaresView[i][j].setBackgroundResource(R.drawable.button_blue_empty); //uncovered square with no mines around
					}
					else
						squaresView[i][j].setBackgroundResource(R.drawable.button_red); //uncovered mine
				}
				else if (grid.getSquare(i,j).isFlagged()){
					squaresView[i][j].setBackgroundResource(R.drawable.button_green); //squares with a flag on it
					if (remainingMinesNumber>0)
						remainingMinesNumber --;
				}
				else
					squaresView[i][j].setBackgroundResource(R.drawable.button_gray); // square not uncovered with no flag on it
			}
		}
		
		mineNumberView.setText(String.valueOf(remainingMinesNumber)); //display remaining mines number
		
		
		if (grid.checkEnd()){
			if (!grid.isGameOver()){ // if win 
				duration = (int)((SystemClock.elapsedRealtime()-chrono.getBase())/1000);
				extractor.open();
				Score[] highScores = extractor.getHighScores(level);
				Score lastHighScore = extractor.getLastHighScore(level);
				int scoresNumber = extractor.getScoreNumber(level);
				extractor.close();
				
				if (highScores == null){ // if there is no high scores stored, store the new score
					dialogContent.setText(R.string.enter_name);
					dialogTitle.setText(R.string.highscore);
					nameEditText.setVisibility(View.VISIBLE);
					saveButton.setVisibility(View.VISIBLE);
					cancelButton.setVisibility(View.VISIBLE);
					restartButton.setVisibility(View.GONE);
					leaveButton.setVisibility(View.GONE);
				}
				
				else if (scoresNumber<ScoreDataBaseExtractor.HIGH_SCORES_LIST_SIZE || lastHighScore.getDuration() > duration){
					// if there is less than max high scores number displayed or if the new score is better than the last one 
					// displayed in score activity , store the new score
					dialogContent.setText(R.string.enter_name); 
					dialogTitle.setText(R.string.highscore);
					nameEditText.setVisibility(View.VISIBLE);
					saveButton.setVisibility(View.VISIBLE);
					cancelButton.setVisibility(View.VISIBLE);
					restartButton.setVisibility(View.GONE);
					leaveButton.setVisibility(View.GONE);
				}
				else
					dialogTitle.setText(R.string.win);
				
			}
			dialog.show();
			chrono.stop();
		}
		
	}
	
	public void onBackPressed(){
		Intent backIntent = new Intent(MainActivity.this,MenuActivity.class);
		finish();
		startActivity(backIntent);
	}

	

}
