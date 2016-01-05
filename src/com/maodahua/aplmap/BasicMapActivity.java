//created by maodahua
//2015.12.10
//copyright maodahua

package com.maodahua.aplmap;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * this Activity is show a basic mapView
 * @author MDH
 *
 */
public class BasicMapActivity extends Activity {
	
	private MapView mapView;
	private AMap aMap;
	//use tow variables to receive values from first Activity
	private double la,lo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basicmap_activity);
		
		mapView = (MapView)findViewById(R.id.map);
		//you must override this method
		mapView.onCreate(savedInstanceState);
		init();
		
		//use Intent to transfer values
		Intent it = getIntent();
		la = it.getDoubleExtra("la", 30);
		lo = it.getDoubleExtra("lo", 30);
		//Toast.makeText(this, la+"  "+lo+"", Toast.LENGTH_LONG).show();
		
		//set camera on map by latlng and zoom(3-20)
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(la, lo),15);
		aMap.moveCamera(cu);
		//set a mark on map
		MarkerOptions mo = new MarkerOptions();
		aMap.addMarker(mo.position(new LatLng(la, lo)));
		
	}
	
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
