package me.hoyah.tetris;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class TetrisResources {
	
	private Context context;
	private Resources res;
	
	private static Bitmap mTetrisBackground;
	private static Bitmap[] mSquares;
	private static Bitmap[] mSquaresPreview;
	
	public TetrisResources(Context context) {
		super();
		this.context = context;
		res = context.getResources();
		mSquares = new Bitmap[7];
		mSquaresPreview = new Bitmap[7];
		
		mTetrisBackground = createImage(res.getDrawable(R.drawable.tetris_bg2), TetrisParams.GAME_AREA_WIDTH, TetrisParams.SCREEN_HEIGHT);
		mSquares[0] = createImage(res.getDrawable(R.drawable.blue), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		mSquares[1] = createImage(res.getDrawable(R.drawable.green), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		mSquares[2] = createImage(res.getDrawable(R.drawable.orange), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		mSquares[3] = createImage(res.getDrawable(R.drawable.purple), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		mSquares[4] = createImage(res.getDrawable(R.drawable.qing), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		mSquares[5] = createImage(res.getDrawable(R.drawable.red), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		mSquares[6] = createImage(res.getDrawable(R.drawable.yellow), TetrisParams.SQUARE_WIDTH, TetrisParams.SQUARE_WIDTH);
		
		mSquaresPreview[0] = createImage(res.getDrawable(R.drawable.blue), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		mSquaresPreview[1] = createImage(res.getDrawable(R.drawable.green), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		mSquaresPreview[2] = createImage(res.getDrawable(R.drawable.orange), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		mSquaresPreview[3] = createImage(res.getDrawable(R.drawable.purple), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		mSquaresPreview[4] = createImage(res.getDrawable(R.drawable.qing), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		mSquaresPreview[5] = createImage(res.getDrawable(R.drawable.red), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		mSquaresPreview[6] = createImage(res.getDrawable(R.drawable.yellow), TetrisParams.SQUARE_WIDTH/2, TetrisParams.SQUARE_WIDTH/2);
		
	}
	
	public static Bitmap createImage(Drawable drawable, int width, int height) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}
	
	public Bitmap getTetrisBackground(){
		
		return mTetrisBackground;
		
	}
	
	public Bitmap getSquare(int index){
		
		return mSquares[index];
		
	}
	
public Bitmap getSquarePreview(int index){
		
		return mSquaresPreview[index];
		
	}
	
}
