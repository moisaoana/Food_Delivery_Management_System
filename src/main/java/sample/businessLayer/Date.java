package sample.businessLayer;

import java.io.Serializable;

public class Date implements Serializable {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minutes;
    private int seconds;
    public Date(int day, int month, int year, int hour, int minutes, int seconds){
        this.day=day;
        this.month=month;
        this.year=year;
        this.hour=hour;
        this.minutes=minutes;
        this.seconds=seconds;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
