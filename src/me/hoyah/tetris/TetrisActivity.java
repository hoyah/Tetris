package me.hoyah.tetris;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class TetrisActivity extends Activity {
	
	private static final String TAG = "TetrisActivity";
	
	TetrisView mTetrisView;	//game area, left part of the screen

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        initTetrisParams();
        mTetrisView = new TetrisView(this);
        mTetrisView.setBackgroundColor(Color.WHITE);
        mTetrisView.setPadding(0, 0, 0, 0);
        setContentView(mTetrisView);
        
    }
    
/*    private void setViews(){
    	LinearLayout layout = new LinearLayout(this);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    			LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    	layout.setLayoutParams(params);
    	layout.setBackgroundColor(Color.WHITE);
    	layout.setPadding(0, 0, 0, 0);
    	
    	mTetrisView = new TetrisView(this);
    	mTetrisView.setLayoutParams(params);
    	mTetrisView.setBackgroundColor(Color.WHITE);
    	
    	layout.addView(mTetrisView);
    	
    	setContentView(layout);
    }*/
    
    public void initTetrisParams(){
    	
    	DisplayMetrics dm = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(dm);
    	TetrisParams.SCREEN_WIDTH = dm.widthPixels;
    	TetrisParams.SCREEN_HEIGHT = dm.heightPixels;
    	//Log.i(TAG, TetrisParams.SCREEN_WIDTH + " x " + TetrisParams.SCREEN_HEIGHT);
    	
/*    	View view = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
    	Rect rect = new Rect();
    	Rect rect2 = new Rect();
    	view.getWindowVisibleDisplayFrame(rect);*/
    	
    	TetrisParams.STATUS_BAR_HEIGHT = 0;
//    	Log.i(TAG, "--------------------------" + TetrisParams.STATUS_BAR_HEIGHT);
    	
    	TetrisParams.GAME_AREA_WIDTH = (TetrisParams.SCREEN_WIDTH/4)*3;
    	
    	TetrisParams.STATE_AREA_WIDTH = (TetrisParams.SCREEN_WIDTH/4)*1;
    	TetrisParams.STATE_AREA_HEIGHT = TetrisParams.SCREEN_HEIGHT - TetrisParams.STATUS_BAR_HEIGHT;	//without titleBar and statusBar
    	
    	TetrisParams.MAP_SQUARE_NUM_IN_X = 8; //square num in x axis
    	
    	TetrisParams.SQUARE_WIDTH = TetrisParams.GAME_AREA_WIDTH/TetrisParams.MAP_SQUARE_NUM_IN_X;
    	//TetrisParams.SQUARE_HEIGHT = TetrisParams.SQUARE_WIDTH;
    	
    	TetrisParams.TILE_WIDTH = TetrisParams.SQUARE_WIDTH*4;
    	TetrisParams.TILE_HEIGHT = TetrisParams.TILE_WIDTH;
    	
    	//TetrisParams.GAME_AREA_VISIBLE_HEIGHT = TetrisParams.SCREEN_HEIGHT;
    	TetrisParams.GAME_AREA_HEIGHT = TetrisParams.SCREEN_HEIGHT - TetrisParams.STATUS_BAR_HEIGHT;	//without titleBar, statusBar, and 4 invisible squares
    	
    	TetrisParams.GAME_AREA_X_OFFSET = (TetrisParams.GAME_AREA_WIDTH - TetrisParams.SQUARE_WIDTH*TetrisParams.MAP_SQUARE_NUM_IN_X)/2;
    	TetrisParams.GAME_AREA_Y_OFFSET = TetrisParams.STATUS_BAR_HEIGHT + TetrisParams.GAME_AREA_HEIGHT%TetrisParams.SQUARE_WIDTH - TetrisParams.SQUARE_WIDTH*4;
    	//TetrisParams.GAME_AREA_Y_OFFSET = (TetrisParams.SCREEN_HEIGHT - TetrisParams.GAME_AREA_HEIGHT)/2;
    	
    	TetrisParams.STATE_AREA_X_OFFSET = TetrisParams.GAME_AREA_WIDTH;
    	TetrisParams.STATE_AREA_Y_OFFSET = TetrisParams.STATUS_BAR_HEIGHT;
    	//TetrisParams.GAME_AREA_Y_OFFSET = (TetrisParams.SCREEN_HEIGHT - TetrisParams.GAME_AREA_HEIGHT)/2;
    	
    	TetrisParams.MAP_SQUARE_NUM_IN_Y = TetrisParams.GAME_AREA_HEIGHT/TetrisParams.SQUARE_WIDTH + 4;	//including 4 invisible line
    	
    }
    
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mTetrisView.onPause();
		
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mTetrisView.onResume();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mTetrisView.saveGame();
	}
    
}