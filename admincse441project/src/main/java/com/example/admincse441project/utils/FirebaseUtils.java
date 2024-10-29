package com.example.admincse441project.utils;

import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.data.model.account.Account;
import com.google.android.gms.tasks.Task;
import com.example.admincse441project.data.model.showtime.ShowTime;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    public static CollectionReference getShowtimesCollection() {
        return FirebaseFirestore.getInstance().collection("showtimes");
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
    public static CollectionReference getTicketsCollection() {
        return FirebaseFirestore.getInstance().collection("tickets");
    }

    public static Task<DocumentReference> addTicket(Ticket ticket) {
        return getTicketsCollection().add(ticket);
    }

    public static Task<Void> updateTicket(Ticket ticket) {
        return getTicketsCollection().document(ticket.getId()).set(ticket);
    }

    public static Task<Void> deleteTicket(String ticketId) {
        return getTicketsCollection().document(ticketId).delete();
    }

    public static Task<DocumentSnapshot> getTicketById(String ticketId) {
        return getTicketsCollection().document(ticketId).get();
    }

    public static CollectionReference getAccountsCollection() {
        return FirebaseFirestore.getInstance().collection("accounts");
    }

    public static Task<DocumentReference> addAccount(Account account) {
        return getAccountsCollection().add(account);
    }

    public static Task<Void> updateAccount(Account account) {
        return getAccountsCollection().document(account.getUid()).set(account);
    }

    public static Task<Void> deleteAccount(String accountId) {
        return getAccountsCollection().document(accountId).delete();
    }
}
