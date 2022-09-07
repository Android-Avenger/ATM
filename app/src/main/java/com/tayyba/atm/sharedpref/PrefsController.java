package com.tayyba.atm.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsController {

    SharedPreferences sharedPreferences;

    public PrefsController(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveLoginUserId(int id,int pin,int wallet,String name ,String email,int password) {
        sharedPreferences.edit()
                .putInt("id", id)
                .putInt("pin",pin)
                .putInt("wallet",wallet)
                .putString("name",name)
                .putString("email",email)
                .putInt("password",password)
                .apply();
    }


    public int getLoginUserId() {
        return sharedPreferences.getInt("id", -1);
    }

    public int getPin(){
        return sharedPreferences.getInt("pin", 0);
    }
    public int getWallet(){
        return sharedPreferences.getInt("wallet", 0);
    }
    public int getPassword(){
        return sharedPreferences.getInt("password", 0);
    }




    public void removeLoginUserId() {
        sharedPreferences.edit().remove("id").apply();
    }

    public static PrefsController getInstance(Context context) {
        return new PrefsController(
                context.getSharedPreferences("prefs_controller",
                        Context.MODE_PRIVATE
                )
        );
    }

}
