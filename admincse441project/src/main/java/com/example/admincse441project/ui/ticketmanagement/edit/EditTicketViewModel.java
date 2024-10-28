package com.example.admincse441project.ui.ticketmanagement.edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class EditTicketViewModel extends ViewModel {
    private MutableLiveData<Ticket> _ticket = new MutableLiveData<>();
    public LiveData<Ticket> getTicket(String ticketId) {
        FirebaseUtils.getTicketsCollection().document(ticketId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Ticket ticket = document.toObject(Ticket.class);
                            _ticket.setValue(ticket);
                        }
                    }
                });
        return _ticket;
    }

    public void updateTicket(Ticket ticket, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUtils.updateTicket(ticket).addOnCompleteListener(onCompleteListener);
    }
}