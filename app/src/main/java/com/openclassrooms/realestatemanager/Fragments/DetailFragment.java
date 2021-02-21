package com.openclassrooms.realestatemanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.R;

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
    private House house;
    private RecyclerView illustrationList;

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

        return view;
    }

    private void updateHouse(){
        area.setText(house.getArea());
        rooms.setText(house.getNumberOfRooms());
        bedrooms.setText(house.getNumberOfBedrooms());
        bathrooms.setText(house.getNumberOfBathrooms());
        pointOfInterest.setText(house.getPointOfInterest());
        address.setText(house.getAddress());
        description.setText(house.getDescription());
    }
}