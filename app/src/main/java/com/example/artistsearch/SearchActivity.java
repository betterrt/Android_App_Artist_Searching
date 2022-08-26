package com.example.artistsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class SearchActivity extends AppCompatActivity implements ArtistSections.CardClickListener{
    public static String TAG = "jrt";
    private List<Artist> artistList;
    private String searchContent;
    private SectionedRecyclerViewAdapter sectionedAdapter;
    private RecyclerView recyclerView;
    //    public String baseurl = "http://10.0.2.2:8080";
    public String baseurl = "https://ruitaoji9832.wl.r.appspot.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        searchContent = intent.getStringExtra("searchQuery");

        // Handle Toolbar
        Toolbar myToolbar = findViewById(R.id.my_search_toolbar);
        myToolbar.setTitle(searchContent.toUpperCase(Locale.ROOT));
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        searchArtist();

    }

    /**
     *  Search the artists according to the searchContent
     */
    private void searchArtist() {
        ProgressBar progress = findViewById(R.id.search_progress);
        TextView loading = findViewById(R.id.search_loading);
        progress.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        artistList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchUrl = baseurl + "/search?search_name=" + searchContent;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONObject("_embedded").getJSONArray("results");
                            // Filter the response
                            for (int i = 0; i < array.length(); i++) {
                                if (!array.getJSONObject(i).getString("og_type").equals("artist")) {
                                    continue;
                                }
                                String title = array.getJSONObject(i).getString("title");
                                String img = array.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                                String id = array.getJSONObject(i).getJSONObject("_links").getJSONObject("self").getString("href");
                                artistList.add(new Artist(title, img, id));
                            }
                            if (artistList.size() == 0) {
                                CardView cardView = findViewById(R.id.card_no_result);
                                cardView.setVisibility(View.VISIBLE);
                            }
                            progress.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            sectionedAdapter = new SectionedRecyclerViewAdapter();
                            sectionedAdapter.addSection(new ArtistSections(SearchActivity.this, artistList,SearchActivity.this));
                            recyclerView = findViewById(R.id.my_second_recyclerview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                            recyclerView.setAdapter(sectionedAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse");
            }
        });
        queue.add(stringRequest);

    }

    /**
     * Onclick function for artist cards
     */
    @Override
    public void onItemRootViewClicked(@NonNull ArtistSections artistSections, int adapterPosition, String artistName, String artistId) {
        Intent intent = new Intent(SearchActivity.this, ShowDetailActivity.class);
        // Pass the selected artist's name and ID to the third activity
        intent.putExtra("artistName", artistName);
        intent.putExtra("artistId", artistId);
        startActivity(intent);
    }

    /**
     * Handle up button in the action bar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}