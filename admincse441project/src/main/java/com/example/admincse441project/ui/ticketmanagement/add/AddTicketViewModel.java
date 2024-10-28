package com.example.admincse441project.ui.ticketmanagement.add;

import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class AddTicketViewModel extends ViewModel {
    private final FirebaseUtils firebaseUtils = new FirebaseUtils();
    public Task<DocumentReference> addTicket(Ticket ticket) {
        return FirebaseUtils.addTicket(ticket).addOnSuccessListener(documentReference -> {
            String generatedId = documentReference.getId();
            ticket.setId(generatedId);
            FirebaseUtils.updateTicket(ticket);
        });
    }
}
