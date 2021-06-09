package com.openclassrooms.realestatemanager.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.Adapter.GalleryRecyclerAdapter;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.RealEstateManagerViewModel;
import com.openclassrooms.realestatemanager.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    private TextView area;
    private TextView rooms;
    private TextView bedrooms;
    private TextView bathrooms;
    private TextView pointOfInterest;
    private TextView address;
    private TextView description;
    private TextView label;
    private ImageView mapView;

    private List<Illustration> gallery = new ArrayList<>();

    // Smartphone
    private House house;
    private long id;

    private RecyclerView recyclerView;
    private GalleryRecyclerAdapter adapter;
    private RealEstateManagerViewModel realEstateManagerViewModel;

    private static final long HOUSE_ID = 1;

    // Required empty public constructor
    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        this.initActivity(view);
        this.configureViewModel();

        return view;
    }

    @Override
    public void onStart() {
        //Smartphone display
        if (house != null) {
            this.updateData(house);
        }
        this.updateDisplay(house);
        super.onStart();
    }

    private void initActivity(View view) {
        area = view.findViewById(R.id.tv_fragment_detail_surface_value);
        rooms = view.findViewById(R.id.tv_fragment_detail_rooms_value);
        bedrooms = view.findViewById(R.id.tv_fragment_detail_bedrooms_value);
        bathrooms = view.findViewById(R.id.tv_fragment_detail_bathrooms_value);
        pointOfInterest = view.findViewById(R.id.tv_fragment_detail_poi_value);
        address = view.findViewById(R.id.tv_fragment_detail_address_value);
        description = view.findViewById(R.id.tv_fragment_detail_description_value);
        label = view.findViewById(R.id.lbl_no_house);
        mapView = view.findViewById(R.id.iv_fragment_detail_mapview);
        recyclerView = view.findViewById(R.id.rv_fragment_detail);

        mapView.setOnClickListener(this);
    }

    public void configureRecyclerView() {
        // this.gallery = new ArrayList<>();
        this.adapter = new GalleryRecyclerAdapter(this.gallery);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getContext());
        this.realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        this.realEstateManagerViewModel.init(HOUSE_ID);
    }

    private void getGalleryHouseFromDatabase(long houseId) {
        this.realEstateManagerViewModel.getGallery(houseId).observe((LifecycleOwner) this, illustrations -> {
            adapter.setData(illustrations);
            updateDisplayList();
        });
    }

    //Tablet display
    public void updateData(House house) {
        updateHouse(house);
        configureViewModel();
        configureRecyclerView();
        getGalleryHouseFromDatabase(house.getId());
    }

    private void updateDisplayList() {
        if (gallery.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            configureRecyclerView();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void updateDisplay(House house) {
        if (house == null) {
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
        Log.e("Test", "Nbre de ch =" + house.getNumberOfRooms());
        List<String> pois = new ArrayList<>();
        if (house.getSchool() == 1) {
            pois.add("école");
        }
        if (house.getShopping() == 1) {
            pois.add("commerce");
        }
        if (house.getPublicTransport() == 1) {
            pois.add("transport");
        }
        if (house.getSwimmingPool() == 1) {
            pois.add("piscine");
        }
        String list = "";
        for (String poi : pois) {
            list = list + " " + poi;
        }

        pointOfInterest.setText(list);
        address.setText(house.getAddress());
        description.setText(house.getDescription());
        //Display house on staticMap
        Glide.with(mapView.getContext())
                .load(convertAndShowAddressOnStaticMap(house.getAddress()))
                .into(mapView);
    }


    public String convertAndShowAddressOnStaticMap(String address) {
        Geocoder coder = new Geocoder(getContext());
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(address, 10);
            if (addresses == null) {
            }
            Address location = addresses.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=15&size=200x200" +
                    "&markers=color:black%7C" + lat + "," + lng + "&sensor=false&key=AIzaSyCV1RfW578IO-3Sr5zeb8uK5xdLx_Gz_5M";
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkConnectivity() {
        if (Utils.haveNetwork()) {
            //Start mapViewFragment

            MapViewFragment mapViewFragment = new MapViewFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_frame_layout, mapViewFragment)
                    .commit();

        } else {
            Toast.makeText(getContext(), "Vous êtes connecté à aucun réseau", Toast.LENGTH_LONG).show();
        }
    }

    //Listener
    public void onHouseClick(House house) {
        if (house != null) {
            this.house = house;
            //Use for modify
            this.id = house.getId();
        }
    }

    @Override
    public void onClick(View view) {
        checkConnectivity();
    }
}