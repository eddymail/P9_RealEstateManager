package com.openclassrooms.realestatemanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.activities.MainActivity;
import com.openclassrooms.realestatemanager.adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.ui.RealEstateManagerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements HouseRecyclerAdapter.OnHouseListener {

    private RecyclerView recyclerView;
    private List<House> houseList = new ArrayList<>();
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 26;
    private HouseRecyclerAdapter adapter;
    private TextView lblNoHouse;
    private RealEstateManagerViewModel realEstateManagerViewModel;
    public static final String BUNDLE_RESULT_LIST = "BUNDLE_RESULT_LIST";
    private List<House> searchResult;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.fragment_main_recyclerview);
        lblNoHouse = view.findViewById(R.id.lbl_no_house);

        this.configureViewModel();
        this.configureRecyclerView();

        Log.e("Test", "onCreateMainfragment");

        return view;
    }

    private void configureRecyclerView() {
        Log.e("Test", "configure RecyclerView");
        //  this.houseList = new ArrayList<>();
        this.adapter = new HouseRecyclerAdapter(this.houseList, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.e("Test", "configure RecyclerView adapter value :" + adapter);
    }

    private void configureViewModel() {

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        realEstateManagerViewModel.getAll().observe((LifecycleOwner) this, houseList -> {
            adapter.setData(houseList);
            updateDisplay();
        });

    }

    private void updateDisplay() {

        if (houseList.size() == 0) {
            lblNoHouse.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            lblNoHouse.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (SEARCH_ACTIVITY_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            houseList = (List<House>) data.getSerializableExtra(BUNDLE_RESULT_LIST);
            String result = data.getStringExtra("Test");
            Log.e("Test", "onActivityResult Search List: " + houseList);
            Log.e("Test", "onActivityResult Test: " + result);
            adapter.setData(houseList);
        }
    }

    @Override
    public void onHouseClick(int position) {
        ((MainActivity) getActivity()).onHouseClick(houseList.get(position));
    }

}