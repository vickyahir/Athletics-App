package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteInformationPositionItem {

	@SerializedName("pos")
	private List<String> pos;

	@SerializedName("id")
	private int id;

	public void setPos(List<String> pos){
		this.pos = pos;
	}

	public List<String> getPos(){
		return pos;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}