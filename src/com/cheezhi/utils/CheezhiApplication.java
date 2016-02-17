package com.cheezhi.utils;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;

public class CheezhiApplication extends Application {
	public static final String SMSSDK_APPKEY = "dd94a032e778";
	public static final String SMSSDK_SECRET = "e8d8160423359c1491bbb4d5bc0f2b28";
	public static double distan;
    private int i = 0;
	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;
    public static List<LatLng> latlng_list ;
    public static double lat;
    public static double lon;
    public static double distances;
    public static float zooms;
    public static boolean myThread = true;
    
    public static float getZooms() {
		return zooms;
	}

	public static void setZooms(float zooms) {
		CheezhiApplication.zooms = zooms;
	}

	public static double getDistances() {
		return distances;
	}

	public static void setDistances(double distances) {
		CheezhiApplication.distances = distances;
	}

	public static double getLat() {
		return lat;
	}

	public static void setLat(double lat) {
		CheezhiApplication.lat = lat;
	}

	public static double getLon() {
		return lon;
	}

	public static void setLon(double lon) {
		CheezhiApplication.lon = lon;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		latlng_list = new ArrayList<LatLng>();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
	}
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// 地图中心为定位地点
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
//			Intent intent = new Intent();
//        	intent.setAction("gogogo");
//        	intent.putExtra("lat",latitude);
//        	intent.putExtra("lon", longitude);
//        	sendBroadcast(intent);
			Log.e("位置", latitude+"&&&"+longitude);
//			lats = intent.getDoubleExtra("lat", 0);
//			lons = intent.getDoubleExtra("lon", 0);
			LatLng latLng = new LatLng(latitude, longitude);
			if (i == 0) {
				setLat(latitude);
				setLon(longitude);
				i++;
			}
			
			if (latitude != getLat() || longitude != getLon()) {
				latlng_list.add(latLng);
				Log.e("size", latlng_list.size()+"<--size");
			}
			Log.e("info", "lat:"+latitude+"lon:"+longitude+"getLat:"+getLat()+"getLon:"+getLon()+"iiiii"+i);
			LatLng l1 = new LatLng(getLat(), getLon());
			LatLng l2 = new LatLng(latitude, longitude);
			double dis = DistanceUtil.getDistance(l1, l2);
			Log.e("dis", dis+"===");
			setDistances(distan += dis);
			setLat(latitude);
			setLon(longitude);
//			intent.putExtra("lat", value)
//			Toast.makeText(context, lats + "--" + lons+"---"+LocationApplication.latlng_list.size(), 0).show();
		}
	}
}
