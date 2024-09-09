package com.szniloycoder.airticket;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.airticket.Models.Location;
import com.szniloycoder.airticket.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private int adultPassenger = 1, childPassenger = 1;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
    private final Calendar calendar = Calendar.getInstance();
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        database = FirebaseDatabase.getInstance();

        initLocation();
        initPassengers();
        initClassSeat();
        initDatePicker();
        setVariable();
    }

    //variable:
    private void setVariable() {

        //putData to Search Activity:
        binding.searchBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("from", ((Location) binding.fromSpinner.getSelectedItem()).getName());
            intent.putExtra("to", ((Location) binding.toSpinner.getSelectedItem()).getName());
            intent.putExtra("date", binding.departureDateTxt.getText().toString());
            intent.putExtra("numPassenger", adultPassenger + childPassenger);
            startActivity(intent);
        });

    }

    //location :
    private void initLocation() {

        binding.progressBarFrom.setVisibility(View.VISIBLE);
        binding.progressBarTo.setVisibility(View.VISIBLE);

        DatabaseReference myRef = database.getReference("Locations");

        ArrayList<Location> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Location location = issue.getValue(Location.class);
                        list.add(location);
                    }

                    //Spinner code:
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.fromSpinner.setAdapter(adapter);
                    binding.toSpinner.setAdapter(adapter);
                    binding.fromSpinner.setSelection(1);
                    //progressBar:
                    binding.progressBarFrom.setVisibility(View.GONE);
                    binding.progressBarTo.setVisibility(View.GONE);

                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Passenger:
    @SuppressLint("SetTextI18n")
    private void initPassengers() {

        //addPassenger:
        binding.plusAddultBtn.setOnClickListener(view -> {

            //addPassenger:
            adultPassenger++;
            //setText:
            binding.adultTxt.setText(adultPassenger + " Adult");
        });

        //minusPassenger:
        binding.minusAdultBtn.setOnClickListener(view -> {

            //minusPassenger:
            if (adultPassenger > 1) {
                adultPassenger--;
                //setText:
                binding.adultTxt.setText(adultPassenger + " Adult");
            }
        });

        //addChild:
        binding.plusChildBtn.setOnClickListener(view -> {
            //addPassenger:
            childPassenger++;
            //setText:
            binding.ChildTxt.setText(childPassenger + " Child");
        });

        //minusChild:
        binding.minusChildBtn.setOnClickListener(view -> {

            //minusChild:
            if (childPassenger > 0) {
                childPassenger--;
                //setText:
                binding.ChildTxt.setText(childPassenger + " Child");
            }
        });
    }

    //ClassSeat:
    private void initClassSeat() {

        //progressBar:
        binding.progressBarClass.setVisibility(View.VISIBLE);

        //Add Class List:
        ArrayList<String> list = new ArrayList<>();
        list.add("Business Class");
        list.add("First Class");
        list.add("Economy Class");

        //Add Class on adapter:
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.classSpinner.setAdapter(adapter);

        //progressBar:
        binding.progressBarClass.setVisibility(View.GONE);
    }

    //DatePicUpDialog:
    private void showDatePickerDialog(TextView textView) {
        //get Date:
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Dialog:
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            calendar.set(selectedYear, selectedMonth, selectedDay);
            String formattedDate = dateFormat.format(calendar.getTime());
            textView.setText(formattedDate);
        }, year, month, day);
        datePickerDialog.show();

    }

    //DatePicker:
    private void initDatePicker() {

        //DepartureDate:
        Calendar calendarToday = Calendar.getInstance();
        String currentDate = dateFormat.format(calendarToday.getTime());
        binding.departureDateTxt.setText(currentDate);

        //ReturnDate:
        Calendar calendarTomorrow = Calendar.getInstance();
        calendarTomorrow.add(Calendar.DAY_OF_YEAR, 1); // add this to show extra 1 day from departure date:
        String tomorrowDate = dateFormat.format(calendarTomorrow.getTime());
        binding.returnDateTxt.setText(tomorrowDate);

        //AddDate:
        binding.departureDateTxt.setOnClickListener(view -> showDatePickerDialog(binding.departureDateTxt));
        binding.returnDateTxt.setOnClickListener(view -> showDatePickerDialog(binding.returnDateTxt));
    }


}