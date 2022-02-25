package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteProfileStateItem {

	@SerializedName("stat")
	private List<AthleteProfileStatItem> stat;

	@SerializedName("id")
	private int id;

	public void setStat(List<AthleteProfileStatItem> stat){
		this.stat = stat;
	}

	public List<AthleteProfileStatItem> getStat(){
		return stat;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}