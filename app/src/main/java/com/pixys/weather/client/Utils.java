package com.pixys.weather.client;

import android.content.Context;
import android.content.pm.PackageManager;

public class Utils {
    public static String getSystemProperty(String key, String defaultValue) {
        String value;

        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
            return (value == null || value.isEmpty()) ? defaultValue : value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
    public static Boolean isBuildValid(Context context){
        PackageManager pm = context.getPackageManager();
        if (pm != null && !pm.hasSystemFeature("com.pixys.weather.client.SUPPORTED")) {
            return false;
        }
        if (getSystemProperty("ro.pixys.version","").isEmpty()) {
            return false;
        }
        if (getSystemProperty("ro.pixys.releasetype","").isEmpty()) {
            return false;
        }
        if (getSystemProperty("ro.pixys.device","").isEmpty()) {
            return false;
        }
        return true;
    }
}
