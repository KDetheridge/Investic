package com.example.investic.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investic.MainActivity;
import com.example.investic.R;
import com.example.investic.databinding.ActivityMainBinding;
import com.example.investic.databinding.FragmentProfileBinding;

import java.util.HashMap;

import db.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button updateProfileButton;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = FragmentProfileBinding.inflate(getLayoutInflater());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //Get the relevant text views and populate them from the database profile record for this user.
        TextView isv = rootView.findViewById(R.id.investmentScrutinyValue);
        TextView iav = rootView.findViewById(R.id.investmentAmountValue);
        TextView irv = rootView.findViewById(R.id.investmentRiskValue);
        MainActivity a = (MainActivity) getActivity();
        HashMap<String,Integer> profile = a.getProfile();
        if (!profile.isEmpty()){
            String scrutiny = String.valueOf(profile.get("InvestmentScrutinyLevel"));
            String risk = String.valueOf(profile.get("InvestmentRiskLevel"));
            String amount = String.valueOf(profile.get("InvestmentAmount"));

            isv.setText(scrutiny);
            irv.setText(risk);
            iav.setText(amount);


        }

        //set the onClick activity for the updateProfileButton. Redirects to the ProfileConfigActivity.
        updateProfileButton = rootView.findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Not yet implemented.",Toast.LENGTH_SHORT).show();
                MainActivity a = (MainActivity) getActivity();
                Intent intent = new Intent(getActivity(), ProfileConfigActivity.class);
                startActivity(intent);



            }
        });

        return rootView;

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

    }
}