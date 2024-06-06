package com.example.marvelstudios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView txtName, txtId;
    ImageView imgSuperhero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        txtName = findViewById(R.id.txtName);
        txtId = findViewById(R.id.txtId);
        imgSuperhero = findViewById(R.id.imgSuperhero);
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String id = intent.getStringExtra("id");
            String image = intent.getStringExtra("image");
            txtName.setText(name);
            txtId.setText(id);
            Picasso.get().load(image).into(imgSuperhero);

        }
    }
}