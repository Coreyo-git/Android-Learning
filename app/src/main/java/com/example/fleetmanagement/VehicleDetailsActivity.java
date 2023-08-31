package com.example.fleetmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    int vehicleId;
    Vehicle vehicle;
    VehicleDao vehicleDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        setViewIds();
        vehicleDao = MyApp.getAppDatabase().vehicleDao();

        if (getIntent().getIntExtra("vehicleId", -1) != -1){
            vehicleId = getIntent().getIntExtra("vehicleId", -1);

            vehicleDao.getVehicleById(vehicleId).observe(this, vehicle -> {
                this.vehicle = vehicle;
                genVehicleProps(vehicle);

                vehicleNumber.setText(String.valueOf(vehicleId));
                vehicleName.setText(vehicle.getName());
                vehicleType.setText(vehicle.getType());
                vehicleSource.setText(vehicle.getSourcePlace());
                vehicleDestination.setText(vehicle.getDestinationPlace());
                vehicleCurrentLocation.setText(vehicle.getCurrentLocation());
                vehicleFuelStatus.setText(String.valueOf(vehicle.getFuelStatus()));
                vehicleGoodsTemp.setText(String.valueOf(vehicle.getGoodsTemperature()));

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (vehicle != null) {
                            updateTheVehicle();
                        }
                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (vehicle != null) {
                            deleteTheVehicle();
                        }
                    }
                });
            });

        }else {
            Toast.makeText(this, "No Vehicle Id Found", Toast.LENGTH_SHORT).show();
        }

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

    private void updateTheVehicle() {
        // Creating the view to create the dialog. We are re-using the dialog we created in Week-4 to add new vehicle.
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_vehicle, null);
        EditText editTextVehicleName = dialogView.findViewById(R.id.editTextVehicleName);
        EditText editTextVehicleType = dialogView.findViewById(R.id.editTextVehicleType);

        // Pre-set the current vehicle name and type to these edittext views.
        editTextVehicleName.setText(vehicle.getName());
        editTextVehicleType.setText(vehicle.getType());

        //Creating the dialog builder to create the pop up dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        //Setting and implementing the update operation
        builder.setPositiveButton("Update", (dialog, which) -> {
            String vehicleName = editTextVehicleName.getText().toString();
            String vehicleType = editTextVehicleType.getText().toString();

            //update the values
            vehicle.setName(vehicleName);
            vehicle.setType(vehicleType);
            AsyncTask.execute(() -> {
                vehicleDao.update(vehicle);
            });
        });

        // Setting the update cancellation
        builder.setNegativeButton("Cancel", (dialog, which) -> {
        // Do nothing or handle any other actions
            dialog.cancel();
        });
        //Creating and showing the dialog.
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void deleteTheVehicle() {
        AsyncTask.execute(() -> {
            vehicleDao.delete(vehicle);
            finish();
        });
    }
}