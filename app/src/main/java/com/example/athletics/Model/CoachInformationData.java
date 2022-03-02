package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CoachInformationData {

	@SerializedName("resume")
	private String resume;

	@SerializedName("sports")
	private List<String> sports;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("profile_video")
	private String profileVideo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private List<String> position;

	public void setResume(String resume){
		this.resume = resume;
	}

	public String getResume(){
		return resume;
	}

	public void setSports(List<String> sports){
		this.sports = sports;
	}

	public List<String> getSports(){
		return sports;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setProfileVideo(String profileVideo){
		this.profileVideo = profileVideo;
	}

	public String getProfileVideo(){
		return profileVideo;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
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