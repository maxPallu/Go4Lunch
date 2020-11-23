package com.maxpallu.go4lunch.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PlaceAutocomplete{

	@SerializedName("predictions")
	private List<PredictionsItem> predictions;

	@SerializedName("status")
	private String status;

	public void setPredictions(List<PredictionsItem> predictions){
		this.predictions = predictions;
	}

	public List<PredictionsItem> getPredictions(){
		return predictions;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}