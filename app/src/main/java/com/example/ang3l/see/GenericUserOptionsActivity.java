package com.example.ang3l.see;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

public class GenericUserOptionsActivity extends AppCompatActivity {
    CardView cardVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_user_options);

        cardVote = findViewById(R.id.cardview_vote);
        cardVote.setOnClickListener(v -> {
            Intent intent = new Intent(this, BoletaActivity.class);
            startActivity(intent);
        });
    }
}
