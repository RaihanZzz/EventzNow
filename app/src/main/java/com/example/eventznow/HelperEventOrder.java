package com.example.eventznow;

import java.util.List;

public class HelperEventOrder {
    String eventID, orderID, eventname, payment, amount, totalpay;
    public String getEventID() {
        return eventID;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public String getEventname() {
        return eventname;
    }
    public void setEventname(String eventname) {
        this.eventname = eventname;
    }
    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getTotalpay() {
        return totalpay;
    }
    public void setTotalpay(String totalpay) {
        this.totalpay = totalpay;
    }
    public HelperEventOrder(String eventID, String orderID, String eventname, String payment, String amount, String totalpay) {
        this.eventID = eventID;
        this.orderID = orderID;
        this.eventname = eventname;
        this.payment = payment;
        this.amount = amount;
        this.totalpay = totalpay;
    }
    public HelperEventOrder() {
    }
}