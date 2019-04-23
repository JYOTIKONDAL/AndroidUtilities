package com.phappy.library;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressUtility {
    public static ProgressDialog progress_Dialog;
    //static String progress_title = "Go";
    public static AlertDialog.Builder alertDialogBuilder = null;
    public static AlertDialog alertDialog = null;

    /**
     * A dialog showing a progress indicator and an optional text message or
     * view. Only a text message or a view can be used at the same time.
     * <p/>
     * The dialog can be made cancelable on back key press.
     *
     * @param activity - context of the activity on which Progress dialog need to be
     *                 shown
     * @param message  - message to shown in progress Dialog
     */
    public static void showProgressCancelable(Context activity, String message) {
        progress_Dialog = ProgressDialog.show(activity, activity.getResources().getString(R.string.app_name), message, true, true);
    }


    /**
     * A dialog showing a progress indicator and an optional text message or
     * view. Only a text message or a view can be used at the same time.
     * <p/>
     * The dialog can't be  cancelable on back key press.
     *
     * @param activity - context of the activity on which Progress dialog need to be
     *                 shown
     * @param message  - message to shown in progress Dialog
     */
    public static void showProgress(Context activity, String message) {
        try {
            if (progress_Dialog != null && progress_Dialog.isShowing()) {
                return;
            }
            progress_Dialog = ProgressDialog.show(activity, activity.getString(R.string.app_name), message, true);
            progress_Dialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*public static void showProgress(Context context,String message, boolean Iscacelable) {
        try {
			progress_Dialog = ProgressDialog.show(context, progress_title, message, true,Iscacelable);
		} catch (Exception e) {
		}

	}*/

    /**
     * Dismiss this dialog, removing it from the screen. This method can be
     * invoked safely from any thread. Note that you should not override this
     * method to do cleanup when the dialog is dismissed
     */
    public static void dismissProgress() {
        try {
            if (progress_Dialog.isShowing())
                progress_Dialog.dismiss();
        } catch (Exception e) {
            //Utilities.showDLog("-dismissProgress progress dialog--", "Exception--"+e);
        }

    }

    /**
     * @return -
     * Returns Whether the dialog is currently showing.
     */
    public static boolean isShowingProgress() {
        if (progress_Dialog != null)
            return progress_Dialog.isShowing();
        else
            return false;
    }
}
