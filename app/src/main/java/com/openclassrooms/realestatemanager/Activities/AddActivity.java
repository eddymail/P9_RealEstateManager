package com.openclassrooms.realestatemanager.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private static final long HOUSE_ID = 1;
    private String category, district, pointOfInterest, address, description, agentName, saleDate, upForSale;;
    private int price, area, numberOfRoom, numberOfBedroom, numberOfBathroom;


    private EditText categoryInput, districtInput, priceInput, areaInput, roomInput, bedroomInput, bathroomInput,
    pointOfInterestValue, descriptionValue, agentNameValue, addressValue;
    private Button addButton, addPictureButton;

    private HouseViewModel houseViewModel;
    private House houseToAdd;
    private String illustration;

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
    addButton = findViewById(R.id.bt_add_activity);
    addPictureButton = findViewById(R.id.bt_add_activity_add_picture);

    configureViewModel();

    addButton.setOnClickListener(this);
    addPictureButton.setOnClickListener(this);
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
        Date date = Calendar.getInstance().getTime();
        upForSale = new SimpleDateFormat("dd-MM-yyyy").format(date);
        price = Integer.parseInt(priceInput.getText().toString());
        area = Integer.parseInt(areaInput.getText().toString());
        numberOfRoom = Integer.parseInt(roomInput.getText().toString());
        numberOfBedroom = Integer.parseInt(bedroomInput.getText().toString());
        numberOfBathroom = Integer.parseInt(bathroomInput.getText().toString());
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.bt_add_activity :
                //Create new house in database
                collectInput();
                createHouseAndAddItToDatabase();
                Toast.makeText(this, "Le bien a été ajouté ", Toast.LENGTH_LONG).show();
                AddActivity.this.finish();
                break;

            case R.id.bt_add_activity_add_picture :
                //Add picture from device
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 123);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            //Access to picture from data
            Uri selectedPicture = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            //Cursor for access
            Cursor cursor = this.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
            //position on line (normalement une seule)
            cursor.moveToFirst();
            //get path
         /*   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgPath = cursor.getString(columnIndex);
            cursor.close();*/
            //get picture
          //  Bitmap bmp = BitmapFactory.decodeFile((imgPath));
            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedPicture);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Test", "descriptionPicture : no" );
            }
            //set picture
            String descriptionPicture;
            if ( bmp != null) {
                descriptionPicture = getStringImage(bmp);
                Log.e("Test", "descriptionPicture : " + descriptionPicture);
                this.illustration = descriptionPicture;
            }
        }
    }

    private void createHouseAndAddItToDatabase() {
        if (illustration == null) {
            illustration = "https://www.ouestfrance-immo.com/photo-vente-maison-la-roche-sur-yon-85/207/maison-a-vendre-la-roche-sur-yon-15080201_1_1603144038_f056394ffbe2dcc301aac986e8da59a7_crop_295-222_.jpg";
        }
        houseToAdd = new House( category, district, price, area,numberOfRoom, numberOfBathroom, numberOfBedroom, pointOfInterest, description, illustration, address, true, upForSale, null, agentName);
        Log.e("Test", "illustration : " + illustration);
        this.houseViewModel.createHouse(houseToAdd);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return "data:image/jpeg;base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}