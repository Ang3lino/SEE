package com.example.ang3l.see.dialogs;

import android.app.Dialog;
import android.app.TaskStackBuilder;
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
import com.example.ang3l.see.classes.VotingRoom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPostulantDialog extends AppCompatDialogFragment {
    private TextView txtEmail;
    private AddPostulantDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_postulant_email_dialog, null);

        txtEmail = view.findViewById(R.id.txt_add_candidate_email_dialog);
        builder.setView(view)
                .setTitle("Agregar candidato")
                .setNegativeButton("cancel", (dialog, which) -> { })
                .setPositiveButton("ok", (dialog, which) -> {
                    StringRequest request = new StringRequest(
                            Request.Method.POST,
                            VolleyHelper.getHostUrl("candidate_registered.php"),
                            response -> { // obtengo la respuesta del servidor
                                try {
                                    JSONArray array = new JSONArray(response);
                                    JSONObject object = array.getJSONObject(0);
                                    String exists = object.getString("exists");
                                    if (exists.contains("true")) {
                                        String inserted = object.getString("inserted");
                                        listener.applyText(object.getString("email"), object.getString("name"));
                                    } else
                                        listener.applyText("", "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            },
                            error -> { error.printStackTrace(); }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", txtEmail.getText().toString());
                            params.put("number", "" + VotingRoom.get().getNumber());
                            return params;
                        }
                    };
                    VolleyHelper.getInstance(getActivity()).addToRequestQueue(request);
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddPostulantDialogListener) context;
        } catch (ClassCastException exc) {
            throw new ClassCastException(context.toString() + " must implement AddPostulantDialogListener");
        }
    }

    /**
     * Interfaz interna necesaria para comunicara datos a otra actividad, esta tiene que implementarla
     */
    public interface AddPostulantDialogListener {
        void applyText(String email, String name);
    }
}
