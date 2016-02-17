package com.cheezhi.cheezhi;

import com.cheezhi.views.RoundProgressBar;
import com.cheezhi.views.RoundProgressBar2;
import com.cheezhi.views.RoundProgressBar3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class FixdetialActivity extends Activity {
	ImageView back;
	private RoundProgressBar mRoundProgressBar1;
	private RoundProgressBar2 mRoundProgressBar2;
	private RoundProgressBar3 mRoundProgressBar3;
	int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fixdetial);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.afid_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar);
		mRoundProgressBar2 = (RoundProgressBar2) findViewById(R.id.roundProgressBar2);
		mRoundProgressBar3 = (RoundProgressBar3) findViewById(R.id.roundProgressBar3);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress <= 80) {
					progress += 3;
					System.out.println(progress);
					mRoundProgressBar1.setProgress(progress);
					mRoundProgressBar2.setProgress(progress);
					mRoundProgressBar3.setProgress(progress);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
