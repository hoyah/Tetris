package me.hoyah.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends Activity {
	
	ImageButton mPlay;
	ImageButton mQuit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.menu_activity_layout);
		
		mPlay = (ImageButton)findViewById(R.id.btn_play);
		mQuit = (ImageButton)findViewById(R.id.btn_quit);
		
		setListeners();
	}

	private void setListeners() {
		// TODO Auto-generated method stub
		mPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this, TetrisActivity.class);
				startActivity(intent);
			}
		});
		mQuit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
