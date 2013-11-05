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

public class MenuActivity extends Activity {
	
	int level;
	boolean levelChoice;
	
	Button scoresButton,helpButton,startButton;
	
	static final String LEVEL = "level";

	final float buttonWidthReference = 200 ,buttonHeightReference = 70, buttonTopMarginReference  = 500 ;
	final float helpButtonLeftMarginReference  = 300, scoresButtonLeftMarginReference  = 1000, startButtonLeftMarginReference  = 650;
	final float screenWidthReference = 1280, screenHeightReference = 752;
	final int textSizeReference = 24;
	static int textSize;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //set activity layout
		requestWindowFeature(Window.FEATURE_NO_TITLE); // no title bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen mode
		setContentView(R.layout.activity_menu);
		
		// get screen size
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels; 
		int screenHeight = displayMetrics.heightPixels;
		float screenWidthRatio = screenWidth/screenWidthReference;
		float screenHeightRatio = screenHeight/screenHeightReference;

		// calculate dimensions and margins of graphical objects
		int buttonWidth = (int) (buttonWidthReference*screenWidthRatio);
		int buttonHeight = (int) (buttonHeightReference*screenHeightRatio);
		int buttonTopMargin = (int) (buttonTopMarginReference*screenHeightRatio);
		int helpButtonLeftMargin= (int) (helpButtonLeftMarginReference*screenWidthRatio);
		int scoresButtonLeftMargin= (int) (scoresButtonLeftMarginReference*screenWidthRatio);
		int startButtonLeftMargin= (int) (startButtonLeftMarginReference*screenWidthRatio);
		
		textSize = (int) (textSizeReference*screenHeightRatio);
		
		
		// set graphical objects
		helpButton = (Button) findViewById(R.id.button_1);
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(buttonWidth,buttonHeight);
		buttonParams.setMargins(helpButtonLeftMargin, buttonTopMargin, 0, 0);
		helpButton.setLayoutParams(buttonParams);
		helpButton.setTextSize(MenuActivity.textSize);
		
		startButton = (Button) findViewById(R.id.button_2);
		buttonParams = new RelativeLayout.LayoutParams(buttonWidth,buttonHeight);
		buttonParams.setMargins(startButtonLeftMargin, buttonTopMargin, 0, 0);
		startButton.setLayoutParams(buttonParams);	
		startButton.setTextSize(MenuActivity.textSize);
		
		scoresButton = (Button) findViewById(R.id.button_3);
		buttonParams = new RelativeLayout.LayoutParams(buttonWidth,buttonHeight);
		buttonParams.setMargins(scoresButtonLeftMargin, buttonTopMargin, 0, 0);
		scoresButton.setLayoutParams(buttonParams);	
		scoresButton.setTextSize(MenuActivity.textSize);
		
		// indicator of the step of choosing the level
		levelChoice = false;
			
		}
	
	public void onClick(View view){
		switch(view.getId()){
		case R.id.button_1 :
			if (levelChoice){
				level = 1;
				Intent intent = new Intent(MenuActivity.this,MainActivity.class);
				intent.putExtra(LEVEL, level);
				finish();
				startActivity(intent);
			}
			else{
				Intent intent = new Intent(MenuActivity.this,HelpActivity.class);
				finish();
				startActivity(intent);
			}
			break;
		case R.id.button_2 :
			if (levelChoice){
				level = 2;
				Intent intent = new Intent(MenuActivity.this,MainActivity.class);
				intent.putExtra(LEVEL, level);
				finish();
				startActivity(intent);
			}
			else{
				levelChoice = true;
				helpButton.setText(R.string.easy);
				startButton.setText(R.string.medium);
				scoresButton.setText(R.string.hard);
			}
			break;
		case R.id.button_3 :
			if (levelChoice){
				level = 3;
				Intent intent = new Intent(MenuActivity.this,MainActivity.class);
				intent.putExtra(LEVEL, level);
				finish();
				startActivity(intent);
			}
			else{
				Intent intent = new Intent(MenuActivity.this,ScoreActivity.class);
				finish();
				startActivity(intent);
			}
			break;
		}

	}
	
	public void onBackPressed(){
		if (levelChoice){
			helpButton.setText(R.string.help);
			startButton.setText(R.string.start);
			scoresButton.setText(R.string.highscores);
			levelChoice = false;
		}
		else	
			finish();
	}
	
	
	
	

	

}
