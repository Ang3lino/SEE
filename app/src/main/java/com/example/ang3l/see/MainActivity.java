package com.example.ang3l.see;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegister = findViewById(R.id.txt_register);
        txtRegister.setOnClickListener(view -> { // we'll pass to CreateAccountActivity through intent
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> { // we'll pass to CreateAccountActivity through intent
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
    }

}
