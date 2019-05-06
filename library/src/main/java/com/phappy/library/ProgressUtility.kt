import android.app.ProgressDialog
import android.content.Context
import com.phappy.library.R

class ProgressUtility {

    @Suppress("DEPRECATION")
    companion object {

        var progress_Dialog: ProgressDialog? = null
        /**
         * @return -
         * Returns Whether the dialog is currently showing.
         */
        val isShowingProgress: Boolean
            get() = if (progress_Dialog != null) {
                progress_Dialog!!.isShowing
            } else
                false

        /**
         * A dialog showing a progress indicator and an optional text message or
         * view. Only a text message or a view can be used at the same time.
         *
         *
         * The dialog can be made cancelable on back key press.
         *
         * @param activity - context of the activity on which Progress dialog need to be
         * shown
         * @param message  - message to shown in progress Dialog
         */
        fun showProgressCancelable(activity: Context, message: String) {
            progress_Dialog =
                    ProgressDialog.show(activity, activity.resources.getString(R.string.app_name), message, true, true)
        }


        /**
         * A dialog showing a progress indicator and an optional text message or
         * view. Only a text message or a view can be used at the same time.
         *
         *
         * The dialog can't be  cancelable on back key press.
         *
         * @param activity - context of the activity on which Progress dialog need to be
         * shown
         * @param message  - message to shown in progress Dialog
         */
        fun showProgress(activity: Context, message: String) {

            try {
                if (progress_Dialog != null && progress_Dialog!!.isShowing) {
                    return
                }
                progress_Dialog = ProgressDialog(activity, R.style.AppTheme)
                progress_Dialog = ProgressDialog.show(activity, activity.getString(R.string.app_name), message, true)
                progress_Dialog!!.setCancelable(false)
            } catch (e: Exception) {
                e.printStackTrace()
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
        fun dismissProgress() {
            try {
                if (progress_Dialog!!.isShowing)
                    progress_Dialog!!.dismiss()
            } catch (e: Exception) {
                //Utilities.showDLog("-dismissProgress progress dialog--", "Exception--"+e);
            }
        }

    }
}
