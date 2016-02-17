package com.cheezhi.cheezhi;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.cheezhi.adapters.GridView1_Adapter;
import com.cheezhi.bean.MenuItem;
import com.cheezhi.fragment.ShowInfoFragment1;
import com.cheezhi.fragment.ShowInfoFragment2;
import com.cheezhi.utils.CheezhiApplication;

public class FixingActivity extends FragmentActivity implements OnClickListener {
	private ViewPager mPager;// 页卡内容
	private List<Fragment> listViews;// Tab页面列表
	LayoutInflater mInflater;
	GridView gridView;
	List<MenuItem> list;
	GridView1_Adapter adapter;
	ImageView user, back;
	private Intent intent;
	TextView detial_infos, tip1, tip2;

	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "bd09ll";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fixing);
		mInflater = LayoutInflater.from(getApplicationContext());
		mLocationClient = ((CheezhiApplication) getApplication()).mLocationClient;
		initViewPage();
		initData();
		initLocation();
	}

	private void initLocation() {
		mLocationClient.start();// 定位SDK
								// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		mLocationClient.requestLocation();
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType(tempcoor);// 可选，默认gcj02，设置返回的定位结果坐标系，
		int span = 5000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(false);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mLocationClient.setLocOption(option);
	}

	private void initViewPage() {
		// TODO Auto-generated method stub
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<Fragment>();
		ShowInfoFragment1 fragment1 = new ShowInfoFragment1();
		ShowInfoFragment2 fragment2 = new ShowInfoFragment2();
		listViews.add(fragment2);
		listViews.add(fragment1);
		mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {

			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return listViews.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listViews.size();
		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		list = new ArrayList<MenuItem>();
		MenuItem item = null;
		item = new MenuItem(R.drawable.aaaaa, "我的行程");
		list.add(item);
		item = new MenuItem(R.drawable.ddddd, "消息提醒");
		list.add(item);
		item = new MenuItem(R.drawable.ccccc, "硬件信息");
		list.add(item);
		item = new MenuItem(R.drawable.bbbbb, "常用设置");
		list.add(item);
		initView();
		adapter.notifyDataSetChanged();
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.fix_back);
		user = (ImageView) findViewById(R.id.fix_head);
		gridView = (GridView) findViewById(R.id.gridview1);
		tip1 = (TextView) findViewById(R.id.tip1);
		tip2 = (TextView) findViewById(R.id.tip2);
		adapter = new GridView1_Adapter(getApplicationContext(), list);
		gridView.setAdapter(adapter);
		back.setOnClickListener(this);
		user.setOnClickListener(this);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					intent = new Intent(FixingActivity.this, TripActivity.class);

				} else if (arg2 == 1) {
					intent = new Intent(FixingActivity.this,
							MessageActivity.class);
				} else if (arg2 == 2) {
					intent = new Intent(FixingActivity.this,
							HardwareInfoActivity.class);
				} else if (arg2 == 3) {
					intent = new Intent(FixingActivity.this, SetActivity.class);
				}
				startActivity(intent);
			}
		});
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				tip1.setBackgroundColor(getApplicationContext().getResources()
						.getColor(R.color.white));
				tip2.setBackgroundColor(getApplicationContext().getResources()
						.getColor(R.color.gray1_hint));
				break;
			case 1:
				tip1.setBackgroundColor(getApplicationContext().getResources()
						.getColor(R.color.gray1_hint));
				tip2.setBackgroundColor(getApplicationContext().getResources()
						.getColor(R.color.white));
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.fix_back:
			finish();
			break;
		case R.id.fix_head:
			intent = new Intent(FixingActivity.this, UserInfoActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		Toast.makeText(getApplicationContext(), "服务停止", Toast.LENGTH_LONG).show();
		super.onDestroy();
	}
}
