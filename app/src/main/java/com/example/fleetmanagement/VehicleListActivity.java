package com.example.fleetmanagement;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fleetmanagement.DB.Vehicle;
import com.example.fleetmanagement.DB.VehicleDao;
import com.example.fleetmanagement.SensorUtil.AccelerometerData;
import com.example.fleetmanagement.SensorUtil.SensorService;
import com.example.fleetmanagement.Utils.MyApp;
import com.example.fleetmanagement.Utils.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;

public class VehicleListActivity extends AppCompatActivity {
    ArrayList<Vehicle> vehicleList;
    private RecyclerView recyclerView;
    private VehicleAdapter vehicleAdapter;
    private Button btnAddNewVehicle;
    private Button btnLogOut;
    private Intent serviceIntent;
    private SensorDataReceiver dataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddNewVehicle = findViewById(R.id.btnAddVehicle);
        btnLogOut = findViewById(R.id.btnLogout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vehicleList = new ArrayList<>();
        getDataFromDatabase();
        vehicleAdapter = new VehicleAdapter(vehicleList);
        recyclerView.setAdapter(vehicleAdapter);

        manageSensorServices();
        
        vehicleAdapter.setOnItemClickListener(position -> {
            Toast.makeText(VehicleListActivity.this,
                    vehicleList.get(position).getName(), Toast.LENGTH_SHORT).show();

            Vehicle vehicle = vehicleList.get(position);

            Intent intent = new Intent(VehicleListActivity.this, VehicleDetailsActivity.class);
            intent.putExtra("vehicleId",vehicle.id);

            startActivity(intent);
        });

        if (SharedPrefManager.isAdmin()){
            btnAddNewVehicle.setVisibility(View.VISIBLE);
            btnAddNewVehicle.setOnClickListener(view -> {
                manageNewVehicleFunctionality();
            });
        }else {
            btnAddNewVehicle.setVisibility(View.GONE);
        }
        

        // Calls logout
        btnLogOut.setOnClickListener(view -> {
            handleLogout();
        });
    }

    private class SensorDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null &&
                    intent.getAction().equals("VEHICLE_SENSOR_DATA")) {
                // Receive the sensor data and update the UI.
                AccelerometerData accelerometerData = (AccelerometerData)
                        intent.getSerializableExtra("accelerometerData");

                // Making changes in the UI based on sensor data
                if (accelerometerData != null && accelerometerData.getMagnitude()
                        < 9.81){
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor
                            (R.color.white, null));
                }else {
                    Toast.makeText(VehicleListActivity.this, "Danger!!!",
                            Toast.LENGTH_SHORT).show();
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor
                            (R.color.red, null));
                }
            }
        }
    }

    private void manageSensorServices() {
        // Start the SensorService
        serviceIntent = new Intent(this, SensorService.class);
        startService(serviceIntent);

        // Register the BroadcastReceiver
        dataReceiver = new SensorDataReceiver();

        // Filter that returns the sensor data
        IntentFilter filter = new IntentFilter("VEHICLE_SENSOR_DATA");

        // registering the filter with our dataReceiver
        registerReceiver(dataReceiver, filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver
        if (dataReceiver != null) {
            unregisterReceiver(dataReceiver);
        }
    }

    private void manageNewVehicleFunctionality() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_vehicle, null);
        builder.setView(dialogView);

        EditText editTextVehicleName = dialogView.findViewById(R.id.editTextVehicleName);
        EditText editTextVehicleType = dialogView.findViewById(R.id.editTextVehicleType);
        EditText editTextVehicleLicensePlate = dialogView.findViewById(R.id.editTextVehicleLicensePlate);
        EditText editTextVehicleSourcePlace = dialogView.findViewById(R.id.editTextVehicleSourcePlace);
        EditText editTextVehicleDestinationPlace = dialogView.findViewById(R.id.editTextVehicleDestinationPlace);
        EditText editTextVehicleCurrentLocation = dialogView.findViewById(R.id.editTextVehicleCurrentLocation);
        EditText editTextVehicleGoodsTemperature = dialogView.findViewById(R.id.editTextVehicleGoodsTemperature);
        EditText editTextVehicleFuelStatus = dialogView.findViewById(R.id.editTextVehicleFuelStatus);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String vehicleName = editTextVehicleName.getText().toString();
            String vehicleType = editTextVehicleType.getText().toString();
            String vehicleLicensePlate = editTextVehicleLicensePlate.getText().toString();
            String vehicleSourcePlace = editTextVehicleSourcePlace.getText().toString();
            String vehicleDestinationPlace = editTextVehicleDestinationPlace.getText().toString();
            String vehicleCurrentLocation = editTextVehicleCurrentLocation.getText().toString();
            Double vehicleGoodsTemperature = Double.parseDouble(editTextVehicleGoodsTemperature.getText().toString());
            Double vehicleFuelStatus = Double.parseDouble(editTextVehicleFuelStatus.getText().toString());

            // Add the new vehicle to the list
            Vehicle vehicle = new Vehicle(vehicleName, vehicleType);
            vehicle.setLicensePlate(vehicleLicensePlate);
            vehicle.setSourcePlace(vehicleSourcePlace);
            vehicle.setDestinationPlace(vehicleDestinationPlace);
            vehicle.setCurrentLocation(vehicleCurrentLocation);
            vehicle.setGoodsTemperature(vehicleGoodsTemperature);
            vehicle.setFuelStatus(vehicleFuelStatus);

            VehicleDao vehicleDao = MyApp.getAppDatabase().vehicleDao();
            AsyncTask.execute(() -> {
                vehicleDao.insert(vehicle);
            });

            // Refresh the RecyclerView
            //vehicleAdapter.notifyDataSetChanged();
        });

        AlertDialog dialog = builder.create();
        builder.setNegativeButton("Cancel", null);
        dialog.show();

    }

    public void handleLogout() {
        SharedPrefManager.setAdmin(false);
        SharedPrefManager.setLoginState(false);
        Intent intent = new Intent(VehicleListActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    // Replace this method with your actual vehicle data
    private void getDataFromDatabase() {
    // Retrieve all vehicles asynchronously using LiveData
        VehicleDao vehicleDao = MyApp.getAppDatabase().vehicleDao();
        LiveData<List<Vehicle>> vehiclesLiveData = vehicleDao.getAllVehicles();
        vehiclesLiveData.observe(this, vehicles -> { // Handle the list of vehicles here
            vehicleList.clear(); // Removes all the existing data
            vehicleList.addAll(vehicles); // Adding all the data from Database
            vehicleAdapter.notifyDataSetChanged(); // Notifying the RecyclerView that the dataset has been changed through its adapter
        });
    }
}