package com.priyanshu.projectexhibition;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Members extends AppCompatActivity {

    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10;
    CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_members);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        card9 = findViewById(R.id.card9);
        card10 = findViewById(R.id.card10);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        txt8 = findViewById(R.id.txt8);
        txt9 = findViewById(R.id.txt9);
        txt10 = findViewById(R.id.txt10);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt1.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt2.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt3.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt4.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt5.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt6.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt7.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt8.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt9.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txt10.getText().toString();
                name = name.substring(11);
                Toast.makeText(Members.this, name+" says Hi!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}