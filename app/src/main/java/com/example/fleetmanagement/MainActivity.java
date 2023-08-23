package com.example.fleetmanagement;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Button;

import com.example.fleetmanagement.Utils.SharedPrefManager;
import com.example.fleetmanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private TextView titleTextView;
    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefManager.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, VehicleListActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);

            setViewIds();

            loginButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(intent);
            });

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
    }

}