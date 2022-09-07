package com.tayyba.atm.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tayyba.atm.LoginEventListener;
import com.tayyba.atm.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ATMDb extends SQLiteOpenHelper {


    private static final String DB_NAME = "atm_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String ID_COL = "id";
    private static final String UUID_COL = "uuid";
    private static final String NAME_COL = "name";
    private static final String PASSWORD_COL = "password";
    private static final String EMAIL_COL = "email";
    private static final String PIN_COL = "pin";
    private static final String WALLET_COL = "wallet";


    public ATMDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UUID_COL + " TEXT, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT UNIQUE,"
                + WALLET_COL + " INTEGER,"
                + PIN_COL + " INTEGER,"
                + PASSWORD_COL + " TEXT)";

        db.execSQL(query);
    }

    public void register(String name, int pin, String email, String password) {


        SQLiteDatabase myDB = this.getWritableDatabase();

        myDB.execSQL("INSERT INTO users(uuid,name,email,wallet,pin,password) VALUES ('" + UUID.randomUUID().toString() + "','" + name + "','" + email + "', '" + 0 + "','" + pin + "','" + password + "')");

    }

    public Boolean login(String email, String password, LoginEventListener loginEventListener) {

        SQLiteDatabase myDB = this.getReadableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.moveToNext()) {

            loginEventListener.onLoginEventListener(
                    cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );
            return true;

        }
        return false;
    }

    public User getUserInfo(int id) {

        SQLiteDatabase myDB = this.getReadableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE id = ? ", new String[]{String.valueOf(id)});

        cursor.moveToNext();

        return new User(
                id,
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getString(6)
        );
    }

    public Boolean checkEmail(String email) {

        SQLiteDatabase myDB = this.getReadableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE id = ? ", new String[]{email});

        return cursor.moveToNext();

    }

    public List<User> getAllUsers() {

        SQLiteDatabase myDB = this.getReadableDatabase();
        List<User> users = new ArrayList<>();

        Cursor cursor = myDB.rawQuery("SELECT * From users", null);

        if (cursor.moveToFirst()) {
            do {
                users.add(new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getString(6)
                ));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return users;
    }

    public void deposit(int id, int wallet) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("Update users Set wallet = '" + wallet + "' Where id = '" + id + "' ");


    }

    public void SendMoney(String email, int amount, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int rWallet = 0, sWallet = 0, deposit, withdraw;

        Cursor recipient = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

        Cursor sender = db.rawQuery("SELECT * FROM users WHERE id = ?", new String[]{String.valueOf(id)});

        if (recipient.moveToNext()) {
            rWallet = recipient.getInt(recipient.getColumnIndexOrThrow(WALLET_COL));
        }
        if (sender.moveToNext()) {
            sWallet = sender.getInt(sender.getColumnIndexOrThrow(WALLET_COL));
        }
        deposit = amount + rWallet;
        withdraw = sWallet - amount;

        db.execSQL("Update users set wallet = '" + deposit + "' WHERE email = '" + email + "'");

        db.execSQL("Update users set wallet = '" + withdraw + "' WHERE id = '" + id + "'");

    }


    public void deleteAllUser() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete From users");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
