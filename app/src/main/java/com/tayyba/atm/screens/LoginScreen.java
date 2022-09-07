package com.tayyba.atm.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tayyba.atm.LoginEventListener;
import com.tayyba.atm.MainActivity;
import com.tayyba.atm.R;
import com.tayyba.atm.db.ATMDb;
import com.tayyba.atm.sharedpref.PrefsController;


public class LoginScreen extends AppCompatActivity implements LoginEventListener  {

    EditText mEmail,mPassword;
    Button mLogin ;
    TextView mRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ATMDb db = new ATMDb(this);

        mLogin = findViewById(R.id.Login);
        mEmail = findViewById(R.id.Email);
        mPassword =findViewById(R.id.password);
        mRegister = findViewById(R.id.register);


        mLogin.setOnClickListener(v -> {

            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
           Boolean isLogin = db.login(email,password,this );



        });

        mRegister.setOnClickListener(v->{
            Intent i = new Intent(LoginScreen.this,RegisterScreen.class);
            startActivity(i);
        });
    }


    @Override
    public void onLoginEventListener(int id, String name, String email, int wallet, int pin, int password) {
        PrefsController.getInstance(this).saveLoginUserId(id,pin,wallet,name,email,password);
        startActivity(new Intent(LoginScreen.this,MainActivity.class));
        finish();
    }
}