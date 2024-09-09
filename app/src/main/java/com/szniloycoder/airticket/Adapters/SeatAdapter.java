package com.szniloycoder.airticket.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szniloycoder.airticket.Models.Seat;
import com.szniloycoder.airticket.R;
import com.szniloycoder.airticket.databinding.SeatIteamBinding;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.viewHolder> {

    List<Seat> seatList;
    Context context;
    ArrayList<String> selectedSeatName = new ArrayList<>();
    SelectedSeat selectedSeat;

    public SeatAdapter(List<Seat> seatList, Context context, SelectedSeat selectedSeat) {
        this.seatList = seatList;
        this.context = context;
        this.selectedSeat = selectedSeat;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_iteam, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Seat seat = seatList.get(position);

        holder.binding.seatImageView.setText(seat.getName());

        switch (seat.getStatus()){

            case AVAILABLE:
                holder.binding.seatImageView.setBackgroundResource(R.drawable.seat_available);
                holder.binding.seatImageView.setTextColor(context.getResources().getColor(R.color.white));
                break;

            case SELECTED:
                holder.binding.seatImageView.setBackgroundResource(R.drawable.seat_selected);
                holder.binding.seatImageView.setTextColor(context.getResources().getColor(R.color.black));
                break;

            case UNAVAILABLE:
                holder.binding.seatImageView.setBackgroundResource(R.drawable.seat_unavailable);
                holder.binding.seatImageView.setTextColor(context.getResources().getColor(R.color.gray));
                break;

            case EMPTY:
                holder.binding.seatImageView.setBackgroundResource(R.drawable.seat_empty);
                holder.binding.seatImageView.setTextColor(Color.parseColor("#00000000"));
                break;

        }

        holder.binding.seatImageView.setOnClickListener(view -> {

            if (seat.getStatus() == Seat.SeatStatus.AVAILABLE){
                seat.setStatus(Seat.SeatStatus.SELECTED);
                selectedSeatName.add(seat.getName()); // Add the seat name when selected
                notifyItemChanged(position);

            } else if (seat.getStatus() == Seat.SeatStatus.SELECTED) {
                seat.setStatus(Seat.SeatStatus.AVAILABLE);
                selectedSeatName.remove(seat.getName()); // Remove the seat name when deselected
                notifyItemChanged(position);
            }

            String selected = selectedSeatName.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "");

            selectedSeat.Return(selected, selectedSeatName.size());
        });

    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder{

        SeatIteamBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SeatIteamBinding.bind(itemView);

        }
    }

    public interface  SelectedSeat{
        void Return(String selectedName,int num);
    }
}
