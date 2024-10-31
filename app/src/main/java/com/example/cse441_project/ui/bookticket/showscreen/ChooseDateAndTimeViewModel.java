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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChooseDateAndTimeViewModel extends ViewModel {

    private final MutableLiveData<List<ShowTime>> _showTimeList = new MutableLiveData<>();
    public LiveData<List<ShowTime>> showTimeList = _showTimeList;
    public final MutableLiveData<Exception> error = new MutableLiveData<>();

    void loadShowtime(String movieIdFromIntent) {
        if (movieIdFromIntent == null || movieIdFromIntent.isEmpty()) {
            error.setValue(new Exception("Invalid Movie ID"));
            return;
        }

        FirebaseUtils.getShowtimeCollection(movieIdFromIntent).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<ShowTime> showtimeList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                Log.e("ChooseDateAndTimeViewModel", "Showtime List Size: " + (querySnapshot != null ? querySnapshot.size() : 0));

                if (querySnapshot != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date currentDate = new Date(); // Lấy ngày hiện tại

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        ShowTime showTime = document.toObject(ShowTime.class);
                        if (showTime != null) {
                            try {
                                // Lấy ngày của showtime và so sánh với ngày hiện tại
                                Date showTimeDate = dateFormat.parse(showTime.getDate());
                                if (showTimeDate != null && !showTimeDate.before(currentDate)) {
                                    // Nếu ngày chiếu lớn hơn hoặc bằng ngày hiện tại, thêm vào danh sách
                                    if (showTime.getIdMovie().equals(movieIdFromIntent)) {
                                        showtimeList.add(showTime);
                                    }
                                } else {
                                    Log.d("ChooseDateAndTimeViewModel", "Showtime has passed: " + showTime.getDate());
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ChooseDateAndTimeViewModel", "Showtime is null for document: " + document.getId());
                        }
                    }
                    // Cập nhật danh sách đã lọc vào LiveData
                    _showTimeList.setValue(showtimeList);
                }
            } else {
                error.setValue(task.getException());
                Log.e("ChooseDateAndTimeViewModel", "Error getting showtimes: ", task.getException());
            }
        });
    }


}
