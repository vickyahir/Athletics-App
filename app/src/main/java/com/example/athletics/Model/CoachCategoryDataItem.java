package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class CoachCategoryDataItem {

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("exclusive")
	private String exclusive;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setExclusive(String exclusive){
		this.exclusive = exclusive;
	}

	public String getExclusive(){
		return exclusive;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}