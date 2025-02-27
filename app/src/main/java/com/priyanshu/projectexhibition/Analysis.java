package com.priyanshu.projectexhibition;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.Objects;

public class Analysis extends AppCompatActivity {
    AutoCompleteTextView autoTxt;
    ArrayList<String> appNames = new ArrayList<>();
    String selectedApp = "";
    AppCompatButton appBtn;
    TextView appTxt1, appTxt2, appTxt3, row11, row12, row13, row14, row15, row21, row22, row23, row24, row25, row31, row32, row33, row34, row35;
    ImageView fraud, like, features, satisfaction;
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
        appTxt1 = findViewById(R.id.appTxt1);
        appTxt2 = findViewById(R.id.appTxt2);
        appTxt3 = findViewById(R.id.appTxt3);
        row11 = findViewById(R.id.row11);
        row12 = findViewById(R.id.row12);
        row13 = findViewById(R.id.row13);
        row14 = findViewById(R.id.row14);
        row15 = findViewById(R.id.row15);
        row21 = findViewById(R.id.row21);
        row22 = findViewById(R.id.row22);
        row23 = findViewById(R.id.row23);
        row24 = findViewById(R.id.row24);
        row25 = findViewById(R.id.row25);
        row31 = findViewById(R.id.row31);
        row32 = findViewById(R.id.row32);
        row33 = findViewById(R.id.row33);
        row34 = findViewById(R.id.row34);
        row35 = findViewById(R.id.row35);
        fraud = findViewById(R.id.fraud);
        like = findViewById(R.id.like);
        satisfaction = findViewById(R.id.satisfaction);
        features = findViewById(R.id.features);

        appNames.add("Candy Crush Saga");
        appNames.add("Duolingo");
        appNames.add("Facebook");
        appNames.add("Instagram");
        appNames.add("Netflix");
        appNames.add("Pinterest");
        appNames.add("Snapchat");
        appNames.add("Spotify");
        appNames.add("Telegram");
        appNames.add("WhatsApp");
        appNames.add("YouTube");

        ArrayAdapter<String> autoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,appNames);
        autoTxt.setAdapter(autoAdapter);
        autoTxt.setThreshold(1);

        autoTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedApp = (String) parent.getItemAtPosition(position);
            }
        });

        autoTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedApp = "";
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        appBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (selectedApp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Select an app from the list", Toast.LENGTH_SHORT).show();
                } else {
                    appTxt1.setText("Analysis for "+selectedApp+":");

                    row11.setText("Model");
                    row12.setText("Precision");
                    row13.setText("Recall");
                    row14.setText("F1 score");
                    row15.setText("Support");
                    row21.setText("Class 0");
                    row22.setText("0.50");
                    row23.setText("0.43");
                    row24.setText("0.46");
                    row25.setText("407");
                    row31.setText("Class 1");
                    row32.setText("0.49");
                    row33.setText("0.57");
                    row34.setText("0.53");
                    row35.setText("396");

                    if(Objects.equals(selectedApp, "Candy Crush Saga")) {
                        appTxt2.setText("Probability of fraudulent activity: 21.54%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.candy_fraud_concerns);
                        like.setImageResource(R.drawable.candy_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.candy_satisfaction_rating);
                        features.setImageResource(R.drawable.candy_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Duolingo")) {
                        appTxt2.setText("Probability of fraudulent activity: 16.38%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.duolingo_fraud_concerns);
                        like.setImageResource(R.drawable.duolingo_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.duolingo_satisfaction_rating);
                        features.setImageResource(R.drawable.duolingo_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Facebook")) {
                        appTxt2.setText("Probability of fraudulent activity: 10.60%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.facebook_fraud_concerns);
                        like.setImageResource(R.drawable.facebook_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.facebook_satisfaction_rating);
                        features.setImageResource(R.drawable.facebook_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Instagram")) {
                        appTxt2.setText("Probability of fraudulent activity: 17.62%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.instagram_fraud_concerns);
                        like.setImageResource(R.drawable.instagram_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.instagram_satisfaction_rating);
                        features.setImageResource(R.drawable.instagram_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Netflix")) {
                        appTxt2.setText("Probability of fraudulent activity: 14.48%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.netflix_fraud_concerns);
                        like.setImageResource(R.drawable.netflix_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.netflix_likelihood_to_recommend);
                        features.setImageResource(R.drawable.netflix_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Pinterest")) {
                        appTxt2.setText("Probability of fraudulent activity: 13.15%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.pinterest_fraud_concerns);
                        like.setImageResource(R.drawable.pinterest_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.pinterest_likelihood_to_recommend);
                        features.setImageResource(R.drawable.pinterest_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Snapchat")) {
                        appTxt2.setText("Probability of fraudulent activity: 23.64%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.snapchat_fraud_concerns);
                        like.setImageResource(R.drawable.snapchat_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.snapchat_satisfaction_rating);
                        features.setImageResource(R.drawable.snapchat_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Spotify")) {
                        appTxt2.setText("Probability of fraudulent activity: 17.14%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.spotify_fraud_concerns);
                        like.setImageResource(R.drawable.spotify_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.spotify_satisfaction_rating);
                        features.setImageResource(R.drawable.spotify_top_liked_features);
                    } else if(Objects.equals(selectedApp, "Telegram")) {
                        appTxt2.setText("Probability of fraudulent activity: 19.86%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.telegram_fraud_concerns);
                        like.setImageResource(R.drawable.telegram_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.telegram_likelihood_to_recommend);
                        features.setImageResource(R.drawable.telegram_top_liked_features);
                    } else if(Objects.equals(selectedApp, "WhatsApp")) {
                        appTxt2.setText("Probability of fraudulent activity: 21.42%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.whatsapp_fraud_concerns);
                        like.setImageResource(R.drawable.whatsapp_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.whatsapp_satisfaction_rating);
                        features.setImageResource(R.drawable.whatsapp_top_liked_features);
                    } else {
                        appTxt2.setText("Probability of fraudulent activity: 15.30%");
                        appTxt3.setText("Graphs:");
                        fraud.setImageResource(R.drawable.youtube_fraud_concerns);
                        like.setImageResource(R.drawable.youtube_likelihood_to_recommend);
                        satisfaction.setImageResource(R.drawable.youtube_satisfaction_rating);
                        features.setImageResource(R.drawable.youtube_top_liked_features);
                    }

                }
            }
        });

    }
}