package com.example.investic.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.investic.R;
import com.example.investic.data.LoginRepository;
import com.example.investic.data.model.Company;

import java.util.ArrayList;
import java.util.HashMap;

import db.DBHelper;

public class CompanyListAdapter extends BaseAdapter {

    Context context;
    String companyNameList[];
    String companyCategoryList[];

    //The general format in which values will be passed to the adapter.
    //Above variables kept for compatibility and modifiability.
    ArrayList<Company> companyList;
    LayoutInflater inflater;
    public CompanyListAdapter(Context c, String [] companyNameList, String [] companyCategoryList){
        this.context = c;
        this.companyNameList = companyNameList;
        this.companyCategoryList = companyCategoryList;
        this.inflater = LayoutInflater.from(c);
    }
    public CompanyListAdapter(Context c, ArrayList<Company> companyList){
        this.context = c;
        this.companyList = companyList;
        this.inflater = LayoutInflater.from(c);
    }


    public int getCount(){
        return this.companyList.size();
    }

    public Object getItem(int pos){
        return this.companyList.get(pos);
    }
    public long getItemId(int pos){
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //initialise view
        View cv = (View) inflater.inflate(R.layout.company_cardview, null);
        //find the company name, category, score, image views from the company_portfolio_view.xml layout file.
        TextView cnv = cv.findViewById(R.id.company_name);
        TextView ctv = cv.findViewById(R.id.company_ticker);
        TextView ccv = cv.findViewById(R.id.company_sector);

        TextView cspv = cv.findViewById((R.id.stock_price_value));
        //Company Share Price Change Value
        TextView cspcv = cv.findViewById(R.id.price_change_value);
        Button addPortfolio = cv.findViewById(R.id.add_portfolio_button);
        TextView cesv = cv.findViewById((R.id.ethical_score_value));
        if (this.companyList != null && ! this.companyList.isEmpty()) {
            Company currCompany = this.companyList.get(pos);
            cnv.setText(currCompany.getName());
            ctv.setText(currCompany.getTickerSymbol());
            ccv.setText(currCompany.getSector());
            cspv.setText('$' + String.valueOf(companyList.get(pos).getCurrentValue()));
            Float netChange = currCompany.getNetChange();

            String netChangeStr = String.valueOf(netChange);
            // <0, red; > 0, green; 0, gray.
            if(netChange < 0.0f){
                cspcv.setText(netChangeStr);
                cspcv.setTextColor(Color.RED);
            }
            else if(netChange > 0.0f)
            {
                cspcv.setText("+" + netChangeStr);
                cspcv.setTextColor(Color.parseColor("#32a852"));
            }
            else{
                cspcv.setTextColor(Color.LTGRAY);
            }

            cesv.setText(String.valueOf(currCompany.getEthicalScore()));
            final boolean[] inPortfolio = {currCompany.isInPortfolio()};
            if(inPortfolio[0]){
                addPortfolio.setBackgroundColor(Color.LTGRAY);
                addPortfolio.setText("Remove from Portfolio");

            }
            else{
                addPortfolio.setBackgroundColor(Color.parseColor("#32a852"));
                addPortfolio.setText("Add to Portfolio");
            }
            addPortfolio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ticker = ctv.getText().toString();
                    DBHelper db = new DBHelper(addPortfolio.getContext());
                    if(inPortfolio[0]){
                        db.removeFromPortfolio(ticker, LoginRepository.getLoggedInUser().getUserId());
                        Toast.makeText(addPortfolio.getContext(),"Removed " + ticker + " from portfolio.", Toast.LENGTH_SHORT).show();
                        addPortfolio.setBackgroundColor(Color.parseColor("#32a852"));
                        addPortfolio.setText("Add to Portfolio");

                        inPortfolio[0] = false;

                    }
                    else{
                        db.addToPortfolio(ticker, LoginRepository.getLoggedInUser().getUserId());
                        Toast.makeText(addPortfolio.getContext(),"Added " + ticker + " to portfolio.", Toast.LENGTH_SHORT).show();
                        addPortfolio.setBackgroundColor(Color.LTGRAY);
                        addPortfolio.setText("Remove from Portfolio");

                        inPortfolio[0] = true;
                    }
                }
            });
        }
        return cv;
    }
}
