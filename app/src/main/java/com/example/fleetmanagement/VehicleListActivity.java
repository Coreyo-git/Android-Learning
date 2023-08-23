package com.example.fleetmanagement;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
public class VehicleListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VehicleAdapter vehicleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Vehicle> vehicleList = generateDummyData(); // Replace this with your actual vehicle data
                vehicleAdapter = new VehicleAdapter(vehicleList);
        recyclerView.setAdapter(vehicleAdapter);
        vehicleAdapter.setOnItemClickListener(position -> {
            Toast.makeText(VehicleListActivity.this,
                    vehicleList.get(position).getName(), Toast.LENGTH_SHORT).show();

            String vehicleName = vehicleList.get(position).getName();
            String vehicleType = vehicleList.get(position).getType();

            Intent intent = new Intent(VehicleListActivity.this, VehicleDetailsActivity.class);
            intent.putExtra("vehicleName", vehicleName);
            intent.putExtra("vehicleType", vehicleType);

            startActivity(intent);
        });
    }
    // Replace this method with your actual vehicle data
    private ArrayList<Vehicle> generateDummyData() {
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle("Car 1", "Sedan"));
        vehicleList.add(new Vehicle("Truck 1", "Heavy Duty"));
        vehicleList.add(new Vehicle("Car 2", "Coupe"));
        vehicleList.add(new Vehicle("Truck 2", "Double Decker"));
        vehicleList.add(new Vehicle("Car 3", "Van"));
        vehicleList.add(new Vehicle("Truck 3", "Heavy Duty"));

        // Add more vehicles as needed, I have added few more for the scrolling feature.
        return vehicleList;
    }
}