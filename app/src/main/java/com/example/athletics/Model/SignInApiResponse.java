package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SignInApiResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private SignInData data;

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

	public SignInData getData() {
		return data;
	}

	public void setData(SignInData data) {
		this.data = data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}