package com.reversecoder.appium.settings.account;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.reversecoder.appium.settings.R;
import com.reversecoder.appium.settings.permission.HandlePermission;
import com.reversecoder.appium.settings.util.Utils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class HandleAccountActivity extends AppCompatActivity {
    private static String TAG = HandleAccountActivity.class.getSimpleName();

    public static String intentExtraAccountType = "accountType";

    private static String stringExtraViaReceiver = "ACCOUNT_TYPE";

    private String accountType = "";

    private HandlePermission handlePermission;

    public static boolean hasExtraRegardingAccountType(Intent intent) {
        return intent.hasExtra(stringExtraViaReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_account);

        handlePermission = new HandlePermission();

        Intent intent = this.getIntent();

        String action = intent.getAction();

        if (Intent.ACTION_MAIN.equals(action) && intent.hasExtra(intentExtraAccountType)) {
            accountType = intent.getStringExtra(intentExtraAccountType);

            // TODO: Need to grant permission via adb before calling this activity and uncomment below lines
//            MainActivityPermissionsDispatcher.showGetAccountAndRemoveWithCheck(this);
//            showGetAccountAndRemove();

            finish();
        }

        // TODO: Need to grant permission via adb before calling this activity and uncomment below lines
//        MainActivityPermissionsDispatcher.showGetAccountAndRemoveWithCheck(this);
//        showGetAccountAndRemove();

        Utils.closeAndRemoveFromRecent(HandleAccountActivity.this);
    }

    @NeedsPermission(Manifest.permission.GET_ACCOUNTS)
    public void showGetAccountAndRemove() {
        removeAccount();
    }

    @OnShowRationale(Manifest.permission.GET_ACCOUNTS)
    void showRationaleForGetAccount(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_get_account)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .show();
    }

    public void removeAccount() {
        HandleAccountHelper handleAccountHelper = new HandleAccountHelper(this);
        handleAccountHelper.removeAccount(accountType);
    }

    public static Intent buildLaunchingMainActivityIntent(@NonNull Context context, @NonNull Intent intent) {
        String accountType = intent.getStringExtra(stringExtraViaReceiver);

        Intent returnIntent = new Intent(context, HandleAccountActivity.class);
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        returnIntent.setAction(Intent.ACTION_MAIN);
        returnIntent.putExtra(HandleAccountActivity.intentExtraAccountType, accountType);
        return returnIntent;
    }

    // TODO: implement https://developer.android.com/guide/components/aidl.html if you'd like to use
    // this method.
    private void grantAllPermissions() {
        grantPermission(this, Manifest.permission.GET_ACCOUNTS);
        grantPermission(getApplicationContext(), Manifest.permission.CHANGE_CONFIGURATION);
        grantPermission(getApplicationContext(), Manifest.permission.SET_ANIMATION_SCALE);
        grantPermission(this, Manifest.permission.WRITE_SECURE_SETTINGS);
    }

    // String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    // mainActivity.grantPermission(this, permission);
    public void grantPermission(@NonNull Context context, @NonNull String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Don't need to grant permission because target apk is under API Level 23");
            return;
        }

        if (!handlePermission.grantPermission(context, context.getPackageName(), permission)) {
            throw new IllegalArgumentException("Failed to grant permission " + permission);
        }
    }
}
