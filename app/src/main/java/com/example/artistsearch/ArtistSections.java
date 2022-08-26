package com.example.artistsearch;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class ArtistSections extends Section {

    private final Activity context;
    private final List<Artist> artistList;
    private final ArtistSections.CardClickListener clickListener;
    public ArtistSections(@NonNull Activity context, @NonNull final List<Artist> list,
                          @NonNull final ArtistSections.CardClickListener clickListener) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.artist_item)
                .build());
        this.context = context;
        this.artistList = list;
        this.clickListener = clickListener;

    }

    @Override
    public int getContentItemsTotal() {
        return artistList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ArtistItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ArtistItemViewHolder artistItemViewHolder = (ArtistItemViewHolder) holder;
        final Artist artist = artistList.get(position);
        // Set the card's title and image
        artistItemViewHolder.textView.setText(artist.name);
        if(!artist.img.equals("/assets/shared/missing_image.png")) {
            // Use Glide to load the images
            Glide.with(context).load(artist.img).into(artistItemViewHolder.imageView);
        }
        artistItemViewHolder.view.setOnClickListener(v ->
                clickListener.onItemRootViewClicked(this, artistItemViewHolder.getAdapterPosition(), artist.name, artist.id)
        );
    }

    interface CardClickListener {
        void onItemRootViewClicked(@NonNull ArtistSections artistSections, int adapterPosition, String artistName, String artistId);
    }
}
