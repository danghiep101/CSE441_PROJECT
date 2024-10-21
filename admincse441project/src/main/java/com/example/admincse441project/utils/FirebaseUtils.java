package com.example.admincse441project.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public static String currentUserId() {
        FirebaseUser firebaseAdmin = firebaseAuth.getCurrentUser();
        if (firebaseAdmin != null) {
            return firebaseAdmin.getUid();
        } else {
            return null;
        }
    }

    public static DocumentReference currentUserDetail() {
        String adminId = currentUserId();
        if (adminId != null) {
            return FirebaseFirestore.getInstance().collection("admin").document(adminId);
        } else {
            throw new IllegalStateException("Admin is not authenticated. Cannot access Firestore.");
        }
    }
}