package com.example.artistsearch;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ShowDetailActivity extends AppCompatActivity {
    public String TAG = "jrt";
    private TabLayout tabLayout;
    private String artistId;
    private String name;
    private String nationality;
    private String birthday;
    private List<Artist> artworkList;
    String artistName;
//    public String baseurl = "http://10.0.2.2:8080";
    public String baseurl = "https://ruitaoji9832.wl.r.appspot.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Intent intent = getIntent();
        artistName = intent.getStringExtra("artistName");
        artistId = intent.getStringExtra("artistId");
        // Handle Toolbar
        Toolbar myToolbar = findViewById(R.id.my_detail_toolbar);
        myToolbar.setTitle(artistName);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Handle the tab
        ViewPager viewPager = findViewById(R.id.viewpager);
        searchAndCreateViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void searchAndCreateViewPager(ViewPager viewPager) {
        ProgressBar progress = findViewById(R.id.detail_progress);
        TextView loading = findViewById(R.id.detail_loading);
        progress.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        artworkList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchUrl = baseurl + "/artists_artworks?id=" + artistId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject artistObject = jsonObject.getJSONObject("artist");
                            JSONObject artworkObject = jsonObject.getJSONObject("artwork");
                            JSONArray array = artworkObject.getJSONObject("_embedded").getJSONArray("artworks");
                            name = artistObject.getString("name");
                            nationality = artistObject.getString("nationality");
                            birthday = artistObject.getString("birthday");
                            String deathday = artistObject.getString("deathday");
                            String biography = artistObject.getString("biography");
                            if(array.length() != 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    String artworkTitle = array.getJSONObject(i).getString("title");
                                    String img = array.getJSONObject(i).getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                                    String artworkId = array.getJSONObject(i).getString("id");
                                    artworkList.add(new Artist(artworkTitle, img, artworkId));
                                }
                            }
                            progress.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                            adapter.addFragment(new FragmentDetail(name, nationality, birthday, deathday, biography), "Details");
                            adapter.addFragment(new FragmentArtwork(artworkList), "Artwork");
                            viewPager.setAdapter(adapter);
                            createTabIcons();
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
     * Set the tab text and icon
     */
    private void createTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("DETAILS");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.info_icon, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("ARTWORK");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.artwork_icon, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }


    /**
     * Adjust the icon of add-favorite button
     */
    private boolean isArtistFavorite(){
        SharedPreferences pref = getSharedPreferences("ARTIST", Context.MODE_PRIVATE);
        if(pref.getAll().keySet().contains(artistName)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_two, menu);
        MenuItem menuItem = menu.findItem(R.id.action_add_favorite);
        if (isArtistFavorite()) {
            menuItem.setIcon(R.drawable.star_solid);
        } else {
            menuItem.setIcon(R.drawable.star_border);
        }
        return true;
    }

    /**
     * Handle up button and add-favorite button
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_add_favorite) {
            SharedPreferences pref = getSharedPreferences("ARTIST", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            if (!isArtistFavorite()) {
                Toast.makeText(this, name + " is added to favorites",
                        Toast.LENGTH_LONG).show();
                item.setIcon(R.drawable.star_solid);
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("name", name);
                    jsonObj.put("nationality", nationality);
                    jsonObj.put("birthday", birthday);
                    jsonObj.put("id", artistId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.putString(name, jsonObj.toString()).commit();
            } else {
                Toast.makeText(this, name + " is removed from favorites",
                        Toast.LENGTH_LONG).show();
                item.setIcon(R.drawable.star_border);
                editor.remove(name).commit();
            }
        }
        return false;
    }

}

