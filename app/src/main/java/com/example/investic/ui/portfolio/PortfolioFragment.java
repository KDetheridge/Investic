package com.example.investic.ui.portfolio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investic.MainActivity;
import com.example.investic.R;
import com.example.investic.adapters.PortfolioAdapter;
import com.example.investic.data.LoginRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PortfolioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PortfolioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PortfolioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PortfolioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PortfolioFragment newInstance(String param1, String param2) {
        PortfolioFragment fragment = new PortfolioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_portfolio, container, false);
        listView = (ListView) rootView.findViewById(R.id.company_list_view);
        MainActivity a = (MainActivity) getActivity();
        ArrayList<HashMap<String,String>> portfolioCompanies = a.getPortfolioDetails();
        if( portfolioCompanies == null){

            Toast toast = Toast.makeText(getContext(),"No companies in your portfolio. Add some and return to this page!", Toast.LENGTH_LONG);
            toast.show();
            return rootView;
        }
//        PortfolioAdapter adapter = new PortfolioAdapter(getContext(),
//                                            new String[]{"Capgemini","Tim Hortons","Dogmans Enterprise"}, new String[]{"Technology Consulting","Food","Content Creation"});
        //ArrayList implementation
        PortfolioAdapter adapter = new PortfolioAdapter(getContext(),portfolioCompanies);
        listView.setAdapter(adapter);
        return rootView;



    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){


        //View v = new CompanyPortfolioView(getContext());


    }
}