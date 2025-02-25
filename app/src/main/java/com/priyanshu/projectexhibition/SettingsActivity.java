package com.priyanshu.projectexhibition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private EditText domainEditText, ipEditText, portEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        domainEditText = findViewById(R.id.domain_edit_text);
        ipEditText = findViewById(R.id.ip_edit_text);
        portEditText = findViewById(R.id.port_edit_text);
        saveButton = findViewById(R.id.save_button);

        // Load saved values
        domainEditText.setText(PreferenceManager.getDomain(this));
        ipEditText.setText(PreferenceManager.getIp(this));
        portEditText.setText(PreferenceManager.getPort(this));

        saveButton.setOnClickListener(v -> {
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

            // Save values
            PreferenceManager.setDomain(this, domain);
            PreferenceManager.setIp(this, ip);
            PreferenceManager.setPort(this, port);

            // Reset Retrofit with new IP
            RetrofitClient.resetRetrofit();
            Toast.makeText(this, "IP saved!", Toast.LENGTH_SHORT).show();
            finish(); // Close settings
        });
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }

    // Validation Functions
    private boolean isValidDomain(String domain) {
        return domain.equals("http") || domain.equals("https");
    }

    private boolean isValidIP(String ip) {
        return ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }

    private boolean isValidPort(String port) {
        return port.matches("^\\d{2,5}$"); // Allows ports like 80, 5000, etc.
    }
}
