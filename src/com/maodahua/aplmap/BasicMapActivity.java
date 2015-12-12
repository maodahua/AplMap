package com.maodahua.aplmap;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BasicMapActivity extends Activity {
	
	private MapView mapView;
	private AMap aMap;
	private double la,lo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basicmap_activity);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
		Intent it = getIntent();
		la = it.getDoubleExtra("la", 30);
		lo = it.getDoubleExtra("lo", 30);
		//Toast.makeText(this, la+"  "+lo+"", Toast.LENGTH_LONG).show();
//		CameraPosition cp = new CameraPosition(new LatLng(lo, la),
//				17, 
//				0,
//				0);
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(la, lo),15);
		aMap.moveCamera(cu);
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
