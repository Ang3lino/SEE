package com.example.ang3l.see.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.ang3l.see.R;
import com.example.ang3l.see.classes.VolleyHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddPostulantDialog extends AppCompatDialogFragment {
    private TextView txtEmail;
    private AddPostulantDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_postulant_email_dialog, null);

        builder.setView(view)
                .setTitle("Agregar candidato")
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("ok", (dialog, which) -> {
                    listener.applyText(txtEmail.getText().toString());
                });
        txtEmail = view.findViewById(R.id.txt_add_candidate_email_dialog);
        return builder.create();
    }

//    private boolean isCandidateInDB() {
//        String toFind = txtEmail.getText().toString();
//        StringRequest request = new StringRequest(
//                Request.Method.POST,
//                VolleyHelper.getHostUrl("login.php"),
//                response -> { // obtengo la respuesta del servidor
//                    try {
//                        JSONArray array = new JSONArray(response);
//                        JSONObject object = array.getJSONObject(0);
//                        String success = object.getString("success");
//                        if (success.contains("true")) {
//                            Toast.makeText(getApplicationContext(), "Acceptado", Toast.LENGTH_LONG).show();
//                            accepted[0] = true;
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Rechazado", Toast.LENGTH_LONG).show();
//                            accepted[0] = false;
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                },
//                error -> { // si el servidor esta apagado nos vamos aqui
//                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//                }
//        ) {
//            @Override // le pasamos los datos del celular medianto un HashMap
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("email", txtEmail.getText().toString().trim());
//                params.put("password", txtPassword.getText().toString());
//                return params;
//            }
//        };
//        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(request);
//        return accepted[0];
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddPostulantDialogListener) context;
        } catch (ClassCastException exc) {
            throw new ClassCastException(context.toString() +
                " must implement AddPostulantDialogListener");
        }
    }

    /**
     * Interfaz interna necesaria para comunicara datos a otra actividad, esta tiene que implementarla
     */
    public interface AddPostulantDialogListener {
        void applyText(String email);
    }
}
