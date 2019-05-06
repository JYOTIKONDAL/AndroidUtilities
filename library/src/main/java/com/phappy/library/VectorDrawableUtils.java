package com.phappy.library;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;

public class VectorDrawableUtils {

    public static Drawable getDrawable(Context context, int drawableResId) {
        Drawable drawable;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            drawable = context.getResources().getDrawable(drawableResId, context.getTheme());
        } else {
            try {
                drawable = VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
            } catch (Exception e) {
                drawable = VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
            }
        }

        return drawable;
    }
}
