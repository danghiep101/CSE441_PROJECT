package com.example.admincse441project.ui.showtimemaganement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.showtime.ShowTime;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowTimeViewModel extends ViewModel {
    private final MutableLiveData<List<ShowTime>> _showtimes = new MutableLiveData<>();
    public LiveData<List<ShowTime>> showtimes = _showtimes;
    public ShowTimeViewModel() {
        loadShowTimes();
    }
    void loadShowTimes() {
        FirebaseUtils.getShowtimesCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<ShowTime> showTimeList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        ShowTime showTime = document.toObject(ShowTime.class);
                        if (showTime != null) {
                            showTimeList.add(showTime);
                        }
                    }
                    _showtimes.setValue(showTimeList);

                }
            }
        });
    }
}
