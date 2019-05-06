package com.phappy.library;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileHandling {
    private static String FOLDER_NAME = "App_Name";
    private static String ROOT_DIRECTORY = "Root_Directory_Name";

    public static String createDirectory(String subFolderDocName) {
        String dirPath;
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + ROOT_DIRECTORY + "/" + subFolderDocName + "/";
        File directory = new File(dirPath);

        if (!directory.exists())
            directory.mkdirs();

        return dirPath;
    }

    public static String getMainStorageFolderPath() {
        String dirPath;

        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + ROOT_DIRECTORY + "/";
        File directory = new File(dirPath);
        Log.i("IMAGE_PATH", "File name for storing images>>>>>>>>" + directory.getAbsolutePath());
        if (!directory.exists())
            directory.mkdirs();
        return dirPath;
    }

    public static String storeProfileDummyImageInSdcard(Bitmap bitmap, String partFileName) {
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = partFileName + ".png";
        File outputFile = new File(directory, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sdPath = directory.getAbsolutePath().toString() + "/" + fileName;
        return sdPath;
    }

    public String getPathOfStoredFile(String imageName) {
        String dirPath;
        dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ROOT_DIRECTORY + "/" + imageName;
        File directory = new File(dirPath);
        if (!directory.exists())
            directory.mkdirs();
        return dirPath;
    }

    public void copyFile(String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            if (outputPath.contains("content://"))
                outputPath = outputPath.replace("content://", "file://");

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath + +System.currentTimeMillis() + ".jpg");
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}
