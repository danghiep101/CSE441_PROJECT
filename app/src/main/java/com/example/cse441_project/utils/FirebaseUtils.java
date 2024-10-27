package com.example.cse441_project.utils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static String currentUserId() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return firebaseUser.getUid();
        } else {
            return null;
        }
    }

    public static DocumentReference currentUserDetail() {
        String userId = currentUserId();
        if (userId != null) {
            return FirebaseFirestore.getInstance().collection("users").document(userId);
        } else {
            throw new IllegalStateException("User is not authenticated. Cannot access Firestore.");
        }
    }

    public static void getShowtimeData(String collection, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        firestore.collection(collection).get().addOnCompleteListener(onCompleteListener);
    }
}
