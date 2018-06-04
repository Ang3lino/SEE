package com.example.ang3l.see;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

public class AdministratorOptionsActivity extends AppCompatActivity {
    CardView cardAddPostulant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_options);

        cardAddPostulant = findViewById(R.id.cardview_add_postulant);
        cardAddPostulant.setOnClickListener(v -> startActivity(new Intent(this, WatchPostulantActivity.class)));
    }

}
