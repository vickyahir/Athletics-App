package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AthleteReqData {

	@SerializedName("university")
	private List<AthleteReqUniversityItem> university;

	@SerializedName("categories")
	private List<AthleteReqCategoriesItem> categories;

	@SerializedName("countries")
	private List<AthleteReqCountriesItem> countries;

	public void setUniversity(List<AthleteReqUniversityItem> university){
		this.university = university;
	}

	public List<AthleteReqUniversityItem> getUniversity(){
		return university;
	}

	public void setCategories(List<AthleteReqCategoriesItem> categories){
		this.categories = categories;
	}

	public List<AthleteReqCategoriesItem> getCategories(){
		return categories;
	}

	public void setCountries(List<AthleteReqCountriesItem> countries){
		this.countries = countries;
	}

	public List<AthleteReqCountriesItem> getCountries(){
		return countries;
	}
}