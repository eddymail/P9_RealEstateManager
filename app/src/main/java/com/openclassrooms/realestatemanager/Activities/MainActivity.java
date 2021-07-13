package com.openclassrooms.realestatemanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.fragments.DetailFragment;
import com.openclassrooms.realestatemanager.fragments.MainFragment;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.ui.RealEstateManagerViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewMain;
    private TextView textViewQuantity;

    private static final long HOUSE_ID = 1;
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 26;
    public static final String BUNDLE_RESULT_LIST = "BUNDLE_RESULT_LIST";

    private RealEstateManagerViewModel realEstateManagerViewModel;
    private List<House> houseList = new ArrayList<>();
    private long id;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private HouseRecyclerAdapter adapter;
    private MainFragment mainFragment;
    private DetailFragment detailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bug 1: Problème de ressource
        // this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
        // this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        //this.configureTextViewMain();
        //this.configureTextViewQuantity();

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        this.configureViewModel();
        this.getAllHousesFromDatabase();

        Utils.context = this;

        this.configureAndShowMainFragment();
        this.configureAndShowDetailFragment();
    }

    private void configureTextViewMain() {
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity() {
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
        // Problème de typage
        this.textViewQuantity.setText(String.valueOf(quantity));
    }

    public void configureAndShowMainFragment() {
        // Get FragmentManager (support) and Try to find existing instance of fragment in FrameLayout container
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (!(fragment instanceof MainFragment)) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }

    public void configureAndShowDetailFragment() {
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        // Add detailFragment only if in Tablet mode
        if (detailFragment == null && findViewById(R.id.frame_layout_detail) != null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, detailFragment)
                    .commit();
        }
    }

    private void configureToolBar() {
        this.toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu_toolbar, menu);

        return true;
    }

    public void onHouseClick(House house) {

        if (detailFragment != null && Utils.isTablet(this)) {
            //Tablet
            detailFragment.updateData(house);
            detailFragment.updateDisplay(house);
            this.id = house.getId();

        } else {
            //Smartphone
            detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, detailFragment)
                    .addToBackStack(null)
                    .commit();
            detailFragment.onHouseClick(house);
            this.id = house.getId();
        }
    }

    private void getAllHousesFromDatabase() {
        this.realEstateManagerViewModel.getAll().observe(this, this::updateList);
    }

    private void updateList(List<House> houses) {
        houseList = new ArrayList<>();
        houseList.addAll(houses);
    }

    private void changeCurrencyToDollarsAndUpdateDataBase(List<House> houses) {
        if (houses != null) {
            for (House house : houses) {
                long houseId = house.getId();
                this.realEstateManagerViewModel.updateIsEuro(false, houseId);
            }
        }
    }

    private void changeCurrencyToEuroAndUpdateDataBase(List<House> houses) {
        if (houses != null) {
            for (House house : houses) {
                long houseId = house.getId();
                this.realEstateManagerViewModel.updateIsEuro(true, houseId);
            }
        }
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        this.realEstateManagerViewModel.init(HOUSE_ID);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_main_drawer_conversion_euro_dollars:
                this.changeCurrencyToDollarsAndUpdateDataBase(houseList);
                Toast.makeText(this, "Prix en dollars", Toast.LENGTH_LONG).show();
                break;
            case R.id.activity_main_drawer_conversion_dollars_euro:
                this.changeCurrencyToEuroAndUpdateDataBase(houseList);
                Toast.makeText(this, "Prix en euros", Toast.LENGTH_LONG).show();
                break;
            case R.id.activity_main_drawer_loan_calculator:
                Intent intentCalculator = new Intent(MainActivity.this, LoanCalculatorActivity.class);
                startActivity(intentCalculator);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_toolbar_add:
                Intent intentAdd = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intentAdd);
                return true;
            case R.id.menu_activity_main_toolbar_modify:
                if (id != 0) {
                    Intent intentModify = new Intent(MainActivity.this, AddActivity.class);
                    intentModify.putExtra("id", id);
                    startActivity(intentModify);
                } else {
                    Toast.makeText(this, "Selectionner un bien à vendre", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menu_activity_main_toolbar_search:
                Intent searchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(searchActivityIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        fragment.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!Utils.isTablet(getBaseContext())) {
            super.onBackPressed();
        } else {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
            Log.e("test", "passe par ici");
        }
    }
}
