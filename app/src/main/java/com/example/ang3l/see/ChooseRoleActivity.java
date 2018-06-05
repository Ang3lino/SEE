package com.example.ang3l.see;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChooseRoleActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnCreateLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        toolbar = findViewById(R.id.toolbar_choose_role);
        btnCreateLobby = findViewById(R.id.btn_create_lobby);
        btnCreateLobby.setOnClickListener(this::onClickedCreateLobby);

        Toast.makeText(this, getIntent().getStringExtra("EMAIL"), Toast.LENGTH_LONG).show();

        setSupportActionBar(toolbar); // le damos soporte a los pobres
        getSupportActionBar().setTitle("SEE");
        toolbar.setSubtitle("Seleccione");
    }

    private void onClickedCreateLobby(View v) {
        Intent intent = new Intent(this, AdministratorOptionsActivity.class);
        startActivity(intent);
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
