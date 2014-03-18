package me.hoyah.tetris;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class TileMatrix {
	
	private static final String TAG = "TileMatrix";
	
	int[][] mTile = new int[4][4];
	Random mRandom;
	int mColor = 0;
	int mShape = 0;
	int mOffsetX = (TetrisParams.MAP_SQUARE_NUM_IN_X-4)/2; //+ 1;
	int mOffsetY = 0;
	
	private Context mContext;
	private TetrisResources mTRes;
	
	public TileMatrix(Context context) {
		super();
		this.mContext = context;
		
		mTRes = new TetrisResources(mContext);
		
		init();
		
	}

	//init tileMatrix
	private void init() {
		// TODO Auto-generated method stub
		mRandom = new Random();
		mColor = Math.abs(mRandom.nextInt()%7);
		mShape = Math.abs(mRandom.nextInt()%28);
		
		if(mTile == null){
			return;
		}
		
		int i, j;
		
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				mTile[i][j] = TileShape.shapes[mShape][i][j];
			}
		}
		
	}
	
	public int getColor() {
		return mColor;
	}

	public void setColor(int mColor) {
		this.mColor = mColor;
	}

	public int getOffsetX() {
		return mOffsetX;
	}

	public void setOffsetX(int mOffsetX) {
		this.mOffsetX = mOffsetX;
	}

	public int getOffsetY() {
		return mOffsetY;
	}

	public void setOffsetY(int mOffsetY) {
		this.mOffsetY = mOffsetY;
	}
	
	public int getShape() {
		return mShape;
	}

	public void setShape(int mShape) {
		this.mShape = mShape;
	}
	
	public int[][] getTile(){
		return mTile;
	}

	public boolean rotateTile(MapMatrix map){
		
		int tempX = 0;
		int tempY = 0;
		int tempShape;
		int[][] tempTile = new int[4][4];
		boolean isRotatable = false;
		
		tempX = mOffsetX;
		tempY = mOffsetY;
		tempShape = mShape;
		
		if(tempShape%4 > 0){
			tempShape--;
		}else{
			tempShape += 3;
		}
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				tempTile[i][j] = TileShape.shapes[tempShape][i][j];
			}
		}
		
		if(map.isOKForTile(tempTile, tempX, tempY)){
			isRotatable = true;
		}else if(map.isOKForTile(tempTile, tempX-1, tempY)){
			tempX--;
			isRotatable = true;
		}else if(map.isOKForTile(tempTile, tempX-2, tempY)){
			tempX -= 2;
			isRotatable = true;
		}else if(map.isOKForTile(tempTile, tempX+1, tempY)){
			tempX++;
			isRotatable = true;
		}else if(map.isOKForTile(tempTile, tempX+2, tempY)){
			tempX += 2;
			isRotatable = true;
		}else{
			
		}
		
		if(isRotatable){
			mShape = tempShape;
			mOffsetX = tempX;
			mOffsetY = tempY;
			for(int i=0; i<4; i++){
				for(int j=0; j<4; j++){
					mTile[i][j] = tempTile[i][j];
				}
			}
			return true;
		}
		
		return false;
	}
	
	public boolean moveLeft(MapMatrix map){
		int i, j;
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				if(mTile[i][j] != 0){
					if(!map.isSpace(mOffsetX + i -1, mOffsetY + j)){
						return false;
					}
				}
			}
		}
		--mOffsetX;
		return true;
	}
	
	public boolean moveRight(MapMatrix map){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(mTile[i][j] != 0){
					if(!map.isSpace(mOffsetX + i + 1, mOffsetY + j)){
						return false;
					}
				}
			}
		}
		++mOffsetX;
		return true;
	}
	
	public boolean moveDown(MapMatrix map){
		int i, j;
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				if(mTile[i][j] != 0){
					if(!map.isSpace(mOffsetX + i, mOffsetY + j + 1)
							|| isReachBaseLine(mOffsetY + j + 1)){
						return false;
					}
				}
			}
		}
		++mOffsetY;
		return true;
	}
	
	public boolean fastMoveDown(MapMatrix map){
		int i, j, k;
		int step = TetrisParams.MAP_SQUARE_NUM_IN_Y;
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				if(mTile[i][j] != 0){
					for(k = mOffsetY + j; k < TetrisParams.MAP_SQUARE_NUM_IN_Y; k++){
						if(!map.isSpace(mOffsetX + i, k + 1)
								|| isReachBaseLine(k + 1)){
							if(step > k - mOffsetY - j){
								step = k - mOffsetY -j;
							}
						}
					}
				}
			}
		}
		mOffsetY += step;
		if(step > 0){
			return true;
		}
		return false;
	}
	
	private boolean isReachBaseLine(int posY){
		if(posY>= TetrisParams.MAP_SQUARE_NUM_IN_Y){
			return true;
		}
		return false;
	}
	
	public void paintTile(Canvas canvas){
		TetrisResources res = new TetrisResources(mContext);
		Paint paint = new Paint();
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(mTile[i][j] != 0){
					canvas.drawBitmap(res.getSquare(mColor), 
							TetrisParams.GAME_AREA_X_OFFSET + (mOffsetX + i)*TetrisParams.SQUARE_WIDTH, 
							TetrisParams.GAME_AREA_Y_OFFSET + (mOffsetY + j)*TetrisParams.SQUARE_WIDTH, 
							paint);
				}
			}
		}
		
	}
	
	public void paintTilePreview(Canvas canvas){
		TetrisResources res = new TetrisResources(mContext);
		Paint paint = new Paint();
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(mTile[i][j] != 0){
					canvas.drawBitmap(res.getSquarePreview(mColor), 
							TetrisParams.GAME_AREA_WIDTH + (TetrisParams.SQUARE_WIDTH/2)*i + 5,
							TetrisParams.SQUARE_WIDTH*2 + (TetrisParams.SQUARE_WIDTH/2)*j,
							paint);
				}
			}
		}
	}
	
	
	
}
