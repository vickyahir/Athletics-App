package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeAthlete {

	@SerializedName("country")
	private String country;

	@SerializedName("category_id")
	private List<String> categoryId;

	@SerializedName("sports")
	private List<String> sports;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("profile_video")
	private String profileVideo;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private List<HomeAthletePositionItem> position;

	@SerializedName("country_id")
	private int countryId;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCategoryId(List<String> categoryId){
		this.categoryId = categoryId;
	}

	public List<String> getCategoryId(){
		return categoryId;
	}

	public void setSports(List<String> sports){
		this.sports = sports;
	}

	public List<String> getSports(){
		return sports;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setProfileVideo(String profileVideo){
		this.profileVideo = profileVideo;
	}

	public String getProfileVideo(){
		return profileVideo;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPosition(List<HomeAthletePositionItem> position){
		this.position = position;
	}

	public List<HomeAthletePositionItem> getPosition(){
		return position;
	}

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}
}