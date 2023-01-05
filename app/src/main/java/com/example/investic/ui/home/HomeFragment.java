package com.example.investic.ui.home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.investic.MainActivity;
import com.example.investic.R;
import com.example.investic.adapters.CompanyListAdapter;
import com.example.investic.data.model.Company;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        MainActivity a = (MainActivity) getActivity();
        ArrayList<Company> allCompanies = a.getAllCompanies();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) rootView.findViewById(R.id.company_list_view);
        //ArrayList implementation
        //final adapter array. Needs to be a final array due to its access in the TextWatcher Changed methods below.
        final CompanyListAdapter[] adapter = {new CompanyListAdapter(getContext(), allCompanies)};
        listView.setAdapter(adapter[0]);

        EditText editText = rootView.findViewById(R.id.search_box);
        editText.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }
                                            //when the text is changed, perform a search and update the company list in realtime.
                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                //run the searchCompanies method from the parent activity and store the results
                                                ArrayList<Company> companyList = a.searchCompanies(editText.getText().toString());
                                                //Create a new adapter and insert into the final adapter array.
                                                adapter[0] = new CompanyListAdapter(getContext(), companyList);
                                                //notify the new adapter of the dataset companyList update so it can reflect the changes
                                                adapter[0].notifyDataSetChanged();
                                                //Assign the new adapter to the existing listView.
                                                listView.setAdapter(adapter[0]);
                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {

                                            }
                                        }
        );
        if( allCompanies == null){

            Toast toast = Toast.makeText(getContext(),"No companies in your portfolio. Add some and return to this page!", Toast.LENGTH_LONG);
            toast.show();
            return rootView;
        }

        return rootView;

    }


}