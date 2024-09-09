package com.szniloycoder.airticket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.database.FirebaseDatabase;
import com.szniloycoder.airticket.Adapters.SeatAdapter;
import com.szniloycoder.airticket.Models.Flight;
import com.szniloycoder.airticket.Models.Seat;
import com.szniloycoder.airticket.databinding.ActivitySeatListBinding;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatListActivity extends AppCompatActivity {
    ActivitySeatListBinding binding;
    Flight flight;
    Double price = 0.0;
    int num = 0;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        database = FirebaseDatabase.getInstance();


        getIntentExtra();
        initSeatList();
        setVariable();

    }

    private void setVariable() {

        binding.btnBack.setOnClickListener(view -> finish());

        binding.btnConfirm.setOnClickListener(view -> {

            if (num > 0) {
                flight.setPassenger(binding.nameSeatSelectedTxt.getText().toString());
                flight.setPrice(price);
                Intent intent = new Intent(SeatListActivity.this, TicketDetailActivity.class);
                intent.putExtra("flight", flight);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select your seat", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getIntentExtra() {

        flight = (Flight) getIntent().getSerializableExtra("flight");
    }


    private void initSeatList() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,7);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 7 == 3) ? 1 : 1;
            }
        });

        binding.seatRecylerView.setLayoutManager(gridLayoutManager);

        List<Seat> seatList = new ArrayList<>();

        int row = 0;
        int numberSeat = flight.getNumberSeat() + (flight.getNumberSeat()/7) + 1;

        Map<Integer, String> seatAlphabetMap = new HashMap<>();
        seatAlphabetMap.put(0,"A");
        seatAlphabetMap.put(1,"B");
        seatAlphabetMap.put(2,"C");
        seatAlphabetMap.put(4,"D");
        seatAlphabetMap.put(5,"E");
        seatAlphabetMap.put(6,"F");

        for (int i = 0; i<numberSeat; i++){
            if (i%7==0){
                row++;
            }
            if (i%7==3){
                seatList.add(new Seat(Seat.SeatStatus.EMPTY, String.valueOf(row)));
            }else {
                String seatName = seatAlphabetMap.get(i % 7) + row;
                Seat.SeatStatus seatStatus = flight.getReservedSeats()
                        .contains(seatName) ? Seat.SeatStatus.UNAVAILABLE : Seat.SeatStatus.AVAILABLE;
                seatList.add(new Seat(seatStatus, seatName));

            }
        }

        @SuppressLint("SetTextI18n") SeatAdapter seatAdapter = new SeatAdapter(seatList,this, (selectedName, num) -> {

            binding.numberSelectedTxt.setText(num +" Seat Selected");
            binding.nameSeatSelectedTxt.setText(selectedName);
            DecimalFormat df = new DecimalFormat("#.##");
            price = (Double.valueOf(df.format(num * flight.getPrice())));
            this.num = num;
            binding.pricesTxt.setText("$" + price);
        });


        binding.seatRecylerView.setAdapter(seatAdapter);
        binding.seatRecylerView.setNestedScrollingEnabled(false);

    }


}