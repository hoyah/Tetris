package me.hoyah.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MapMatrix {
	
	private int[][] map = new int[TetrisParams.MAP_SQUARE_NUM_IN_X][TetrisParams.MAP_SQUARE_NUM_IN_Y];
	private Context mContext;
	private TetrisResources mTRes;
	
	public MapMatrix(Context context) {
		super();
		this.mContext = context;
		mTRes = new TetrisResources(mContext);
		
		clearMap();
	}
	
	//clear all squares on map
	public void clearMap(){
		int i, j;
		for(i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
			for(j=0; j<TetrisParams.MAP_SQUARE_NUM_IN_Y; j++){
				map[i][j] = 0;
			}
		}
	}
	
	//game over or not
	public boolean isGameOver(){
		for(int i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
			if(map[i][4] != 0){ //the 4th line(invisible) is blocked
				return true;
			}
		}
		return false;
	}
	
	//tell a square area is space or not
	public boolean isSpace(int posX, int posY){
		if(posX < 0 || posX >= TetrisParams.MAP_SQUARE_NUM_IN_X){
			return false;
		}
		if(posY < 0 || posY >= TetrisParams.MAP_SQUARE_NUM_IN_Y){
			return false;
		}
		if(map[posX][posY] == 0){
			return true;
		}
		return false;
	}
	
	//tell a tile is movable or not
	public boolean isOKForTile(int[][] tile, int posX, int posY){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(tile[i][j] != 0){
					if(!isSpace(posX + i, posY + j)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void placeTile(TileMatrix tile){
		int i, j;
		for(i=0; i<4; i++){
			for(j=0; j<4; j++){
				if(tile.mTile[i][j] != 0){
					map[tile.getOffsetX() + i][tile.getOffsetY() + j] = tile.getColor();
				}
			}
		}
	}
	
	public int removeLInes(){
		int high = 0;
		int low = 0;
		
		high = highestFullRowIndex();
		low = lowestFullRowIndex();
		int lineCount = low - high + 1;
		if(lineCount > 0){
			eliminateRows(high, lineCount);
			return lineCount;
		}
		return 0;
	}

	private void eliminateRows(int high, int lineCount) {
		// TODO Auto-generated method stub
		int i, j;
		for(j = high + lineCount - 1; j >= lineCount; j--){
			for(i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
				map[i][j] = map[i][j-lineCount];
			}
		}
	}

	private int lowestFullRowIndex() {
		// TODO Auto-generated method stub
		int result = TetrisParams.MAP_SQUARE_NUM_IN_Y - 1;
		boolean removeable = true;
		int i, j;
		for(j = TetrisParams.MAP_SQUARE_NUM_IN_Y - 1; j >= 0; j--){
			removeable = true;
			for(i=0; i < TetrisParams.MAP_SQUARE_NUM_IN_X && removeable; i++){
				if(isSpace(i,j)){
					result--;
					removeable = false;
				}
			}
			if(removeable){
				break;
			}
		}
		return result;
	}

	private int highestFullRowIndex() {
		// TODO Auto-generated method stub
		int result = 0;
		boolean removeable = true;
		int i, j;
		for(j=0; j<TetrisParams.MAP_SQUARE_NUM_IN_Y; j++){
			removeable = true;
			for(i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X && removeable; i++){
				if(isSpace(i, j)){
					result++;
					removeable = false;
				}
			}
			if(removeable){
				break;
			}
		}
		return result;
	}
	
	private void removeSingleLine(int lineIndex, int time){
		int i, j, t;
		for(t=0; t<time; t++){
			for(j=lineIndex; j>0; j--){
				for(i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
					map[i][j] = map[i][j-1];
				}
			}
		}
	}
	
	public void paintMap(Canvas canvas){
		Paint paint = new Paint();
		paint.setAlpha(0x60);
		canvas.drawBitmap(mTRes.getTetrisBackground(), 0, 0, paint);
		paint.setAlpha(0xee);
		
		/*for(int i=TetrisParams.MAP_SQUARE_NUM_IN_X-1; i>=0; i--){
			for(int j = TetrisParams.MAP_SQUARE_NUM_IN_Y-1; j>=0; j--){
				
			}
		}*/
		
		for(int i=0; i<TetrisParams.MAP_SQUARE_NUM_IN_X; i++){
			for(int j=0; j<TetrisParams.MAP_SQUARE_NUM_IN_Y; j++){
				if(map[i][j] != 0){
					canvas.drawBitmap(mTRes.getSquare(map[i][j]), 
							TetrisParams.GAME_AREA_X_OFFSET + TetrisParams.SQUARE_WIDTH*i, 
							TetrisParams.GAME_AREA_Y_OFFSET + TetrisParams.SQUARE_WIDTH*j, 
							paint);
				}
			}
		}
	}
	
	public int[][] getMap(){
		return map;
	}

}
