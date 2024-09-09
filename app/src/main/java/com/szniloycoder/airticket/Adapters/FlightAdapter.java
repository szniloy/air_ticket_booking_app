package com.szniloycoder.airticket.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.szniloycoder.airticket.Models.Flight;
import com.szniloycoder.airticket.R;
import com.szniloycoder.airticket.SeatListActivity;
import com.szniloycoder.airticket.databinding.ViewholderFlightBinding;

import java.util.ArrayList;

public class FlightAdapter extends  RecyclerView.Adapter<FlightAdapter.viewHolder>{

    ArrayList<Flight> list;
    Context context;

    public FlightAdapter(ArrayList<Flight> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_flight, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Flight flight = list.get(position);

        Glide.with(context)
                .load(flight.getAirlineLogo())
                .into(holder.binding.logo);

        holder.binding.fromTxt.setText(flight.getFrom());
        holder.binding.fromShortTxt.setText(flight.getFromShort());
        holder.binding.toTxt.setText(flight.getTo());
        holder.binding.toShortTxt.setText(flight.getToShort());
        holder.binding.arriveTxt.setText(flight.getArriveTime());
        holder.binding.classTxt.setText(flight.getClassSeat());
        holder.binding.priceTxt.setText("$"+flight.getPrice());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, SeatListActivity.class);
            intent.putExtra("flight", flight);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{

        ViewholderFlightBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ViewholderFlightBinding.bind(itemView);
        }
    }
}
