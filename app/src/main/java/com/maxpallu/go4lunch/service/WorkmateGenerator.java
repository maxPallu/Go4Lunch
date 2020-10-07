package com.maxpallu.go4lunch.service;

import com.maxpallu.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class WorkmateGenerator {

    public static List<Workmate> WORKMATES = Arrays.asList(
            new Workmate("1", "Scarlett", "https://www.coolgenerator.com/Pic/Face//female/female2017102611179682.jpg", "ChIJXUM2f-lv5kcR_qirHSdh-mA", "Hotel Vernet, Champs - Élysées"),
            new Workmate("2", "Hugh", "https://www.coolgenerator.com/Pic/Face//male/male20161086289769012.jpg", "ChIJXUM2f-lv5kcR_qirHSdh-mA", "Hotel Vernet, Champs - Élysées"),
            new Workmate("3", "Nana", "https://www.coolgenerator.com/Pic/Face//female/female20161025051693054.jpg", "ChIJuz8N65Nv5kcRSgQjNgskN2Q", "Hotel du Collectionneur"),
            new Workmate("4", "Godfrey", "https://www.coolgenerator.com/Pic/Face//male/male20171086078296112.jpg", "ChIJaQacl-pv5kcRC2DmtH2M0jk", "Hotel Le Royal Monceau - Raffles Paris"),
            new Workmate("5", "Henry", "https://www.coolgenerator.com/Pic/Face//male/male1084941394562.jpg", null, null),
            new Workmate("6", "Angelina", "https://www.coolgenerator.com/Pic/Face//female/female20161024986145050.jpg", "ChIJc8zx1tpv5kcR4XnN_LMREjQ", "Hôtel La Maison Champs Elysées"),
            new Workmate("7", "Charles", "https://www.coolgenerator.com/Pic/Face//male/male1085853612823.jpg", null, null)
    );

    static List<Workmate> generateWorkmates() {
        return new ArrayList<>(WORKMATES);
    }
}
