package com.android.tvremoteime.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.android.tvremoteime.utils.RomUtils;

import java.lang.reflect.Method;

public class BackgroundPopupUtil {

    // 小米
    private boolean xiaomi(Context context) {
        AppOpsManager ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            int op = 10021; // >= 23
            // ops.checkOpNoThrow(op, uid, packageName)
            Method method = ops.getClass().getMethod("checkOpNoThrow", int.class, int.class, String.class);
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean vivo(Context context) {
        String packageName = context.getPackageName();
        Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
        String selection = "pkgname = ?";
        String[] selectionArgs = new String[]{packageName};
        try {
            Cursor cursor = context
                    .getContentResolver()
                    .query(uri2, null, selection, selectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int currentMode = cursor.getInt(cursor.getColumnIndex("currentstate"));
                    cursor.close();
                    return currentMode == 0;
                } else {
                    cursor.close();
                    return false;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static void gotoAppDetailIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

}
