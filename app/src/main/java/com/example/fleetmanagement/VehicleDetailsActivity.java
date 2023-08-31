package com.example.fleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleetmanagement.DB.Vehicle;
import com.example.fleetmanagement.DB.VehicleDao;
import com.example.fleetmanagement.Utils.MyApp;

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
    private Button updateButton;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        setViewIds();
        VehicleDao vehicleDao = MyApp.getAppDatabase().vehicleDao();

        if (getIntent().getIntExtra("vehicleId", -1) != -1){
            int vehicleId = getIntent().getIntExtra("vehicleId", -1);

            vehicleDao.getVehicleById(vehicleId).observe(this, vehicle -> {
                genVehicleProps(vehicle);

                vehicleNumber.setText(String.valueOf(vehicleId));
                vehicleName.setText(vehicle.getName());
                vehicleType.setText(vehicle.getType());
                vehicleSource.setText(vehicle.getSourcePlace());
                vehicleDestination.setText(vehicle.getDestinationPlace());
                vehicleCurrentLocation.setText(vehicle.getCurrentLocation());
                vehicleFuelStatus.setText(String.valueOf(vehicle.getFuelStatus()));
                vehicleGoodsTemp.setText(String.valueOf(vehicle.getGoodsTemperature()));
            });

        }else {
            Toast.makeText(this, "No Vehicle Id Found", Toast.LENGTH_SHORT).show();
        }

        backButton.setOnClickListener(view -> {
            Intent gotoIntent = new Intent(VehicleDetailsActivity.this, VehicleListActivity.class);
            startActivity(gotoIntent);
        });

        deleteButton.setOnClickListener(view -> {
        });
        updateButton.setOnClickListener(view -> {
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
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
    }

    public void genVehicleProps(Vehicle vehicle) {
        vehicle.setSourcePlace("Byron Bay");
        vehicle.setDestinationPlace("Brisbane");
        vehicle.setCurrentLocation("Helensvale");
        vehicle.setFuelStatus(55.5);
        vehicle.setGoodsTemperature(12.2);
    }
}