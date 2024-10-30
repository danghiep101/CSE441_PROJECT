package com.example.cse441_project.utils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

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

    public static CollectionReference getShowtimeCollection(String movieId) {
        return FirebaseFirestore.getInstance().collection("showtimes");
    }

    public static Query getShowtimeIdmovie(String idMovie) {
        return FirebaseFirestore.getInstance().collection("showtimes").whereEqualTo("idMovie", idMovie);
    }

    public static DocumentReference getShowtimeById(String showtimeId) {
        return firestore.collection("showtimes").document(showtimeId);
    }

    public static Query getTicketsByShowtimeAndSeat(String showtimeId) {
        return FirebaseFirestore.getInstance()
                .collection("tickets")
                .whereEqualTo("showtimeId", showtimeId)
                .whereNotEqualTo("seat", "");
    }

    public static Query getTicketByUserId(String user_id) {
        return FirebaseFirestore.getInstance()
                .collection("tickets")
                .whereEqualTo("user_id", user_id);
    }
}
