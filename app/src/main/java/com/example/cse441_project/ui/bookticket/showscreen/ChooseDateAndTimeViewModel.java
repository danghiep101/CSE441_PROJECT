package com.example.cse441_project.ui.bookticket.showscreen;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseDateAndTimeViewModel extends ViewModel {

    private final MutableLiveData<List<ShowTime>> _showTimeList = new MutableLiveData<>();
    public LiveData<List<ShowTime>> showTimeList = _showTimeList;
    public final MutableLiveData<Exception> error = new MutableLiveData<>();

    void loadShowtime(String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            error.setValue(new Exception("Invalid Movie ID"));
            return;
        }

        FirebaseUtils.getShowtimeCollection(movieId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<ShowTime> showtimeList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                Log.e("ChooseDateAndTimeViewModel", "Showtime List Size: " + (querySnapshot != null ? querySnapshot.size() : 0));

                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        ShowTime showTime = document.toObject(ShowTime.class);
                        if (showTime != null) {
                            showtimeList.add(showTime);
                        } else {
                            Log.e("ChooseDateAndTimeViewModel", "Showtime is null for document: " + document.getId());
                        }
                    }
                    _showTimeList.setValue(showtimeList);
                }
            } else {
                error.setValue(task.getException());
                Log.e("ChooseDateAndTimeViewModel", "Error getting showtimes: ", task.getException());
            }
        });
    }
}
