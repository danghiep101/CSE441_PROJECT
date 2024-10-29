package com.example.admincse441project.ui.showtimemaganement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.showtime.ShowTime;
import com.example.admincse441project.data.model.movie.ResultsItem; // Thêm import này
import com.example.admincse441project.data.repository.MovieRepositoryImp; // Thêm import này
import com.example.admincse441project.ui.showtimemaganement.NowPlayingMovieViewModel; // Thêm import này
import com.example.admincse441project.ui.showtimemaganement.NowPlayingViewModelFactory; // Thêm import này

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddShowtimeActivity extends AppCompatActivity {
    private Spinner spinnerMovie;
    private EditText editTextAvailableSeat, editTextUnavailableSeat, editTextStartTime, editTextEndTime, editTextDate,editNameCinema;
    private Button button;
    private AddShowTimeVIewModel viewModel;
    private NowPlayingMovieViewModel movieViewModel;

    private HashMap<String, String> movieIdMap; // Thêm biến này để lưu idMovie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddShowTimeVIewModel.class);
        setContentView(R.layout.activity_add_showtime);

        // Khởi tạo các view
        spinnerMovie = findViewById(R.id.editTextText3); // Cập nhật id thành spinnerMovie
        editTextAvailableSeat = findViewById(R.id.editTextText4);
        editTextUnavailableSeat = findViewById(R.id.editTextText5);
        editTextStartTime = findViewById(R.id.editTextText);
        editTextEndTime = findViewById(R.id.editTextText2);
        editTextDate = findViewById(R.id.editTextText12);
        editNameCinema=findViewById(R.id.editTextText9);
        button = findViewById(R.id.buttonAdd);

        // Khởi tạo HashMap
        movieIdMap = new HashMap<>();

        // Thiết lập sự kiện khi nút được nhấn
        button.setOnClickListener(view -> addShowtime());

        // Thiết lập sự kiện cho nút quay lại
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());

        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo ViewModel cho Movie
        NowPlayingViewModelFactory factory = new NowPlayingViewModelFactory(new MovieRepositoryImp());
        movieViewModel = new ViewModelProvider(this, factory).get(NowPlayingMovieViewModel.class);
        movieViewModel.loadMovies();
        observeMovies();
    }

    private void observeMovies() {
        movieViewModel.movies.observe(this, movies -> {
            if (movies != null) {
                List<String> movieTitles = new ArrayList<>();
                for (ResultsItem movie : movies) {
                    movieTitles.add(movie.getTitle());
                    movieIdMap.put(movie.getTitle(), String.valueOf(movie.getId()));
                    // Lưu idMovie vào HashMap
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, movieTitles);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMovie.setAdapter(adapter);
            }
        });

        movieViewModel.error.observe(this, exception -> {
            Log.e("AddShowtimeActivity", "Error loading movies", exception);
            Toast.makeText(this, "Error loading movies", Toast.LENGTH_SHORT).show();
        });
    }

    private void addTicket() {

    }

    private void addShowtime() {
        // Lấy dữ liệu từ các EditText và Spinner
        String name = (String) spinnerMovie.getSelectedItem(); // Lấy tên phim từ spinner
        String availableSeat = editTextAvailableSeat.getText().toString().trim();
        String unavailableSeat = editTextUnavailableSeat.getText().toString().trim();
        String startTimeStr = editTextStartTime.getText().toString().trim();
        String endTimeStr = editTextEndTime.getText().toString().trim();
        String nameCinema=editNameCinema.getText().toString().trim();
        String dateStr = editTextDate.getText().toString().trim();

        // Kiểm tra xem tất cả các trường đã được điền hay chưa
        if (!name.isEmpty() && !availableSeat.isEmpty() && !unavailableSeat.isEmpty() &&
                !startTimeStr.isEmpty() && !endTimeStr.isEmpty() && !dateStr.isEmpty()) {

            String idMovie = movieIdMap.get(name); // Lấy idMovie tương ứng

            // Tạo đối tượng ShowTime
            ShowTime showTime = new ShowTime(null, name, availableSeat, unavailableSeat, startTimeStr, endTimeStr, dateStr, idMovie,nameCinema);

            // Gọi ViewModel để thêm showtime
            viewModel.addShowTime(showTime).addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Successfully added Showtime", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> {
                Log.e("AddShowTimeActivity", "Error adding Showtime", e);
                Toast.makeText(this, "Add Showtime failed", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private String parseTimeToString(String timeStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            sdf.parse(timeStr); // Không cần phải chuyển đổi thành Time
            return timeStr; // Trả về chuỗi thời gian
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseDateToString(String dateStr) {
        try {
            // Định dạng cho dữ liệu đầu vào
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            // Định dạng cho dữ liệu đầu ra
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // Phân tích chuỗi ngày
            java.util.Date date = inputFormat.parse(dateStr);
            // Chuyển đổi sang định dạng yyyy-MM-dd
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
