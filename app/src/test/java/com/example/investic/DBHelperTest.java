package com.example.investic;

import org.junit.Test;

import db.DBHelper;

public class DBHelperTest {

    @Test
    public void ethicalScoreQueryIsCorrect(){

        DBHelper db = new DBHelper(InvesticApplication.getContext());
        String query = db.constructEthicalScoreQuery("NVDA",3);
        System.out.println(query);
    }
}
