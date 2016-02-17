package com.cheezhi.cheezhi;

import com.cheezhi.utils.CheezhiApplication;
import com.cheezhi.utils.Methods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private int mBackKeyPressedTimes = 0;
	LinearLayout linking, linked;
	TextView link;
	TextView cancelLink, cutLink, join;
	int index = 0;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				linking.setVisibility(View.GONE);
				linked.setVisibility(View.VISIBLE);
				loado();
				break;
			case 2:
				index = (Integer) msg.obj;
				if (index != 0) {
					link.setText(index + "s后跳转");
				} else {
					Methods.showToast(MainActivity.this, "jump");
					link.setVisibility(View.GONE);
					Intent intent = new Intent(MainActivity.this,
							FixingActivity.class);
					startActivity(intent);
				}

				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		linking = (LinearLayout) findViewById(R.id.link_loading);
		linked = (LinearLayout) findViewById(R.id.linked);
		link = (TextView) findViewById(R.id.linking);
		cancelLink = (TextView) findViewById(R.id.cancel_link);
		cutLink = (TextView) findViewById(R.id.cut_link);
		join = (TextView) findViewById(R.id.joinin);
		join.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		;

	}

	private void loado() {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 5; i >= 0; i--) {
					try {
						Thread.sleep(1000);
						Message msg = new Message();
						msg.what = 2;
						msg.obj = i;
						handler.sendMessage(msg);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.joinin:
			Intent intent = new Intent(MainActivity.this, FixingActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (mBackKeyPressedTimes == 0) {
			Toast.makeText(this, "再按一次退出程序 ", Toast.LENGTH_SHORT).show();
			mBackKeyPressedTimes = 1;
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						mBackKeyPressedTimes = 0;
					}
				}
			}.start();
			return;
		} else {
			finish();
		}
		super.onBackPressed();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		CheezhiApplication.setDistances(0);
		CheezhiApplication.latlng_list.clear();
		CheezhiApplication.distan = 0;
		super.onDestroy();
	}
}
