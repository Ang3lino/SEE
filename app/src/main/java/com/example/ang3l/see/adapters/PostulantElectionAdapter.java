package com.example.ang3l.see.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ang3l.see.R;
import com.example.ang3l.see.items.PostulantElectionItem;
import com.example.ang3l.see.items.PostulantItem;

import java.util.ArrayList;

public class PostulantElectionAdapter
        extends RecyclerView.Adapter<PostulantElectionAdapter.PostulantViewHolder> {
    private Context context;
    private ArrayList<PostulantElectionItem> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // La clase interna debe ser publica
    public class PostulantViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView match;
        public TextView email;
        public ImageView profile;

        public PostulantViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_candidate_election_name);
            match = itemView.findViewById(R.id.txt_match_election);
            email = itemView.findViewById(R.id.txt_email_candidate_election);
            profile = itemView.findViewById(R.id.img_candidate_election);

            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    public PostulantElectionAdapter(Context context, ArrayList<PostulantElectionItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PostulantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.postulant_election_item, parent, false);
        return new PostulantViewHolder(v);
    }

    /**
     * Metodo que sera llamado cada que una vista entre a la pantalla
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PostulantViewHolder holder, int position) {
        PostulantElectionItem current = list.get(position);
        String name = current.getName();
        String match = current.getMatch();
        String email = current.getEmail();

        holder.name.setText(name);
        holder.match.setText(match);
        holder.email.setText(email);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
