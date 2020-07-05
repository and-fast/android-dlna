package com.android.tvremoteime;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.zxt.dlna.dmr.ZxtMediaRenderer;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;

public class DLNAUtils {

    private static String TAG = "DLNAUtils";

    private static ZxtMediaRenderer mMediaRenderer = null;

    // 设置名称
    public static void setDLNANameSuffix(Context context, String nameSuffix) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("dlna_settings", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nameSuffix", nameSuffix);
        editor.apply();
        startDLNAService(context);
    }

    // 默认名称
    public static String getDLNANameSuffix(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("dlna_settings", 0);
        return sharedPreferences.getString("nameSuffix", "Test");
    }

    public static void startDLNAService(final Context context) {
        ServiceConnection serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                String dlnaName = context.getString(R.string.app_name);
                String dlnaNameSuffix = getDLNANameSuffix(context);

                if (!TextUtils.isEmpty(dlnaNameSuffix)) {
                    dlnaName += "(" + dlnaNameSuffix + ")";
                }

                AndroidUpnpService upnpService = (AndroidUpnpService) service;

                if (mMediaRenderer != null) {
                    mMediaRenderer.stopAllMediaPlayers();
                    upnpService.getRegistry().removeAllLocalDevices();
                    mMediaRenderer = null;
                }

                mMediaRenderer = new ZxtMediaRenderer(1, dlnaName, context);

                upnpService.getRegistry().addDevice(mMediaRenderer.getDevice());

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "DLNA: onServiceDisconnected");
            }

        };

        context.bindService(
                new Intent(context, AndroidUpnpServiceImpl.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    public static void stopDLNAService() {
        if (mMediaRenderer != null) {
            mMediaRenderer.stopAllMediaPlayers();
        }
    }

}
