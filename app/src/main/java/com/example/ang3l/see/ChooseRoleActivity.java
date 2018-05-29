package com.example.ang3l.see;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ChooseRoleActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

//        toolbar = findViewById(R.id.toolbar_choose_role);
//        setSupportActionBar(toolbar); // le damos soporte a los pobres
    }
}
