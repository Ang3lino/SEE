package com.example.ang3l.see;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChooseRoleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnCreateLobby;
    private int roomId;
    private Gson gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        toolbar = findViewById(R.id.toolbar_choose_role);
        btnCreateLobby = findViewById(R.id.btn_create_lobby);
        btnCreateLobby.setOnClickListener(this::onClickedCreateLobby);

        gs = new Gson();
        setSupportActionBar(toolbar); // le damos soporte a los pobres
        getSupportActionBar().setTitle("SEE");
        toolbar.setSubtitle("Seleccione");
    }

    private void onClickedCreateLobby(View v) {
        Intent intent = new Intent(this, AdministratorOptionsActivity.class);
        String emailOfCreator = getIntent().getStringExtra("EMAIL");
        createLobby(emailOfCreator);
        VotingRoom room = new VotingRoom(roomId, false, emailOfCreator);
        intent.putExtra("room", gs.toJson(room));
        startActivity(intent);
    }

    private void createLobby(String email) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("create_voting_room.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(0);
                        String success = object.getString("inserted");
                        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                        if (success.contains("false"))
                            Toast.makeText(
                                this,
                                "Ha habido un error en la creacion de la sala, por favor, intente otra vez",
                                Toast.LENGTH_SHORT
                            ).show();
                        else {
                            roomId = object.getInt("number");
                            Toast.makeText(this, "" + roomId, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    /**
     * Infla un action bar en caso de ser instanciada con un menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.leave_options, menu);
        return true;
    }

    /**
     * Metodo llamado cuando se toca un elemento del actionbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Me tocaste 7u7", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
