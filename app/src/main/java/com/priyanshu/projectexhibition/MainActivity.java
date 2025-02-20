package com.priyanshu.projectexhibition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    AppCompatButton description, comments, clickbait, members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        description = findViewById(R.id.description);
        comments = findViewById(R.id.comments);
        clickbait = findViewById(R.id.clickbait);
        members = findViewById(R.id.members);

        Intent iComments = new Intent(getApplicationContext(), Analysis.class);
        Intent iDescription = new Intent(getApplicationContext(), Description.class);
        Intent iMembers = new Intent(getApplicationContext(), Members.class);
        Intent iClickbait = new Intent(getApplicationContext(), Clickbait.class);

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iDescription);
            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iComments);
            }
        });

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iMembers);
            }
        });

        clickbait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iClickbait);
            }
        });

    }
}