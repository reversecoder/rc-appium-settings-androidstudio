package com.reversecoder.appium.settings.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.reversecoder.appium.settings.account.HandleAccountActivity;
import com.reversecoder.appium.settings.animation.HandleAnimations;
import com.reversecoder.appium.settings.locale.HandleLocaleActivity;

public class HandleBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = HandleBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (HandleAccountActivity.hasExtraRegardingAccountType(intent)) {
            Intent buildLaunchingMainActivityIntent =
                    HandleAccountActivity.buildLaunchingMainActivityIntent(context, intent);
            launchMainActivity(context, buildLaunchingMainActivityIntent);
        }

        if (HandleLocaleActivity.hasExtraRegardingLocal(intent)) {
            Intent handleLocalActivityIntent =
                    HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent);
            launchHandleLocalActivity(context, handleLocalActivityIntent);
        }

        if (HandleAnimations.hasExtraRegardingAnimation(intent)) {
            HandleAnimations.enableAnimationsWithIntent(intent);
        }
    }

    public void launchMainActivity(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }

    public void launchHandleLocalActivity(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }
}
