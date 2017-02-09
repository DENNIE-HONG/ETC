package com.baidu.baidulocationdemo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.baidulocationdemo.IupdateLatLon;
import com.baidu.baidulocationdemo.LocationActivity;
import com.baidu.baidulocationdemo.LocationActivityTwo;
import com.baidu.baidulocationdemo.LocationApplication;
import com.baidu.baidulocationdemo.MyLocationMapActivity;
import com.baidu.baidulocationdemo.LocationApplication.MyLocationListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.etc.demo.R;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//OnGetGeoCoderResultListener

/**
 * The settings fragment 
 * @author Luyan Hong
 */
public class LocationActivity extends Activity implements IupdateLatLon{
//	private int count;
//	private String date;
//	private String start;
//	private String end;
	private TextView map_content;
	private List<LatLng> data = null;
	private double latitude,longitude;
	private int datanum;
	private int total_count;  
	private boolean flag;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	GeoCoder mSearch = null; // 鎼滅储妯″潡锛屼篃鍙幓鎺夊湴鍥炬ā鍧楃嫭绔嬩娇鐢�
	
	 private LocationMode tempMode = LocationMode.Hight_Accuracy;//楂樼簿搴�
	 private String tempcoor="bd09ll";
//	 private String tempcoor="gcj02";
	 private LocationClient mLocationClient;
	 private HeatMap heatmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		int[] count = i.getExtras().getIntArray("count");
		//String[] date = i.getStringArrayExtra("date");
		//start = i.getExtras().getString("start");
		//end = i.getExtras().getString("end");
		float[] lat = i.getExtras().getFloatArray("lat");
		float[] lon = i.getExtras().getFloatArray("lon");
		//System.out.println(lat[0]);
		
		//------------------------------
		setContentView(R.layout.activity_my_map);
		flag = true;
		
		//mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomBy(5));
		//mLocationClient= ((LocationApplication)getActivity().getApplication()).mLocationClient;
		
//		((LocationApplication)getActivity().getApplication()).latLon = this;
		 
		mMapView = (MapView) findViewById(R.id.mapview);
		mBaiduMap = mMapView.getMap();
		map_content = (TextView) findViewById(R.id.map_content);
	//	mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(5));
		mLocationClient= ((LocationApplication)getApplication()).mLocationClient;
//			mSearch = GeoCoder.newInstance();
//			mSearch.setOnGetGeoCodeResultListener(this);
		((LocationApplication)getApplication()).latLon = this;
		initLocation(); 
		mLocationClient.start();
		//initLocation();
		
		datanum = count.length;
		//List<LatLng> data = null;
		total_count = 0;
		List<LatLng> list = new ArrayList<LatLng>();
		//while(latitude!= 0){
		//	try {
				for(int j = 0;j < datanum;j++){
					for(int k = 0; k < count[j]; k++){
						list.add(new LatLng(lat[j],lon[j]));
					}
					total_count += count[j];
				}	
				data = list;
//				map_content = (TextView) findViewById(R.id.map_content);
				map_content.setText("Speaker_count < " + (total_count / 10 + 1) * 10 +" people");
				//map_content.setVisibility(View.INVISIBLE);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		//heatmap = new HeatMap.Builder().data(data).build();
		//}
		
			//	initLocation();
		
	}
	

//	private Activity getActivity() {
//		// TODO Auto-generated method stub
//		return null;
//	}


//	@Override
//	public void onGetGeoCodeResult(GeoCodeResult arg0) {
//		
//	}

//	@Override
//	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//			Toast.makeText(LocationActivity.this, "鎶辨瓑锛屾湭鑳芥壘鍒扮粨鏋�, Toast.LENGTH_LONG)
//					.show();
//			return;
//		}
//		mBaiduMap.clear();
//		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
//					.icon(BitmapDescriptorFactory
//							.fromResource(R.drawable.icon_gcoding)));
////		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
////				.icon(BitmapDescriptorFactory
////						.fromResource(R.drawable.icon_gcoding)));
////		OverlayOptions ooCircle = new CircleOptions().fillColor(0x6aff0000).visible(true)
////				.center(result.getLocation()).stroke(new Stroke(1, 0xAA000000))
////				.radius(1*count);
////		mBaiduMap.addOverlay(ooCircle);
//		// 娣诲姞鏂囧瓧
//		OverlayOptions ooText = new TextOptions().bgColor(0x00000000).fontSize(40).fontColor(0xFF000000).text("speak count:n").rotate(0)
//				.position(result.getLocation());
//		mBaiduMap.addOverlay(ooText);
//		
//		
////		Toast.makeText(MyLocationMapActivity.this, result.getAddress(),Toast.LENGTH_SHORT).show();
//		// 娣诲姞鍦�
//		
//		
//		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
//				.getLocation()));
//	}
	
	private  void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//鍙�锛岄粯璁ら珮绮惧害锛岃缃畾浣嶆ā寮忥紝楂樼簿搴︼紝浣庡姛鑰楋紝浠呰澶�
        option.setCoorType(tempcoor);//鍙�锛岄粯璁cj02锛岃缃繑鍥炵殑瀹氫綅缁撴灉鍧愭爣绯伙紝
        int span=3000;
        option.setScanSpan(span);//鍙�锛岄粯璁�锛屽嵆浠呭畾浣嶄竴娆★紝璁剧疆鍙戣捣瀹氫綅璇锋眰鐨勯棿闅旈渶瑕佸ぇ浜庣瓑浜�000ms鎵嶆槸鏈夋晥鐨�
        option.setIsNeedAddress(true);//鍙�锛岃缃槸鍚﹂渶瑕佸湴鍧�俊鎭紝榛樿涓嶉渶瑕�
        option.setOpenGps(true);//鍙�锛岄粯璁alse,璁剧疆鏄惁浣跨敤gps
        option.setLocationNotify(true);//鍙�锛岄粯璁alse锛岃缃槸鍚﹀綋gps鏈夋晥鏃舵寜鐓�S1娆￠鐜囪緭鍑篏PS缁撴灉
        option.setIgnoreKillProcess(true);//鍙�锛岄粯璁rue锛屽畾浣峉DK鍐呴儴鏄竴涓猄ERVICE锛屽苟鏀惧埌浜嗙嫭绔嬭繘绋嬶紝璁剧疆鏄惁鍦╯top鐨勬椂鍊欐潃姝昏繖涓繘绋嬶紝榛樿涓嶆潃姝�
        option.setEnableSimulateGps(false);//鍙�锛岄粯璁alse锛岃缃槸鍚﹂渶瑕佽繃婊ps浠跨湡缁撴灉锛岄粯璁ら渶瑕�
        option.setIsNeedLocationDescribe(true);//鍙�锛岄粯璁alse锛岃缃槸鍚﹂渶瑕佷綅缃涔夊寲缁撴灉锛屽彲浠ュ湪BDLocation.getLocationDescribe閲屽緱鍒帮紝缁撴灉绫讳技浜庘�鍦ㄥ寳浜ぉ瀹夐棬闄勮繎鈥�
        option.setIsNeedLocationPoiList(true);//鍙�锛岄粯璁alse锛岃缃槸鍚﹂渶瑕丳OI缁撴灉锛屽彲浠ュ湪BDLocation.getPoiList閲屽緱鍒�
        mLocationClient.setLocOption(option);
        
    }
	
	public void update(double lat, double lon) {
		System.out.println("---->>>>>>>>鎴戠殑缁忓害涓�+lat+">>>鎴戠殑绾害涓�+lon);
		this.latitude = lat;
		this.longitude = lon;
		
		 //璁剧疆涓績鐐�
	
			LatLng p = new LatLng(latitude,longitude);
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(p));
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
			mLocationClient.stop();
			heatmap = new HeatMap.Builder().data(data).build();
			mBaiduMap.addHeatMap(heatmap);
//			for(int i = 0; i < datanum; i++){
				mBaiduMap.addOverlay(new MarkerOptions().position(p)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.icon_gcoding)));
//				OverlayOptions ooText = new TextOptions().bgColor(0x00000000).fontSize(40).fontColor(0xFF000000).text("me").rotate(0)
//				.position(p);
//		mBaiduMap.addOverlay(ooText);
			//	mBaiduMap.addOverlay(ooCircle);
				
//			}
							
		
	}

}
