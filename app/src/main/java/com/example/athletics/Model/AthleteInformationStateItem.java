package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteInformationStateItem {

	@SerializedName("stat")
	private List<AthleteInformationStatItem> stat;

	@SerializedName("id")
	private int id;

	public void setStat(List<AthleteInformationStatItem> stat){
		this.stat = stat;
	}

	public List<AthleteInformationStatItem> getStat(){
		return stat;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}