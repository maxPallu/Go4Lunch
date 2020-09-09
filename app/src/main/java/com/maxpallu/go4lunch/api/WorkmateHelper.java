package com.maxpallu.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.models.Workmate;

public class WorkmateHelper {

    private static final String COLLECTION_NAME = "workmates";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkmatesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createWorkmate(String id, String name, String urlPicture) {
        Workmate workmate = new Workmate(id, name, urlPicture);
        return WorkmateHelper.getWorkmatesCollection().document(id).set(workmate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getWorkmate(String id) {
        return WorkmateHelper.getWorkmatesCollection().document(id).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateName(String name, String id) {
        return WorkmateHelper.getWorkmatesCollection().document(id).update("name", name);
    }

    // --- DELETE ---

    public static Task<Void> deleteWorkmate(String id) {
        return WorkmateHelper.getWorkmatesCollection().document(id).delete();
    }
}
