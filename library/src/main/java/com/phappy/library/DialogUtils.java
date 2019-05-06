package com.phappy.library;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by MalhotraAjay on 12/29/2017.
 */

public class DialogUtils {

    public static Snackbar showRetrySnackBar(@NonNull View parentView, String msg, final CallBackListner callBackListner, final int value) {
        try {
            final Snackbar snackBar = Snackbar.make(parentView, msg, Snackbar.LENGTH_INDEFINITE);
            snackBar.setActionTextColor(Color.WHITE);
            View view = snackBar.getView();
            TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackBar.setAction(R.string.string_retry, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackBar.dismiss();
                    callBackListner.onViewClick(view,value);
                }
            });
            snackBar.show();
            return snackBar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Snackbar showNoNetworkSnackBar(@NonNull View parentView, final CallBackListner listener, int value) {
        String msg = parentView.getContext().getResources().getString(R.string.string_internet_not_found_toast);
        return showRetrySnackBar(parentView, msg, listener,value);
    }

    public static void showNoNetworkSnackBar(@NonNull View parentView) {
        String msg = parentView.getContext().getResources().getString(R.string.string_internet_not_found_toast);
        showSnackBar(parentView, msg);
    }

    public static void showSnackBar(@NonNull View parentView, String msg){
        Snackbar.make(parentView, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBarMaxLine(@NonNull View parentView, String msg){
        Snackbar snackbar;
        snackbar = Snackbar.make(parentView, msg, Snackbar.LENGTH_INDEFINITE);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }
    public static void showToast(@NonNull Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static android.support.v7.app.AlertDialog getSingleChoiceDialog(Context context, int title, int items, int checkedItem, final DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static android.support.v7.app.AlertDialog getSingleChoiceDialog(Context context, List<? extends Object> items, int checkedItem, final DialogInterface.OnClickListener listener) {
        int size = items.size();
        String[] itemArray = new String[size];
        for (int i = 0; i < size; i++) {
            itemArray[i] = items.get(i).toString();
        }
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setSingleChoiceItems(itemArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onClick(dialog, which);
            }
        });
        return builder.create();
    }

    public static DatePickerDialog getBirthDatePicker(Context context, Date mDate, DatePickerDialog.OnDateSetListener callback) {
        Calendar calendar = Calendar.getInstance();
        if(mDate !=null)
            calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, callback, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    public static void openSettingsForPermission(View mView, final Context mContext) {
        Snackbar snackbar;
        snackbar = Snackbar.make(/*mBinding.addParentView*/mView, "Seems like you have explicitly revoked the permissions. Kindly go to settings and enable application's permissions manually.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
            }
        });
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(4);
        snackbar.show();
    }

}
