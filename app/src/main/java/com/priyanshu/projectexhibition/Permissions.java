package com.priyanshu.projectexhibition;

import android.app.AppOpsManager;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

import java.util.*;

public class Permissions extends AppCompatActivity {

    AutoCompleteTextView appSelector;
    TextView permissionsText, titleText;
    ProgressBar progressBar;
    Button detectAnomaliesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permissions);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        appSelector = findViewById(R.id.appSelector);
        permissionsText = findViewById(R.id.permissionsText);
        titleText = findViewById(R.id.analysisTitle);
        progressBar = findViewById(R.id.progressBar);
        detectAnomaliesButton = findViewById(R.id.detectAnomaliesButton);

        List<String> appNames = getAllInstalledAppNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, appNames);
        appSelector.setAdapter(adapter);
        appSelector.setThreshold(1);

        if (!hasUsageAccessPermission()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            Toast.makeText(this, "Please grant Usage Access Permission", Toast.LENGTH_LONG).show();
        }

        appSelector.setOnItemClickListener((parent, view, position, id) -> {
            String selectedApp = parent.getItemAtPosition(position).toString();
            titleText.setText("Analysis of " + selectedApp);
            String packageName = getPackageNameFromAppName(selectedApp);
            displayGrantedPermissions(packageName);
        });

        detectAnomaliesButton.setOnClickListener(v -> {
            new AnomalyDetectionTask().execute();
        });
    }

    private List<String> getAllInstalledAppNames() {
        List<String> appNames = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        for (ApplicationInfo appInfo : apps) {
            appNames.add(pm.getApplicationLabel(appInfo).toString());
        }
        Collections.sort(appNames);
        return appNames;
    }

    private String getPackageNameFromAppName(String appName) {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        for (ApplicationInfo appInfo : apps) {
            if (pm.getApplicationLabel(appInfo).toString().equals(appName)) {
                return appInfo.packageName;
            }
        }
        return "";
    }

    private boolean hasUsageAccessPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void displayGrantedPermissions(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

            List<String> frequentList = new ArrayList<>();
            List<String> frequentSet = Arrays.asList(
                    "INTERNET", "CAMERA", "ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION",
                    "READ_EXTERNAL_STORAGE", "WRITE_EXTERNAL_STORAGE", "READ_CONTACTS",
                    "RECORD_AUDIO", "VIBRATE", "BLUETOOTH", "FOREGROUND_SERVICE"
            );

            if (packageInfo.requestedPermissions != null) {
                for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                    if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                        String fullPermission = packageInfo.requestedPermissions[i];
                        String shortPermission = fullPermission.substring(fullPermission.lastIndexOf('.') + 1);
                        if (frequentSet.contains(shortPermission)) {
                            frequentList.add(shortPermission);
                        }
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            if (!frequentList.isEmpty()) {
                result.append("🔹 Permissions:\n");
                for (String perm : frequentList) {
                    result.append("• ").append(perm).append("\n");
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result.append(getDataUsageForPackage(packageName));
            }

            permissionsText.setText(result.length() > 0 ?
                    result.toString() : "No data or permissions info found.");

        } catch (PackageManager.NameNotFoundException e) {
            permissionsText.setText("Unable to fetch app info.");
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getDataUsageForPackage(String packageName) {
        try {
            NetworkStatsManager statsManager = (NetworkStatsManager) getSystemService(Context.NETWORK_STATS_SERVICE);
            PackageManager pm = getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            int uid = appInfo.uid;

            long now = System.currentTimeMillis();
            long oneWeekAgo = now - (7L * 24 * 60 * 60 * 1000);

            long mobileBytes = 0, wifiBytes = 0;
            Map<Integer, Long> hourUsageMap = new HashMap<>();

            NetworkStats.Bucket bucket;

            NetworkStats mobileStats = statsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_MOBILE, null, oneWeekAgo, now, uid);
            while (mobileStats.hasNextBucket()) {
                bucket = new NetworkStats.Bucket();
                mobileStats.getNextBucket(bucket);
                mobileBytes += bucket.getRxBytes() + bucket.getTxBytes();

                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.setTimeInMillis(bucket.getStartTimeStamp());
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                hourUsageMap.put(hour, hourUsageMap.getOrDefault(hour, 0L) + bucket.getRxBytes() + bucket.getTxBytes());
            }

            NetworkStats wifiStats = statsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_WIFI, null, oneWeekAgo, now, uid);
            while (wifiStats.hasNextBucket()) {
                bucket = new NetworkStats.Bucket();
                wifiStats.getNextBucket(bucket);
                wifiBytes += bucket.getRxBytes() + bucket.getTxBytes();

                Calendar cal = Calendar.getInstance(Locale.getDefault());
                cal.setTimeInMillis(bucket.getStartTimeStamp());
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                hourUsageMap.put(hour, hourUsageMap.getOrDefault(hour, 0L) + bucket.getRxBytes() + bucket.getTxBytes());
            }

            int peakHour = -1;
            long peakBytes = 0;
            for (Map.Entry<Integer, Long> entry : hourUsageMap.entrySet()) {
                if (entry.getValue() > peakBytes) {
                    peakBytes = entry.getValue();
                    peakHour = entry.getKey();
                }
            }

            String peakTime = (peakHour != -1)
                    ? String.format(Locale.getDefault(), "%02d:00 - %02d:00", peakHour, (peakHour + 1) % 24)
                    : "N/A";

            return String.format(Locale.getDefault(),
                    "\n📶 Data used (last 7 days):\n" +
                            "• Mobile: %.2f MB\n" +
                            "• WiFi: %.2f MB\n" +
                            "\n⏱️ Peak usage time: %s",
                    mobileBytes / (1024.0 * 1024),
                    wifiBytes / (1024.0 * 1024),
                    peakTime);

        } catch (Exception e) {
            e.printStackTrace();
            return "\n📶 Data usage info not available.";
        }
    }

    private class AnomalyDetectionTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titleText.setText("Analysis Report:");
            progressBar.setVisibility(View.VISIBLE);
            permissionsText.setText("🔍 Detecting suspicious apps...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            PackageManager pm = getPackageManager();
            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
            List<String> suspiciousApps = new ArrayList<>();

            List<String> suspiciousPermissions = Arrays.asList(
                    "RECORD_AUDIO", "CAMERA", "ACCESS_FINE_LOCATION", "READ_CONTACTS", "READ_SMS"
            );

            for (ApplicationInfo appInfo : apps) {
                try {
                    PackageInfo packageInfo = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);
                    if (packageInfo.requestedPermissions != null) {
                        for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                            String perm = packageInfo.requestedPermissions[i];
                            if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                                String shortPerm = perm.substring(perm.lastIndexOf('.') + 1);
                                if (suspiciousPermissions.contains(shortPerm)) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        String usage = getDataUsageForPackage(appInfo.packageName);
                                        if (usage.contains("MB") || usage.contains("GB")) {
                                            String label = pm.getApplicationLabel(appInfo).toString();
                                            suspiciousApps.add(label + " — " + shortPerm);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (suspiciousApps.isEmpty()) {
                return "✅ No suspicious apps detected.";
            } else {
                Collections.sort(suspiciousApps);  // 🔥 Sort the list alphabetically
                StringBuilder sb = new StringBuilder("⚠️ Suspicious Apps:\n");
                for (String app : suspiciousApps) {
                    sb.append("• ").append(app).append("\n");
                }
                return sb.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            permissionsText.setText(result);
        }
    }
}
