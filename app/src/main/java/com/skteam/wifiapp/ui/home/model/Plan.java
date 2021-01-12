package com.skteam.wifiapp.ui.home.model;

public class Plan {
    String price;
    String Time;
    String Access;

    public Plan(String price, String time, String access) {
        this.price = price;
        Time = time;
        Access = access;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAccess() {
        return Access;
    }

    public void setAccess(String access) {
        Access = access;
    }
}
