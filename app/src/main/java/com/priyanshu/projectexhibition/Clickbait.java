package com.priyanshu.projectexhibition;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Clickbait extends AppCompatActivity {

    private EditText editText;
    private AppCompatButton sendButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clickbait);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        resultText = findViewById(R.id.resultText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextToFlask();
            }
        });
    }
    private void sendTextToFlask() {
        String inputText = editText.getText().toString();
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getInstance(this);
        Call<ResponseData> call = apiService.sendText(new RequestData(inputText));

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String prediction = response.body().prediction;
                    resultText.setText("Prediction: " + prediction);

                    // Change text color based on prediction result
                    if (prediction.equalsIgnoreCase("Possible Clickbait")) {
                        resultText.setTextColor(Color.RED);  // Red for clickbait
                    } else {
                        resultText.setTextColor(Color.GREEN); // Green for non-clickbait
                    }
                } else {
                    resultText.setText("Error: Invalid response");
                    resultText.setTextColor(Color.GRAY);  // Gray for errors
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                resultText.setText("Failed: " + t.getMessage());
            }
        });
    }
}