package com.example.ang3l.see;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.classes.VolleyHelper;
import com.example.ang3l.see.classes.Voter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnJumper;

    private TextView txtEmail;
    private TextView txtPassword;
    private TextView txtRegister;

    private TextInputLayout txtlayEmail;
    private TextInputLayout txtlayPassword;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();

        txtRegister = findViewById(R.id.txt_register);
        txtRegister.setOnClickListener(view -> { // we'll pass to CreateAccountActivity through intent
            startActivity(new Intent(this, CreateAccountActivity.class));
        });

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> {
            onClickedLogin(view);
        });

        btnJumper = findViewById(R.id.btn_jumper);
//        btnJumper.setVisibility(View.GONE);
        btnJumper.setOnClickListener(view -> { // pasamos a una actividad a probar
            startActivity(new Intent(this, BoletaActivity.class));
        });
    }

    private void initWidgets() {
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);

        txtlayEmail = findViewById(R.id.txtlay_email);
        txtlayPassword = findViewById(R.id.txtlay_password);

        builder = new AlertDialog.Builder(this);
    }

    // llamada si se intenta iniciar sesion
    private void onClickedLogin(View view) {
        boolean allFilledFields = true;

        if (txtEmail.getText().toString().isEmpty()) {
            txtlayEmail.setError(getString(R.string.error_field_required_email));
            allFilledFields = false;
        } else txtlayEmail.setErrorEnabled(false);

        if (txtPassword.getText().toString().isEmpty()) {
            txtlayPassword.setError(getString(R.string.error_field_required_password));
            allFilledFields = false;
        } else txtlayPassword.setErrorEnabled(false);

        if (allFilledFields) askDBvalidUser();
        else Toast.makeText(this, "Campos vacios", Toast.LENGTH_LONG).show();
    }

    // we need to pass email to other activity
    private void askDBvalidUser() {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("login.php"),
                response -> { // obtengo la respuesta del servidor
                    try {
                        JSONArray array = new JSONArray(response);
                        JSONObject object = array.getJSONObject(0);
                        String success = object.getString("success");
                        if (success.contains("true")) {
                            Intent intent = new Intent(this, ChooseRoleActivity.class);
                            intent.putExtra("EMAIL", object.getString("email"));
                            Voter.init(object.getString("email"), object.getString("nombre"));
                            startActivity(intent);
                        } else if (success.contains("false")) {
                            builder.setTitle("Usuario no registrado");
                            builder.setMessage("Por favor, cree una cuenta");
                            builder.show();
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
            @Override // le pasamos los datos del celular medianto un HashMap
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", txtEmail.getText().toString().trim());
                params.put("password", txtPassword.getText().toString());
                return params;
            }
        };

        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
