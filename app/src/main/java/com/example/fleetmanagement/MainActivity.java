package com.example.fleetmanagement;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.example.fleetmanagement.Utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    private TextView titleTextView;
    private Button loginButton, signupButton, addVehicleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViewIds();

        if(SharedPrefManager.isLoggedIn()) {
            addVehicleButton.setVisibility(View.VISIBLE);
            addVehicleButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, VehicleListActivity.class);
                startActivity(intent);
            });
        } else {
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            });

            signupButton.setVisibility(View.VISIBLE);
            signupButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this,
                        SignupActivity.class);
                startActivity(intent);
            });
        }
    }



    private void setViewIds()
    {
        titleTextView = findViewById(R.id.title);
        loginButton = findViewById(R.id.btnLogin);
        signupButton = findViewById(R.id.btnSignup);
        addVehicleButton = findViewById(R.id.btnAddVehicle);
    }

}