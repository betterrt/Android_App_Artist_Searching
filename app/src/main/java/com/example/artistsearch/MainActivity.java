package com.example.artistsearch;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "jrt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ArtistSearch);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar myToolbar = findViewById(R.id.my_home_toolbar);
        setSupportActionBar(myToolbar);
        findViewById(R.id.favorite_header).setVisibility(View.VISIBLE);
        myToolbar.setVisibility(View.VISIBLE);
        // Date
        TextView textView = findViewById(R.id.date_text);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        String currentDate = simpleDateFormat.format(new Date());
        textView.setText(currentDate);
        // Favorite artists
        setRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    /**
     * Get the list of user favorite artists from SharedPreference and render them to recyclerview
     */
    private void setRecyclerView() {
        List<Artist> favoriteArtistsList = new ArrayList<>();
        SharedPreferences pref = getSharedPreferences("ARTIST", Context.MODE_PRIVATE);
        for (String key : pref.getAll().keySet()) {
            String jsonString = pref.getString(key, null);
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String name = jsonObject.getString("name");
                String nationality = jsonObject.getString("nationality");
                String birthday = jsonObject.getString("birthday");
                String artistId = jsonObject.getString("id");
                favoriteArtistsList.add(new Artist(name, birthday, nationality, artistId));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SectionedRecyclerViewAdapter sectionedAdapter = new SectionedRecyclerViewAdapter();
        sectionedAdapter.addSection(new FavoriteSections(favoriteArtistsList));
        RecyclerView recyclerView = findViewById(R.id.my_first_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(sectionedAdapter);
    }

    /**
     * link to artsy homepage
     */
    public void toArtsy(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.artsy.net/"));
        startActivity(browserIntent);
    }

    /**
     * Redirect to search result activity and show corresponding artist
     */
    public void toFavorite(View view) {
        ImageButton imageButton = view.findViewById(R.id.favorite_button);
        Intent intent = new Intent(MainActivity.this, ShowDetailActivity.class);
        Artist artistClicked = (Artist) imageButton.getTag();
        intent.putExtra("artistName", artistClicked.name);
        intent.putExtra("artistId", artistClicked.id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_one, menu);
        // Get search query and send it
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("searchQuery", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

}