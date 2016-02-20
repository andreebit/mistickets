package com.sandovalrojas.paselibre.models;

/**
 * Created by andree on 19/02/16.
 */
public class Order {
    private int id;
    private String number;
    private String dateTime;

    public Order(int id, String number, String dateTime) {
        this.id = id;
        this.number = number;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
