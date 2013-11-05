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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class HelpActivity extends Activity {
	
	final float rulesWidthReference = 1000 ,rulesHeightReference = 500 ;
	final float rulesLeftMarginReference  = 250, rulesTopMarginReference  = 180;
	final float screenWidthReference = 1280, screenHeightReference = 752;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //set activity layout
		requestWindowFeature(Window.FEATURE_NO_TITLE); // no title bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen mode
		setContentView(R.layout.activity_help);
		
		// get screen size
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels; 
		int screenHeight = displayMetrics.heightPixels;
		float screenWidthRatio = screenWidth/screenWidthReference;
		float screenHeightRatio = screenHeight/screenHeightReference;

		// calculate dimensions and margins of graphical objects
		int rulesWidth = (int) (rulesWidthReference*screenWidthRatio);
		int rulesHeight = (int) (rulesHeightReference*screenHeightRatio);
		int rulesLeftMargin= (int) (rulesLeftMarginReference*screenWidthRatio);
		int rulesTopMargin = (int) (rulesTopMarginReference*screenHeightRatio);
		
		// resize graphical objects
		LinearLayout rulesView = (LinearLayout) findViewById(R.id.rules_layout);
		
		RelativeLayout.LayoutParams rulesParams = new RelativeLayout.LayoutParams(rulesWidth,rulesHeight);
		rulesParams.setMargins(rulesLeftMargin, rulesTopMargin, 0, 0);
		rulesView.setLayoutParams(rulesParams);
		
		TextView object = (TextView) findViewById(R.id.object);
		object.setTextSize(MenuActivity.textSize);
		TextView rules = (TextView) findViewById(R.id.rules);
		rules.setTextSize(MenuActivity.textSize);
		TextView howtoplay = (TextView) findViewById(R.id.howtoplay);
		howtoplay.setTextSize(MenuActivity.textSize);
	
	}
	
	public void onBackPressed(){
		Intent backIntent = new Intent(HelpActivity.this,MenuActivity.class);
		finish();
		startActivity(backIntent);
	}

}
