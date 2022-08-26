package com.example.artistsearch;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    final View view;
    final TextView nameView;
    final TextView nationalityView;
    final TextView birthdayView;
    // Use to store the id information of artist through button tag
    final ImageButton buttonView;
//    final TextView tvDate;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        nameView = itemView.findViewById(R.id.favorite_name);
        nationalityView = itemView.findViewById(R.id.favorite_nationality);
        birthdayView = itemView.findViewById(R.id.favorite_birthday);
        buttonView = itemView.findViewById(R.id.favorite_button);
    }
}
