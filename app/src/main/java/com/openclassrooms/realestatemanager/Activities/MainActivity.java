package com.openclassrooms.realestatemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Fragments.DetailFragment;
import com.openclassrooms.realestatemanager.Fragments.MainFragment;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textViewMain;
    private TextView textViewQuantity;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainFragment mainFragment;
    private DetailFragment detailFragment;
    private long id;

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

        Log.e("Test", "Mainactivity onCreate");

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
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (mainFragment == null) {
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
            detailFragment.updateDisplay(house);
            detailFragment.updateDisplayDetails(house);
            this.id = house.getId();
            Log.e("Test", "MainActivity onClick Tablette");

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
            Log.e("Test", "MainActivity onClick Smartphone");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.activity_main_drawer_conversion :
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
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_activity_main_toolbar_modify:
                if(id != 0) {
                    Intent intentModify = new Intent(MainActivity.this, AddActivity.class);
                    intentModify.putExtra("id", id);
                    startActivity(intentModify);
                } else {
                    Toast.makeText(this, "Selectionner un bien à vendre", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.menu_activity_main_toolbar_search:
                Toast.makeText(this, "Recherche", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
