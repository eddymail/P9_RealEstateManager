package com.openclassrooms.realestatemanager.Activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.Fragments.MainFragment;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.HouseDataRepository;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long HOUSE_ID = 1;
    private String category, district, pointOfInterest, address, description, agentName, saleDate, dateOfSale;;
    private int price, area, numberOfRoom, numberOfBedroom, numberOfBathroom;


    private EditText categoryInput, districtInput, priceInput, areaInput, roomInput, bedroomInput, bathroomInput,
    pointOfInterestValue, descriptionValue, saleDateValue, dateOfSaleValue, agentNameValue, addressValue;
    private Button addButton;

    private HouseViewModel houseViewModel;
    private House houseToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

    categoryInput = findViewById(R.id.et_add_activity_category);
    districtInput = findViewById(R.id.et_add_activity_district);
    priceInput = findViewById(R.id.et_add_activity_price);
    areaInput = findViewById(R.id.et_add_activity_area);
    roomInput = findViewById(R.id.et_add_activity_room);
    bedroomInput = findViewById(R.id.et_add_activity_bedroom);
    bathroomInput = findViewById(R.id.et_add_activity_bathroom);
    pointOfInterestValue = findViewById(R.id.et_add_activity_pointOfInterest);
    descriptionValue = findViewById(R.id.et_add_activity_description);
    addressValue = findViewById(R.id.et_add_activity_address);
    agentNameValue = findViewById(R.id.et_add_activity_agent_name);
    saleDateValue = findViewById(R.id.et_add_activity_sale_date);
    dateOfSaleValue = findViewById(R.id.et_add_activity_date_of_sale);
    addButton = findViewById(R.id.bt_add_activity);

    configureViewModel();

    addButton.setOnClickListener(this);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.houseViewModel = ViewModelProviders.of(this, viewModelFactory).get(HouseViewModel.class);
        this.houseViewModel.init(HOUSE_ID);
    }

    private void collectInput() {
        category = categoryInput.getText().toString();
        district = districtInput.getText().toString();
        pointOfInterest = pointOfInterestValue.getText().toString();
        address = addressValue.getText().toString();
        description = descriptionValue.getText().toString();
        agentName = agentNameValue.getText().toString();
        dateOfSale = dateOfSaleValue.getText().toString();
        saleDate = saleDateValue.getText().toString();
        price = Integer.parseInt(priceInput.getText().toString());
        area = Integer.parseInt(areaInput.getText().toString());
        numberOfRoom = Integer.parseInt(roomInput.getText().toString());
        numberOfBedroom = Integer.parseInt(bedroomInput.getText().toString());
        numberOfBathroom = Integer.parseInt(bathroomInput.getText().toString());
    }

    private void createHouseAndAddItToDatabase() {

        houseToAdd = new House( category, district, price, area,numberOfRoom, numberOfBathroom, numberOfBedroom, pointOfInterest, description, ContextCompat.getDrawable(this,R.drawable.ic_baseline_home_24).toString(), address, true, saleDate, null, agentName);
        this.houseViewModel.createHouse(houseToAdd);
    }

    @Override
    public void onClick(View view) {
        collectInput();
        createHouseAndAddItToDatabase();
        Toast.makeText(this, "Le bien a été ajouté ", Toast.LENGTH_LONG).show();
        AddActivity.this.finish();
    }
}