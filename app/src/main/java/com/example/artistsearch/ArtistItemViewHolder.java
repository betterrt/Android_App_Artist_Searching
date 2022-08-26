package com.example.artistsearch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtistItemViewHolder extends RecyclerView.ViewHolder {
    final View view;
    final TextView textView;
    final ImageView imageView;
    public ArtistItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        textView = itemView.findViewById(R.id.artist_card_title);
        imageView = itemView.findViewById(R.id.artist_card_img);
    }
}
