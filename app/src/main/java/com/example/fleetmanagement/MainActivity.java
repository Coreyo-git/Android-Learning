package com.example.fleetmanagement;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.fleetmanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private TextView titleTextView;
    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewIds();

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        });

        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    SingupActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds()
    {
        titleTextView = findViewById(R.id.title);
        loginButton = findViewById(R.id.btnLogin);
        signupButton = findViewById(R.id.btnSignup);
    }

}