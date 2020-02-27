package com.test;

import com.facebook.react.ReactActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ReactActivity {
public static MainActivity mainActivity;
public static Boolean isVisible = false;
private static final String TAG = "MainActivity";
private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "Test";
  }

  @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  //  setContentView(R.layout.activity_main);

    mainActivity = this;
    registerWithNotificationHubs();
    FirebaseService.createChannelAndHandleNotifications(getApplicationContext());
}

@Override
protected void onStart() {
    super.onStart();
    isVisible = true;
}

@Override
protected void onPause() {
    super.onPause();
    isVisible = false;
}

@Override
protected void onResume() {
    super.onResume();
    isVisible = true;
}

@Override
protected void onStop() {
    super.onStop();
    isVisible = false;
}

public void ToastNotify(final String notificationMessage) {
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_LONG).show();
            // TextView helloText = (TextView) findViewById(R.id.text_hello);
            // helloText.setText(notificationMessage);
        }
    });
}

  private boolean checkPlayServices() {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
    if (resultCode != ConnectionResult.SUCCESS) {
        if (apiAvailability.isUserResolvableError(resultCode)) {
            apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                    .show();
        } else {
            Log.i(TAG, "This device is not supported by Google Play Services.");
           ToastNotify("This device is not supported by Google Play Services.");
            finish();
        }
        return false;
    }
    return true;
}

public void registerWithNotificationHubs()
{
    if (checkPlayServices()) {
        // Start IntentService to register this application with FCM.
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}


}
