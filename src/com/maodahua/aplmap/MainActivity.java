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
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private static double PI = Math.PI;
	private static double OFFSET = 0.00669342162296594323;
	private static double AXIS = 6378245.0;
	double la,lo;
	double[] lalo = new double[2];
	
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
				la = location.getLatitude();
				lo = location.getLongitude();
				lalo = wgs2GCJ(la, lo);
				//Toast.makeText(getBaseContext(), lalo[0]+""+"  "+lalo[1]+"", Toast.LENGTH_LONG).show();
			}
		}
	};
	
	
	public void btnClicked(View v)
	{
		Intent it = new Intent(MainActivity.this,BasicMapActivity.class);
		it.putExtra("la", lalo[0]);
		it.putExtra("lo", lalo[1]);
		startActivity(it);
	}
	
	
	public static double[] delta(double wgLat, double wgLon){
		double[] latlon = new double[2];
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat /180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - OFFSET * magic * magic;
		double sqrMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrMagic) * PI);
		dLon = (dLon * 180.0) / (AXIS / sqrMagic * Math.cos(radLat) * PI);
		latlon[0] = dLat;
		latlon[1] = dLon;
		return latlon;
	}
	
	public static double transformLat(double x, double y){
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));  
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;  
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;  
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;  
        return ret;
	}
	public static double transformLon(double x, double y){  
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));  
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;  
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;  
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;  
        return ret;  
    }
	
	public static double[] wgs2GCJ(double wgLat, double wgLon) {  
    	double[] latlon  = new double[2];
        
        double[] deltaD =  delta(wgLat,wgLon);
        latlon[0] = wgLat + deltaD[0];
        latlon[1] = wgLon + deltaD[1];
	    return latlon;
    }  
}
