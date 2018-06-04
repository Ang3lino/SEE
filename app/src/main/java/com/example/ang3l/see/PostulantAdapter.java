package com.example.ang3l.see;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ang3l.see.items.PostulantItem;

import java.util.ArrayList;

public class PostulantAdapter extends RecyclerView.Adapter<PostulantAdapter.ViewHolder> {
    private ArrayList<PostulantItem> postulantItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email;
        public ImageView imageId;

        public ViewHolder(View view) {
            super(view);
            imageId = view.findViewById(R.id.img_postulant_profile);
            name = view.findViewById(R.id.txt_postulant_name);
            email = view.findViewById(R.id.txt_postulant_email);
        }
    }

    public PostulantAdapter(ArrayList<PostulantItem> list) {
        postulantItems = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.postulant_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostulantItem current = postulantItems.get(position);
        holder.imageId.setImageResource(current.getImageId());
        holder.name.setText(current.getName());
        holder.email.setText(current.getEmail());
    }

    @Override
    public int getItemCount() {
        return postulantItems.size();
    }
}
