package com.example.investic.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.investic.R;
import com.example.investic.ui.portfolio.CompanyPortfolioView;
import com.example.investic.ui.portfolio.PortfolioFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PortfolioAdapter extends BaseAdapter {

    Context context;
    String companyNameList[];
    String companyCategoryList[];

    //The general format in which values will be passed to the adapter.
    //Above variables kept for compatibility and modifiability.
    ArrayList<HashMap<String, String>> companyList;
    LayoutInflater inflater;
    public PortfolioAdapter(Context c, String [] companyNameList, String [] companyCategoryList){
        this.context = c;
        this.companyNameList = companyNameList;
        this.companyCategoryList = companyCategoryList;
        this.inflater = LayoutInflater.from(c);
    }
    public PortfolioAdapter(Context c, ArrayList<HashMap<String, String>> companyList){
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
        View cv = (View) inflater.inflate(R.layout.company_portfolio_view, null);
        //find the company name, category, score, image views from the company_portfolio_view.xml layout file.
        TextView cnv = cv.findViewById(R.id.company_name);
        TextView ccv = cv.findViewById(R.id.company_category);
        if (this.companyList != null && ! this.companyList.isEmpty()) {
            cnv.setText(this.companyList.get(pos).get("CompanyName"));
            ccv.setText(this.companyList.get(pos).get("CompanyCategory"));
        }
        return cv;
    }

}
