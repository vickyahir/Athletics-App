package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeCoach {

	@SerializedName("sports")
	private List<String> sports;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("profile_video")
	private String profileVideo;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private List<String> position;

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

	public void setPosition(List<String> position){
		this.position = position;
	}

	public List<String> getPosition(){
		return position;
	}
}