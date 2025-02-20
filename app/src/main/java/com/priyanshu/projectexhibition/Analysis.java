package com.priyanshu.projectexhibition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class Analysis extends AppCompatActivity {
    AutoCompleteTextView autoTxt;
    ArrayList<String> appNames = new ArrayList<>();
    String selectedApp = "";
    AppCompatButton appBtn;
    TextView appTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        autoTxt = findViewById(R.id.autoTxt);
        appBtn = findViewById(R.id.appBtn);
        appTxt = findViewById(R.id.appTxt);

        appNames.add("Clash of Clans");
        appNames.add("CNN");
        appNames.add("Telegram");
        appNames.add("The New York Times");
        appNames.add("Clash Royale");
        appNames.add("Android");

        ArrayAdapter<String> autoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,appNames);
        autoTxt.setAdapter(autoAdapter);
        autoTxt.setThreshold(1);

        autoTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedApp = (String) parent.getItemAtPosition(position);
            }
        });

        appBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedApp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Select an app from the list", Toast.LENGTH_SHORT).show();
                } else {
                    appTxt.setText("Analysis for " + selectedApp);
                }
            }
        });
    }
}