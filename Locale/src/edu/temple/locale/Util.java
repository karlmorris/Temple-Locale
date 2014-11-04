package edu.temple.locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.content.Context;
import android.util.Log;

public class Util {
	
	public static boolean servicesConnected(Context context) {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(context);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
        // Google Play services was not available for some reason.
        // resultCode holds the error code.
        } else {
            Log.e("Location updated", "Google Play services is unavailable");
            return false;
        }
    }
}
