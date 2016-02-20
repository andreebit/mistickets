package com.sandovalrojas.paselibre.models;

/**
 * Created by andree on 19/02/16.
 */
public class Ticket {

    private int id;
    private String event;
    private String description;
    private double price;
    private int quantity;
    private String qr;
    private String status;

    public Ticket(int id, String event, String description, double price, int quantity, String qr, String status) {
        this.id = id;
        this.event = event;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.qr = qr;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
