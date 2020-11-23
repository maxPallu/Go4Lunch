package com.maxpallu.go4lunch.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class StructuredFormatting{

	@SerializedName("main_text_matched_substrings")
	private List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings;

	@SerializedName("secondary_text")
	private String secondaryText;

	@SerializedName("main_text")
	private String mainText;

	public void setMainTextMatchedSubstrings(List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings){
		this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
	}

	public List<MainTextMatchedSubstringsItem> getMainTextMatchedSubstrings(){
		return mainTextMatchedSubstrings;
	}

	public void setSecondaryText(String secondaryText){
		this.secondaryText = secondaryText;
	}

	public String getSecondaryText(){
		return secondaryText;
	}

	public void setMainText(String mainText){
		this.mainText = mainText;
	}

	public String getMainText(){
		return mainText;
	}
}