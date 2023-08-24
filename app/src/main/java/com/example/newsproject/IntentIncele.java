package com.example.newsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class IntentIncele extends AppCompatActivity {

    ShapeableImageView img2;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_incele);

        img2 = findViewById(R.id.imageView237);
        txt = findViewById(R.id.textView);

        String name = getIntent().getStringExtra("name");
        String status = getIntent().getStringExtra("status");
        String image = getIntent().getStringExtra("image");
        String type = getIntent().getStringExtra("type");

        Picasso.get().load(image).into(img2);
        txt.setText("Name : " + name + "\n" + "Status : " + status + "\n" + "Type : " + type);
    }
}