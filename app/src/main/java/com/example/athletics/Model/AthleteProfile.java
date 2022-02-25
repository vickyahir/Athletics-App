package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthleteProfile {

    @SerializedName("country")
    private String country;

    @SerializedName("references")
    private List<Object> references;

    @SerializedName("gender")
    private String gender;

    @SerializedName("year")
    private int year;

    @SerializedName("university")
    private AthleteProfileUniversity university;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("speed")
    private String speed;

    @SerializedName("category_id")
    private List<String> categoryId;

    @SerializedName("major")
    private String major;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("school")
    private String school;

    @SerializedName("gpa")
    private String gpa;

    @SerializedName("id")
    private int id;

    @SerializedName("state_id")
    private int stateId;

    @SerializedName("state")
    private List<AthleteProfileStateItem> state;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("height")
    private double height;

    @SerializedName("sports")
    private List<String> sports;

    @SerializedName("community_services")
    private List<String> communityServices;

    @SerializedName("profile_video")
    private String profileVideo;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("weight")
    private int weight;

    @SerializedName("team")
    private String team;

    @SerializedName("year_complete")
    private int yearComplete;

    @SerializedName("playing_weight")
    private int playingWeight;

    @SerializedName("followers")
    private List<String> followers;

    @SerializedName("university_id")
    private int universityId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("position")
    private List<AthleteProfilePositionItem> position;

    @SerializedName("is_following")
    private boolean isFollowing;

    @SerializedName("country_id")
    private int countryId;

    @SerializedName("age")
    private int age;

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setReferences(List<Object> references) {
        this.references = references;
    }

    public List<Object> getReferences() {
        return references;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setUniversity(AthleteProfileUniversity university) {
        this.university = university;
    }

    public AthleteProfileUniversity getUniversity() {
        return university;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getSpeed() {
        return speed;
    }

    public void setCategoryId(List<String> categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getCategoryId() {
        return categoryId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getGpa() {
        return gpa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setState(List<AthleteProfileStateItem> state) {
        this.state = state;
    }

    public List<AthleteProfileStateItem> getState() {
        return state;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setCommunityServices(List<String> communityServices) {
        this.communityServices = communityServices;
    }

    public List<String> getCommunityServices() {
        return communityServices;
    }

    public void setProfileVideo(String profileVideo) {
        this.profileVideo = profileVideo;
    }

    public String getProfileVideo() {
        return profileVideo;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public void setYearComplete(int yearComplete) {
        this.yearComplete = yearComplete;
    }

    public int getYearComplete() {
        return yearComplete;
    }

    public void setPlayingWeight(int playingWeight) {
        this.playingWeight = playingWeight;
    }

    public int getPlayingWeight() {
        return playingWeight;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setPosition(List<AthleteProfilePositionItem> position) {
        this.position = position;
    }

    public List<AthleteProfilePositionItem> getPosition() {
        return position;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public boolean isIsFollowing() {
        return isFollowing;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}