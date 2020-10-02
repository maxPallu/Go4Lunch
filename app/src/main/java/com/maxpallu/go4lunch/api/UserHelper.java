package com.maxpallu.go4lunch.api;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UserHelper {

    public static final String COLLECTION_NAME = "users";

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createUser(String id, String name, String mail, Uri urlPicture, String restaurantName, String restaurantId) {
        User userToCreate = new User(id, name, mail, urlPicture, restaurantName, restaurantId);
        return UserHelper.getUsersCollection().document(id).set(userToCreate);
    }

    public static Task<DocumentSnapshot> getUser(String id) {
        return UserHelper.getUsersCollection().document(id).get();
    }

    public static String getUserId() {
        return Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    public static Task<Void> updateUser(String id, String name, String mail, String urlPicture) {
        return UserHelper.getUsersCollection().document(id).update("id", id, "name", name, "mail", mail, "urlPicture", urlPicture);
    }

    public static Task<Void> updateRestaurant(String id, String restaurantName, String restaurantId) {
        return UserHelper.getUsersCollection().document(id).update("restaurantName", restaurantName, "restaurantId", restaurantId);
    }

    public static Task<Void> updateName(String name, String id) {
        return UserHelper.getUsersCollection().document(id).update("name", name);
    }

    public static Task<Void> deleteUser(String id) {
        return UserHelper.getUsersCollection().document(id).delete();
    }
}
