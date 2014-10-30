package edu.temple.locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener{

	TextView gpsData;
	
	Button viewOnMap;
	
	double lat = 0, lon = 0, speed = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		gpsData = (TextView) findViewById(R.id.gpsData);
		viewOnMap = (Button) findViewById(R.id.viewOnMap);
		
		
		
		viewOnMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "http://maps.google.com/maps?z=12&t=m&q=loc:" + lat + "+" + lon;
				
				
				Intent viewMapIntent = new Intent(Intent.ACTION_VIEW);
				
				viewMapIntent.setData(Uri.parse(url));
				
				startActivity(viewMapIntent);
				
			}
		});
		
		
		LocationManager locationManager = (LocationManager)
				this.getSystemService(Context.LOCATION_SERVICE);
		
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		updateLocationData(location);
	}

	@Override
	public void onLocationChanged(Location location) {
		updateLocationData(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onProviderDisabled(String provider) {}
	
	
	private void updateLocationData(Location location){
		
		
		lat = location.getLatitude();
		lon = location.getLongitude();
		speed = location.getSpeed();
		
		String message = "Latitude: " + lat + "\n"
				+ "Longitude: " + lon + "\n"
				+ "Speed: " + speed;
		
		gpsData.setText(message);
	}
}
