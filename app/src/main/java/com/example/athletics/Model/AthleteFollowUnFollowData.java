package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteFollowUnFollowData {

	@SerializedName("followers")
	private List<String> followers;

	@SerializedName("is_following")
	private boolean isFollowing;

	public void setFollowers(List<String> followers){
		this.followers = followers;
	}

	public List<String> getFollowers(){
		return followers;
	}

	public void setIsFollowing(boolean isFollowing){
		this.isFollowing = isFollowing;
	}

	public boolean isIsFollowing(){
		return isFollowing;
	}
}