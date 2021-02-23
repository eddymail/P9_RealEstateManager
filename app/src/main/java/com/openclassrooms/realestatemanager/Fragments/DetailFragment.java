package com.openclassrooms.realestatemanager.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Activities.MainActivity;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.R;

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

    private List<Illustration> gallery;
    private List<House> houseList = MainFragment.houseList;
    private House house;
    private RecyclerView illustrationList;
    private int position;

    public DetailFragment() {
        // Required empty public constructor
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

        Log.e("Test", "DETAILFRAGMENT onCreatView");

        //Smartphone
      /*  if(position == 0){
            updateHouse(getHouseById(position + 1));
        }*/

        return view;
    }

    public House getHouseById(int houseId) {
        for (House house: houseList) {
            if(house.getId() == houseId)

                return house;

            Log.e("Test", "House district :" + house.getDistrict());
        }
        return null;
    }

    public void updateHouse(House house){

        area.setText(house.getArea());
        rooms.setText(house.getNumberOfRooms());
        bedrooms.setText(house.getNumberOfBedrooms());
        bathrooms.setText(house.getNumberOfBathrooms());
        pointOfInterest.setText(house.getPointOfInterest());
        address.setText(house.getAddress());
        description.setText(house.getDescription());
    }

    public void onHouseClick(int position) {

   //     Toast.makeText(getActivity(), "ClickOn " + position, Toast.LENGTH_LONG).show();

        Log.e("Test", "DETAIL FRAGMENT onHouseClick d = " + position + 1);

        this.position = position;

       // Tablette
        updateHouse(getHouseById(position + 1));

    }

}