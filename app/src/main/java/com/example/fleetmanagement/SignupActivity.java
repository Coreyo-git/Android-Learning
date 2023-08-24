package com.example.fleetmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fleetmanagement.Utils.SharedPrefManager;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button signupButton;
    private Button gotoLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        setViewIds();

        signupButton.setOnClickListener(main -> {
            String name = nameEditText.getText().toString();
            String phoneNumber= phoneNumberEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(isValidSignUp(name, phoneNumber, email, password)){
                Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                SharedPrefManager.setLoginState(true);
                if(SharedPrefManager.isAdmin()) {
                    SharedPrefManager.setAdmin(true);
                } else {
                    SharedPrefManager.setAdmin(false);
                }
            } else {
                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
            }
        });

        gotoLoginButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this,
                    LoginActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds()
    {
        nameEditText = findViewById(R.id.nameEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);
        gotoLoginButton = findViewById(R.id.gotoLoginButton);
    }

    private boolean isValidSignUp(String name, String phoneNumber, String email, String password)
    {
        // Check name
        if (name.length() < 3 || !name.matches("^[a-zA-Z]+$")) {
            System.out.println("Error: Name should have at least three alphabet characters.");
            return false;
        }

        // Check email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Error: Invalid email format.");
            return false;
        }

        // Check phone number format
        if (!phoneNumber.matches("^\\d{10}$")) {
            System.out.println("Error: Invalid phone number format. (10 digits required)");
            return false;
        }

        // Check password length
        if (password.length() < 5) {
            System.out.println("Error: Password should have at least five characters.");
            return false;
        }

        // If everything is okay
        return true;
    }
}