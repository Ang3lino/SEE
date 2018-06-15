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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChooseRoleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnCreateLobby, btnJoinLobby;
    private TextView txtNumber;

    private int roomId;

    private Gson gs;
            // f: 01347075

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        toolbar = findViewById(R.id.toolbar_choose_role);

        findAllViewsById();

        setSupportActionBar(toolbar); // le damos soporte a los pobres
        getSupportActionBar().setTitle("SEE");
        toolbar.setSubtitle("Seleccione");
    }

    private void findAllViewsById() {
        btnCreateLobby = findViewById(R.id.btn_create_lobby);
        btnCreateLobby.setOnClickListener(this::onClickedCreateLobby);

        txtNumber = findViewById(R.id.txt_room_number);

        btnJoinLobby = findViewById(R.id.btn_join_room);
        btnJoinLobby.setOnClickListener(this::onClickedJoinLobby);

        gs = new Gson();
    }

    private void onClickedJoinLobby(View view) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("verify_room_existence.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(0);
                        if (object.getBoolean("exists")) {
                            VotingRoom room = VotingRoom.init(
                                    Integer.parseInt(txtNumber.getText().toString()),
                                    false,
                                    getIntent().getStringExtra("EMAIL"));
                            startActivity(new Intent(this, GenericUserOptionsActivity.class));
                        } else {
                            Toast.makeText(this, "la sala no existe", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override // le pasamos los datos del celular medianto un HashMap
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", txtNumber.getText().toString().trim());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void onClickedCreateLobby(View v) {
        Intent intent = new Intent(this, AdministratorOptionsActivity.class);
        String emailOfCreator = getIntent().getStringExtra("EMAIL");
        createLobby(emailOfCreator);
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
//                        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                        if (success.contains("false"))
                            Toast.makeText(
                                this,
                                "Ha habido un error en la creacion de la sala, por favor, intente otra vez",
                                Toast.LENGTH_SHORT
                            ).show();
                        else {
                            roomId = object.getInt("number");
//                            Toast.makeText(this, "" + roomId, Toast.LENGTH_SHORT).show();
                            VotingRoom room = VotingRoom.init(roomId, false, email);
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
