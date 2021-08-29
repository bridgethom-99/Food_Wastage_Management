package com.steph.foodwastagemanagement;

public class Event {
    private String Location;
    private String  Plates;
    private String Date;

    public Event() {
    }

    public Event(String location, String plates, String date) {
        this.Location = location;
        this.Plates = plates;
        this.Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPlates() {
        return Plates;
    }

    public void setPlates(String plates) {
        Plates = plates;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
