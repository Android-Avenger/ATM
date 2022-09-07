package com.tayyba.atm.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tayyba.atm.R;
import com.tayyba.atm.db.ATMDb;

public class RegisterScreen extends AppCompatActivity {

    TextView mLogin;
    Button mRegister;
    EditText mName, mEmail, mPin, mPassword, mConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        ATMDb db = new ATMDb(this);

        mLogin = findViewById(R.id.loginR);
        mRegister = findViewById(R.id.RegisterButton);

        mName = findViewById(R.id.nameR);
        mEmail = findViewById(R.id.EmailR);
        mPin = findViewById(R.id.PinR);
        mPassword = findViewById(R.id.PasswordR);
        mConfirmPassword = findViewById(R.id.confirmPassword);


        mRegister.setOnClickListener(v -> {

            if (mName.getText().toString().equals("") ||
                    mEmail.getText().toString().equals("") ||
                    mPin.getText().toString().equals("") ||
                    mPassword.getText().toString().equals("") ||
                    mConfirmPassword.getText().toString().equals("")
            ) {
                if (mName.getText().toString().equals("")) {
                    mName.setError("Enter your Name");
                    mName.requestFocus();

                } else if (mEmail.getText().toString().equals("")) {
                    mEmail.setError("Enter your Confirm");
                    mEmail.requestFocus();
                }else if (mPin.getText().toString().equals("")) {
                    mPin.setError("Enter your Pin");
                    mPin.requestFocus();

                } else if (mPassword.getText().toString().equals("")) {
                    mPassword.setError("Enter your Password");
                    mPassword.requestFocus();

                } else if (mConfirmPassword.getText().toString().equals("")) {
                    mConfirmPassword.setError("Enter your Confirm");
                    mConfirmPassword.requestFocus();
                }
            } else {

                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                int pin = Integer.parseInt(mPin.getText().toString().trim());
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();

                if (!password.equals(confirmPassword)) {
                    mPassword.setError("passwords did not match ");
                    mPassword.requestFocus();
                    mConfirmPassword.setText("");
                } else {


                    db.register(name, pin, email, password);

                    mName.setText("");
                    mPin.setText("");
                    mPassword.setText("");
                    mEmail.setText("");
                    mConfirmPassword.setText("");

                    Toast.makeText(this, "User has been Registered", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(RegisterScreen.this, LoginScreen.class);
                    startActivity(i);

                }
            }
        });


        mLogin.setOnClickListener(v -> {
            Intent i = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(i);
        });

    }
}