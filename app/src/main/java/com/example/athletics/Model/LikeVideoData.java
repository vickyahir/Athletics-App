package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LikeVideoData {

	@SerializedName("is_like")
	private boolean isLike;

	@SerializedName("likes")
	private List<String> likes;

	public void setIsLike(boolean isLike){
		this.isLike = isLike;
	}

	public boolean isIsLike(){
		return isLike;
	}

	public void setLikes(List<String> likes){
		this.likes = likes;
	}

	public List<String> getLikes(){
		return likes;
	}
}