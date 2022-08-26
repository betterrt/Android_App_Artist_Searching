package com.example.artistsearch;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentDetail extends Fragment {

    private String name;
    private String nationality;
    private String birthday;
    private String deathday;
    private String biography;
    private static final String EMPTY = "";
    private static final String NULL = "null";
    public FragmentDetail(String name, String nationality, String birthday, String deathday, String biography) {
        this.name = name;
        this.nationality = nationality;
        this.birthday = birthday;
        this.deathday = deathday;
        this.biography = biography;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView nameView = view.findViewById(R.id.detail_name_content);
        TextView nationalityView = view.findViewById(R.id.detail_nationality_content);
        TextView birthdayView = view.findViewById(R.id.detail_birthday_content);
        TextView deathdayView = view.findViewById(R.id.detail_deathday_content);
        TextView biographyView = view.findViewById(R.id.detail_biography_content);
        // name can not be empty
        nameView.setText(name);
        if (!nationality.equals(EMPTY) && !nationality.equals(NULL)) {
            nationalityView.setText(nationality);
            nationalityView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_nationality).setVisibility(View.VISIBLE);
        }
        if (!birthday.equals(EMPTY) && !birthday.equals(NULL)) {
            birthdayView.setText(birthday);
            birthdayView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_birthday).setVisibility(View.VISIBLE);
        }
        if (!deathday.equals(EMPTY) && !deathday.equals(NULL)) {
            deathdayView.setText(deathday);
            deathdayView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_deathday).setVisibility(View.VISIBLE);
        }
        if (!biography.equals(EMPTY) && !biography.equals(NULL)) {
            biographyView.setText(biography);
            biographyView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.detail_biography).setVisibility(View.VISIBLE);
        }

        return view;
    }

}