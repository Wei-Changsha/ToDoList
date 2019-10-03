package com.example.todolist.db;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {
    private int id;
    private String account;
    private String password;
    private boolean isRemember;

    public User(String _account,String _password){
        this.account=_account;
        this.password=_password;
    }
    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }
}
