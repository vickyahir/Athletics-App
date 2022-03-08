package com.example.athletics.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StateListApiResponse{

	@SerializedName("msg")
	private Object msg;

	@SerializedName("data")
	private List<StateListDataItem> data;

	@SerializedName("status")
	private boolean status;

	public void setMsg(Object msg){
		this.msg = msg;
	}

	public Object getMsg(){
		return msg;
	}

	public void setData(List<StateListDataItem> data){
		this.data = data;
	}

	public List<StateListDataItem> getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}