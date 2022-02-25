package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageDetailData {

    @SerializedName("athlete")
    private List<PackageDetailAthleteItem> athlete;

    @SerializedName("school")
    private List<PackageDetailAthleteItem> school;

    @SerializedName("visitor")
    private List<PackageDetailAthleteItem> visitor;


    public List<PackageDetailAthleteItem> getAthlete() {
        return athlete;
    }

    public void setAthlete(List<PackageDetailAthleteItem> athlete) {
        this.athlete = athlete;
    }

    public List<PackageDetailAthleteItem> getSchool() {
        return school;
    }

    public void setSchool(List<PackageDetailAthleteItem> school) {
        this.school = school;
    }

    public List<PackageDetailAthleteItem> getVisitor() {
        return visitor;
    }

    public void setVisitor(List<PackageDetailAthleteItem> visitor) {
        this.visitor = visitor;
    }
}