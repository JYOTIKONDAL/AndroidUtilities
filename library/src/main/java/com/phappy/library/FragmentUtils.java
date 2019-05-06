package com.phappy.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils {

    public static void replaceFragment(@NonNull Fragment fragment, boolean isReplace, String tag, int id, FragmentManager fragmentManager, Bundle bundle) {
        Fragment existingFrag = fragmentManager.findFragmentById(id);

        if (existingFrag != null && fragment.getClass().equals(existingFrag.getClass())
                && existingFrag.getTag() != null && existingFrag.getTag().equals(tag))
            return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);
        if (existingFrag != null && isReplace)
            fragmentTransaction.replace(id, fragment, tag).commit();
        else
            fragmentTransaction.add(id, fragment, tag).commit();
    }

    public static void replaceFragment(Fragment fragment, String tag, int id, FragmentManager fragmentManager) {
        replaceFragment(fragment, true, tag, id, fragmentManager, null);
    }

    public static void replaceFragment(Fragment fragment, int id, FragmentManager fragmentManager) {
        replaceFragment(fragment, true, "", id, fragmentManager, null);
    }

    public static void replaceFragment(Fragment fragment, String tag, int id, FragmentManager fragmentManager, Bundle bundle) {
        replaceFragment(fragment, true, tag, id, fragmentManager, bundle);
    }
}
