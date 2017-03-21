package com.reversecoder.appium.settings.network;

import android.content.Context;


public abstract class NetworkService {
  protected Context mContext;

  public NetworkService(Context context) {
    this.mContext = context;
  }

  public abstract boolean enable();
  public abstract boolean disable();
}
