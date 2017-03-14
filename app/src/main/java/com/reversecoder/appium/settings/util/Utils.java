package com.reversecoder.appium.settings.util;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

/**
 * Created by alam on 3/14/17.
 */

public class Utils {

    public static void closeAndRemoveFromRecent(final Activity activity) {
        // Close yourself!
        Log.d("rc-appium-settings: ", "Closing appium settings app");
        //you need to add below property in manifest file of the activity tag of application
        //android:excludeFromRecents="true"
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    activity.finishAndRemoveTask();
                } else {
                    activity.finish();
                }
            }
        }, 1000);
    }
}
