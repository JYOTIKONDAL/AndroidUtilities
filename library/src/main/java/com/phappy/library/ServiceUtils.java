package com.phappy.library;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceUtils {
    /**
     * \
     * @param serviceClass for which service class u want to check is it running or not
     * @param context context of activity
     * @return boolean value
     */
    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
