package com.example.artistsearch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtworkItemViewHolder extends RecyclerView.ViewHolder {
    final View view;
    final TextView textView;
    final ImageView imageView;
    public ArtworkItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        textView = itemView.findViewById(R.id.artwork_card_text);
        imageView = itemView.findViewById(R.id.artwork_card_img);
    }
}
