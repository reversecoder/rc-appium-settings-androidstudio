package com.reversecoder.appium.settings.network;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.reversecoder.appium.settings.R;

import java.util.Iterator;


public class NetworkActivity extends Activity {
  private static final String TAG = "APPIUM SETTINGS";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_network);

    Log.d(TAG, "Entering Appium settings");

    Bundle extras = this.getIntent().getExtras();
    Iterator iter = extras.keySet().iterator();
    int i = 0;
    while (iter.hasNext()) {
      String name = (String)iter.next();
      NetworkService service = ServicesFactory.getService(this, name);
      if (service != null) {
        String value = extras.getString(name);
        updateView(i, name, value);
        boolean status = (value.equalsIgnoreCase("on")) ? service.enable() : service.disable();
        Log.d(TAG, "Status: " + status);
      } else {
        Log.e(TAG, "Unknown service '" + name + "'");
      }
      i++;
    }

    // Close yourself!
    Log.d(TAG, "Closing settings app");
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      public void run() {
        NetworkActivity.this.finish();
      }
    }, 1000);
  }

  private void updateView(int index, String name, String value) {
    int viewId = this.getResources().getIdentifier("notice_" + index, "id", this.getPackageName());
    final TextView notice = (TextView) findViewById(viewId);
    Log.d(TAG, name + "_" + value);
    int stringId = this.getResources().getIdentifier(name + "_" + value, "string", this.getPackageName());
    notice.setText(getResources().getString(stringId));
  }
}
