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

public class WithDraw extends AppCompatActivity {

    EditText mAmount, mPin;
    MaterialCardView mWithDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);

        ATMDb db = new ATMDb(this);

        mAmount = findViewById(R.id.withdrawAmount);
        mPin = findViewById(R.id.withdrawPin);
        mWithDraw = findViewById(R.id.withdrawButton);

        User user = db.getUserInfo(PrefsController.getInstance(this).getLoginUserId());

        int id = user.getId();
        int wallet = user.getWallet();
        int pin = user.getPin();

        mWithDraw.setOnClickListener(v -> {

            int amount = Integer.parseInt(mAmount.getText().toString().trim());
            int myPin = Integer.parseInt(mPin.getText().toString().trim());

            if (wallet < amount) {
                mAmount.setError("You have insufficient amount of money ");
                mAmount.requestFocus();
                mAmount.setText("");

            } else {

                int i = wallet - amount;

                if (myPin == pin) {

                    db.deposit(id, i);
                    Toast.makeText(this, "Your Money has been withDrawn", Toast.LENGTH_SHORT).show();

                } else {

                    mPin.setError("Wrong Pin");
                    mPin.requestFocus();
                    mPin.setText("");
                }
            }
        });

    }
}