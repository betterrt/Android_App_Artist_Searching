package com.example.artistsearch;

public class Artist {
    // name, img, id can be used both on artist and artwork
    public String name;
    public String img;
    public String id;
    public String birthday;
    public String deathday;
    public String nationality;
    public String biography;


    /**
     * Used for items in artistList and artworkList
     */
    public Artist(String name, String img, String id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    /**
     * Used for items in favorites list
     */
    public Artist(String name, String birthday, String nationality, String id) {
        this.name = name;
        this.birthday = birthday;
        this.nationality = nationality;
        this.id = id;
    }

    /**
     * Used for storing information in favorite chevron button tag
     */
    public Artist(String name, String id) {
        this.name = name;
        this.id = id;
    }



}
