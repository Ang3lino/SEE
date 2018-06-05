package com.example.ang3l.see;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ang3l.see.dialogs.AddPostulantDialog;
import com.example.ang3l.see.items.PostulantItem;

import java.util.ArrayList;

public class WatchPostulantActivity extends AppCompatActivity {
    private ArrayList<PostulantItem> postulantItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnAddPostulant;

    private void initPostulantItems() {
        postulantItems = new ArrayList<>();

        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "meade", "meade@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "anaya", "anaya@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "amlo", "amlo@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "bronco", "bronco@gmail.com") );

        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "meade", "meade@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "anaya", "anaya@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "amlo", "amlo@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "bronco", "bronco@gmail.com") );

        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "meade", "meade@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "anaya", "anaya@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "amlo", "amlo@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "bronco", "bronco@gmail.com") );

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
        btnAddPostulant = findViewById(R.id.btn_add_postulant_show_dialog);

        btnAddPostulant.setOnClickListener(this::openDialog);
    }

    private void openDialog(View view) {
        AddPostulantDialog dialog = new AddPostulantDialog();
        dialog.show(getSupportFragmentManager(), "Candidatos");
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
