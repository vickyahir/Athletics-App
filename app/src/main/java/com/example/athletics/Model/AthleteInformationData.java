package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteInformationData {

	@SerializedName("country")
	private String country;

	@SerializedName("references")
	private List<Object> references;

	@SerializedName("gender")
	private String gender;

	@SerializedName("year")
	private String year;

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
	private String stateId;

	@SerializedName("state")
	private List<AthleteInformationStateItem> state;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("height")
	private String height;

	@SerializedName("sports")
	private List<String> sports;

	@SerializedName("community_services")
	private List<String> communityServices;

	@SerializedName("profile_video")
	private String profileVideo;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("weight")
	private String weight;

	@SerializedName("team")
	private String team;

	@SerializedName("year_complete")
	private String yearComplete;

	@SerializedName("playing_weight")
	private String playingWeight;

	@SerializedName("followers")
	private List<String> followers;

	@SerializedName("university_id")
	private String universityId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("position")
	private List<AthleteInformationPositionItem> position;

	@SerializedName("country_id")
	private String countryId;

	@SerializedName("age")
	private String age;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setReferences(List<Object> references){
		this.references = references;
	}

	public List<Object> getReferences(){
		return references;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setYear(String year){
		this.year = year;
	}

	public String getYear(){
		return year;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setSpeed(String speed){
		this.speed = speed;
	}

	public String getSpeed(){
		return speed;
	}

	public void setCategoryId(List<String> categoryId){
		this.categoryId = categoryId;
	}

	public List<String> getCategoryId(){
		return categoryId;
	}

	public void setMajor(String major){
		this.major = major;
	}

	public String getMajor(){
		return major;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSchool(String school){
		this.school = school;
	}

	public String getSchool(){
		return school;
	}

	public void setGpa(String gpa){
		this.gpa = gpa;
	}

	public String getGpa(){
		return gpa;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStateId(String stateId){
		this.stateId = stateId;
	}

	public String getStateId(){
		return stateId;
	}

	public void setState(List<AthleteInformationStateItem> state){
		this.state = state;
	}

	public List<AthleteInformationStateItem> getState(){
		return state;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setHeight(String height){
		this.height = height;
	}

	public String getHeight(){
		return height;
	}

	public void setSports(List<String> sports){
		this.sports = sports;
	}

	public List<String> getSports(){
		return sports;
	}

	public void setCommunityServices(List<String> communityServices){
		this.communityServices = communityServices;
	}

	public List<String> getCommunityServices(){
		return communityServices;
	}

	public void setProfileVideo(String profileVideo){
		this.profileVideo = profileVideo;
	}

	public String getProfileVideo(){
		return profileVideo;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setWeight(String weight){
		this.weight = weight;
	}

	public String getWeight(){
		return weight;
	}

	public void setTeam(String team){
		this.team = team;
	}

	public String getTeam(){
		return team;
	}

	public void setYearComplete(String yearComplete){
		this.yearComplete = yearComplete;
	}

	public String getYearComplete(){
		return yearComplete;
	}

	public void setPlayingWeight(String playingWeight){
		this.playingWeight = playingWeight;
	}

	public String getPlayingWeight(){
		return playingWeight;
	}

	public void setFollowers(List<String> followers){
		this.followers = followers;
	}

	public List<String> getFollowers(){
		return followers;
	}

	public void setUniversityId(String universityId){
		this.universityId = universityId;
	}

	public String getUniversityId(){
		return universityId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setPosition(List<AthleteInformationPositionItem> position){
		this.position = position;
	}

	public List<AthleteInformationPositionItem> getPosition(){
		return position;
	}

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}
}