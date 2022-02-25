package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class AthleteProfileStatItem {

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private String value;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}