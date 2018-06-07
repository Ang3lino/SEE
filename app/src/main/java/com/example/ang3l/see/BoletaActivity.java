package com.example.ang3l.see;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class BoletaActivity extends AppCompatActivity {
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boleta);

        initAll();
    }

    private void changeFont() { // mata la app :/
        Typeface customFont = Typeface.createFromAsset(
                getAssets(),
                "font/cinzel_decorative"
        );
        txtTitle.setTypeface(customFont);
    }

    private void initAll() {
        txtTitle = findViewById(R.id.txt_electoral_process);
    }
}
