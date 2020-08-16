package com.maxpallu.go4lunch.di;

import com.maxpallu.go4lunch.service.WorkmateAPI;
import com.maxpallu.go4lunch.service.WorkmateApiService;

public class DI {

    private static WorkmateApiService service = new WorkmateAPI();

    public static WorkmateApiService getService() { return service;}

    public static WorkmateApiService getNewInstanceApiService() {
        return new WorkmateAPI();
    }
}
