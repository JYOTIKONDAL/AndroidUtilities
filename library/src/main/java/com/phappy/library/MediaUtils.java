package com.phappy.library;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import java.io.File;
import java.util.UUID;

public class MediaUtils {
    private File cameraFile;
    private final Activity mActivity;
    public static final String SYS_UNIQUE_ID = "intPosition";
    public static final int REQUEST_CAMERA_PHOTO = 002;
    public static final int REQUEST_TAKE_GALLERY_PICTURE = 001;
    public static final int REQUEST_CAMERA = 3;
    public static final int REQUEST_STORAGE = 8;
    protected static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected static final String[] PERMISSION_CAMERA = {Manifest.permission.CAMERA};

    public MediaUtils(Activity activity) {
        this.mActivity = activity;
    }

    public void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String uniqueId = getUniqueId();
        String imagePath = FileHandling.getMainStorageFolderPath() + uniqueId + ".jpg";
        Uri imageCameraUri;

        cameraFile = new File(imagePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageCameraUri = FileProvider.getUriForFile(mActivity,
                    mActivity.getApplicationContext().getPackageName() + ".provider", cameraFile);
        } else {
            imageCameraUri = Uri.fromFile(cameraFile);
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageCameraUri);

        try {
            cameraIntent.putExtra("return-data", true);
            cameraIntent.putExtra(SYS_UNIQUE_ID, uniqueId);
            mActivity.startActivityForResult(cameraIntent, REQUEST_CAMERA_PHOTO);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
    }

    public static String getUniqueId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(Intent.createChooser(intent, mActivity.getString(R.string.string_select_picture)), REQUEST_TAKE_GALLERY_PICTURE);
    }

    private static void checkCameraPermission(FragmentActivity activity, MediaUtils mediaUtils) {
        if (isCameraPermissionRequired(activity)) {
            requestCameraPermissions(activity);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            mediaUtils.openCamera();
        }
    }

    private static void checkStoragePermission(FragmentActivity activity, MediaUtils mediaUtils) {
        if (isStoragePermissionRequired(activity)) {
            requestStoragePermission(activity);
        } else {
            mediaUtils.openGallery();
        }
    }

    /**
     * Checks for Android M permissions
     */
    public static boolean isCameraPermissionRequired(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, PERMISSION_CAMERA[0]) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void requestCameraPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSION_CAMERA, REQUEST_CAMERA);
    }

    /**
     * is storage read/write permissions required
     *
     * @param activity
     * @return
     */
    public static boolean isStoragePermissionRequired(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, PERMISSION_STORAGE[0]) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, PERMISSION_STORAGE[1]) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void requestStoragePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_STORAGE);
    }


}
