package com.openclassrooms.realestatemanager.Activities;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.Fragments.MainFragment;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.RealEstateManagerViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private RealEstateManagerViewModel realEstateManagerViewModel;
    private SearchListener searchListener;
    private MainFragment fragment;

    //For Data
    private String district;
    private int school = 0;
    private int shopping = 0;
    private int publicTransport = 0;
    private int swimmingPool = 0;
    private int miniArea;
    private int maxiArea;
    private int miniRoom;
    private int maxiRoom;
    // private String[] queries;
    private static final long HOUSE_ID = 1;
    private List<House> houseList = new ArrayList<>();

    //For Design
    private EditText etMiniPrice;
    private EditText etMaxiPrice;
    private EditText etDistrict;
    private EditText etMiniArea;
    private EditText etMaxiArea;
    private EditText etMiniRoom;
    private EditText etMaxiRoom;
    private Button btFilter;
    private CheckBox schoolCb;
    private CheckBox shoppingCb;
    private CheckBox publicTransportCb;
    private CheckBox swimmingPoolCb;

    // TODO
    // Changer le type du prix
    private int miniPrice;
    private int maxiPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.initActivity();
        this.configureViewModel();
    }

    private void initActivity() {
        etMiniPrice = findViewById(R.id.et_search_activity_price_mini);
        etMaxiPrice = findViewById(R.id.et_search_activity_price_max);
        etDistrict = findViewById(R.id.et_search_activity_district);
        etMiniArea = findViewById(R.id.et_search_activity_mini_area);
        etMaxiArea = findViewById(R.id.et_search_activity_maxi_area);
        etMiniRoom = findViewById(R.id.et_search_activity_mini_room);
        etMaxiRoom = findViewById(R.id.et_search_activity_maxi_room);

        schoolCb = findViewById(R.id.cb_search_activity_school);
        shoppingCb = findViewById(R.id.cb_search_activity_shopping);
        publicTransportCb = findViewById(R.id.cb_search_activity_public_transport);
        swimmingPoolCb = findViewById(R.id.cb_search_activity_swimming_pool);

        btFilter = findViewById(R.id.bt_search_activity_filter_);
        
        btFilter.setOnClickListener(this);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        this.realEstateManagerViewModel.init(HOUSE_ID);
    }

    private void getDataFromEditText() {
        district = etDistrict.getText().toString();
        String miniPriceInput = etMiniPrice.getText().toString();
        String maxiPriceInput = etMaxiPrice.getText().toString();
        String miniAreaInput = etMiniArea.getText().toString();
        String maxiAreaInput = etMaxiArea.getText().toString();
        String miniRoomInput = etMiniRoom.getText().toString();
        String maxiRoomInput = etMaxiRoom.getText().toString();
        if(miniPriceInput == "" & maxiPriceInput == "") {
            miniPrice = 0;
            maxiPrice = 0;
        } else {
            miniPrice = Integer.parseInt(miniPriceInput);
            maxiPrice = Integer.parseInt(maxiPriceInput);
        }
        if(miniAreaInput == "" & maxiAreaInput == "") {
            miniArea = 0;
            maxiArea = 0;
        } else {
            miniArea = Integer.parseInt(miniAreaInput);
            maxiArea = Integer.parseInt(maxiAreaInput);
        }
        if(miniRoomInput == "" & maxiRoomInput == "") {
            miniRoom = 0;
            maxiRoom = 0;
        } else {
            miniRoom = Integer.parseInt(miniRoomInput);
            maxiRoom = Integer.parseInt(maxiRoomInput);
        }
        if (schoolCb.isChecked()) {
            school = 1;
        }
        if (shoppingCb.isChecked()) {
            shopping = 1;
        }
        if (publicTransportCb.isChecked()) {
            publicTransport = 1;
        }
        if (swimmingPoolCb.isChecked()) {
            swimmingPool = 1;
        }

        Log.e("Test", " SAISIE district, = " + district);
        Log.e("Test", " SAISIE miniPrice = " + miniPrice);
        Log.e("Test", " SAISIE maxiPrice = " + maxiPrice);
        Log.e("Test", " SAISIE miniArea = " + miniArea);
        Log.e("Test", " SAISIE maxiArea = " + maxiArea);
        Log.e("Test", " SAISIE miniRoom = " + miniRoom);
        Log.e("Test", " SAISIE maxiRoom = " + maxiRoom);
        Log.e("Test", " SAISIE school = " + school);
        Log.e("Test", " SAISIE shopping = " + shopping);
        Log.e("Test", " SAISIE publicTransport = " + publicTransport);
        Log.e("Test", " SAISIE swimmingPool = " + swimmingPool);

    }

    private void getSearchedList(List<House> houseList) {

        Log.e("Test", " getHouses houseList: " + houseList.size());
        if (houseList.isEmpty()) {
            Toast.makeText(this, "Aucun bien ne correspond", Toast.LENGTH_LONG).show();
        } else {
            fragment = new MainFragment();
            searchListener = (SearchListener) fragment;
            searchListener.onSearch(houseList);
            Log.e("Test", " searchListner : " + searchListener);
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        this.getDataFromEditText();

        realEstateManagerViewModel.getSearchedHouse(district,
                miniPrice,
                maxiPrice,
                miniArea,
                maxiArea,
                miniRoom,
                maxiRoom,
                school,
                shopping,
                publicTransport,
                swimmingPool).observe(this, this::getSearchedList);

        Log.e("Test", " RECHERCHE district, = " + district);
        Log.e("Test", " RECHERCHE miniPrice = " + miniPrice);
        Log.e("Test", " RECHERCHE maxiPrice = " + maxiPrice);
        Log.e("Test", " RECHERCHE miniArea = " + miniArea);
        Log.e("Test", " RECHERCHE maxiArea = " + maxiArea);
        Log.e("Test", " RECHERCHE miniRoom = " + miniRoom);
        Log.e("Test", " RECHERCHE maxiRoom = " + maxiRoom);
        Log.e("Test", " RECHERCHE school = " + school);
        Log.e("Test", " RECHERCHE shopping = " + shopping);
        Log.e("Test", " RECHERCHE publicTransport = " + publicTransport);
        Log.e("Test", " RECHERCHE swimmingPool = " + swimmingPool);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        fragment = new MainFragment();
        searchListener = (SearchListener) fragment;
    }

    public interface SearchListener {
        void onSearch(List<House> searchResult);
    }

}