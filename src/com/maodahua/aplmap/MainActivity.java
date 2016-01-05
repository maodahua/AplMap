//created by maodahua
//2015.12.10
//copyright maodahua

package com.maodahua.aplmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	TextView t1, t2;
	Location mLocation;
	LocationManager mLocationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		t1 = (TextView)findViewById(R.id.textView1);
		t2 = (TextView)findViewById(R.id.textView2);
		mLocation = getLocation();
	}
	
	/**
	 * return a location which get values by this method.
	 * @return
	 */
	private Location getLocation()
	{
		mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		Criteria cr = new Criteria();
		//set max accuracy
		cr.setAccuracy(Criteria.ACCURACY_FINE);
		//set altitude
		cr.setAltitudeRequired(false);
		//set bearing
		cr.setBearingRequired(false);
		//set cost
		cr.setCostAllowed(true);
		//set power
		cr.setPowerRequirement(Criteria.POWER_MEDIUM);
		
		//find a best provider
		String provider = mLocationManager.getBestProvider(cr, true);
		//get the last location
		Location location = mLocationManager.getLastKnownLocation(provider);
		//2sec 100metres 
		mLocationManager.requestLocationUpdates(provider, 2000, 100, locationListener);
		return location;
	}
	
	private final LocationListener locationListener = new LocationListener()
	{
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			if(location != null)
			{
				t1.setText("纬度"+location.getLatitude());
				Log.d("location", location.getLatitude()+"");
				t2.setText("经度"+location.getLongitude());
			}
		}
	};
	
	
	public void btnClicked(View v)
	{
		Intent it = new Intent(MainActivity.this,BasicMapActivity.class);
		it.putExtra("la", mLocation.getLatitude());
		it.putExtra("lo", mLocation.getLongitude());
		startActivity(it);
	}
}
