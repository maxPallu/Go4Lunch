package com.maxpallu.go4lunch.service;

import com.maxpallu.go4lunch.models.Workmate;

import java.util.List;

public interface WorkmateApiService {

    List<Workmate> getWorkmates();

    void createWorkmate(Workmate workmate);

    void deleteWorkmate(Workmate workmate);
}
