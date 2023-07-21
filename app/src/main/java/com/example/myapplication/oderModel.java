package com.example.myapplication;

public class oderModel {
    private String oderId;
    private String address;
    private String orderStatus;
    private String note;

    // creating constructor for our variables.
    public oderModel(String oderId, String address, String orderStatus, String note) {
        this.oderId = oderId;
        this.address = address;
        this.orderStatus = orderStatus;
        this.note = note;
    }

    // creating getter and setter methods.
    public String oderId() {
        return oderId;
    }

    public void oderId(String oderId) {
        this.oderId = oderId;
    }

    public String address() {
        return address;
    }

    public void address(String address) {
        this.address = address;
    }

    public String orderStatus() {
        return orderStatus;
    }

    public void orderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String note() {
        return note;
    }

    public void note(String note) {
        this.note = note;
    }


}
