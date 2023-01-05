package com.example.investic;

import android.content.Intent;
import android.os.Bundle;

import com.example.investic.data.LoginRepository;
import com.example.investic.data.model.Company;
import com.example.investic.profile.ProfileConfigActivity;
import com.example.investic.profile.ProfileFragment;
import com.example.investic.ui.home.HomeFragment;
import com.example.investic.ui.portfolio.PortfolioFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.Menu;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;

import db.DBHelper;
import com.example.investic.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Get the user instance from the LoginRepository
        Integer uID =  LoginRepository.getLoggedInUser().getUserId();
        DBHelper db = new DBHelper(getApplicationContext());

        //if the profile has not yet been configured for this user
        if(!db.isProfileConfigured(uID)){
            //create a new intent and redirect to the Profile Config activity.
            Intent i = new Intent(getApplicationContext(), ProfileConfigActivity.class);
            startActivity(i);
            finish();
        }
        else{
            //Set the default view to the portfolio fragment.
            replaceFragment(new PortfolioFragment());
        };
        setSupportActionBar(binding.toolbar);


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());

                    break;
                case R.id.portfolio:
                    replaceFragment(new PortfolioFragment());

                    break;
            }
            return true;
        });


    }

    private void replaceFragment(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FrameLayout fl = (FrameLayout) findViewById(R.id.frameLayout);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,f);
        ft.commit();
        Log.d("Fragment replaced",f.toString());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
     * Access the database associated with this app and get the current user portfolio details
     * including company details.

     */
    public ArrayList<Company> getPortfolioDetails(){
        Integer uID =  LoginRepository.getLoggedInUser().getUserId();
        DBHelper db = new DBHelper(getApplicationContext());
        return db.getPortfolio(uID);
    }

    //Access the database associated with this app and get the current user profile details
    public HashMap<String,Integer> getProfile(){
        Integer uID =  LoginRepository.getLoggedInUser().getUserId();
        DBHelper db = new DBHelper(getApplicationContext());
        return db.getProfile(uID);
    }
    /*
     * Access the database associated with this app and get all details for all companies.
     * Used in the home view for the list of companies

     */

    public ArrayList<Company> getAllCompanies(){
        Integer uID =  LoginRepository.getLoggedInUser().getUserId();
        DBHelper db = new DBHelper(getApplicationContext());
        return db.getAllCompanies(uID);
    }

    /*
    * Access the database associated with this app and get all details for all with a
    * ticker symbol or name matching the searchTerm
    * @param searchTerm the term to look for in the CompanyTicker and CompanyName of the Company table.
    * */
    public ArrayList<Company> searchCompanies(String searchTerm){
        Integer uID =  LoginRepository.getLoggedInUser().getUserId();
        System.out.println("SEARCH TERM: " + searchTerm);
        DBHelper db = new DBHelper(getApplicationContext());
        return db.getCompaniesByPartialMatch(searchTerm,uID);
    }

}

