package com.demineur;


import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.OrientationListener;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity {
	
	int level;
	Button easyButton,mediumButton,hardButton;
	
	ScoreDataBaseExtractor extractor;
	
	final float listWidthReference = 996 ,listHeightReference = 534 ;
	final float listLeftMarginReference  = 256, listTopMarginReference  = 188;
	final float buttonWidthReference = 160 ,buttonHeightReference = 65 ;
	final float easyButtonTopMarginReference  = 300, buttonLeftMarginReference  = 45;
	final float mediumButtonTopMarginReference  = 425, hardButtonTopMarginReference  = 550;
	final float nameViewWidthReference = 400, nameViewHeightReference = 70 ;
	final float scoreViewWidthReference = 400, scoreViewHeightReference = 70 ;
	final float screenWidthReference = 1280, screenHeightReference = 752;
	final float lineBottomMarginReference = 20,  lineTopMarginReference = 20 ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //set activity layout
		requestWindowFeature(Window.FEATURE_NO_TITLE); // no title bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen mode
		setContentView(R.layout.activity_score);
		
		// get screen size
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels; 
		int screenHeight = displayMetrics.heightPixels;
		float screenWidthRatio = screenWidth/screenWidthReference;
		float screenHeightRatio = screenHeight/screenHeightReference;

		// calculate dimensions and margins of graphical objects
		// scoll view for scores list
		int listWidth = (int) (listWidthReference*screenWidthRatio);
		int listHeight = (int) (listHeightReference*screenHeightRatio);
		int listLeftMargin= (int) (listLeftMarginReference*screenWidthRatio);
		int listTopMargin = (int) (listTopMarginReference*screenHeightRatio);
		
		// buttons
		int buttonWidth = (int) (buttonWidthReference*screenWidthRatio);
		int buttonHeight = (int) (buttonHeightReference*screenHeightRatio);
		int buttonLeftMargin= (int) (buttonLeftMarginReference*screenWidthRatio);
		int easyButtonTopMargin = (int) (easyButtonTopMarginReference*screenHeightRatio);
		int mediumButtonTopMargin = (int) (mediumButtonTopMarginReference*screenHeightRatio);
		int hardButtonTopMargin = (int) (hardButtonTopMarginReference*screenHeightRatio);
		
		
		
		
		// resize graphical elements
		ScrollView scoreScrollView = (ScrollView) findViewById(R.id.score_scroll_view);
		
		RelativeLayout.LayoutParams listParams = new RelativeLayout.LayoutParams(listWidth,listHeight);
		listParams.setMargins(listLeftMargin, listTopMargin, 0, 0);
		scoreScrollView.setLayoutParams(listParams);	
		
		easyButton = (Button) findViewById(R.id.button_1);
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(buttonWidth,buttonHeight);
		buttonParams.setMargins(buttonLeftMargin, easyButtonTopMargin, 0, 0);
		easyButton.setLayoutParams(buttonParams);
		easyButton.setTextSize(MenuActivity.textSize);
		
		mediumButton = (Button) findViewById(R.id.button_2);
		buttonParams = new RelativeLayout.LayoutParams(buttonWidth,buttonHeight);
		buttonParams.setMargins(buttonLeftMargin, mediumButtonTopMargin, 0, 0);
		mediumButton.setLayoutParams(buttonParams);	
		mediumButton.setTextSize(MenuActivity.textSize);
		
		hardButton = (Button) findViewById(R.id.button_3);
		buttonParams = new RelativeLayout.LayoutParams(buttonWidth,buttonHeight);
		buttonParams.setMargins(buttonLeftMargin, hardButtonTopMargin, 0, 0);
		hardButton.setLayoutParams(buttonParams);
		hardButton.setTextSize(MenuActivity.textSize);
		
		extractor = new ScoreDataBaseExtractor(this);
		
	}
	
	public void onClick(View view){
		
		switch (view.getId()){
		case R.id.button_1:
			level = 1;
			break;
		case R.id.button_2:
			level = 2;
			break;
		case R.id.button_3:
			level = 3;
			break;
		
		}
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels; 
		int screenHeight = displayMetrics.heightPixels;
		float screenWidthRatio = screenWidth/screenWidthReference;
		float screenHeightRatio = screenHeight/screenHeightReference;

		int nameViewWidth = (int) (nameViewWidthReference*screenWidthRatio);
		int nameViewHeight = (int) (nameViewHeightReference*screenHeightRatio);
		int scoreViewWidth = (int) (scoreViewWidthReference*screenWidthRatio);
		int scoreViewHeight = (int) (scoreViewHeightReference*screenHeightRatio);
		int lineBottomMargin = (int) (lineBottomMarginReference*screenHeightRatio);
		int lineTopMargin = (int) (lineTopMarginReference*screenHeightRatio);
		
		
		LinearLayout.LayoutParams titleLineParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		titleLineParams.setMargins(0,lineTopMargin, 0, lineBottomMargin);
		LinearLayout.LayoutParams lineParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams nameViewParams = new LinearLayout.LayoutParams(
				nameViewWidth,LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams scoreViewParams = new LinearLayout.LayoutParams(
				scoreViewWidth,LinearLayout.LayoutParams.WRAP_CONTENT);
		
		
		extractor.open();
		int length = extractor.getScoreNumber(level);
		extractor.close();
		if (length> ScoreDataBaseExtractor.HIGH_SCORES_LIST_SIZE)
			length = ScoreDataBaseExtractor.HIGH_SCORES_LIST_SIZE;
		
		LinearLayout scoreList = (LinearLayout) findViewById(R.id.score_list);
		scoreList.removeAllViews();
		
		//checking if there are stored high scores
		if (length == 0){ // if not display msg
			LinearLayout.LayoutParams emptyScoreViewParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			emptyScoreViewParams.setMargins(0,lineTopMargin, 0, 0);
			TextView emptyScoreView = new TextView (this);
			emptyScoreView.setLayoutParams(emptyScoreViewParams);
			emptyScoreView.setText(R.string.no_scores);
			scoreList.addView(emptyScoreView);
			emptyScoreView.setTextColor(Color.WHITE);
			emptyScoreView.setTextSize(MenuActivity.textSize);
			emptyScoreView.setGravity(Gravity.CENTER);
		}
		else{ // if there is
			extractor.open();
			Score[] highScores = extractor.getHighScores(level); //get the list
			extractor.close();
			
			
			
			// create columns titles
			// row containing columns titles
			LinearLayout line = new LinearLayout(this);
			line.setLayoutParams(titleLineParams);
			line.setOrientation(LinearLayout.HORIZONTAL);
			line.setGravity(Gravity.CENTER);
			
			// title of column name
			TextView nameView = new TextView (this);
			nameView.setLayoutParams(nameViewParams);
			nameView.setText(R.string.name);
			nameView.setTextColor(Color.WHITE);
			nameView.setTextSize(MenuActivity.textSize + 2);
			nameView.setTypeface(null,Typeface.BOLD);
			nameView.setGravity(Gravity.CENTER);
			
			line.addView(nameView);
			
			//title of column score
			TextView scoreView = new TextView (this);
			scoreView.setLayoutParams(scoreViewParams);
			scoreView.setText(R.string.score);
			scoreView.setTextColor(Color.WHITE);
			scoreView.setTextSize(MenuActivity.textSize + 2);
			scoreView.setTypeface(null,Typeface.BOLD);
			scoreView.setGravity(Gravity.CENTER);
			
			line.addView(scoreView);
			scoreList.addView(line);
			
			// creating the rows containing high scores
			for (int i = 0; i< length ; i++) {
				line = new LinearLayout(this);
				line.setLayoutParams(lineParams);
				line.setOrientation(LinearLayout.HORIZONTAL);
				line.setGravity(Gravity.CENTER);
				nameView = new TextView (this);
				nameView.setLayoutParams(nameViewParams);
				nameView.setText(highScores[i].getName());
				nameView.setTextColor(Color.WHITE);
				nameView.setTextSize(MenuActivity.textSize);
				nameView.setGravity(Gravity.CENTER);
				line.addView(nameView);
				scoreView = new TextView (this);
				scoreView.setLayoutParams(scoreViewParams);
				scoreView.setText(String.valueOf(highScores[i].getDuration()));
				scoreView.setTextColor(Color.WHITE);
				scoreView.setTextSize(MenuActivity.textSize);
				scoreView.setGravity(Gravity.CENTER);
				line.addView(scoreView);
				scoreList.addView(line);
			}
		}
		
		
		
		
		
		
		

	}
	
	
	public void onBackPressed(){
		Intent backIntent = new Intent(ScoreActivity.this,MenuActivity.class);
		finish();
		startActivity(backIntent);
	}
	

	

}
