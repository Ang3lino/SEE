package com.example.ang3l.see;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.adapters.PostulantElectionAdapter;
import com.example.ang3l.see.classes.VolleyHelper;
import com.example.ang3l.see.classes.Voter;
import com.example.ang3l.see.classes.VotingRoom;
import com.example.ang3l.see.items.PostulantElectionItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BoletaActivity extends AppCompatActivity implements PostulantElectionAdapter.OnItemClickListener {
    private TextView txtTitle;

    private RecyclerView recycler;
    private PostulantElectionAdapter adapter;
    private ArrayList<PostulantElectionItem> postulants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boleta);

        initAll();
        buildRecycler();
        generateBallot();
    }

    private void generateBallot() {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("get_candidates_array.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String name = object.getString("name");
                            String match = "";
                            String email = object.getString("email");
                            postulants.add( new PostulantElectionItem(name, match, email) );
                        }
                        adapter = new PostulantElectionAdapter(this, postulants);
                        adapter.setOnItemClickListener(this);
                        recycler.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override // le pasamos los datos del celular medianto un HashMap
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", "" + VotingRoom.get().getNumber());
                params.put("email", Voter.getInstance().getEmail());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void buildRecycler() {
        recycler = findViewById(R.id.recyclerview_candidates_election);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAll() {
        postulants = new ArrayList<>();
        txtTitle = findViewById(R.id.txt_electoral_process);
    }

    @Override
    public void onItemClick(int position) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("voting_intent.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(0);
                        if (object.getBoolean("can_we_vote")) {
                            boolean changed = object.getBoolean("changed");
                            if (changed)
                                Toast.makeText(this, "Su voto fue cambiado", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, "Voto efectuado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, R.string.deactivated_elections, Toast.LENGTH_SHORT).show();
                            Toast.makeText(this, R.string.vote_not_effected, Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                PostulantElectionItem current = postulants.get(position);
                params.put("number", "" + VotingRoom.get().getNumber());
                params.put("email", Voter.getInstance().getEmail());
                params.put("post_email", current.getEmail());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
