package me.hoyah.tetris;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TetrisView extends View implements Runnable, OnTouchListener, OnGestureListener {
	
	private static final String TAG = "TetrisView";
	
	private final String STATE_FILE = "tetris.dt";
	
	private static final int FLING_MIN_DISTANCE = 80;
	private static final int FLING_MIN_VELOCITY = 100;
	GestureDetector mGestureDetector;
	
	final int STATE_MENU = 0;
	final int STATE_PLAY = 1;
	final int STATE_PAUSE = 2;
	final int STATE_OVER = 3;
	
	int mGameSate = STATE_PLAY;
	protected RefreshHandler mRefreshHandler;
	
	int score = 0;
	int speed = 1;
	int deletedLineCount = 0;
	
	boolean isCombo = false;
	boolean isPaused = false;
	
	long mMoveDelay = 600;
	long mLastMove = 0;
	
	private Context context;
	private Paint paint = new Paint();
	
	TileMatrix currentTile;
	TileMatrix nextTile;
	MapMatrix map;
	TetrisResources res;
	Resources resources;
	
	public TetrisView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}
	
	protected void init() {
		// TODO Auto-generated method stub
		currentTile = new TileMatrix(context);
		nextTile = new TileMatrix(context);
		map = new MapMatrix(context);
		mRefreshHandler = new RefreshHandler(this);
		res = new TetrisResources(context);
		resources = getResources();
		
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		
		setFocusable(true);
		setListeners();
		
		new Thread(this).start();
		
	}
	
	
	
	void setListeners() {
		// TODO Auto-generated method stub
		mGestureDetector = new GestureDetector(this);
		this.setOnTouchListener(this);
		this.setLongClickable(true);
	}

	//state tell, logic()
	public void stateSwitch(){
		switch(mGameSate){
		case STATE_MENU:
			mGameSate = STATE_PLAY;
			break;
		case STATE_PLAY:
			playGame();
			break;
		case STATE_PAUSE:
			break;
		case STATE_OVER:
			break;
		default:
			break;
		}
	}
	
	public void startGame(){
		mGameSate = STATE_PLAY;
		map.clearMap();
		currentTile = new TileMatrix(context);
		nextTile = new TileMatrix(context);
		
		score = 0;
		deletedLineCount = 0;
		
		isPaused = false;
		isCombo = false;
		
		playGame();
	}
	
	public void playGame(){
		long now = System.currentTimeMillis();
		if(now - mLastMove > mMoveDelay){
			if(isPaused){
				return;
			}
			if(isCombo){
				map.placeTile(currentTile);
				if(map.isGameOver()){
					mGameSate = STATE_OVER;
					return;
				}
				int line = map.removeLInes();
				if(line > 0){
					//play music
				}
				deletedLineCount += line;
				scoreCount(line);
				
				currentTile = nextTile;
				nextTile = new TileMatrix(context);
				
				isCombo = false;
			}
			moveDown();
			mLastMove = now;
		}
	}
	
	private void scoreCount(int line){
		switch(line){
		case 1: score += 100;break;
		case 2: score += 300;break;
		case 3: score += 600;break;
		case 4: score += 1000;break;
		default: break;
		
		//setLevel code ,to be added
		}
	}
	
	//onDraw()
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		switch(mGameSate){
		case STATE_MENU:
			paintMenu(canvas);
			break;
		case STATE_PLAY:
			paintGame(canvas);
			break;
		case STATE_PAUSE:
			paintPause(canvas);
			break;
		case STATE_OVER:
			paintOver(canvas);
			break;
		default:
			break;
		}
	}





	public boolean isGameOver(){
		return map.isGameOver();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_UP:
			upEvent();
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			downEvent();
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			leftEvent();
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			rightEvent();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void upEvent() {
		// TODO Auto-generated method stub
		if(mGameSate == STATE_PLAY){
			if(!isPaused){
				rotate();
				//wait to add code for play music tone
			}
		}else if(mGameSate == STATE_PAUSE){
			
		}else if(mGameSate == STATE_MENU){
			
		}
	}

	private void downEvent() {
		// TODO Auto-generated method stub
		if(mGameSate == STATE_PLAY){
			if(!isPaused){
				fastMoveDown();
				//wait to add code for play music tone
			}
		}else if(mGameSate == STATE_PAUSE){
			
		}else if(mGameSate == STATE_MENU){
			
		}
	}

	private void leftEvent() {
		// TODO Auto-generated method stub
		if(mGameSate == STATE_PLAY){
			if(!isPaused){
				moveLeft();
				//wait to add code for play music tone
			}
		}else if(mGameSate == STATE_PAUSE){
			
		}else if(mGameSate == STATE_MENU){
			
		}
	}

	private void rightEvent() {
		// TODO Auto-generated method stub
		if(mGameSate == STATE_PLAY){
			if(!isPaused){
				moveRight();
				//wait to add code for play music tone
			}
		}else if(mGameSate == STATE_PAUSE){
			
		}else if(mGameSate == STATE_MENU){
			
		}
	}

	protected void rotate() {
		// TODO Auto-generated method stub
		if(!isCombo){
			currentTile.rotateTile(map);
		}
	}

	protected void moveLeft() {
		// TODO Auto-generated method stub
		if(!isCombo){
			currentTile.moveLeft(map);
		}
	}

	protected void moveRight() {
		// TODO Auto-generated method stub
		if(!isCombo){
			currentTile.moveRight(map);
		}
	}
	
	protected void moveDown() {
		// TODO Auto-generated method stub
		if(!isCombo){
			if(!currentTile.moveDown(map)){
				isCombo = true;
			}
		}
	}

	protected void fastMoveDown() {
		// TODO Auto-generated method stub
		if(!isCombo){
			currentTile.fastMoveDown(map);
			isCombo = true;
		}
	}
	
	private void paintMenu(Canvas canvas){
		//to be added
	}
	
	private void paintGame(Canvas canvas){
		map.paintMap(canvas);
		currentTile.paintTile(canvas);
		
		paint.setTextSize(18);
//		paintNextTile(canvas);
		nextTile.paintTilePreview(canvas);
		paintSpeed(canvas);
		paintScore(canvas);
		paintDelLineNum(canvas);
	}
	
	private void paintSpeed(Canvas canvas){
		//to be added
	}
	
	private void paintScore(Canvas canvas){
		paint.setColor(Color.BLUE);
		canvas.drawText(resources.getString(R.string.game_score), 
				getSquareDistance(TetrisParams.MAP_SQUARE_NUM_IN_X) + getRightMarginToMap(),
				getSquareDistance(6),
				paint);
		paint.setColor(Color.RED);
		canvas.drawText(String.valueOf(score), 
				getSquareDistance(TetrisParams.MAP_SQUARE_NUM_IN_X) + 2*getRightMarginToMap(), 
				getSquareDistance(7), 
				paint);
	}
	
	private void paintDelLineNum(Canvas canvas){
		paint.setColor(Color.BLUE);
		canvas.drawText(resources.getString(R.string.game_line_del), 
				getSquareDistance(TetrisParams.MAP_SQUARE_NUM_IN_X) + getRightMarginToMap(),
				getSquareDistance(9),
				paint);
		paint.setColor(Color.RED);
		canvas.drawText(String.valueOf(deletedLineCount), 
				getSquareDistance(TetrisParams.MAP_SQUARE_NUM_IN_X) + 2*getRightMarginToMap(), 
				getSquareDistance(10), 
				paint);
	}
	
	private int getSquareDistance(int squareNum){
		return TetrisParams.SQUARE_WIDTH * squareNum;
	}
	
	private int getRightMarginToMap(){
		return 10;
	}
	
	private void paintPause(Canvas canvas){
		
	}
	
	private void paintOver(Canvas canvas){
		paintGame(canvas);
		Paint paint = new Paint();
		paint.setTextSize(36);
		paint.setAntiAlias(true);
		paint.setARGB(0xe0, 0xff, 0x00, 0x00);
		canvas.drawText("Game Over", 
				getSquareDistance(1),
				TetrisParams.SCREEN_HEIGHT/3,
				paint);
	}

	public void run() {
		// TODO Auto-generated method stub
		while(!Thread.currentThread().isInterrupted()){
			Message ms = new Message();
			ms.what = RefreshHandler.MESSAGE_REFRESH;
			this.mRefreshHandler.sendMessage(ms);
			try{
				Thread.sleep(mMoveDelay);
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public void restoreGame(){
		Properties pro = new Properties();
		try{
			FileInputStream fis = context.openFileInput(STATE_FILE);
			pro.load(fis);
			fis.close();
		}catch(IOException e){
			return;
		}
		
		mGameSate = Integer.valueOf(pro.get("mGameState").toString());
		speed = Integer.valueOf(pro.get("speed").toString());
		score = Integer.valueOf(pro.get("deletedLineCount").toString());
		isCombo = Boolean.valueOf(pro.get("isCombo").toString());
		isPaused = Boolean.valueOf(pro.get("isPaused").toString());
		
		restoreMap(pro);
		restoreTile(pro, currentTile);
		restoreTile(pro, nextTile);
	}
	
	private void restoreMap(Properties pro){
		int[][] mMap = map.getMap();
		int i, j;
		for(i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
			for(j=0; j<TetrisParams.MAP_SQUARE_NUM_IN_Y; j++){
				mMap[i][j] = Integer.valueOf(pro.get("map"+i+j).toString());
			}
		}
	}
	
	private void restoreTile(Properties pro, TileMatrix tile){
		int[][] mTile = tile.getTile();
		int i, j;
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				mTile[i][j] = Integer.valueOf(pro.get("tile"+i+j).toString());
			}
		}
		tile.setColor(Integer.valueOf(pro.get("tileColor").toString()));
		tile.setShape(Integer.valueOf(pro.get("tileShape").toString()));
		tile.setOffsetX(Integer.valueOf(pro.get("tileOffsetX").toString()));
		tile.setOffsetY(Integer.valueOf(pro.get("tileOffsetY").toString()));
	}
	
	public void saveGame(){
		Properties pro = new Properties();
		
		pro.put("mGameSate", String.valueOf(mGameSate));
		pro.put("speed", String.valueOf(speed));
		pro.put("score", String.valueOf(score));
		pro.put("deletedLineCount", String.valueOf(deletedLineCount));
		pro.put("isCombo", new Boolean(isCombo).toString());
		pro.put("isPaused", new Boolean(isPaused).toString());
		
		saveMap(pro);
		saveTile(pro, currentTile);
		saveTile(pro, nextTile);
		
		try{
			FileOutputStream fos = context.openFileOutput(STATE_FILE, Context.MODE_WORLD_WRITEABLE);
			pro.store(fos, "");
			fos.close();
		}catch(IOException e){
			return;
		}
	}
	
	private void saveMap(Properties pro){
		int[][] mMap = map.getMap();
		int i, j;
		for(i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
			for(j=0; j<TetrisParams.MAP_SQUARE_NUM_IN_Y; j++){
				pro.put("map" + i + j, String.valueOf(mMap[i][j]));
			}
		}
	}
	
	private void saveTile(Properties pro, TileMatrix tile){
		int[][] mTile = tile.getTile();
		int i, j;
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				pro.put("tile" + i + j, String.valueOf(mTile[i][j]));
			}
		}
		pro.put("tileColor", String.valueOf(tile.getColor()));
		pro.put("tileShape", String.valueOf(tile.getShape()));
		pro.put("tileOffsetX", String.valueOf(tile.getOffsetX()));
		pro.put("tileOffsetY", String.valueOf(tile.getOffsetY()));
	}
	

	
	public void onPause(){
		mRefreshHandler.pause();
		isPaused = true;
	}
	
	public void onResume(){
		mRefreshHandler.resume();
		isPaused = false;
	}
	
	public void freeResources(){
		
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
//		Message msg = new Message();
//		msg.what = RefreshHandler.MESSAGE_REFRESH;
		if(e1.getY()-e2.getY()>FLING_MIN_DISTANCE
				&& Math.abs(velocityY)>FLING_MIN_VELOCITY){
//			Log.i(TAG, "-------------------------up event");
			upEvent();
//			mRefreshHandler.sendMessage(msg);
		}else if(e1.getX()-e2.getX()>FLING_MIN_DISTANCE
				&& Math.abs(velocityX)>FLING_MIN_VELOCITY){
//			Log.i(TAG, "-------------------------left event");
			leftEvent();
//			mRefreshHandler.sendMessage(msg);
		}else if(e2.getX()-e1.getX()>FLING_MIN_DISTANCE
				&& Math.abs(velocityX)>FLING_MIN_VELOCITY){
//			Log.i(TAG, "-------------------------right event");
			rightEvent();
//			mRefreshHandler.sendMessage(msg);
		}else if(e2.getY()-e1.getY()>FLING_MIN_DISTANCE
				&& Math.abs(velocityX)>FLING_MIN_VELOCITY){
//			Log.i(TAG, "-------------------------down event");
			downEvent();
//			mRefreshHandler.sendMessage(msg);
		}else{
			
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

}

class RefreshHandler extends Handler{
	final static int MESSAGE_REFRESH = 0xeeeeeeee;
	
	final static int DELAY_MILLIS = 100;
	TetrisView tv;
	boolean isPaused = false;
	
	public RefreshHandler(TetrisView tv){
		super();
		this.tv = tv;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		if(!isPaused){
			if(msg.what == MESSAGE_REFRESH){
				tv.stateSwitch();
				tv.invalidate();
			}
		}
	}
	
	public void pause(){
		isPaused = true;
	}
	
	public void resume(){
		isPaused = false;
	}
	
}
