package com.tayyba.atm.models;

public class User {

    int id;
    String uuid;
    String name;
    String email;
    int pin;
    int wallet;
    String password;

    public User(int id, String uuid, String name, String email,  int wallet,int pin, String password) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.wallet = wallet;
        this.pin = pin;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
