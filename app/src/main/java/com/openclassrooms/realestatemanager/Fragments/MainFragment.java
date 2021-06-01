package com.openclassrooms.realestatemanager.Fragments;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Activities.MainActivity;
import com.openclassrooms.realestatemanager.Activities.SearchResultsActivity;
import com.openclassrooms.realestatemanager.Adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.RealEstateManagerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements HouseRecyclerAdapter.OnHouseListener, SearchResultsActivity.SearchListener {

    private RecyclerView recyclerView;
    private List<House> houseList = new ArrayList<>();
    private HouseRecyclerAdapter adapter;
    private TextView lblNoHouse;
    private RealEstateManagerViewModel realEstateManagerViewModel;

    private static final long HOUSE_ID = 1;

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


        return view;
    }

    private void configureRecyclerView() {
        Log.e("Test", "configure RecyclerView");
        this.houseList = new ArrayList<>();
        this.adapter = new HouseRecyclerAdapter(this.houseList, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void configureViewModel() {

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        realEstateManagerViewModel.getAll().observe((LifecycleOwner) this, houseList -> {
            Log.e("Test", "onSearch RecyclerView List size : "  + houseList.size());
            adapter.setData(houseList);
            updateDisplay();
        });

        /*
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        this.realEstateManagerViewModel.init(HOUSE_ID);
        */
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
    public void onHouseClick(int position) {
        ((MainActivity) getActivity()).onHouseClick(houseList.get(position));
    }

    @Override
    public void onSearch(List<House> searchedList) {

        this.houseList = searchedList;
        Log.e("Test", "onSearch searchedList size : "  + searchedList.size());
        // this.adapter = new HouseRecyclerAdapter(houseList,this);
        // adapter.notifyDataSetChanged();
        // Log.e("Test", "onSearch adapter : "  + adapter);
        // adapter.setData(houseList);
    }
}