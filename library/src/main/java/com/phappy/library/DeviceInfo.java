package com.phappy.library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

public class DeviceInfo {

    public static void getInfoAboutDevice(Context mContext) {
        /*
         * String Format
         * "MyChair/2.9.4 (iPhone; iOS 10.3.1; Scale/2.00)";
         *
         * */
        StringBuilder mStringBuilder = new StringBuilder(mContext.getResources().getString(R.string.app_name) + "/");
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            mStringBuilder.append(pInfo.versionName + " " + " (Android; " + "OS Version " + Build.VERSION.RELEASE + "; " + "Scale/");
            mStringBuilder.append(getDeviceDensity(mContext));
            //mStringBuilder.toString() is the device info string
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Error",e.toString());
        }
    }

    private static String getDeviceDensity(Context context) {
        String deviceDensity;
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                deviceDensity = 0.75 + " ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                deviceDensity = 1.0 + " mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                deviceDensity = 1.5 + " hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                deviceDensity = 2.0 + " xhdpi";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                deviceDensity = 3.0 + " xxhdpi";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                deviceDensity = 4.0 + " xxxhdpi";
                break;
            default:
                deviceDensity = "Not found";
        }
        return deviceDensity;
    }

    public static String generateDeviceId(Context mContext) {
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

}
