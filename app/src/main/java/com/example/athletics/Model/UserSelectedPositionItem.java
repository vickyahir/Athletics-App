package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class UserSelectedPositionItem {

    @SerializedName("name")
    private String name;

    @SerializedName("IsSelected")
    private boolean IsSelected = false;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

	public boolean isSelected() {
		return IsSelected;
	}

	public void setSelected(boolean selected) {
		IsSelected = selected;
	}
}