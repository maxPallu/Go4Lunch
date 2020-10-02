package com.maxpallu.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    public static Task<DocumentReference> createWorkmate(String id, String name, String urlPicture, String restaurantId, String restaurantName) {
        Workmate workmate = new Workmate(id, name, urlPicture, restaurantId, restaurantName);
        return WorkmateHelper.getWorkmatesCollection().add(workmate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getWorkmate(String id) {
        return WorkmateHelper.getWorkmatesCollection().document(id).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateName(String name, String id) {
        return WorkmateHelper.getWorkmatesCollection().document(id).update("name", name);
    }

    public static Task<Void> updateRestaurant(String id, String restaurantName, String restaurantId) {
        return WorkmateHelper.getWorkmatesCollection().document(id).update("restaurantName", restaurantName, "restaurantId", restaurantId);
    }

    // --- DELETE ---

    public static Task<Void> deleteWorkmate(String id) {
        return WorkmateHelper.getWorkmatesCollection().document(id).delete();
    }
}
