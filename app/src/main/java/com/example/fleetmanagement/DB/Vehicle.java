package com.example.fleetmanagement.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehicles")
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String type;
    private String number;
    private String sourcePlace;
    private String destinationPlace;
    private String currentLocation;
    private double goodsTemperature;
    private double fuelStatus;

    public Vehicle(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String vehicleNumber) {
        this.number = vehicleNumber;
    }

    public String getSourcePlace() {
        return sourcePlace;
    }

    public void setSourcePlace(String sourcePlace) {
        this.sourcePlace = sourcePlace;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double getGoodsTemperature() {
        return goodsTemperature;
    }

    public void setGoodsTemperature(double goodsTemperature) {
        this.goodsTemperature = goodsTemperature;
    }

    public double getFuelStatus() {
        return fuelStatus;
    }

    public void setFuelStatus(double fuelStatus) {
        this.fuelStatus = fuelStatus;
    }
}
