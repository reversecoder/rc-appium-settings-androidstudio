package com.reversecoder.appium.settings.network.handlers;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.reversecoder.appium.settings.network.NetworkService;


public class WiFiService extends NetworkService {
  private static final String TAG = "APPIUM SETTINGS (WIFI)";

  public WiFiService(Context context) {
    super(context);
  }

  public boolean enable() {
    Log.d(TAG, "Enabling wifi");

    return setWiFi(true);
  }

  public boolean disable() {
    Log.d(TAG, "Disabling wifi");

    return setWiFi(false);
  }

  private boolean setWiFi(boolean state) {
    WifiManager mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    return mWifiManager.setWifiEnabled(state);
  }
}
