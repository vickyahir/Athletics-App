package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserLikeVideoApiResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private List<UserLikeVideoDataItem> data;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(List<UserLikeVideoDataItem> data){
		this.data = data;
	}

	public List<UserLikeVideoDataItem> getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}