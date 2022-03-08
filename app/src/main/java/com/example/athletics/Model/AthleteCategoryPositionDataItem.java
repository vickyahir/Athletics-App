package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteCategoryPositionDataItem {

	@SerializedName("stats")
	private List<String> stats;

	@SerializedName("name")
	private String name;

	@SerializedName("positions")
	private List<String> positions;

	@SerializedName("id")
	private int id;

	public void setStats(List<String> stats){
		this.stats = stats;
	}

	public List<String> getStats(){
		return stats;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPositions(List<String> positions){
		this.positions = positions;
	}

	public List<String> getPositions(){
		return positions;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}