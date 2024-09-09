package com.szniloycoder.airticket.Models;

import android.content.Intent;

import java.io.Serializable;

public class Flight  implements Serializable {

    String airlineLogo;
    String airlineName;
    String arriveTime;
    String classSeat;
    String date;
    String from;
    String fromShort;
    Integer numberSeat;
    Double price;
    String reservedSeats;
    String time;
    String to;
    String toShort;
    String passenger;

    @Override
    public String toString() {
        return from;
    }

    public Flight() {
    }

    public Flight(String airlineLogo, String airlineName, String arriveTime, String classSeat, String date, String from, String fromShort, Integer numberSeat, Double price, String reservedSeats, String time, String to, String toShort, String passenger) {
        this.airlineLogo = airlineLogo;
        this.airlineName = airlineName;
        this.arriveTime = arriveTime;
        this.classSeat = classSeat;
        this.date = date;
        this.from = from;
        this.fromShort = fromShort;
        this.numberSeat = numberSeat;
        this.price = price;
        this.reservedSeats = reservedSeats;
        this.time = time;
        this.to = to;
        this.toShort = toShort;
        this.passenger = passenger;
    }

    public String getAirlineLogo() {
        return airlineLogo;
    }

    public void setAirlineLogo(String airlineLogo) {
        this.airlineLogo = airlineLogo;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getClassSeat() {
        return classSeat;
    }

    public void setClassSeat(String classSeat) {
        this.classSeat = classSeat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromShort() {
        return fromShort;
    }

    public void setFromShort(String fromShort) {
        this.fromShort = fromShort;
    }

    public Integer getNumberSeat() {
        return numberSeat;
    }

    public void setNumberSeat(Integer numberSeat) {
        this.numberSeat = numberSeat;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(String reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToShort() {
        return toShort;
    }

    public void setToShort(String toShort) {
        this.toShort = toShort;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }
}
