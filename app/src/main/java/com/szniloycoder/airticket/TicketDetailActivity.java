package com.szniloycoder.airticket;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.szniloycoder.airticket.Models.Flight;
import com.szniloycoder.airticket.databinding.ActivityTicketDetailBinding;

public class TicketDetailActivity extends AppCompatActivity {
    ActivityTicketDetailBinding binding;
    Flight flight;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        database = FirebaseDatabase.getInstance();

        getIntentExtra();
        setVariable();
    }

    @SuppressLint("SetTextI18n")
    private void setVariable() {

        binding.btnBack.setOnClickListener(view -> finish());
        binding.fromTxt.setText(flight.getFrom());
        binding.fromShortTxt.setText(flight.getFromShort());
        binding.fromSmallTxt.setText(flight.getFrom());
        binding.toTxt.setText(flight.getTo());
        binding.toShortTxt.setText(flight.getToShort());
        binding.toSmallTxt.setText(flight.getTo());
        binding.dateTxt.setText(flight.getDate());
        binding.timeTxt.setText(flight.getTime());
        binding.arriveTxt.setText(flight.getArriveTime());
        binding.classTxt.setText(flight.getClassSeat());
        binding.priceTxt.setText("$"+flight.getPrice());
        binding.airlinesTxt.setText(flight.getAirlineName());
        binding.seatTxt.setText(flight.getPassenger());

        Glide.with(this)
                .load(flight.getAirlineLogo())
                .into(binding.logo);


    }

    private void getIntentExtra() {

        flight = (Flight) getIntent().getSerializableExtra("flight");
    }
}