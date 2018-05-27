package com.example.ang3l.see;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ang3l.see.classes.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText txtUsername,
            txtLocation,
            txtEmail,
            txtPassword,
            txtConfirmPassword;
    //private EditText txtCurp, txtNationality;
    private TextInputLayout inputLayoutUsername,
            inputLayoutLocation,
            inputLayoutEmail,
            inputLayoutPassword,
            inputLayoutConfirmPassword;
    //private TextInputLayout inputLayoutNationality, inputLayoutCurp;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        btnRegister = findViewById(R.id.btn_register_create_account);
        initWidgets();
        btnRegister.setOnClickListener(view -> onRegisterClicked(view));
    }

    private void initWidgets() {
        builder = new AlertDialog.Builder(this);
        txtUsername = (EditText) findViewById(R.id.username);
        //txtNationality = (EditText) findViewById(R.id.nationality);
        //txtCurp = (EditText) findViewById(R.id.curp);
        txtLocation = (EditText) findViewById(R.id.location);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);
        txtConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        inputLayoutUsername = findViewById(R.id.text_layout_username);
        //inputLayoutNationality = findViewById(R.id.text_layout_nationality);
        //inputLayoutCurp = findViewById(R.id.text_layout_curp);
        inputLayoutLocation = findViewById(R.id.text_layout_location);
        inputLayoutEmail = findViewById(R.id.text_layout_email);
        inputLayoutPassword = findViewById(R.id.text_layout_password);
        inputLayoutConfirmPassword = findViewById(R.id.text_layout_confirm_password);
    }

    private void onRegisterClicked(View view) {
        boolean isValid = true;
        if (txtUsername.getText().toString().isEmpty()) {
            // username layout estara en edo. de error hasta que se diga lo contrario explicitamente con setErrorEnabled
            inputLayoutUsername.setError(getString(R.string.error_field_required_username));
            isValid = false;
        } else inputLayoutUsername.setErrorEnabled(false);

        if (txtLocation.getText().toString().isEmpty()) {
            inputLayoutLocation.setError(getString(R.string.error_field_required_location));
            isValid = false;
        } else inputLayoutLocation.setErrorEnabled(false);

        if (!txtEmail.getText().toString().contains("@")) {
            inputLayoutEmail.setError(getString(R.string.error_field_required_email));
            isValid = false;
        } else inputLayoutEmail.setErrorEnabled(false);

        if (txtPassword.getText().toString().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.error_field_required_password));
            isValid = false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
            if (!txtConfirmPassword.getText().toString().equals(txtPassword.getText().toString())) {
                inputLayoutPassword.setError(getString(R.string.error_field_required_confirm_password));
                isValid = false;
            } else inputLayoutConfirmPassword.setErrorEnabled(false);
        }

        if (isValid) {
            //Toast.makeText(this, "Todo bien", Toast.LENGTH_SHORT).show();
            insertIntoDatabase();
        } else {
            Toast.makeText(this, "Existen campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    TextView test;

    private void insertIntoDatabase() {
        test = findViewById(R.id.txt_test);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                VolleyHelper.getHostUrl("create_account.php"),
            response -> {
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = array.getJSONObject(0);
                    String success = object.getString("success");
                    if (success.contains("true")) {
                        builder.setTitle(R.string.welcome);
                        builder.setMessage("Gracias por unirse a nuestra comunidad");
                    } else if (success.contains("false")) {
                        builder.setTitle(R.string.repeated_user_title);
                        builder.setMessage(R.string.repeate_user_message);
                    }
                    builder.show(); // esto tambien se muestra explicitamente, no lo olvides
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                test.setText(response.toString());
            },
                error -> { // si el servidor esta apagado nos vamos aqui
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", txtUsername.getText().toString());
                params.put("location", txtLocation.getText().toString());
                params.put("email", txtEmail.getText().toString().trim());
                params.put("password", txtPassword.getText().toString());
                return params;
            }
        };
        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

}
