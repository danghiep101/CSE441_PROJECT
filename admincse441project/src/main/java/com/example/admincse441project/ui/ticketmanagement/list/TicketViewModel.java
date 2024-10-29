package com.example.admincse441project.ui.ticketmanagement.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TicketViewModel extends ViewModel {
    private final MutableLiveData<List<Ticket>> _ticket = new MutableLiveData<>();
    public LiveData<List<Ticket>> tickets = _ticket;
    public TicketViewModel() {
        loadTickets();
    }
    void loadTickets() {
        FirebaseUtils.getTicketsCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Ticket> ticketList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Ticket ticket = document.toObject(Ticket.class);
                        if (ticket != null) {
                            ticketList.add(ticket);
                        }
                    }
                    _ticket.setValue(ticketList);
                }
            }
        });
    }
}