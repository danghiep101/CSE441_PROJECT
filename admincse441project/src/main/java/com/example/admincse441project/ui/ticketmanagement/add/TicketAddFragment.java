package com.example.admincse441project.ui.ticketmanagement.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.admincse441project.R;
import com.example.admincse441project.data.repository.MovieRepositoryImp;
import com.example.admincse441project.ui.ticketmanagement.NowPlayingMovieViewModel;
import com.example.admincse441project.ui.ticketmanagement.NowPlayingViewModelFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TicketAddFragment extends Fragment {
    private View view;
    private DatePickerDialog datePickerDialog;

    private Spinner spnMovie;
    private Spinner spnScreen;
    private Spinner spnSeat;
    private Button btnDate;
    private NowPlayingMovieViewModel viewModel;

    public TicketAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_add, container, false);

        spnMovie = view.findViewById(R.id.spn_movie);
        spnScreen = view.findViewById(R.id.spn_screen);
        spnSeat = view.findViewById(R.id.spn_seat);
        btnDate = view.findViewById(R.id.btn_date);

        btnDate.setText(getTodaysDate());
        initDatePicker();
        btnDate.setOnClickListener(v -> openDatePicker(v));

        NowPlayingViewModelFactory factory = new NowPlayingViewModelFactory(new MovieRepositoryImp());
        viewModel = new ViewModelProvider(this, factory).get(NowPlayingMovieViewModel.class);

        viewModel.loadMovies();
        observeMovies();

        setupScreenSpinner();
        setupSeatSpinner();

        return view;
    }

    private void observeMovies() {
        viewModel.movies.observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                List<String> movieTitles = new ArrayList<>();
                for (com.example.admincse441project.data.model.movie.ResultsItem movie : movies) {
                    movieTitles.add(movie.getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.item_spinner, movieTitles) {
                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        // Inflate the layout for the dropdown items
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_dropdown, parent, false);
                        TextView textView = view.findViewById(R.id.txt_spinner_item_dropdown);
                        textView.setText(movieTitles.get(position)); // Set the text for the spinner item
                        return view;
                    }
                };

                // Set dropdown view resource - Use the same layout for dropdown
                adapter.setDropDownViewResource(R.layout.item_spinner);
                spnMovie.setAdapter(adapter);
            }
        });

        viewModel.error.observe(getViewLifecycleOwner(), exception -> {
            exception.printStackTrace();
        });
    }

    private void setupScreenSpinner() {
        List<String> screens = List.of("Screen 1", "Screen 2", "Screen 3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item_spinner, screens);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spnScreen.setAdapter(adapter);
    }

    private void setupSeatSpinner() {
        List<String> seats = List.of("A1", "A2", "A3", "B1", "B2", "B3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item_spinner, seats);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spnSeat.setAdapter(adapter);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(requireContext(), dateSetListener, year, month, day);
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}
