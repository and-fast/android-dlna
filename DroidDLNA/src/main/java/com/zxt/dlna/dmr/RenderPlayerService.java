/*
 * RenderPlayerService.java
 * Description:
 * Author: zxt
 */
package com.zxt.dlna.dmr;


import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;


public class RenderPlayerService extends Service {

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onStart(Intent intent, int startId) {
        //xgf fix bug null point
        if (null == intent) {
            return;
        }

        super.onStart(intent, startId);

        if ("video".equalsIgnoreCase(intent.getStringExtra("type"))) {
            String name = intent.getStringExtra("name");
            String uri = intent.getStringExtra("playURI");

            try {

                ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                String actionName = appInfo.metaData.getString("DLNA_VIDEO_PLAYER");

                if (actionName != null) {
                    startActivity(
                            new Intent(actionName,
                                    Uri.parse("dlna://video/?name=" + name + "&uri=" + Base64.encodeToString(uri.getBytes(), Base64.DEFAULT)))
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
