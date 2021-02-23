package com.openclassrooms.realestatemanager.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Fragments.DetailFragment;
import com.openclassrooms.realestatemanager.Fragments.MainFragment;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import static com.openclassrooms.realestatemanager.Fragments.MainFragment.houseList;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;

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

        this.configureAndShowMainFragment();
        this.configureAndShowDetailFragment();
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
        // Problème de typage
        this.textViewQuantity.setText(String.valueOf(quantity));
    }

    public void configureAndShowMainFragment() {
      // Get FragmentManager (support) and Try to find existing instance of fragment in FrameLayout container
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();

            Log.e("Test", "MAINACTIVTY configureAndShowMAINFragment: Condition OK");
        } else {
            Log.e("Test", "MAINACTIVTY configureAndShowMAINFragment: Condition NO");
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

            Log.e("Test", "MAINACTIVTY configureAndShowDETAILFragment: Condition OK");
        } else {

            Log.e("Test", "MAINACTIVTY configureAndShowDETAILFragment: Condition NO");
        }
    }

    // Add with Gaethan
    public void onHouseClick(int position) {

        if (detailFragment != null) {
            detailFragment.onHouseClick(position);

            Log.e("Test", "MAIN ACTIVITY  OnHouseCLick house Id = " + houseList.get(position).getId());

        }   else {

            detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_main, detailFragment)
                    .commit();


           //detailFragment.onHouseClick(position);
           //detailFragment.updateHouse(detailFragment.getHouseById(position + 1));

            Log.e("Test", "MAIN ACTIVITY  OnHouseCLick FRAGMENT IS NULL house Id = " + houseList.get(position).getId());

            position = houseList.get(position).getId();
        }




    }

}
