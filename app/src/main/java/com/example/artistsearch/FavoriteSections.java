package com.example.artistsearch;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;


public class FavoriteSections extends Section {
    // Here should be Artist obj
    private final List<Artist> artistList;

    public FavoriteSections(@NonNull final List<Artist> list) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.favorite_item)
                .footerResourceId(R.layout.home_footer)
                .build());
        this.artistList = list;
    }

    @Override
    public int getContentItemsTotal() {
        return artistList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final Artist artist = artistList.get(position);
        itemHolder.nameView.setText(artist.name);
        itemHolder.nationalityView.setText(artist.nationality);
        itemHolder.birthdayView.setText(artist.birthday);
        itemHolder.buttonView.setTag(new Artist(artist.name, artist.id));
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        final FooterViewHolder footerHolder = (FooterViewHolder) holder;

    }

}
