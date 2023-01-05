package com.example.investic.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.investic.R;
import com.example.investic.data.model.Company;

import java.util.ArrayList;
import java.util.HashMap;

public class PortfolioAdapter extends BaseAdapter {

    Context context;
    String companyNameList[];
    String companyCategoryList[];

    //The general format in which values will be passed to the adapter.
    //Above variables kept for compatibility and modifiability.
    ArrayList<Company> companyList;
    LayoutInflater inflater;
    public PortfolioAdapter(Context c, String [] companyNameList, String [] companyCategoryList){
        this.context = c;
        this.companyNameList = companyNameList;
        this.companyCategoryList = companyCategoryList;
        this.inflater = LayoutInflater.from(c);
    }
    public PortfolioAdapter(Context c, ArrayList<Company> companyList){
        this.context = c;
        this.companyList = companyList;
        this.inflater = LayoutInflater.from(c);
    }


    public int getCount(){
        return this.companyList.size();
    }

    public Object getItem(int pos){
        return null;
    }
    public long getItemId(int pos){
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        //initialise view
        View cv = (View) inflater.inflate(R.layout.company_portfolio_cardview, null);
        //find the company name, category, score, image views from the company_portfolio_view.xml layout file.
        TextView cnv = cv.findViewById(R.id.company_name);
        TextView ctv = cv.findViewById(R.id.company_ticker);
        TextView ccv = cv.findViewById(R.id.company_sector);

        TextView cspv = cv.findViewById((R.id.stock_price_value));
        //Company Share Price Change Value
        TextView cspcv = cv.findViewById(R.id.price_change_value);
        TextView cesv = cv.findViewById((R.id.ethical_score_value));
        if (this.companyList != null && ! this.companyList.isEmpty()) {
            cnv.setText(this.companyList.get(pos).getName());
            ctv.setText(this.companyList.get(pos).getTickerSymbol());
            ccv.setText(this.companyList.get(pos).getSector());
            cspv.setText('$' + String.valueOf(companyList.get(pos).getCurrentValue()));
            Float netChange = this.companyList.get(pos).getNetChange();

            String netChangeStr = String.valueOf(netChange);
            // <0, red; > 0, green; 0, gray.
            if(netChange < 0){
                cspcv.setText(netChangeStr);
                cspcv.setTextColor(Color.RED);
            }
            else if(netChange > 0)
            {
                cspcv.setText("+" + netChangeStr);
                cspcv.setTextColor(Color.parseColor("#32a852"));
            }
            else{
                cspcv.setTextColor(Color.LTGRAY);
            }

            cesv.setText(String.valueOf(this.companyList.get(pos).getEthicalScore()));
        }
        return cv;
    }

}
