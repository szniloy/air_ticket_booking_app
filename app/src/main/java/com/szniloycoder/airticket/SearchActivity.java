package com.szniloycoder.airticket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.airticket.Adapters.FlightAdapter;
import com.szniloycoder.airticket.Models.Flight;
import com.szniloycoder.airticket.databinding.ActivityMainBinding;
import com.szniloycoder.airticket.databinding.ActivitySearchBinding;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    String from, to, date;
    int numPassenger;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        database = FirebaseDatabase.getInstance();

        getIntentExtra();
        initList();
        setVariable();
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void initList() {
        binding.progressBarSearch.setVisibility(View.VISIBLE);

        DatabaseReference myRef = database.getReference("Flights");
        ArrayList<Flight> list = new ArrayList<>();
        Query query = myRef.orderByChild("from").equalTo(from);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Flight flight = issue.getValue(Flight.class);
                        assert flight != null;
                        Log.d("SearchActivity", "Flight found: " + flight.getFrom() + " to " + flight.getTo());
                        if (flight.getTo().equals(to)) {
                            list.add(flight);
                        }

                        //To filter with date, uncomment below lines and comment top line;
                       /* if (flight.getTo().equals(to) && flight.getDate().equals(date)) {
                            list.add(flight);
                        } */


                        if (!list.isEmpty()) {
                            binding.searchView.setLayoutManager(new LinearLayoutManager
                                    (SearchActivity.this, LinearLayoutManager.VERTICAL, false));
                            binding.searchView.setAdapter(new FlightAdapter(list, SearchActivity.this));
                            binding.animationView.setVisibility(View.GONE); // Hide Lottie animation

                        }else {
                            // No flights found
                            binding.searchView.setVisibility(View.GONE);
                            binding.animationView.setVisibility(View.VISIBLE); // Show Lottie animation
                            binding.animationView.playAnimation(); // Play the animation
                        }

                    }
                } else {
                    // No flights found
                    binding.searchView.setVisibility(View.GONE);
                    binding.animationView.setVisibility(View.VISIBLE); // Show Lottie animation
                    binding.animationView.playAnimation(); // Play the animation
                }

                binding.progressBarSearch.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBarSearch.setVisibility(View.GONE);

            }
        });


    }

    private void getIntentExtra() {
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        date = getIntent().getStringExtra("date");

        // Log the values
        Log.d("SearchActivityIntent", "From: " + from + ", To: " + to + ", Date: " + date);
    }
}