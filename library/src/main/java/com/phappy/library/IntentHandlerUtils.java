package com.phappy.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class IntentHandlerUtils {
    public static final String URL_EXTRA = "urlExtra";
    public static final String TITLE_EXTRA = "titleExtra";

    public static void startActivity(AppCompatActivity currentActivity, Class newActivity, boolean isClose) {
        currentActivity.startActivity(new Intent(currentActivity, newActivity));
        if (isClose)
            currentActivity.finish();
    }

    public static void startSettingActivity(Context mContext) {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mContext.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myAppSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        myAppSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        mContext.startActivity(myAppSettings);
    }

    public static void startWebView(Context mContext, String url, String title, Activity activity) {
        Intent intent = new Intent(mContext,activity.getClass());
        intent.putExtra(URL_EXTRA, url);
        intent.putExtra(TITLE_EXTRA, title);
        mContext.startActivity(intent);
    }

    public static void openMapActivityWithAddress(Context mContext, String address){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        mContext.startActivity(mapIntent);
    }

}
