package com.example.cse441_project.ui.bookticket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.example.cse441_project.utils.FirebaseUtils;
public class SelectShowTime extends ViewModel {
    private MutableLiveData<ShowTime> _showTime = new MutableLiveData<>();
    public LiveData<ShowTime> getShowTime(String discountId) {
        FirebaseUtils.getShowtimesCollection().document(discountId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            ShowTime discount = document.toObject(ShowTime.class);
                            _showTime.setValue(discount);
                        }
                    }
                });
        return _showTime;
    }

    public void updateShowTime(ShowTime showTime, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUtils.updateShowtime(showTime).addOnCompleteListener(onCompleteListener);
    }
}
