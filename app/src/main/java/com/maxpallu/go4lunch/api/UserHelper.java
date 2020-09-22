package com.maxpallu.go4lunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {

    public static final String COLLECTION_NAME = "users";

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createUser(String id, String name, String mail, String urlPicture) {
        User userToCreate = new User(id, name, mail, urlPicture);
        return UserHelper.getUsersCollection().document(id).set(userToCreate);
    }

    public static Task<DocumentSnapshot> getUser(String id) {
        return UserHelper.getUsersCollection().document(id).get();
    }

    public static Task<Void> updateName(String name, String id) {
        return UserHelper.getUsersCollection().document(id).update("name", name);
    }

    public static Task<Void> deleteUser(String id) {
        return UserHelper.getUsersCollection().document(id).delete();
    }
}
