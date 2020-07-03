/*
 * RenderPlayerService.java
 * Description:
 * Author: zxt
 */
package com.zxt.dlna.dmr;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import com.zxt.dlna.R;
import com.zxt.dlna.dmp.RenderPlayerEvent;

import org.greenrobot.eventbus.EventBus;


public class RenderPlayerService extends Service {

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //xgf fix bug null point
        if (null == intent) {
            return;
        }

        super.onStart(intent, startId);

        if ("video".equalsIgnoreCase(intent.getStringExtra("type"))) {
            String name = intent.getStringExtra("name");
            String uri = intent.getStringExtra("playURI");

            EventBus.getDefault().postSticky(new RenderPlayerEvent(name, uri));

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

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            setForeground();

        } else {

        }
    }

    @TargetApi(26)
    private void setForeground() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("ID", "NAME", NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, "ID")
                .setContentTitle("开启DLNA投屏服务")
                .setContentText("开启DLNA投屏服务")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .build();

        startForeground(1, notification);
    }
}
