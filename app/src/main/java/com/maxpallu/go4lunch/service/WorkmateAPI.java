package com.maxpallu.go4lunch.service;

import com.maxpallu.go4lunch.models.Workmate;

import java.util.List;

public class WorkmateAPI implements WorkmateApiService {

    private List<Workmate> workmates = WorkmateGenerator.generateWorkmates();

    @Override
    public List<Workmate> getWorkmates() {
        return workmates;
    }

    @Override
    public void createWorkmate(Workmate workmate) {
        workmates.add(workmate);
    }

    @Override
    public void deleteWorkmate(Workmate workmate) {
        workmates.remove(workmate);
    }
}
