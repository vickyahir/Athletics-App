package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class AthleteReqCategoriesItem {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("isSelected")
    private boolean isSelected = false;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}