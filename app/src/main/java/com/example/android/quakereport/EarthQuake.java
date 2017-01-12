package com.example.android.quakereport;

/**
 * Created by priyankanandiraju on 12/18/16.
 */

public class EarthQuake {
    private double magnitude;
    private String name;
    private long date;
    private String url;

    public EarthQuake(double magnitude, String name, long date, String url) {
        this.magnitude = magnitude;
        this.name = name;
        this.date = date;
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EarthQuake{" +
                "magnitude=" + magnitude +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", url='" + url + '\'' +
                '}';
    }
}
