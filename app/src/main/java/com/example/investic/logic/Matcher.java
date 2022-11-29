package com.example.investic.logic;
import android.app.Application;

import com.example.investic.InvesticApplication;
import com.example.investic.data.LoginDataSource;
import com.example.investic.data.LoginRepository;
import com.example.investic.data.model.Company;
import com.example.investic.data.model.LoggedInUser;

import java.util.ArrayList;
import java.util.List;

import db.DBHelper;
public class Matcher {

    private static volatile Matcher instance;


    // private constructor : singleton access
    private Matcher() {

    }

    public static Matcher getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new Matcher();
        }
        return instance;
    }

    public static ArrayList<Company> get10Matches(int userID){
        DBHelper db = new DBHelper(InvesticApplication.getContext());
        db.find10Matches(userID);
        return new ArrayList<Company>();
    }
}
