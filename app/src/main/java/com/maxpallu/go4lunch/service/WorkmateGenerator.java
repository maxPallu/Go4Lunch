package com.maxpallu.go4lunch.service;

import com.maxpallu.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class WorkmateGenerator {

    public static List<Workmate> WORKMATES = Arrays.asList(
            new Workmate("1", "Scarlett", "https://www.coolgenerator.com/Pic/Face//female/female2017102611179682.jpg", "0"),
            new Workmate("2", "Hugh", "https://www.coolgenerator.com/Pic/Face//male/male20161086289769012.jpg", "0"),
            new Workmate("3", "Nana", "https://www.coolgenerator.com/Pic/Face//female/female20161025051693054.jpg", "3"),
            new Workmate("4", "Godfrey", "https://www.coolgenerator.com/Pic/Face//male/male20171086078296112.jpg", "2"),
            new Workmate("5", "Henry", "https://www.coolgenerator.com/Pic/Face//male/male1084941394562.jpg", "1"),
            new Workmate("6", "Angelina", "https://www.coolgenerator.com/Pic/Face//female/female20161024986145050.jpg", "1"),
            new Workmate("7", "Charles", "https://www.coolgenerator.com/Pic/Face//male/male1085853612823.jpg", " ")
    );

    static List<Workmate> generateWorkmates() {
        return new ArrayList<>(WORKMATES);
    }
}
