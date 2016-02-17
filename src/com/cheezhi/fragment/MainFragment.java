package com.cheezhi.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheezhi.cheezhi.FixingActivity;
import com.cheezhi.cheezhi.R;
import com.cheezhi.utils.Methods;

public class MainFragment extends Fragment implements OnClickListener {
	View view;
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
				if (index !=0) {
					link.setText(index+"s后跳转");
				}else {
					Methods.showToast(getActivity(), "jump");
					link.setVisibility(View.GONE);
					Intent intent = new Intent(getActivity(),FixingActivity.class);
					startActivity(intent);
				}
				
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.main_fragment1, null);
		initView();
		initData();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		linking = (LinearLayout) view.findViewById(R.id.link_loading);
		linked = (LinearLayout) view.findViewById(R.id.linked);
		link = (TextView) view.findViewById(R.id.linking);
		cancelLink = (TextView) view.findViewById(R.id.cancel_link);
		cutLink = (TextView) view.findViewById(R.id.cut_link);
		join = (TextView) view.findViewById(R.id.joinin);
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
			Intent intent = new Intent(getActivity(),FixingActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
