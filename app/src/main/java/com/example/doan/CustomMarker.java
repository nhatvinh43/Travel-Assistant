package com.example.doan;

public class CustomMarker {
    private double lat, lng;
    private String name;
    private int type;

    public CustomMarker(double lat, double lng, String name, int type){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.type = type;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getType() {
        return type;
    }

}
