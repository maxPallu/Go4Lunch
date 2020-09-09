package com.maxpallu.go4lunch.service;

import com.maxpallu.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class WorkmateGenerator {

    public static List<Workmate> WORKMATES = Arrays.asList(
            new Workmate("1", "Scarlett", "https://i.pravatar.cc/200?img=1"),
            new Workmate("2", "Hugh", "https://i.pravatar.cc/200?img=8"),
            new Workmate("3", "Nana", "https://i.pravatar.cc/200?img=10"),
            new Workmate("4", "Godfrey", "https://i.pravatar.cc/200?img=11"),
            new Workmate("5", "Henry", "https://i.pravatar.cc/200?img=12"),
            new Workmate("6", "Angelina", "https://i.pravatar.cc/200?img=26")
    );

    static List<Workmate> generateWorkmates() {
        return new ArrayList<>(WORKMATES);
    }
}
