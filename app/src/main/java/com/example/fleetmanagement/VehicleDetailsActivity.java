package com.example.fleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class VehicleDetailsActivity extends AppCompatActivity {
    private TextView vehicleNumber;
    private TextView vehicleName;
    private TextView vehicleType;
    private TextView vehicleSource;
    private TextView vehicleDestination;
    private TextView vehicleCurrentLocation;
    private TextView vehicleFuelStatus;
    private TextView vehicleGoodsTemp;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        Intent intent = getIntent();
        Vehicle vehicle = genVehicle(intent.getStringExtra("vehicleName"), intent.getStringExtra("vehicleType"));
        setViewIds();

        vehicleNumber.setText(vehicle.getNumber());
        vehicleName.setText(vehicle.getName());
        vehicleType.setText(vehicle.getType());
        vehicleSource.setText(vehicle.getSourcePlace());
        vehicleDestination.setText(vehicle.getDestinationPlace());
        vehicleCurrentLocation.setText(vehicle.getCurrentLocation());
        vehicleFuelStatus.setText(String.valueOf(vehicle.getFuelStatus()));
        vehicleGoodsTemp.setText(String.valueOf(vehicle.getGoodsTemperature()));

        backButton.setOnClickListener(view -> {
            Intent gotoIntent = new Intent(VehicleDetailsActivity.this, VehicleListActivity.class);
            startActivity(gotoIntent);
        });
    }

    private void setViewIds()
    {
        vehicleNumber = findViewById(R.id.vehicleNumber);
        vehicleName = findViewById(R.id.vehicleName);
        vehicleType = findViewById(R.id.vehicleType);
        vehicleSource = findViewById(R.id.vehicleSource);
        vehicleDestination = findViewById(R.id.vehicleDestination);
        vehicleCurrentLocation = findViewById(R.id.vehicleCurrentLocation);
        vehicleFuelStatus = findViewById(R.id.vehicleFuelStatus);
        vehicleGoodsTemp = findViewById(R.id.vehicleGoodsTemp);
        backButton = findViewById(R.id.backButton);
    }

    public Vehicle genVehicle(String name, String type) {
        Random rand = new Random();
        Vehicle vehicle = new Vehicle(name, type);
        vehicle.setNumber(String.valueOf(rand.nextInt(101)));
        vehicle.setSourcePlace("Byron Bay");
        vehicle.setDestinationPlace("Brisbane");
        vehicle.setCurrentLocation("Helensvale");
        vehicle.setFuelStatus(55.5);
        vehicle.setGoodsTemperature(12.2);
        return vehicle;
    }
}