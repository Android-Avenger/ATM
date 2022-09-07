package com.tayyba.atm.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tayyba.atm.MainActivity;
import com.tayyba.atm.R;
import com.tayyba.atm.sharedpref.PrefsController;

public class SplashScreen extends AppCompatActivity {

    Boolean Registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() ->
        {

            int persistedId = PrefsController.getInstance(this).getLoginUserId();

            if (persistedId == -1) {
                startActivity(new Intent(this, LoginScreen.class));
            }
            else {
                startActivity(new Intent(this, MainActivity.class));
            }

        }, 3000);

    }
}