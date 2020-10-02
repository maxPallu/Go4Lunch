package com.maxpallu.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RestaurantHelper {

    private static final String COLLECTION_NAME = "restaurants";

    public static CollectionReference getRestaurantCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createRestaurant(String id, String name, String restaurantId) {
        Restaurant restaurantToCreate = new Restaurant(name, restaurantId);
        return RestaurantHelper.getRestaurantCollection().document(id).set(restaurantToCreate);
    }

    public static Task<Void> updateName(String name, String restaurantId, String id) {
        return RestaurantHelper.getRestaurantCollection().document(id).update("name", name, "restaurantId", restaurantId);
    }

    public static Task<DocumentSnapshot> getRestaurant(String id) {
        return RestaurantHelper.getRestaurantCollection().document(id).get();
    }

    public static Task<Void> updateClient(List<String> clients, String id) {
        return RestaurantHelper.getRestaurantCollection().document(id).update("clients", clients);
    }
}
