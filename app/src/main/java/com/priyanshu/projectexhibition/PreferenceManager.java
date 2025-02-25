package com.priyanshu.projectexhibition;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "app_prefs";

    // Keys for SharedPreferences
    private static final String USE_FULL_URL_KEY = "use_full_url";
    private static final String BASE_URL_KEY = "base_url";
    private static final String DOMAIN_KEY = "domain";
    private static final String IP_KEY = "ip";
    private static final String PORT_KEY = "port";

    // Default Values
    private static final String DEFAULT_BASE_URL = "http://example.com/";
    private static final String DEFAULT_DOMAIN = "http";
    private static final String DEFAULT_IP = "192.168.42.212";
    private static final String DEFAULT_PORT = "5000";

    /** Save user preference for using full URL */
    public static void setUseFullUrl(Context context, boolean useFullUrl) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(USE_FULL_URL_KEY, useFullUrl).apply();
    }

    /** Retrieve user preference for using full URL */
    public static boolean getUseFullUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(USE_FULL_URL_KEY, false);
    }

    /** Save full API base URL */
    public static void setBaseUrl(Context context, String baseUrl) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(BASE_URL_KEY, baseUrl).apply();
    }

    /** Retrieve full API base URL */
    public static String getBaseUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(BASE_URL_KEY, DEFAULT_BASE_URL);
    }

    /** Save domain (http or https) */
    public static void setDomain(Context context, String domain) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(DOMAIN_KEY, domain).apply();
    }

    /** Retrieve domain */
    public static String getDomain(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(DOMAIN_KEY, DEFAULT_DOMAIN);
    }

    /** Save IP address */
    public static void setIp(Context context, String ip) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(IP_KEY, ip).apply();
    }

    /** Retrieve IP address */
    public static String getIp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(IP_KEY, DEFAULT_IP);
    }

    /** Save port */
    public static void setPort(Context context, String port) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(PORT_KEY, port).apply();
    }

    /** Retrieve port */
    public static String getPort(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PORT_KEY, DEFAULT_PORT);
    }

    /** Get the correct API Base URL based on user preference */
    public static String getApiBaseUrl(Context context) {
        if (getUseFullUrl(context)) {
            return getBaseUrl(context); // Use full URL if selected
        }
        return getDomain(context) + "://" + getIp(context) + ":" + getPort(context) + "/";
    }
}
