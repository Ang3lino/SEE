package com.example.ang3l.see;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.classes.VolleyHelper;
import com.example.ang3l.see.classes.VotingRoom;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdministratorOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardStartVoting;
    private CardView cardFinalizeVoting;
    private CardView cardAddPostulant;
    private CardView cardWatchIntegrants;
    private CardView cardTrends;

    private VotingRoom room;

    private TextView txtRoomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_options);

        initWidgets();
        Toast.makeText(this, "Ha ingresado como: " + room.getCreator(), Toast.LENGTH_LONG).show();
    }

    private void initWidgets() {
        room = VotingRoom.get();

        txtRoomNumber = findViewById(R.id.txt_room_number);
        txtRoomNumber.setText("" + VotingRoom.get().getNumber());

        cardStartVoting = findViewById(R.id.cardview_start_voting);
        cardFinalizeVoting = findViewById(R.id.cardview_finalize_voting);
        cardAddPostulant = findViewById(R.id.cardview_add_postulant);
        cardWatchIntegrants = findViewById(R.id.cardview_watch_integrants);
        cardTrends = findViewById(R.id.cardview_trends);
        cardTrends.setOnClickListener(v -> {
            startActivity(new Intent(this, TrendsActivity.class));
        });

        cardAddPostulant.setOnClickListener(v -> {
            Intent intent = new Intent(this, WatchPostulantActivity.class);
            intent.putExtra("isCreator", true);
            startActivity(intent);
        });

        cardStartVoting.setOnClickListener(this);
        cardFinalizeVoting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("room_switch.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(0);
                        boolean success = object.getBoolean("success");
                        if (!success)
                            Toast.makeText(this, "Error al cambiar el estado, pruebe otra vez",
                                    Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Actualizacion realizada", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
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
                String activate;
                if (v.getId() == R.id.cardview_start_voting) activate = "true";
                else activate = "false";
                params.put("control", activate);
                params.put("number", "" + VotingRoom.get().getNumber());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
