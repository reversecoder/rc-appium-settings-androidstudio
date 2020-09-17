package com.reversecoder.appium.settings.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.reversecoder.appium.settings.R;

import java.util.Iterator;

public class PlayerActivity extends Activity {

    private static final String TAG = "APPIUM SETTINGS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Log.d(TAG, "Entering Appium settings");

        Bundle extras = this.getIntent().getExtras();
        Iterator iter = extras.keySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            String name = (String) iter.next();
            if (name.equalsIgnoreCase("audio")) {
                String value = extras.getString(name);
                // Start playing audio file from sdcard
                Log.d(TAG, TAG + ">>onCreate>>key: " + name + " value: " + value);
                Intent playerIntent = new Intent(PlayerActivity.this, PlayerService.class);
                playerIntent.putExtra(name, value);
                startService(playerIntent);
            }
            i++;
        }

        // Close yourself!
        Log.d(TAG, "Closing settings app");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                PlayerActivity.this.finish();
            }
        }, 10);
    }
}