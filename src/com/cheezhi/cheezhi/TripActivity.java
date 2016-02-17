package com.cheezhi.cheezhi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.cheezhi.histogran.BarChartView;
import com.cheezhi.utils.AnimationUtil;
import com.cheezhi.utils.CheezhiApplication;

public class TripActivity extends Activity {
	RelativeLayout history;
	LinearLayout rangeMap, bigLin;
	ImageView upDown;
	Animation mShowAction, mHiddenAction;
	boolean flags = true;
	public static String LABLE_TEXT[] = { "", "七月", "八月", "九月", "十月", "十一月",
			"十二月", "一月", "二月", "三月", "四月", "五月", "六月" };
	private LinearLayout layoutViewContent;
	// 设置单柱的数值
	private double first[] = new double[] { 30, 40, 60, 80, 30, 40, 60, 80, 30,
			40, 60, 80 };
	private double second[] = new double[] { 50, 60, 88, 91, 50, 60, 88, 91,
			50, 60, 88, 91 };
	private List<String> options = new ArrayList<String>();
	private boolean isSingleView;
	private BarChartView view;
	/*
	 * 
	 * map变量
	 */
	public TextView distance_text,now_range;
	//定义百度地图组件和变量
	MapView mMapView = null;
	BaiduMap mBaiduMap;
	LatLng p1, p2;
	List<LatLng> points, poins2;
	Button btn1, btn2;
	Marker marker;
	boolean flag = true;
	private double lating, longing;

	public double getLating() {
		return lating;
	}

	public void setLating(double lating) {
		this.lating = lating;
	}

	public double getLonging() {
		return longing;
	}

	public void setLonging(double longing) {
		this.longing = longing;
	}

	int i = 0;
	Thread thread;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				distance_text.setText(CheezhiApplication.getDistances() + "");
				now_range.setText((int)CheezhiApplication.getDistances() + "\n"+"m");
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trip);
		initView();
		initViewMap();
		CheezhiApplication.setZooms(20);//初始化地图的缩放级别为20
		showHistogram();//显示柱状图
	}

	private void showHistogram() {
		isSingleView = true;
		for (String tem : LABLE_TEXT) {
			options.add(tem);
		}
		layoutViewContent = (LinearLayout) findViewById(R.id.barview_content);
		view = new BarChartView(TripActivity.this, isSingleView);
		view.initData(first, second, options, "里程数");
		layoutViewContent.setBackgroundColor(0xffffffff);
		layoutViewContent.addView(view.getBarChartView());
	}

	private void initView() {
		points = new ArrayList<LatLng>();
		now_range = (TextView) findViewById(R.id.now_range);
		bigLin = (LinearLayout) findViewById(R.id.big_lin1);
		history = (RelativeLayout) findViewById(R.id.history_range);
		rangeMap = (LinearLayout) findViewById(R.id.barview_content);
		upDown = (ImageView) findViewById(R.id.show_zhu_map);
		history.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (flags) {
					upDown.setImageResource(R.drawable.up_zhu);
					rangeMap.setVisibility(View.VISIBLE);
					rangeMap.setAnimation(AnimationUtil.moveToViewLocation());
				} else {
					upDown.setImageResource(R.drawable.down_zhu);
					rangeMap.setVisibility(View.GONE);
					rangeMap.setAnimation(AnimationUtil.moveToViewBottom());
				}
				flags = !flags;
			}
		});
	}

	private void initThread() {
		// TODO Auto-generated method stub
		thread = new Thread(new Runnable() {

			public void run() {
				while (CheezhiApplication.myThread) {
					initCurrentMap();
				}
				Log.e("TAG", "线程中断");
			}
		});
		thread.start();
	}

	private void initCurrentMap() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
		double latitude = CheezhiApplication.getLat();
		double longitude = CheezhiApplication.getLon();
		Log.e("wwwwwwwwww", latitude + "&&" + longitude);
		tipPlace(latitude, longitude);
		if (i == 0) {
			setLating(latitude);
			setLonging(longitude);
			i++;
		}

		if (latitude != getLating() || longitude != getLonging()) {
//			Toast.makeText(TripActivity.this, getLating()+"==="+getLonging(), Toast.LENGTH_LONG).show();
			drawLines(getLating(), latitude, getLonging(), longitude);
			setLating(latitude);
			setLonging(longitude);
		}
	}

	private void drawAreadyLin(List<LatLng> agoList) {
		BitmapDescriptor custom1 = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_road_red_arrow);
		OverlayOptions ooPolyline = new PolylineOptions().width(15)
				.color(0xAAFF0000).points(agoList).customTexture(custom1);
		// .textureIndex(index);
		// 添加到地图
		mBaiduMap.addOverlay(ooPolyline);
	}

	private void drawLines(double oldLat, double newLat, double oldLng,
			double newLng) {
		// TODO Auto-generated method stub
		// 绘制纹理图
		BitmapDescriptor custom1 = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_road_red_arrow);
		Log.e("old", oldLat + "<==" + oldLng + "--" + newLat + "<==" + newLng);
		LatLng pt1 = new LatLng(oldLat, oldLng);
		LatLng pt2 = new LatLng(newLat, newLng);
		points.add(pt1);// 点元素
		points.add(pt2);// 点元素
		OverlayOptions ooPolyline = new PolylineOptions().width(15)
				.color(0xAAFF0000).points(points).customTexture(custom1);
		mBaiduMap.addOverlay(ooPolyline);
	}

	private void initViewMap() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		removeZoom();
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapStatusChange(MapStatus arg0) {
				// TODO Auto-generated method stub
				float zoom1 = arg0.zoom;
				CheezhiApplication.setZooms(zoom1);
				Log.e("zoom", zoom1 + "<---");
			}
		});
		distance_text = (TextView) findViewById(R.id.distance_text);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMapView.getMap().snapshot(new SnapshotReadyCallback() {

					@Override
					public void onSnapshotReady(Bitmap bitmap) {
						// TODO Auto-generated method stub
						File file = new File("/mnt/sdcard/test.png");
						FileOutputStream out;
						try {
							out = new FileOutputStream(file);
							if (bitmap.compress(Bitmap.CompressFormat.PNG, 100,
									out)) {
								out.flush();
								out.close();
							}
							Toast.makeText(TripActivity.this,
									"屏幕截图成功，图片存在: " + file.toString(),
									Toast.LENGTH_SHORT).show();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!flag) {
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				} else {
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				}
				flag = !flag;
			}
		});
	}

	private void tipPlace(double lats, double longs) {
		if (i != 0) {
			// Log.e("iii", "i = "+i);
			marker.remove();
		}

		LatLng point = new LatLng(lats, longs);
		MapStatus mMapStatus = new MapStatus.Builder().target(point)
				.zoom(CheezhiApplication.getZooms()).build();

		// mMapView.getMap().get

		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
		mBaiduMap.animateMapStatus(u);

		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_geo);

		OverlayOptions options = new MarkerOptions().position(point)
				.icon(bitmap).zIndex(9).draggable(true);

		marker = (Marker) (mBaiduMap.addOverlay(options));
	}

	@Override
	protected void onResume() {
		super.onResume();
		i = 0;
		poins2 = CheezhiApplication.latlng_list;
		if (poins2.size() > 1) {
			drawAreadyLin(poins2);
		}
		CheezhiApplication.myThread = true;
		initThread();
	}
	private void removeZoom(){
		 int childCount = mMapView.getChildCount();
         View zoom = null;
         for (int i = 0; i < childCount; i++) {
                 View child = mMapView.getChildAt(i);
                 if (child instanceof ZoomControls) {
                         zoom = child;
                         break;
                 }
                 if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){            
                     child.setVisibility(View.INVISIBLE);           
                 }
         }
         zoom.setVisibility(View.GONE);
	}
	@Override
	protected void onPause() {
		super.onPause();
		marker.remove();
		CheezhiApplication.myThread = false;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// mMapView.onDestroy();
	}
}
