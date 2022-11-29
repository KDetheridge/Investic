package com.example.investic;

import android.os.Bundle;

import com.example.investic.data.LoginRepository;
import com.example.investic.profile.ProfileFragment;
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

        setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.home:
                    //replaceFragment(new HomeFragment());
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

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    private void replaceFragment(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FrameLayout fl = (FrameLayout) findViewById(R.id.frameLayout);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,f);
        ft.commit();
//        if(f instanceof PortfolioFragment){
//            FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
//            FrameLayout linearLayout1 = (FrameLayout) fl.findViewById(R.id.fragment_portfolio);
//            LinearLayout portfLinLayout = (LinearLayout) linearLayout1.findViewById(R.id.linear_layout);
//            //portfLinLayout.addView(new CompanyPortfolioView(this));
//            View.inflate(this, R.layout.company_portfolio_view, portfLinLayout);
//            View.inflate(this, R.layout.company_portfolio_view, portfLinLayout);
//            View.inflate(this, R.layout.company_portfolio_view, portfLinLayout);
//        }
        Log.d("Fragment replaced",f.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public ArrayList<HashMap<String,String>> getPortfolioDetails(){

        Integer uID =  LoginRepository.getLoggedInUser().getUserId();
        DBHelper db = new DBHelper(getApplicationContext());
        return db.getPortfolio(uID);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }
//        if(id == R.id.action_profile){
//
//            Intent intent = new Intent(getApplicationContext(),ProfileFragment.class);
//            Fragment fragment = new ProfileFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}