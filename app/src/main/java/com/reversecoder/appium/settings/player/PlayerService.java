package com.reversecoder.appium.settings.player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Iterator;

public class PlayerService extends Service {

    private String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mMediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        Iterator iter = extras.keySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (key.equalsIgnoreCase("audio")) {
                String value = extras.getString(key);
                // Start playing audio file from sdcard
                Log.d(TAG, TAG + ">>onStartCommand>>key: " + key + " value: " + value);
                if (!TextUtils.isEmpty(value)) {
                    Uri mUri = Uri.fromFile(new File(value));
                    mMediaPlayer = MediaPlayer.create(this, mUri);
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            destroyMediaPlayer();
                        }
                    });
                    mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            destroyMediaPlayer();
                            return false;
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMediaPlayer.start();
                        }
                    }, 2000);
                }
            }
            i++;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyMediaPlayer();
    }

    public boolean destroyMediaPlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            return true;
        }
        return false;
    }
}