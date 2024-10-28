package com.example.cse441_project.ui.bookticket.showscreen;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.data.model.showtime.ShowTime;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class ChooseDateAndTimeViewModel extends ViewModel {

    private final MutableLiveData<List<String>> dateList = new MutableLiveData<>();
    private final MutableLiveData<List<ShowTime>> showTimeList = new MutableLiveData<>();

    public LiveData<List<String>> getDateList() {
        return dateList;
    }

    public LiveData<List<ShowTime>> getShowTimeList() {
        return showTimeList;
    }

    // Phương thức này sẽ lấy danh sách ngày từ Firestore
    public void loadDateList(String collectionName, String movieid) {
        FirebaseUtils.getShowtimeDataByMovieId(collectionName, movieid, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<String> dates = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String date = document.getString("date");
                        if (date != null && !dates.contains(date)) {
                            dates.add(date); // Thêm ngày vào danh sách nếu chưa có
                        }
                    }
                    dateList.setValue(dates);
                } else {
                    // Xử lý khi có lỗi
                    dateList.setValue(new ArrayList<>());
                }
            }
        });
    }

    // Phương thức này sẽ lấy danh sách ShowTime từ Firestore dựa trên MOVIE_ID
    public void loadShowtimeList(String collectionName, String movieId) {
        FirebaseUtils.getShowtimeDataByMovieId(collectionName, movieId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<ShowTime> showtimes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ShowTime showTime = new ShowTime();
                        showTime.setId(document.getString("id"));
                        showTime.setName(document.getString("name"));
                        showTime.setStartTime(document.getString("startTime"));
                        showTime.setEndTime(document.getString("endTime"));
                        showTime.setDate(document.getString("date"));

                        showtimes.add(showTime);
                    }
                    showTimeList.setValue(showtimes);
                } else {
                    showTimeList.setValue(new ArrayList<>());
                }
            }
        });
    }
}
