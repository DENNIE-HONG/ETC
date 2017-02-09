package com.etc.demo;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.etc.demo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	private MapView ChargingMapView;
	private BaiduMap ChargingBaiduMap;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;//高精度
	private String tempcoor="bd09ll";
	private LocationClient mLocationClient;
	public static double latitude = -1;
	public static double longitude = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ChargingMapView = (MapView) findViewById(R.id.mapview);
		ChargingBaiduMap = ChargingMapView.getMap();
		initLocation();
        mLocationClient.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private  void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        int span=7000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }
	
	@Override
	public void update(double lat, double lon) {
		this.latitude = lat;
		this.longitude = lon;
		if(latitude!=-1){
	    	LatLng p = new LatLng(latitude,longitude);
			ChargingBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(p));
			ChargingBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(3));
			mLocationClient.stop();
		}
    	else{
    		Toast.makeText(getActivity(), "Can't get your location...", Toast.LENGTH_SHORT).show();
    	}
		
	}

}
