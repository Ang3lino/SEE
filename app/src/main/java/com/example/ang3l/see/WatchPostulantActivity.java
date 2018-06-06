package com.example.ang3l.see;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ang3l.see.classes.VotingRoom;
import com.example.ang3l.see.dialogs.AddPostulantDialog;
import com.example.ang3l.see.items.PostulantItem;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WatchPostulantActivity extends AppCompatActivity
        implements AddPostulantDialog.AddPostulantDialogListener {

    private ArrayList<PostulantItem> postulantItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAddPostulant;

    private VotingRoom room;

    private void initPostulantItems() {
        postulantItems = new ArrayList<>();

        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "meade", "meade@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "anaya", "anaya@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "amlo", "amlo@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "bronco", "bronco@gmail.com") );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_postulant);

        initWidget();
        initPostulantItems();
        buildRecyclerView();
    }

    private void initWidget() {
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
