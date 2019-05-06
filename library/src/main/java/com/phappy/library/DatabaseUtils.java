package com.phappy.library;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.regex.Pattern;

public class DatabaseUtils {
    private static final String GETTER_EXPRESSION = "(get)([A-Z]\\w+)";
    private static final String SETTER_EXPRESSION = "(set)([A-Z]\\w+)";
    private static final String SETTER_REPLACE_EXPRESSION = "set$2";
    private static final Pattern GETTER_PATTERN = Pattern.compile(GETTER_EXPRESSION);
    private static final Pattern SETTER_PATTERN = Pattern.compile(SETTER_EXPRESSION);

    /**
     * Copy properties of javabean style objects. It is mandatory to have getters and setters of fields
     * that are required to be copied
     *
     * @param from      The object from where the values of properties are to be copied.
     * @param to        The object that will have copied values.
     * @param whitelist optional. The properties which are required.
     * @param blacklist optional. The properties which can be ignored.
     * @return the object with values assigned to respective properties.
     */
    public static Object copy(Object from, Object to, Set<String> whitelist, Set<String> blacklist) {
        for (Method method : from.getClass().getDeclaredMethods()) {
            String name = method.getName();
            if (whitelist != null && !whitelist.contains(name)) {
                continue;
            }
            if (blacklist != null && blacklist.contains(name)) {
                continue;
            }
            if (Modifier.isPublic(method.getModifiers()) && isGetter(method)) {
                Method setter = getSetterForGetter(to, method);
                if (setter != null) {
                    try {
                        Object product = method.invoke(from);
                        setter.invoke(to, product);
                    } catch (IllegalAccessException e) {
                        //
                    } catch (InvocationTargetException e) {
                        //
                    }
                }
            }
        }
        return to;
    }

    /**
     * Copy properties of javabean style objects. It is mandatory to have getters and setters of fields
     * that are required to be copied
     *
     * @param from The object from values of properties are to be copied.
     * @param to   The object that will have copied values.
     * @return the object with values assigned to respective properties.
     */
    public static Object copy(Object from, Object to) {
        return copy(from, to, null, null);
    }

    public static boolean isGetter(Method method) {
        return isGetter(method.getName());
    }

    public static boolean isGetter(String methodName) {
        return GETTER_PATTERN.matcher(methodName).matches();
    }

    public static boolean isSetter(Method method) {
        return isSetter(method.getName());
    }

    public static boolean isSetter(String methodName) {
        return SETTER_PATTERN.matcher(methodName).matches();
    }

    public static String getSetterNameFromGetterName(String methodName) {
        return methodName.replaceFirst(GETTER_EXPRESSION, SETTER_REPLACE_EXPRESSION);
    }

    public static Method getSetterForGetter(Object instance, Method method) {
        String setterName = getSetterNameFromGetterName(method.getName());
        try {
            return instance.getClass().getMethod(setterName, method.getReturnType());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * Copy parcelable object. This is helpful when copy by value is required and not copy by reference
     *
     * @param orig original parcelable object to copy
     * @param <T>  Type of object
     * @return New object with copied data.
     */
    public static <T extends Parcelable> T copy(T orig) {
        Parcel p = Parcel.obtain();
        orig.writeToParcel(p, 0);
        p.setDataPosition(0);
        T copy = null;
        try {
            copy = (T) orig.getClass().getDeclaredConstructor(new Class[]{Parcel.class}).newInstance(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return copy;
    }
}
