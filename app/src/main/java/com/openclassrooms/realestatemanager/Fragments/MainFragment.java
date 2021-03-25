package com.openclassrooms.realestatemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Activities.MainActivity;
import com.openclassrooms.realestatemanager.Adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements HouseRecyclerAdapter.OnHouseListener {

    private RecyclerView recyclerView;
    private List<House> houseList = new ArrayList<>();
    private HouseRecyclerAdapter adapter;
    private TextView lblNoHouse;
    private HouseViewModel houseViewModel;

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
        this.getAllHousesFromDatabase();

        Log.e("Test", "MainFragment onCreateView");

        return view;
    }

    private void configureRecyclerView() {

        this.houseList = new ArrayList<>();
        this.adapter = new HouseRecyclerAdapter(this.houseList, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void configureViewModel() {

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.houseViewModel = ViewModelProviders.of(this, viewModelFactory).get(HouseViewModel.class);
        this.houseViewModel.init(HOUSE_ID);

    }

    private void updateDisplayList() {

        if (houseList.size() == 0) {
            lblNoHouse.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            lblNoHouse.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void getAllHousesFromDatabase() {
        this.houseViewModel.getAll().observe(this, this::updateList);
    }

    public void updateList(List<House> houses) {
        houseList.addAll(houses);
        adapter.notifyDataSetChanged();
        this.updateDisplayList();
    }

    @Override
    public void onHouseClick(int position) {
        ((MainActivity) getActivity()).onHouseClick(houseList.get(position));
    }
}