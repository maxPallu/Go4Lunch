package com.maxpallu.go4lunch.models;

import com.google.gson.annotations.SerializedName;

public class TermsItem{

	@SerializedName("offset")
	private int offset;

	@SerializedName("value")
	private String value;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}