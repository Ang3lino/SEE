package com.example.ang3l.see;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.classes.VolleyHelper;
import com.example.ang3l.see.classes.Voter;
import com.example.ang3l.see.classes.VotingRoom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GenericUserOptionsActivity extends AppCompatActivity {
    private CardView cardVote, cardTrend, cardPrediction, cardWatchCandidates;
    private FloatingActionButton fabSudo;
    private TextView txtRoomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_user_options);

        initAll();
    }

    private void initAll() {
        cardWatchCandidates = findViewById(R.id.cardview_watch_candidates);

        cardTrend = findViewById(R.id.cardview_trends);
        cardTrend.setOnClickListener(v -> {
            startActivity(new Intent(this, TrendsActivity.class));
        });

        cardVote = findViewById(R.id.cardview_vote);
        cardVote.setOnClickListener(v -> {
            Intent intent = new Intent(this, BoletaActivity.class);
            startActivity(intent);
        });

        cardPrediction = findViewById(R.id.cardview_predictions);
        cardPrediction.setOnClickListener(this::probableCandidate);

        txtRoomNumber = findViewById(R.id.txt_room_number);
        txtRoomNumber.setText("" + VotingRoom.get().getNumber());

        fabSudo = findViewById(R.id.fab_admin_mode);
        isCreator();
    }

    private void probableCandidate(View view) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("count_votes.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        int min = -1;
                        String dominantCandidate = new String();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject currentObject = array.getJSONObject(i);
                            int count = currentObject.getInt("count");
                            if (count > min) {
                                min = count;
                                dominantCandidate = currentObject.getString("nombre");
                            }
                        }
                        Toast.makeText(this,
                                "Es probable que: " + dominantCandidate +" gane la elecion",
                                Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override // le pasamos los datos del celular medianto un HashMap
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", "" + VotingRoom.get().getNumber());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void isCreator() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("is_creator.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(0);
                        if (object.getBoolean("isCreator")) {
                            fabSudo.setOnClickListener(v -> {
                                Intent intent = new Intent(this, AdministratorOptionsActivity.class);
                                intent.putExtra("id", VotingRoom.get().getNumber());
                                startActivity(intent);
                            });
                            cardWatchCandidates.setOnClickListener(v -> {
                                Intent intent = new Intent(this, WatchPostulantActivity.class);
                                intent.putExtra("isCreator", true);
                                startActivity(intent);
                            });
                        }
                        else  {
                            fabSudo.setVisibility(View.GONE);
                            cardWatchCandidates.setOnClickListener(v -> {
                                Intent intent = new Intent(this, WatchPostulantActivity.class);
                                intent.putExtra("isCreator", false);
                                startActivity(intent);
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override // le pasamos los datos del celular medianto un HashMap
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", "" + VotingRoom.get().getNumber());
                params.put("email", Voter.getInstance().getEmail());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void watchCandidatesClicked(View view) {

    }
}
