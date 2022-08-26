package com.example.artistsearch;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentArtwork extends Fragment implements ArtworkSections.CardClickListener{
    public static String TAG = "jrt";
    LinearLayout inflate;
    private List<Artist> artworkList;
    private RecyclerView recyclerView;
    private View view;
//    public String baseurl = "http://10.0.2.2:8080";
     public String baseurl = "https://ruitaoji9832.wl.r.appspot.com";
    public FragmentArtwork(List<Artist> artworkList) {
        this.artworkList = artworkList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artwork, container, false);
        recyclerView = view.findViewById(R.id.my_third_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ArtworkSections(view, artworkList,this));
        return view;
    }


    /**
     * Onclick function for artwork cards
     */

    @Override
    public void onItemRootViewClicked(@NonNull ArtworkSections artworkSections, int adapterPosition, String artworkId) {
        artworkList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        String searchUrl = baseurl + "/genes?id=" + artworkId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String name;
                        String img;
                        String description;
                        try {
                            Log.e(TAG, "onResponse: hasres");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONObject("_embedded").getJSONArray("genes");
                            if (array.length() == 0) {
                                name = null;
                                img = null;
                                description = null;
                            } else {
                                name = array.getJSONObject(0).getString("name");
                                img = array.getJSONObject(0).getJSONObject("_links").getJSONObject("thumbnail").getString("href");
                                description = array.getJSONObject(0).getString("description");
                            }
                            // Get inflate of dialog or we can't get the view in it
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Dialog dialog = new Dialog(requireContext());
                                    inflate = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog, null);
                                    dialog.setContentView(inflate);
                                    if(name == null) {
                                        inflate.findViewById(R.id.text_no_category).setVisibility(View.VISIBLE);
                                        dialog.show();
                                        return;
                                    }
                                    TextView nameView = inflate.findViewById(R.id.dialog_name);
                                    TextView descriptionView = inflate.findViewById(R.id.dialog_description_content);
                                    ImageView imgView = inflate.findViewById(R.id.dialog_img);
                                    nameView.setText(name);
                                    Glide.with(requireContext()).load(img).into(imgView);
                                    descriptionView.setText(description);
                                    inflate.findViewById(R.id.dialog_view).setVisibility(View.VISIBLE);
                                    dialog.show();
                                }
                            });

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

}