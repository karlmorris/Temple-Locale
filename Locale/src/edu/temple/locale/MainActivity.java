package edu.temple.locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener, GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {


	// Global constants
	/*
	 * Define a request code to send to Google Play services
	 * This code is returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	LocationClient mLocationClient;


	TextView gpsData;

	Button viewOnMap;

	double lat = 0, lon = 0, speed = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		gpsData = (TextView) findViewById(R.id.gpsData);
		viewOnMap = (Button) findViewById(R.id.viewOnMap);


		/*
		 * Create a new location client, using the enclosing class to
		 * handle callbacks.
		 */
		mLocationClient = new LocationClient(this, this, this);

		viewOnMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://maps.google.com/maps?z=12&t=m&q=loc:" + lat + "+" + lon;


				Intent viewMapIntent = new Intent(Intent.ACTION_VIEW);

				viewMapIntent.setData(Uri.parse(url));

				startActivity(viewMapIntent);

			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (Util.servicesConnected(this)) {
			// Connect the client.
			mLocationClient.connect();
		}
	}

	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	private void updateLocationData(Location location){


		lat = location.getLatitude();
		lon = location.getLongitude();
		speed = location.getSpeed();

		String message = "Latitude: " + lat + "\n"
				+ "Longitude: " + lon + "\n"
				+ "Speed: " + speed;

		gpsData.setText(message);
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		LocationRequest locationRequest = LocationRequest.create();
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationClient.requestLocationUpdates(locationRequest, this);
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();

	}

	/*
	 * Called by Location Services if the attempt to
	 * Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * start a Google Play services activity that can resolve
		 * error.
		 */

		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(
						this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the
			 * user with the error.
			 */

		}

	}

	@Override
	public void onLocationChanged(Location location) {
		updateLocationData(location);	
	}
}
