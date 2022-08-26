package com.example.artistsearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

//public class ArtworkSections extends Section {
//
//    private final Fragment context;
//    private final List<Artist> artworkList;
//    private final ArtworkSections.CardClickListener clickListener;
//    public ArtworkSections(@NonNull Fragment context, @NonNull final List<Artist> list,
//                           @NonNull final ArtworkSections.CardClickListener clickListener) {
//        super(SectionParameters.builder()
//                .itemResourceId(R.layout.artist_item)
//                .build());
//        this.context = context;
//        this.artworkList = list;
//        this.clickListener = clickListener;
//
//    }
//
//    @Override
//    public int getContentItemsTotal() {
//        return artworkList.size();
//    }
//
//    @Override
//    public RecyclerView.ViewHolder getItemViewHolder(View view) {
//        //View itemView= LayoutInflater.from(view.getContext()).inflate(R.layout.)
//        return new ArtworkItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        final ArtworkItemViewHolder artworkItemViewHolder = (ArtworkItemViewHolder) holder;
//        final Artist artwork = artworkList.get(position);
//        // Set the card's title and image
//        artworkItemViewHolder.textView.setText(artwork.name);
//        // Use Glide to load the images
//        Glide.with(context).load(artwork.img).into(artworkItemViewHolder.imageView);
//        artworkItemViewHolder.view.setOnClickListener(v ->
//                clickListener.onItemRootViewClicked(this, artworkItemViewHolder.getAdapterPosition(), artwork.id)
//        );
//    }
//
//    interface CardClickListener {
//        void onItemRootViewClicked(@NonNull ArtworkSections artworkSections, int adapterPosition, String artworkId);
//    }
//}

public class ArtworkSections extends RecyclerView.Adapter<ArtworkItemViewHolder> {
    private View view;
    private final List<Artist> artworkList;
    private final ArtworkSections.CardClickListener clickListener;
    public ArtworkSections(@NonNull View view, @NonNull final List<Artist> list,
                           @NonNull final ArtworkSections.CardClickListener clickListener) {
        this.view = view;
        this.artworkList = list;
        this.clickListener = clickListener;
        // If no artwork
        if(artworkList.size() == 0){
            view.findViewById(R.id.text_no_artwork).setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public ArtworkItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_item,parent,false);
        return new ArtworkItemViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkItemViewHolder viewHolder, int position) {
        final ArtworkItemViewHolder holder =  viewHolder;
        final Artist artwork = artworkList.get(position);
        // Set the card's title and image
        holder.textView.setText(artwork.name);
        // Use Glide to load the images
        Glide.with(holder.imageView.getContext()).load(artwork.img).into(holder.imageView);
        holder.view.setOnClickListener(v ->
                clickListener.onItemRootViewClicked(this, holder.getAdapterPosition(), artwork.id)
        );
    }

    interface CardClickListener {
        void onItemRootViewClicked(@NonNull ArtworkSections artworkSections, int adapterPosition, String artworkId);
    }
}


