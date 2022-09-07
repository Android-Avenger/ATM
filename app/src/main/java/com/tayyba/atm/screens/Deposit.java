package com.tayyba.atm.screens;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.tayyba.atm.R;
import com.tayyba.atm.db.ATMDb;
import com.tayyba.atm.models.User;
import com.tayyba.atm.sharedpref.PrefsController;

public class Deposit extends AppCompatActivity {

    EditText mAmount, mPin;
    MaterialCardView mDeposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        ATMDb db = new ATMDb(this);

        mAmount = findViewById(R.id.depositAmount);
        mPin = findViewById(R.id.depositPin);

        mDeposit = findViewById(R.id.depositButton);

        User user = db.getUserInfo(PrefsController.getInstance(this).getLoginUserId());

        int id = user.getId();
        int wallet = user.getWallet();
        int pin = user.getPin();

        mDeposit.setOnClickListener(v -> {

            int amount = Integer.parseInt(mAmount.getText().toString().trim());
            int myPin = Integer.parseInt(mPin.getText().toString().trim());

            int i = wallet + amount;

            if (myPin == pin) {

                db.deposit(id ,i );
                Toast.makeText(this, "Your Money has been Deposited", Toast.LENGTH_SHORT).show();

            } else {

                mPin.setError("Wrong Pin");
                mPin.requestFocus();
                mPin.setText("");
            }
        });
    }

}