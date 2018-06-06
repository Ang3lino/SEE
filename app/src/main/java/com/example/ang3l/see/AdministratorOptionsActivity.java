package com.example.ang3l.see;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.classes.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdministratorOptionsActivity extends AppCompatActivity {
    private CardView cardStartVoting;
    private CardView cardFinalizeVoting;
    private CardView cardAddPostulant;
    private CardView cardWatchIntegrants;
    private CardView cardTrends;

    private int idRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_options);


        initWidgets();
    }

    private void initWidgets() {
        cardStartVoting = findViewById(R.id.cardview_start_voting);
        cardFinalizeVoting = findViewById(R.id.cardview_finalize_voting);
        cardAddPostulant = findViewById(R.id.cardview_add_postulant);
        cardWatchIntegrants = findViewById(R.id.cardview_watch_integrants);
        cardTrends = findViewById(R.id.cardview_trends);

        cardStartVoting.setOnClickListener(this::onStartVotingClicked);
        cardAddPostulant.setOnClickListener(v -> {
            startActivity(new Intent(this, WatchPostulantActivity.class));
        });
    }

    private void onStartVotingClicked(View v) {

    }

}
