package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class HomeCoachDataItem {

	@SerializedName("image")
	private String image;

	@SerializedName("is_active")
	private int isActive;

	@SerializedName("role")
	private int role;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private String emailVerifiedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("expired_at")
	private String expiredAt;

	@SerializedName("coach")
	private HomeCoach coach;

	@SerializedName("email")
	private String email;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setIsActive(int isActive){
		this.isActive = isActive;
	}

	public int getIsActive(){
		return isActive;
	}

	public void setRole(int role){
		this.role = role;
	}

	public int getRole(){
		return role;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(String emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public String getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setExpiredAt(String expiredAt){
		this.expiredAt = expiredAt;
	}

	public String getExpiredAt(){
		return expiredAt;
	}

	public void setCoach(HomeCoach coach){
		this.coach = coach;
	}

	public HomeCoach getCoach(){
		return coach;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}