package com.szniloycoder.airticket.Models;

public class Location {

    private int Id;

    private String Name;

    public Location() {
    }

    public Location(int id, String name) {
        Id = id;
        Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
