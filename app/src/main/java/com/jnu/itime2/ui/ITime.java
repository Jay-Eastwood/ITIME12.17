package com.jnu.itime2.ui;

import java.io.Serializable;

public class ITime implements Serializable {
    private String name;
    private String price;
    private int pictureId;
    private String time;
    private int year;
    private int day;
    private int hour;
    private int min;
    private int sec;
    private int month;
    private int pictureTime;

    public ITime(String name, String price, int pictureId, String time,int year,int month,int day,int pictureTime ) {
        this.name = name;
        this.price = price;
        this.pictureId = pictureId;
        this.time=time;
        this.year=year;
        this.day=day;
        this.month=month;
        this.hour=hour;
        this.min=min;
        this.sec=sec;
        this.pictureTime= pictureTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setyear(int year) { this.year = year; }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) { this.sec = sec; }

    public int getpictureTime() { return pictureTime; }
    public void setpictureTime(int pictureTime) { this.pictureTime =pictureTime; }




}
