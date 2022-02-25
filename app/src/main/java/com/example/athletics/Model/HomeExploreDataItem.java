package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeExploreDataItem {

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("is_like")
	private boolean isLike;

	@SerializedName("athlete")
	private HomeExploreAthlete athlete;

	@SerializedName("id")
	private int id;

	@SerializedName("video")
	private String video;

	@SerializedName("title")
	private String title;

	@SerializedName("views")
	private int views;

	@SerializedName("likes")
	private List<String> likes;

	public void setThumb(String thumb){
		this.thumb = thumb;
	}

	public String getThumb(){
		return thumb;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setIsLike(boolean isLike){
		this.isLike = isLike;
	}

	public boolean isIsLike(){
		return isLike;
	}

	public void setAthlete(HomeExploreAthlete athlete){
		this.athlete = athlete;
	}

	public HomeExploreAthlete getAthlete(){
		return athlete;
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

	public void setViews(int views){
		this.views = views;
	}

	public int getViews(){
		return views;
	}

	public void setLikes(List<String> likes){
		this.likes = likes;
	}

	public List<String> getLikes(){
		return likes;
	}
}