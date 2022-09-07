package com.tayyba.atm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tayyba.atm.db.ATMDb;
import com.tayyba.atm.models.User;
import com.tayyba.atm.screens.Deposit;
import com.tayyba.atm.screens.LoginScreen;
import com.tayyba.atm.screens.UserList;
import com.tayyba.atm.screens.WithDraw;
import com.tayyba.atm.sharedpref.PrefsController;

public class MainActivity extends AppCompatActivity {

    LinearLayout mSendMoney;
    ImageView mDeposit, mWithDraw, mDataBase, mLogout;
    TextView mName, mWallet;
    FrameLayout deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ATMDb db = new ATMDb(this);

        mName = findViewById(R.id.nameWL);
        mWallet = findViewById(R.id.userWalletWL);
        mDataBase = findViewById(R.id.database);
        mDeposit = findViewById(R.id.deposit);
        mLogout = findViewById(R.id.logoutUser);
        deleteAll = findViewById(R.id.deleteall);
        mWithDraw = findViewById(R.id.withdraw);
        mSendMoney = findViewById(R.id.sendMoney);

        deleteAll.setOnClickListener(v -> {

            db.deleteAllUser();
            PrefsController.getInstance(this).removeLoginUserId();

        });

        User user = db.getUserInfo(PrefsController.getInstance(this).getLoginUserId());

        String w = "$ " + user.getWallet();

        mName.setText(user.getName());
        mWallet.setText(w);

        mLogout.setOnClickListener(v -> {
            PrefsController.getInstance(this).removeLoginUserId();
            startActivity(new Intent(MainActivity.this, LoginScreen.class));
            finish();
        });

        mDeposit.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Deposit.class));
        });

        mWithDraw.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WithDraw.class));
        });

        mSendMoney.setOnClickListener(v -> {
            showDialog(user.getId(),user.getPin(),user.getWallet(), db);
        });

        mDataBase.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, db.getAllUsers().size() + "", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, UserList.class);
            startActivity(i);
        });

    }

    private void showDialog(int id, int pin, int wallet, ATMDb db) {

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.send_money_dialog);

        EditText mEmail = dialog.findViewById(R.id.recipientEmail);
        EditText mAmount = dialog.findViewById(R.id.senderAmount);
        EditText mPin = dialog.findViewById(R.id.senderPin);
        TextView mSend = dialog.findViewById(R.id.send);

        mSend.setOnClickListener(v -> {

            String email = mEmail.getText().toString().trim();
            int sPin = Integer.parseInt(mPin.getText().toString().trim());
            int amount = Integer.parseInt(mAmount.getText().toString().trim());

            if (sPin != pin || wallet < amount) {
                if (wallet < amount) {
                    mAmount.setError("you have insufficient amount of money ");
                    mAmount.requestFocus();
                    mAmount.setText("");
                } else {
                    mPin.setError("incorrect pin");
                    mPin.requestFocus();
                    mPin.setText("");

                }

            }

            if (!db.checkEmail(email)) {

                db.SendMoney(email, amount, id);
                Toast.makeText(this, "Successfully transferred", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(dialog::dismiss, 300);

            }else {
                mEmail.setError("User does not exist ");
                mEmail.requestFocus();
                mEmail.setText("");
            }

        });

        dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        if (window != null) {
            layoutParams.copyFrom(window.getAttributes());
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

    }
}