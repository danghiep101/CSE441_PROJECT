package com.example.cse441_project.utils;

import com.example.cse441_project.data.model.discount.Discount;
import com.example.cse441_project.data.model.showtime.ShowTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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


    public static Query getShowtimeIdmovie(String idMovie) {
        return FirebaseFirestore.getInstance().collection("showtimes").whereEqualTo("idMovie", idMovie);
    }

    public static DocumentReference getShowtimeById(String showtimeId) {
        return firestore.collection("showtimes").document(showtimeId);
    }

    public static Query getTicketsByShowtime(String showtimeId) {
        return FirebaseFirestore.getInstance()
                .collection("tickets")
                .whereEqualTo("showtimeId", showtimeId)
                .whereEqualTo("seat", "");
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
                .whereEqualTo("userId", user_id);
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




    public static CollectionReference getShowtimesCollection() {
        return FirebaseFirestore.getInstance().collection("showtimes");
    }
    public static CollectionReference getDiscountsCollection() {
        return FirebaseFirestore.getInstance().collection("discounts");
    }

    public static Task<DocumentReference> addDiscount(Discount discount) {
        return getDiscountsCollection().add(discount);
    }

    public static Task<Void> updateDiscount(Discount discount) {
        return getDiscountsCollection().document(discount.getId()).set(discount);
    }

    public static Task<Void> deleteDiscount(String discountId) {
        return getDiscountsCollection().document(discountId).delete();
    }
    public static Task<DocumentReference> addShowtime(ShowTime showtime) {
        return getShowtimesCollection().add(showtime);
    }

    public static Task<Void> updateShowtime(ShowTime showtime) {
        return getShowtimesCollection().document(showtime.getId()).set(showtime);
    }

    public static Task<Void> deleteShowTime(String showId) {
        return getShowtimesCollection().document(showId).delete();
    }
}
