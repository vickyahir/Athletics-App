package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CoachProfileData {

	@SerializedName("image")
	private String image;

	@SerializedName("is_active")
	private String isActive;

	@SerializedName("role")
	private String role;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("sports")
	private List<String> sports;

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
	private CoachProfileCoach coach;

	@SerializedName("email")
	private String email;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setIsActive(String isActive){
		this.isActive = isActive;
	}

	public String getIsActive(){
		return isActive;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSports(List<String> sports){
		this.sports = sports;
	}

	public List<String> getSports(){
		return sports;
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

	public void setCoach(CoachProfileCoach coach){
		this.coach = coach;
	}

	public CoachProfileCoach getCoach(){
		return coach;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}