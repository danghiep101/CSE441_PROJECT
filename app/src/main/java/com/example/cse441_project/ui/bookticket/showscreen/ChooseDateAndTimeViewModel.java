package com.example.cse441_project.ui.bookticket.showscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.showscreen.ShowScreen;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChooseDateAndTimeViewModel extends ViewModel {

    private final MutableLiveData<List<ShowScreen>> showScreenList = new MutableLiveData<>();

    public LiveData<List<ShowScreen>> getShowScreenList() {
        return showScreenList;
    }

    public void fetchShowtimeData() {
        FirebaseUtils.getShowtimeData("showtime", task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<ShowScreen> screens = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ShowScreen screen = document.toObject(ShowScreen.class);
                    screens.add(screen);
                }
                showScreenList.setValue(screens);
            } else {
                showScreenList.setValue(new ArrayList<>());
            }
        });
    }
}
