package com.example.lost_and_found_app_android;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Date;
import java.time.LocalDate;
public class LostAndFoundModel {
    private int itemId;
    private String userName;
    private int phoneNumber;
    private String itemDescription;
    private String date;
    private String itemLocationName;

    private double itemLocationLat;
    private double itemLocationLng;
    private boolean isDeleted;
    private String lostOrFound;
    public LostAndFoundModel(int itemId, String userName, int phoneNumber, String itemDescription, String date, boolean isDeleted, double itemLocationLat, String itemLocationName, double itemLocationLng, String lostOrFound) {
        this.itemId = itemId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.itemDescription = itemDescription;
        this.date = date;
        this.isDeleted = isDeleted;
        this.itemLocationLat = itemLocationLat;
        this.itemLocationName = itemLocationName;
        this.itemLocationLng = itemLocationLng;
        this.lostOrFound = lostOrFound;
    }
    public String toString() {
        return  "ID: " + itemId + ", Name: " + userName + ", Phone: " + phoneNumber + ", Item: " + itemDescription + ", Date: " + date + ", Item Location: " + itemLocationName + ", Lost Or Found: " + lostOrFound;
    }
    // Getters
    public int getItemId() {
        return itemId;
    }

    public String getUserName() {
        return userName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getDate() {
        return date;
    }

    public String getItemLocation() {
        return itemLocationName;
    }
    public double getItemLocationLat() {
        return itemLocationLat;
    }
    public double getItemLocationLng() {
        return itemLocationLng;
    }public boolean getIsDeleted() {
        return isDeleted;
    }

    public String getLostOrFound() {
        return lostOrFound;
    }

    // Setters
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocationName = itemLocation;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setLostOrFound(String lostOrFound) {
        this.lostOrFound = lostOrFound;
    }
}