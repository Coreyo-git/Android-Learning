package com.example.fleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fleetmanagement.Utils.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button gotoSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViewIds();

        emailEditText.setText("ad@ad.com");
        passwordEditText.setText("ad123");

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(isValidCredentials(email, password)) {
                // Successful login, navigate to next activity
                Toast.makeText(LoginActivity.this, "Login successful",
                        Toast.LENGTH_SHORT).show();

                SharedPrefManager.setLoginState(true);
                if (email.equals("ad@ad.com")){
                    SharedPrefManager.setAdmin(true);
                }else {
                    SharedPrefManager.setAdmin(false);
                }
                Intent intent = new Intent(this, VehicleListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                emailEditText.setError("Invalid email");
                passwordEditText.setError("Invalid password");
            }
        });

        gotoSignUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);

        });
    }

    private void setViewIds()
    {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        gotoSignUpButton = findViewById(R.id.gotoSignUpButton);
    }

    private boolean isValidCredentials(String email, String password) {
        // Perform validation logic here
        // Return true if credentials are valid, false otherwise
        return (email.equals("ex@ex.com") && password.equals("ex123")) ||
                (email.equals("ad@ad.com") && password.equals("ad123"));
    }
}