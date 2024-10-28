package com.example.admincse441project.ui.ticketmanagement.edit;

import static com.example.admincse441project.utils.FirebaseUtils.deleteDiscount;
import static com.example.admincse441project.utils.FirebaseUtils.deleteTicket;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.data.model.ticket.Ticket;
import com.example.admincse441project.data.repository.MovieRepositoryImp;
import com.example.admincse441project.ui.ticketmanagement.NowPlayingMovieViewModel;
import com.example.admincse441project.ui.ticketmanagement.NowPlayingViewModelFactory;
import com.example.admincse441project.ui.ticketmanagement.add.AddTicketViewModel;
import com.example.admincse441project.ui.ticketmanagement.list.TicketListFragment;
import com.example.admincse441project.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TicketEditFragment extends Fragment {
    private View view;

    private EditTicketViewModel editTicketViewModel;
    private NowPlayingMovieViewModel viewModel;

    private DatePickerDialog datePickerDialog;
    private Spinner spnEditMovie;
    private Spinner spnEditScreen;
    private Button btnEditDate;
    private Button btnEditTime;
    private Button btnEditTicket;
    private ImageView imgDeleteTicket;
    private ImageView btnBack;

    private int hour, minute;
    private String ticketId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ticket_edit, container, false);

        spnEditMovie = view.findViewById(R.id.spn_edit_movie);
        spnEditScreen = view.findViewById(R.id.spn_edit_screen);
        btnEditDate = view.findViewById(R.id.btn_edit_date);
        btnEditTime = view.findViewById(R.id.btn_edit_time);
        btnEditTicket = view.findViewById(R.id.btn_edit_ticket);
        imgDeleteTicket = view.findViewById(R.id.img_delete_ticket);
        btnBack = view.findViewById(R.id.btn_back_edit_ticket);

        // Handle btnDate
        btnEditDate.setText(getTodaysDate());
        initDatePicker();
        btnEditDate.setOnClickListener(v -> openDatePicker(v));

        // hanlde btnTime
        btnEditTime.setOnClickListener(v -> popTimePicker(v));

        // Call api and load movie title into spinner
        NowPlayingViewModelFactory factory = new NowPlayingViewModelFactory(new MovieRepositoryImp());
        viewModel = new ViewModelProvider(this, factory).get(NowPlayingMovieViewModel.class);
        viewModel.loadMovies();
        observeMovies();

        editTicketViewModel = new ViewModelProvider(this).get(EditTicketViewModel.class);

        setupScreenSpinner();

        ticketId = getArguments().getString("TICKET_ID");

        FirebaseUtils.getTicketById(ticketId)
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Ticket ticket = documentSnapshot.toObject(Ticket.class);

                        btnEditDate.setText(ticket.getDate());
                        btnEditTime.setText(ticket.getTime());
                        setSpinnerSelectionByValue(spnEditMovie, ticket.getMovie_name());
                        setSpinnerSelectionByValue(spnEditScreen, ticket.getScreen());

                    } else {
                        Log.d("Ticket không tồn tại", "Ticket không tồn tại");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Lỗi khi lấy ticket: ", "Lỗi khi lấy ticket: ");
                });
        onViewClickListeners();
        return view;
    }

    private void onViewClickListeners() {
        btnEditTicket.setOnClickListener(v -> saveTicket(ticketId));
        btnBack.setOnClickListener(v -> {
            Fragment newFragment = new TicketListFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        imgDeleteTicket.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirm delete")
                    .setMessage("Are you sure you want to delete this ticket?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        deleteTicket(ticketId);
                        Toast.makeText(requireContext(), "Ticket deleted", Toast.LENGTH_SHORT).show();

                        Fragment newFragment = new TicketListFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void saveTicket(String ticketId) {
        Ticket ticket = new Ticket(ticketId, spnEditMovie.getSelectedItem().toString(), spnEditScreen.getSelectedItem().toString(), "", btnEditDate.getText().toString(), btnEditTime.getText().toString(), "Active");
        editTicketViewModel.updateTicket(ticket, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(requireContext(), "Ticket updated successfully", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(requireContext(), "Failed to update ticket", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void setSpinnerSelectionByValue(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            if (position >= 0) {
                spinner.setSelection(position);
            } else {
                Log.d("Spinner", "Giá trị không tồn tại trong danh sách");
            }
        }
    }

    private void observeMovies() {
        viewModel.movies.observe(getViewLifecycleOwner(), movies -> {
            if (movies != null) {
                List<String> movieTitles = new ArrayList<>();
                for (com.example.admincse441project.data.model.movie.ResultsItem movie : movies) {
                    movieTitles.add(movie.getTitle());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item_spinner, movieTitles);
                adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
                spnEditMovie.setAdapter(adapter);

                // Khi dữ liệu đã tải xong, thiết lập giá trị mặc định cho Spinner (nếu ticketId đã có)
                if (getArguments() != null) {
                    String ticketId = getArguments().getString("TICKET_ID");
                    FirebaseUtils.getTicketById(ticketId)
                            .addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.exists()) {
                                    Ticket ticket = documentSnapshot.toObject(Ticket.class);
                                    setSpinnerSelectionByValue(spnEditMovie, ticket.getMovie_name());
                                }
                            });
                }
            }
        });
    }

    private void setupScreenSpinner() {
        List<String> screens = List.of("Screen 1", "Screen 2", "Screen 3");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.item_spinner, screens);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spnEditScreen.setAdapter(adapter);
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

    private void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                btnEditTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }
}