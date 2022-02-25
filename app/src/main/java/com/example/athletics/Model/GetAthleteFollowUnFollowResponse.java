package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class GetAthleteFollowUnFollowResponse{

	@SerializedName("msg")
	private Object msg;

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private AthleteFollowUnFollowData data;

	@SerializedName("status")
	private boolean status;

	public void setMsg(Object msg){
		this.msg = msg;
	}

	public Object getMsg(){
		return msg;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(AthleteFollowUnFollowData data){
		this.data = data;
	}

	public AthleteFollowUnFollowData getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}