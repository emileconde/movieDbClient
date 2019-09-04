package com.example.android.conde.com.cinephile.models;

import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("id")
    private int id;
    @SerializedName("birthday")
    private String birthDay;
    @SerializedName("name")
    private String name;
    @SerializedName("place_of_birth")
    private String birthPlace;
    @SerializedName("biography")
    private String bio;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("known_for_department")
    private String occupation;


    public Profile(int id, String birthDay, String name, String birthPlace, String bio, String profilePath, String occupation) {
        this.id = id;
        this.birthDay = birthDay;
        this.name = name;
        this.birthPlace = birthPlace;
        this.bio = bio;
        this.profilePath = profilePath;
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getId() {
        return id;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
