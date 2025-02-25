package com.priyanshu.projectexhibition;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String DOMAIN_KEY = "domain";
    private static final String IP_KEY = "ip";
    private static final String PORT_KEY = "port";

    private static final String DEFAULT_DOMAIN = "http";
    private static final String DEFAULT_IP = "192.168.42.212";
    private static final String DEFAULT_PORT = "5000";

    public static void setDomain(Context context, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(DOMAIN_KEY, domain).apply();
    }

    public static String getDomain(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(DOMAIN_KEY, DEFAULT_DOMAIN);
    }

    public static void setIp(Context context, String ip) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(IP_KEY, ip).apply();
    }

    public static String getIp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(IP_KEY, DEFAULT_IP);
    }

    public static void setPort(Context context, String port) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(PORT_KEY, port).apply();
    }

    public static String getPort(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PORT_KEY, DEFAULT_PORT);
    }

    public static String getFullBaseUrl(Context context) {
        return getDomain(context) + "://" + getIp(context) + ":" + getPort(context) + "/";
    }
}
