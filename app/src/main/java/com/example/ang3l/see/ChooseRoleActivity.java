package com.example.ang3l.see;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ChooseRoleActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        toolbar = findViewById(R.id.toolbar_choose_role);
        setSupportActionBar(toolbar); // le damos soporte a los pobres
        getSupportActionBar().setTitle("SEE");
        toolbar.setSubtitle("Seleccione");
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
