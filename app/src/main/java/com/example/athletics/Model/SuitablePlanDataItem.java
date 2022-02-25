package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SuitablePlanDataItem {

	@SerializedName("for_who")
	private String forWho;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("max_usage")
	private String maxUsage;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("free_enable")
	private int free_enable;

	@SerializedName("id")
	private int id;

	public int getFree_enable() {
		return free_enable;
	}

	public void setFree_enable(int free_enable) {
		this.free_enable = free_enable;
	}

	@SerializedName("detail")
	private List<String> detail;

	@SerializedName("day")
	private String day;

	public void setForWho(String forWho){
		this.forWho = forWho;
	}

	public String getForWho(){
		return forWho;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setMaxUsage(String maxUsage){
		this.maxUsage = maxUsage;
	}

	public String getMaxUsage(){
		return maxUsage;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDetail(List<String> detail){
		this.detail = detail;
	}

	public List<String> getDetail(){
		return detail;
	}

	public void setDay(String day){
		this.day = day;
	}

	public String getDay(){
		return day;
	}
}