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

import com.example.ang3l.see.R;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddPostulantDialogListener) context;
        } catch (ClassCastException exc) {
            throw new ClassCastException(context.toString() +
                "must implement AddPostulantDialogListener");
        }
    }

    public interface AddPostulantDialogListener {
        void applyText(String email);
    }
}
