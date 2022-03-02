package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyVideoDataItem {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("is_like")
	private boolean isLike;

	@SerializedName("athlete")
	private MyVideoAthlete athlete;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("video")
	private String video;

	@SerializedName("title")
	private String title;

	@SerializedName("views")
	private String views;

	@SerializedName("status")
	private String status;

	@SerializedName("likes")
	private List<String> likes;

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

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setIsLike(boolean isLike){
		this.isLike = isLike;
	}

	public boolean isIsLike(){
		return isLike;
	}

	public void setAthlete(MyVideoAthlete athlete){
		this.athlete = athlete;
	}

	public MyVideoAthlete getAthlete(){
		return athlete;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setViews(String views){
		this.views = views;
	}

	public String getViews(){
		return views;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setLikes(List<String> likes){
		this.likes = likes;
	}

	public List<String> getLikes(){
		return likes;
	}
}