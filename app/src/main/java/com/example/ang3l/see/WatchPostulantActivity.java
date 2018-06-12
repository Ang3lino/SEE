package com.example.ang3l.see;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.classes.VolleyHelper;
import com.example.ang3l.see.classes.VotingRoom;
import com.example.ang3l.see.dialogs.AddPostulantDialog;
import com.example.ang3l.see.items.PostulantItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WatchPostulantActivity extends AppCompatActivity
        implements AddPostulantDialog.AddPostulantDialogListener {

    private ArrayList<PostulantItem> postulantItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAddPostulant;

    private void initStaticPostulantItems() {
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "meade", "meade@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "anaya", "anaya@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "amlo", "amlo@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "bronco", "bronco@gmail.com") );
    }

    private void fillListFromDB() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("get_candidates_array.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject postulant = array.getJSONObject(i);
                            String name = postulant.getString("name");
                            String email = postulant.getString("email");
                            postulantItems.add(new PostulantItem(R.mipmap.ic_postulant, name, email));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> {
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", "" + VotingRoom.get().getNumber());
                return params;
            }
        };
        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_postulant);

        initWidget();
        buildRecyclerView();
        fillListFromDB();
    }

    private void initWidget() {
        postulantItems = new ArrayList<>();

        fabAddPostulant = findViewById(R.id.fab_add_postulant_show_dialog);
        fabAddPostulant.setOnClickListener(this::openDialogAdd);
    }

    private void openDialogAdd(View view) {
        AddPostulantDialog dialog = new AddPostulantDialog();
        dialog.show(getSupportFragmentManager(), "Candidatos");
    }

    public void applyText(String email, String name) {
        int pos = 0;
        if (email.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "E-mail no registrado", Toast.LENGTH_LONG).show();
        }
        else  {
            postulantItems.add(pos, new PostulantItem(R.mipmap.ic_postulant, name, email));
            adapter.notifyItemInserted(pos);
        }
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview_candidates);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        adapter = new PostulantAdapter(postulantItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
