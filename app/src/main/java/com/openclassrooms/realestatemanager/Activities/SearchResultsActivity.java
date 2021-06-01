package com.openclassrooms.realestatemanager.Activities;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private EditText etMiniPrice, etMaxiPrice, etDistrict, etMiniArea,etMaxiArea , etPoi1, etPoi2, etDateOfEntry, etDateOfSale;
    private Button btFilter;
    private String miniPrice, maxiPrice, district, poi1, poi2, dateOfEntry, dateOfSale;
    private int miniArea, maxiArea;
    private String[] queries;
    private RealEstateManagerViewModel realEstateManagerViewModel;

    private static final long HOUSE_ID = 1;
    private List<House> houseList = new ArrayList<>();
    private SearchListener searchListener;
    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.initActivity();
        this.configureViewModel();
    }

    private void initActivity() {
        etMiniPrice = findViewById(R.id.et_price_mini_search_activity);
        etMaxiPrice = findViewById(R.id.et_price_max_search_activity);
        etDistrict = findViewById(R.id.et_search_district_activity);
        btFilter = findViewById(R.id.bt_filter_search_activity);
        etMiniArea = findViewById(R.id.et_mini_area_search_activity);
        etMaxiArea = findViewById(R.id.et_maxi_area_search_activity);
        etPoi1 = findViewById(R.id.et_poi_search_activity);
        etPoi2 = findViewById(R.id.et_poi_two_search_activity);
        etDateOfEntry = findViewById(R.id.et_date_of_entry_search_activity);
        etDateOfSale = findViewById(R.id.et_date_of_sale_search_activity);

        btFilter.setOnClickListener(this);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        this.realEstateManagerViewModel.init(HOUSE_ID);
    }

    private void getDataFromEditText() {
        district = etDistrict.getText().toString();
        miniPrice = etMiniPrice.getText().toString();
        maxiPrice = etMaxiPrice.getText().toString();
      String miniInput = etMiniArea.getText().toString();
        String maxiInput = etMaxiArea.getText().toString();
        if(miniInput == "" & maxiInput == ""){
            miniArea = 0;
            maxiArea = 0;
        } else {
            miniArea = Integer.parseInt(miniInput);
            maxiArea = Integer.parseInt(maxiInput);
        }
        poi1 = etPoi1.getText().toString();
        poi2 = etPoi2.getText().toString();
        dateOfSale = etDateOfEntry.getText().toString();
        dateOfEntry = etDateOfSale.getText().toString();
        Log.e("Test", " SAISIE saleDate = " + dateOfEntry);
        if(dateOfEntry.isEmpty()) {
            dateOfEntry = null;
            Log.e("Test", " SAISIE saleDate = " + dateOfEntry);
        }
        Log.e("Test", " SAISIE district, = " + district);
        Log.e("Test", " SAISIE miniPrice = " + miniPrice);
        Log.e("Test", " SAISIE maxiPrice = " + maxiPrice);
        Log.e("Test", " SAISIE miniArea = " + miniArea);
        Log.e("Test", " SAISIE maxiArea = " + maxiArea);
        Log.e("Test", " SAISIE poi1 = " + poi1);
        Log.e("Test", " SAISIE dateOfSale = " + dateOfSale);
        Log.e("Test", " SAISIE saleDate = " + dateOfEntry);
    }

    private void getSearchedList(List<House> houseList) {

        Log.e("Test", " getHouses houseList: " + houseList.size());
        if (houseList.isEmpty()) {
            Toast.makeText(this, "Aucun bien ne correspond", Toast.LENGTH_LONG).show();
        }else {
            fragment = new MainFragment();
            searchListener = fragment;
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
                poi1,
                dateOfSale,
                dateOfEntry).observe(this, this::getSearchedList);

        Log.e("Test", " RECHERCHE district, = " + district);
        Log.e("Test", " RECHERCHE miniPrice = " + miniPrice);
        Log.e("Test", " RECHERCHE maxiPrice = " + maxiPrice);
        Log.e("Test", " RECHERCHE miniArea = " + miniArea);
        Log.e("Test", " RECHERCHE maxiArea = " + maxiArea);
        Log.e("Test", " RECHERCHE poi1 = " + poi1);
        Log.e("Test", " RECHERCHE dateOfSale = " + dateOfSale);
        Log.e("Test", " RECHERCHE saleDate = " + dateOfEntry);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        fragment = new MainFragment();
        searchListener = (SearchListener) fragment;

    }

    public interface SearchListener {
        void onSearch(List<House> resultList);
    }

}