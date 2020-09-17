package com.reversecoder.appium.settings.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.reversecoder.appium.settings.R;
import com.reversecoder.appium.settings.util.Utils;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.closeAndRemoveFromRecent(MainActivity.this);
    }
}
