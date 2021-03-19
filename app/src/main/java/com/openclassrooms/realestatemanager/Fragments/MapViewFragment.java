package com.openclassrooms.realestatemanager.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private HouseViewModel houseViewModel;
    private List<House> houseList = new ArrayList<>();
    private House house;
    private LatLng houseLatLng;
    private static final long HOUSE_ID = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        //Async map
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(MapViewFragment.this);
        }

        this.configureViewModel();
        this.getAllHousesFromDatabase();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void setMarker() {

        for (House house : houseList) {
            String address = house.getAddress();

            Geocoder coder = new Geocoder(getContext());
            List<Address> addresses;
            try {
                addresses = coder.getFromLocationName(address, 10);

                if (addresses == null) {
                }
                Log.e("Test", "addresse  = " + addresses);
                Address location = addresses.get(0);
                double lat = location.getLatitude();
                double lng = location.getLongitude();

                houseLatLng = new LatLng(lat, lng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(houseLatLng);
                if (houseLatLng != null) {
                    this.googleMap.addMarker(markerOptions);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(houseLatLng, 12));
    }

    private void getAllHousesFromDatabase() {
        this.houseViewModel.getAll().observe(this, this::updateList);
    }

    private void updateList(List<House> houses) {
        houseList = new ArrayList<>();
        houseList.addAll(houses);
        setMarker();
        Log.e("Test", "houseList = " + houseList.size());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.houseViewModel = ViewModelProviders.of(this, viewModelFactory).get(HouseViewModel.class);
        this.houseViewModel.init(HOUSE_ID);

    }
}