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
	private LocationMode tempMode = LocationMode.Hight_Accuracy;//�߾���
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
        option.setLocationMode(tempMode);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType(tempcoor);//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ��
        int span=7000;
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
        option.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
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
