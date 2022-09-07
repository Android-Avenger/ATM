package com.tayyba.atm;

public interface LoginEventListener {

    void onLoginEventListener(int id , String name ,String email , int wallet , int pin , int password);
}
