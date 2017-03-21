package com.reversecoder.appium.settings.network;

import android.content.Context;
import com.reversecoder.appium.settings.network.handlers.*;


public class ServicesFactory {
  public static NetworkService getService(Context context, String name) {
    if (name.equalsIgnoreCase("wifi")) {
      return new WiFiService(context);
    } else if (name.equalsIgnoreCase("data")) {
      return new DataService(context);
    } else {
      return null;
    }
  }
}
