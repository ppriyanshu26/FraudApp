package com.priyanshu.projectexhibition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    private SwitchCompat urlModeSwitch;
    private EditText baseUrlEditText, domainEditText, ipEditText, portEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        urlModeSwitch = findViewById(R.id.url_mode_switch);
        baseUrlEditText = findViewById(R.id.base_url_edit_text);
        domainEditText = findViewById(R.id.domain_edit_text);
        ipEditText = findViewById(R.id.ip_edit_text);
        portEditText = findViewById(R.id.port_edit_text);
        saveButton = findViewById(R.id.save_button);

        // Load saved settings
        boolean useFullUrl = PreferenceManager.getUseFullUrl(this);
        urlModeSwitch.setChecked(useFullUrl);

        if (useFullUrl) {
            baseUrlEditText.setText(PreferenceManager.getBaseUrl(this));
            showFullUrlField();
        } else {
            domainEditText.setText(PreferenceManager.getDomain(this));
            ipEditText.setText(PreferenceManager.getIp(this));
            portEditText.setText(PreferenceManager.getPort(this));
            showDomainIpPortFields();
        }

        // Toggle input fields based on switch state
        urlModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showFullUrlField();
            } else {
                showDomainIpPortFields();
            }
        });

        saveButton.setOnClickListener(v -> {
            boolean useFullUrlMode = urlModeSwitch.isChecked();
            PreferenceManager.setUseFullUrl(this, useFullUrlMode);

            if (useFullUrlMode) {
                String baseUrl = baseUrlEditText.getText().toString().trim();
                if (!isValidUrl(baseUrl)) {
                    Toast.makeText(this, "Enter a valid API URL (http/https)", Toast.LENGTH_SHORT).show();
                    return;
                }
                PreferenceManager.setBaseUrl(this, baseUrl);
            } else {
                String domain = domainEditText.getText().toString().trim();
                String ip = ipEditText.getText().toString().trim();
                String port = portEditText.getText().toString().trim();

                if (!isValidDomain(domain)) {
                    Toast.makeText(this, "Enter valid domain (http or https)", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidIP(ip)) {
                    Toast.makeText(this, "Enter a valid IP address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidPort(port)) {
                    Toast.makeText(this, "Enter a valid port", Toast.LENGTH_SHORT).show();
                    return;
                }

                PreferenceManager.setDomain(this, domain);
                PreferenceManager.setIp(this, ip);
                PreferenceManager.setPort(this, port);
            }

            RetrofitClient.resetRetrofit();
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showFullUrlField() {
        baseUrlEditText.setVisibility(EditText.VISIBLE);
        domainEditText.setVisibility(EditText.GONE);
        ipEditText.setVisibility(EditText.GONE);
        portEditText.setVisibility(EditText.GONE);
    }

    private void showDomainIpPortFields() {
        baseUrlEditText.setVisibility(EditText.GONE);
        domainEditText.setVisibility(EditText.VISIBLE);
        ipEditText.setVisibility(EditText.VISIBLE);
        portEditText.setVisibility(EditText.VISIBLE);
    }

    private boolean isValidUrl(String url) {
        return url.matches("^(https?://)[^\\s/$.?#].[^\\s]*$");
    }

    private boolean isValidDomain(String domain) {
        return domain.equals("http") || domain.equals("https");
    }

    private boolean isValidIP(String ip) {
        return ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    private boolean isValidPort(String port) {
        return port.matches("^\\d{2,5}$");
    }
}
