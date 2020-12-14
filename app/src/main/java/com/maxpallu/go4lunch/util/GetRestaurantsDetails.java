package com.maxpallu.go4lunch.util;

import com.maxpallu.go4lunch.models.DetailsResult;

import java.util.List;

public class GetRestaurantsDetails {

    public DetailsResult getRestaurants(List<DetailsResult> mResults, String id) {
        for(int i =0; i<mResults.size(); i++) {
            if(mResults.get(i).getPlaceId().equals(id)) {
                return mResults.get(i);
            }
        }
        return null;
    }
}
