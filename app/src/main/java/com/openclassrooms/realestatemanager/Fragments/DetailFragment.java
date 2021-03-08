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

import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Adapter.GalleryRecyclerAdapter;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private TextView area;
    private TextView rooms;
    private TextView bedrooms;
    private TextView bathrooms;
    private TextView pointOfInterest;
    private TextView address;
    private TextView description;
    private TextView label;

    private List<Illustration> gallery = new ArrayList<>();

    // Smartphone
    private House house;

    private RecyclerView recyclerView;
    private GalleryRecyclerAdapter adapter;
    private HouseViewModel houseViewModel;

    private static final long HOUSE_ID = 1;

    // Required empty public constructor
    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        area = view.findViewById(R.id.tv_fragment_detail_surface_value);
        rooms = view.findViewById(R.id.tv_fragment_detail_rooms_value);
        bedrooms = view.findViewById(R.id.tv_fragment_detail_bedrooms_value);
        bathrooms = view.findViewById(R.id.tv_fragment_detail_bathrooms_value);
        pointOfInterest = view.findViewById(R.id.tv_fragment_detail_poi_value);
        address = view.findViewById(R.id.tv_fragment_detail_address_value);
        description = view.findViewById(R.id.tv_fragment_detail_description_value);
        label = view.findViewById(R.id.lbl_no_house);

        recyclerView = view.findViewById(R.id.rv_fragment_detail);

        this.configureViewModel();

        return view;
    }

    @Override
    public void onStart() {
        //Smartphone display
        if (house != null) {
            updateDisplay(house);
        }

        this.updateDisplayDetails(house);

        super.onStart();
    }

    public void configureRecyclerView() {
        this.gallery = new ArrayList<>();
        this.adapter = new GalleryRecyclerAdapter(this.gallery);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.houseViewModel = ViewModelProviders.of(this, viewModelFactory).get(HouseViewModel.class);
        this.houseViewModel.init(HOUSE_ID);
    }

    //Tablet display
    public void updateDisplay(House house) {
        updateHouse(house);
        configureViewModel();
        configureRecyclerView();
        getGalleryHouseFromDatabase(house.getId());
    }

    public void updateDisplayDetails(House house){
        if (house == null) {
            Log.e("Test","" + getView());
            getView().setVisibility(View.GONE);
        } else {
            getView().setVisibility(View.VISIBLE);
        }
    }

    public void updateHouse(House house) {
        area.setText(String.valueOf(house.getArea()));
        rooms.setText(String.valueOf(house.getNumberOfRooms()));
        bedrooms.setText(String.valueOf(house.getNumberOfBedrooms()));
        bathrooms.setText(String.valueOf(house.getNumberOfBathrooms()));
        pointOfInterest.setText(house.getPointOfInterest());
        address.setText(house.getAddress());
        description.setText(house.getDescription());
    }

    private void getGalleryHouseFromDatabase (long houseId) {
        this.houseViewModel.getGallery(houseId).observe(this, this :: updateList);
    }

    private void updateList(List<Illustration> illustrations) {
        gallery.addAll(illustrations);
        adapter.notifyDataSetChanged();
    }

    //Listener
    public void onHouseClick(House house) {
        this.house = house;
    }
}