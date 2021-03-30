package com.openclassrooms.realestatemanager.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.RealEstateManagerViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etMiniPrice, etMaxiPrice, etDistrict, etMiniArea,etMaxiArea , etPoi1, etPoi2, etDateOfSale, etSaleDate;
    private Button btFilter;
    private String miniPrice, maxiPrice, district, poi1, poi2, dateOfSale, saleDate;
    private int miniArea, maxiArea;
    private String[] queries;
    private RealEstateManagerViewModel realEstateManagerViewModel;

    private static final long HOUSE_ID = 1;
    private List<House> houseList = new ArrayList<>();
    private SearchListener searchListener;

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
        etDateOfSale = findViewById(R.id.et_on_sale_date_search_activity);
        etSaleDate = findViewById(R.id.et_sell_date_search_activity);

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
        if (!miniInput.isEmpty() & !maxiInput.isEmpty()) {
            miniArea = Integer.parseInt(miniInput);
            maxiArea = Integer.parseInt(maxiInput);
        }else {
            miniArea = 0;
            maxiArea = 0;
        }
        poi1 = etPoi1.getText().toString();
        poi2 = etPoi2.getText().toString();
        dateOfSale = etDateOfSale.getText().toString();
        saleDate = etSaleDate.getText().toString();
    }

    private List<House> getHouses(List<House> databaseList) {
        houseList = databaseList;
        Log.e("Test", " getHouses : " + houseList.size());
        // searchListener.onSearch(houseList);
        return houseList;

    }

    @Override
    public void onClick(View view) {
        this.getDataFromEditText();
        realEstateManagerViewModel.searchDatabase(district,miniPrice,maxiPrice, miniArea,maxiArea, poi1, dateOfSale, saleDate).observe(this, this::getHouses);
        finish();
    }

/*    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        try {
         // searchListener = (SearchListener) fragment;
            searchListener = (SearchListener) getFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString() + "must implement searchListener");
        }

    }*/

    public interface SearchListener {
        void onSearch(List<House> houseList);
    }

}