package com.example.admincse441project.ui.showtimemaganement;

import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.showtime.ShowTime;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class AddShowTimeVIewModel extends ViewModel {
    private final FirebaseUtils firebaseUtils = new FirebaseUtils();
    public Task<DocumentReference> addShowTime(ShowTime show) {
        return FirebaseUtils.addShowtime(show).addOnSuccessListener(documentReference -> {
            String generatedId = documentReference.getId();
            show.setId(generatedId);
            FirebaseUtils.updateShowtime(show);
        });
    }
}
