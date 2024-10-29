package com.example.admincse441project.ui.showtimemaganement;

import static com.example.admincse441project.utils.FirebaseUtils.deleteShowTime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.showtime.ShowTime;
import com.example.admincse441project.data.model.movie.ResultsItem; // Thêm import này
import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.data.repository.MovieRepositoryImp; // Thêm import này
import com.example.admincse441project.ui.showtimemaganement.NowPlayingMovieViewModel; // Thêm import này
import com.example.admincse441project.ui.showtimemaganement.NowPlayingViewModelFactory; // Thêm import này
import com.example.admincse441project.ui.ticketmanagement.add.AddTicketViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EditShowtimeActivity extends AppCompatActivity {
    private Spinner nameSpinner;
    private EditText startTimeEditText, endTimeEditText, availableSeatEditText, unavailableSeatEditText, dateEditText,nameCinema;
    private Button saveButton, addTicketButton;
    private ImageView deleteButton;
    private EditShowTimeViewModel viewModel;
    private AddTicketViewModel addTicketViewModel;
    private NowPlayingMovieViewModel movieViewModel;
    private HashMap<String, String> movieIdMap; // Thêm biến này để lưu idMovie

    private String showTimeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_showtime);

        viewModel = new ViewModelProvider(this).get(EditShowTimeViewModel.class);
        addTicketViewModel = new ViewModelProvider(this).get(AddTicketViewModel.class);
        movieIdMap = new HashMap<>(); // Khởi tạo HashMap để lưu idMovie

        initializeViews();
        setupSpinner();

        Intent intent = getIntent();
        showTimeId = intent.getStringExtra("SHOWTIME_ID");
        fetchShowTime(showTimeId);

        saveButton.setOnClickListener(v -> saveShowtime(showTimeId));
        addTicketButton.setOnClickListener(v -> addTicket(showTimeId));
        setupClickListeners();

        // Khởi tạo ViewModel cho Movie
        NowPlayingViewModelFactory factory = new NowPlayingViewModelFactory(new MovieRepositoryImp());
        movieViewModel = new ViewModelProvider(this, factory).get(NowPlayingMovieViewModel.class);
        movieViewModel.loadMovies();
        observeMovies();
    }

    private void initializeViews() {
        nameSpinner = findViewById(R.id.editTextText3);
        dateEditText = findViewById(R.id.editTextText6);
        startTimeEditText = findViewById(R.id.editTextText);
        endTimeEditText = findViewById(R.id.editTextText2);
        availableSeatEditText = findViewById(R.id.editTextText4);
        unavailableSeatEditText = findViewById(R.id.editTextText8);
        saveButton = findViewById(R.id.button);
        nameCinema=findViewById(R.id.editTextText10);
        deleteButton = findViewById(R.id.imageButton3);
        addTicketButton = findViewById(R.id.btn_showtime_add_ticket);
    }

    private void setupSpinner() {
        List<String> showTimeNames = getShowTimeNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, showTimeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapter);
    }

    private void fetchShowTime(String showTimeId) {
        viewModel.getShowTime(showTimeId).observe(this, showTime -> {
            int position = getPositionForShowTimeName(showTime.getName());
            nameSpinner.setSelection(position);
            startTimeEditText.setText(showTime.getStartTime());
            endTimeEditText.setText(showTime.getEndTime());
            availableSeatEditText.setText(showTime.getAvailableSeat());
            nameCinema.setText(showTime.getNameCinema());
            unavailableSeatEditText.setText(showTime.getUnavailableSeat());
            dateEditText.setText(showTime.getDate());
        });
    }

    private int getPositionForShowTimeName(String name) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) nameSpinner.getAdapter();
        return adapter.getPosition(name);
    }

    private void saveShowtime(String id) {
        String selectedName = nameSpinner.getSelectedItem().toString();
        String startTimeStr = startTimeEditText.getText().toString().trim();
        String endTimeStr = endTimeEditText.getText().toString().trim();
        String availableSeat = availableSeatEditText.getText().toString().trim();
        String unavailableSeat = unavailableSeatEditText.getText().toString().trim();
        String Cinema=nameCinema.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        if (selectedName.isEmpty() || startTimeStr.isEmpty() || endTimeStr.isEmpty() || availableSeat.isEmpty() || unavailableSeat.isEmpty() || date.isEmpty() || Cinema.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidTimeFormat(startTimeStr) || !isValidTimeFormat(endTimeStr)) {
            Toast.makeText(this, "Định dạng thời gian không hợp lệ. Vui lòng sử dụng HH:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        String idMovie = movieIdMap.get(selectedName); // Lấy idMovie tương ứng
        ShowTime showTime = new ShowTime(id, selectedName, availableSeat, unavailableSeat, startTimeStr, endTimeStr, date, idMovie,Cinema);

        viewModel.updateShowTime(showTime, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Showtime updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update Showtime", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    private void addTicket(String showtimeId) {
        for(int i = 0; i < Integer.parseInt(availableSeatEditText.getText().toString().trim()); i++) {
            Ticket ticket = new Ticket(null, showtimeId, "", "Available");

            if (i == 0) {
                addTicketViewModel.addTicket(ticket).addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Adding ticket...", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Log.e("TicketAddFragment", "Error adding ticket!", e);
                    Toast.makeText(this, "Add ticket failed!", Toast.LENGTH_SHORT).show();
                });
            }
            else if (i == Integer.parseInt(availableSeatEditText.getText().toString().trim()) - 1) {
                addTicketViewModel.addTicket(ticket).addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Successfully added ticket!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Log.e("TicketAddFragment", "Error adding ticket!", e);
                    Toast.makeText(this, "Add ticket failed!", Toast.LENGTH_SHORT).show();
                });
            }

            addTicketViewModel.addTicket(ticket).addOnSuccessListener(documentReference -> {
            }).addOnFailureListener(e -> {
                Log.e("TicketAddFragment", "Error adding ticket!", e);
                Toast.makeText(this, "Add ticket failed!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private boolean isValidTimeFormat(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(timeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void setupClickListeners() {
        Intent intent = getIntent();
        String showTimeId = intent.getStringExtra("SHOWTIME_ID");

        deleteButton.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa showtime này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteShowTime(showTimeId);
                    Toast.makeText(this, "ShowTime đã được xóa", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show());
    }

    private void observeMovies() {
        movieViewModel.movies.observe(this, movies -> {
            if (movies != null) {
                List<String> movieTitles = new ArrayList<>();
                for (ResultsItem movie : movies) {
                    movieTitles.add(movie.getTitle());
                    movieIdMap.put(movie.getTitle(), String.valueOf(movie.getId()));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, movieTitles);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nameSpinner.setAdapter(adapter);
            }
        });

        movieViewModel.error.observe(this, exception -> {
            Log.e("EditShowtimeActivity", "Error loading movies", exception);
            Toast.makeText(this, "Lỗi khi tải phim", Toast.LENGTH_SHORT).show();
        });
    }

    private List<String> getShowTimeNames() {
        // Thay đổi phương thức này để lấy dữ liệu thực tế từ nguồn của bạn
        return List.of("Showtime 1", "Showtime 2", "Showtime 3"); // Ví dụ
    }
}
