package com.example.ang3l.see;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ang3l.see.items.PostulantItem;

import java.util.ArrayList;

public class WatchPostulantActivity extends AppCompatActivity {
    private ArrayList<PostulantItem> postulantItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_postulant);

        postulantItems = new ArrayList<>();
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "meade", "meade@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "anaya", "anaya@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "amlo", "amlo@gmail.com") );
        postulantItems.add( new PostulantItem(R.mipmap.ic_postulant, "bronco", "bronco@gmail.com") );

    }
}
