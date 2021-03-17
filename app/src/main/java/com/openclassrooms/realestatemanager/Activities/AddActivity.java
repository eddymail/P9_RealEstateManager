package com.openclassrooms.realestatemanager.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.Adapter.HouseRecyclerAdapter;
import com.openclassrooms.realestatemanager.Fragments.DetailFragment;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Ui.HouseViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int RESULT_ADD_PICTURE = 1;
    public static final int RESULT_TAKE_PICTURE = 2;
    private static final long HOUSE_ID = 1;
    private String category, district, pointOfInterest, address, description, realEstateAgent, dateOfSale, dateOfEntry;;
    private int price, area, numberOfRooms, numberOfBedRooms, numberOfBathrooms;
    private long id;

    private EditText categoryInput, districtInput, priceInput, areaInput, roomInput, bedroomInput, bathroomInput,
    pointOfInterestValue, descriptionValue, agentNameValue, addressValue;
    private Button addBtn, addPictureBtn, takePictureBtn;

    private HouseRecyclerAdapter adapter;
    private HouseViewModel houseViewModel;

    private House houseToAdd;
    private House houseToUpdate;
    private String illustration;
    private String picturePath = null;
    private String descriptionPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        configureViewModel();

     Bundle extras = getIntent().getExtras();
         if (extras != null) {
             id = extras.getLong("id", -1);
             Log.e("Test", "AddActivity = id :" + id);
         }
            //create
            if (id == -1 || id == 0) {
                this.initActivity();
                addBtn.setText("Ajouter");
            } else {
                //Modify
                this.initActivity();
                this.getCurrentHouse(id);
                addBtn.setText("Modifier");
            }

        addBtn.setOnClickListener(this);
        addPictureBtn.setOnClickListener(this);
        takePictureBtn.setOnClickListener(this);

        Log.e("Test", "AddActivity onCreate :");
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.houseViewModel = ViewModelProviders.of(this, viewModelFactory).get(HouseViewModel.class);
        this.houseViewModel.init(HOUSE_ID);
    }

    private void initActivity() {
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
        addBtn = findViewById(R.id.bt_add_activity);
        addPictureBtn = findViewById(R.id.bt_add_activity_add_picture);
        takePictureBtn = findViewById(R.id.bt_add_activity_take_picture);
    }

    private void collectInput() {
        category = categoryInput.getText().toString();
        district = districtInput.getText().toString();
        pointOfInterest = pointOfInterestValue.getText().toString();
        address = addressValue.getText().toString();
        description = descriptionValue.getText().toString();
        realEstateAgent = agentNameValue.getText().toString();
        Date date = Calendar.getInstance().getTime();
        dateOfEntry = new SimpleDateFormat("dd-MM-yyyy").format(date);
        price = Integer.parseInt(priceInput.getText().toString());
        area = Integer.parseInt(areaInput.getText().toString());
        numberOfRooms = Integer.parseInt(roomInput.getText().toString());
        numberOfBedRooms = Integer.parseInt(bedroomInput.getText().toString());
        numberOfBathrooms = Integer.parseInt(bathroomInput.getText().toString());
    }

    private void prepopulateTextView(House houseToDisplay) {
        categoryInput.setText(houseToDisplay.getCategory());
        districtInput.setText(houseToDisplay.getDistrict());
        priceInput.setText(String.valueOf(houseToDisplay.getPrice()));
        areaInput.setText(String.valueOf(houseToDisplay.getArea()));
        roomInput.setText(String.valueOf(houseToDisplay.getNumberOfRooms()));
        bedroomInput.setText(String.valueOf(houseToDisplay.getNumberOfBedrooms()));
        bathroomInput.setText(String.valueOf(houseToDisplay.getNumberOfBathrooms()));
        pointOfInterestValue.setText(houseToDisplay.getPointOfInterest());
        descriptionValue.setText(houseToDisplay.getDescription());
        addressValue.setText(houseToDisplay.getAddress());
        agentNameValue.setText(houseToDisplay.getRealEstateAgent());
    }

    private void getCurrentHouse(long id) {
        this.houseViewModel.getHouse(id).observe(this,this::prepopulateTextView);
    }

    private void getHouseToUpdate(long id) {
        Log.e("Test", "getHouseToUpdate : id vaut = " + id);
        this.houseViewModel.getHouse(id).observe(this,this::updateHouseAndUpdateDatabase);
    }

    private void createHouseAndAddItToDatabase() {
        if (illustration == null) {
            illustration = "https://www.ouestfrance-immo.com/photo-vente-maison-la-roche-sur-yon-85/207/maison-a-vendre-la-roche-sur-yon-15080201_1_1603144038_f056394ffbe2dcc301aac986e8da59a7_crop_295-222_.jpg";
        }
        houseToAdd = new House( category, district, price, area, numberOfRooms, numberOfBathrooms, numberOfBedRooms, pointOfInterest, description, illustration, address, true, dateOfEntry, null, realEstateAgent);
        Log.e("Test", "illustration : " + illustration);
        this.houseViewModel.createHouse(houseToAdd);
    }

    private void updateHouseAndUpdateDatabase(House house) {
        id = house.getId();
        illustration = house.getIllustration();
        dateOfSale = house.getDateOfSale();
        dateOfEntry = house.getDateOfEntry();
        Log.e("Test", "Avant Modify house id: "+ house.getId() + "area: " + house.getArea() );
        houseToUpdate = new House( category, district, price, area, numberOfRooms, numberOfBathrooms, numberOfBedRooms, pointOfInterest, description, illustration, address, true, dateOfEntry, null, realEstateAgent);
        Log.e("Test", "Après Modify  house id: " + house.getId() + "area: " + houseToUpdate.getArea());
        this.houseViewModel.updateHouse(category, district,price, area, numberOfRooms, numberOfBathrooms,
                numberOfBedRooms,pointOfInterest, description, illustration, address, true,
                dateOfEntry,dateOfSale, realEstateAgent, id);
    }

    //Start Activity for get picture from device
    private void addPictureFromDevice() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_ADD_PICTURE);
    }

    //Start Activity for take picture with device
    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //check intent can be managed
        if(intent.resolveActivity(getPackageManager()) != null) {
            //Create a unique file name
            String time = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            File pictureDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File pictureFile = File.createTempFile("picture"+time,".jpg", pictureDir);
                //Save full path
                picturePath = pictureFile.getAbsolutePath();
                //Create uri
                Uri pictureUri = FileProvider.getUriForFile(AddActivity.this, AddActivity.this.getApplicationContext().getPackageName()+".provider", pictureFile);
                //Uri to intent for save picture in temporary file
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                //Open activity
                startActivityForResult(intent, RESULT_TAKE_PICTURE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //OnClick method
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.bt_add_activity :

                if (id == -1 || id == 0) {
                    //Create new house in database
                    this.collectInput();
                    this.createHouseAndAddItToDatabase();
                    Toast.makeText(this, "Le bien a été ajouté ", Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                    AddActivity.this.finish();
                } else {
                    //Update house in database
                    this.collectInput();
                    this.getHouseToUpdate(id);
                    Toast.makeText(this, "Le bien a été modifié ", Toast.LENGTH_LONG).show();
                   // adapter.notifyDataSetChanged();
                    AddActivity.this.finish();
                }
                break;

            case R.id.bt_add_activity_add_picture :
                //Add picture from device
                this.addPictureFromDevice();
                break;

            case R.id.bt_add_activity_take_picture:
                //Take picture with device
                this.takePicture();
                break;
        }
    }


    //Result for Add Picture and Take Picture
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case RESULT_ADD_PICTURE :
                    //Access to picture from data
                    Uri selectedPicture = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    //Cursor for access
                    Cursor cursor = this.getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
                    //position on line (normalement une seule)
                    cursor.moveToFirst();
                    //get path
                    //get picture
                    Bitmap bmp = null;
                    try {
                        bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedPicture);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Test", "descriptionPicture : no" );
                    }
                    //set picture
                    if ( bmp != null) {
                        descriptionPicture = getStringImage(bmp);
                        Log.e("Test", "descriptionPicture : " + descriptionPicture);
                        this.illustration = descriptionPicture;
                    }
                    break;

                case RESULT_TAKE_PICTURE :
                    //Get picture
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    //scale picture
                    scaleBitmap(bitmap);
                    //set picture
                    if ( bitmap != null) {
                        descriptionPicture = getStringImage(bitmap);
                        Log.e("Test", "descriptionPicture : " + descriptionPicture);
                        this.illustration = descriptionPicture;
                    }
                    break;
            }
        }

    }

    //Scale bitmap
    public Bitmap scaleBitmap(Bitmap mBitmap) {
        int ScaleSize = 50;//max Height or width to Scale
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float excessSizeRatio = width > height ? width / ScaleSize : height / ScaleSize;
        Bitmap bitmap = Bitmap.createBitmap(
                mBitmap, 0, 0,(int) (width/excessSizeRatio),(int) (height/excessSizeRatio));
        //mBitmap.recycle(); if you are not using mBitmap Obj
        return bitmap;
    }

    //Bitmap to String
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return "data:image/jpeg;base64," + Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}