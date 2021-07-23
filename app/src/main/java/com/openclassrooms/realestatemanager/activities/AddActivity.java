package com.openclassrooms.realestatemanager.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.fragments.DescriptionPictureDialog;
import com.openclassrooms.realestatemanager.fragments.GalleryPictureDialog;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Illustration;
import com.openclassrooms.realestatemanager.ui.RealEstateManagerViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, GalleryPictureDialog.DialogListener {

    private RealEstateManagerViewModel realEstateManagerViewModel;

    //For Data
    public String category;
    public String district;
    public String address;
    public String description;
    public String realEstateAgent;
    public String dateOfSale;
    public String dateOfEntry;
    public String picture;
    public String tvDescription;
    public boolean available = true;
    public int school = 0;
    public int shopping = 0;
    public int publicTransport = 0;
    public int swimmingPool = 0;
    public int price;
    public int area;
    public int numberOfRooms;
    public int numberOfBedRooms;
    public int numberOfBathrooms;
    private long id;
    private List<House> houseList = new ArrayList<>();
    private House houseToAdd;
    private House houseToUpdate;
    private Illustration illustrationToAdd;

    //For Design
    private EditText categoryInput;
    private EditText districtInput;
    private EditText priceInput;
    private EditText areaInput;
    private EditText roomInput;
    private EditText bedroomInput;
    private EditText bathroomInput;
    private EditText descriptionValue;
    private EditText agentNameValue;
    private EditText addressValue;
    private EditText etDateOfSale;
    private Button addBtn;
    private Button selectDescriptionPictureBtn;
    private Button selectGalleryPictureBtn;
    private Button addDescriptionPictureBtn;
    private Button addGalleryPictureBtn;
    private CheckBox schoolCb;
    private CheckBox shoppingCb;
    private CheckBox publicTransportCb;
    private CheckBox swimmingPoolCb;

    public static final int RESULT_ADD_PICTURE = 1;
    public static final int RESULT_TAKE_PICTURE = 2;
    private static final long HOUSE_ID = 1;
    private String picturePath = null;
    private Uri selectedPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        configureViewModel();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id", -1);
        }
        //create display or modify display
        if (id == -1 || id == 0) {
            //Create
            this.initActivity();
            selectGalleryPictureBtn.setVisibility(View.GONE);
            addGalleryPictureBtn.setVisibility(View.GONE);
            addDescriptionPictureBtn.setVisibility(View.GONE);
            etDateOfSale.setVisibility(View.GONE);
            addBtn.setText("Ajouter");
        } else {
            //Modify
            this.initActivity();
            selectGalleryPictureBtn.setVisibility(View.VISIBLE);
            addGalleryPictureBtn.setVisibility(View.VISIBLE);
            etDateOfSale.setVisibility(View.VISIBLE);
            this.getCurrentHouse(id);
            addBtn.setText("Modifier");
        }

        addBtn.setOnClickListener(this);
        selectDescriptionPictureBtn.setOnClickListener(this);
        selectGalleryPictureBtn.setOnClickListener(this);
        addDescriptionPictureBtn.setOnClickListener(this);
        addGalleryPictureBtn.setOnClickListener(this);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.realEstateManagerViewModel = ViewModelProviders.of(this, viewModelFactory).get(RealEstateManagerViewModel.class);
        this.realEstateManagerViewModel.init(HOUSE_ID);
    }

    private void initActivity() {
        categoryInput = findViewById(R.id.et_add_activity_category);
        districtInput = findViewById(R.id.et_add_activity_district);
        priceInput = findViewById(R.id.et_add_activity_price);
        areaInput = findViewById(R.id.et_add_activity_area);
        roomInput = findViewById(R.id.et_add_activity_room);
        bedroomInput = findViewById(R.id.et_add_activity_bedroom);
        bathroomInput = findViewById(R.id.et_add_activity_bathroom);

        schoolCb = findViewById(R.id.cb_add_activity_school);
        shoppingCb = findViewById(R.id.cb_add_activity_shopping);
        publicTransportCb = findViewById(R.id.cb_add_activity_public_transport);
        swimmingPoolCb = findViewById(R.id.cb_add_activity_swimming_pool);

        descriptionValue = findViewById(R.id.et_add_activity_description);
        addressValue = findViewById(R.id.et_add_activity_address);
        agentNameValue = findViewById(R.id.et_add_activity_agent_name);

        addBtn = findViewById(R.id.bt_add_activity);
        selectDescriptionPictureBtn = findViewById(R.id.bt_add_activity_select_description_picture);
        addDescriptionPictureBtn = findViewById(R.id.bt_add_activity_add_description_picture);
        selectGalleryPictureBtn = findViewById(R.id.bt_add_activity_select_gallerie_picture);
        addGalleryPictureBtn = findViewById(R.id.bt_add_activity_add_gallerie_picture);
        etDateOfSale = findViewById(R.id.et_add_activity_date_of_sale);
    }

    private void collectInputUser() {
        category = categoryInput.getText().toString();
        district = districtInput.getText().toString().toUpperCase();
        address = addressValue.getText().toString();
        description = descriptionValue.getText().toString();
        realEstateAgent = agentNameValue.getText().toString();
        dateOfSale = etDateOfSale.getText().toString();

        Date date = Calendar.getInstance().getTime();
        dateOfEntry = new SimpleDateFormat("dd-MM-yyyy").format(date);

        String strPrice = priceInput.getText().toString();
        if (!TextUtils.isEmpty(strPrice)) {
            price = Integer.parseInt(strPrice);
        }

        String strArea = areaInput.getText().toString();
        if (!TextUtils.isEmpty(strArea)) {
            area = Integer.parseInt(strArea);
        }

        String strNumberOfRooms = roomInput.getText().toString();
        if (!TextUtils.isEmpty(strNumberOfRooms)) {
            numberOfRooms = Integer.parseInt(strNumberOfRooms);
        }

        String strNumberOfBedRooms = bedroomInput.getText().toString();
        if (!TextUtils.isEmpty(strNumberOfBedRooms)) {
            numberOfBedRooms = Integer.parseInt(strNumberOfBedRooms);
        }

        String strNumberOfBathrooms = bathroomInput.getText().toString();
        if (!TextUtils.isEmpty(strNumberOfBathrooms)) {
            numberOfBathrooms = Integer.parseInt(strNumberOfBathrooms);
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
    }

    private Boolean checkInput() {

        Boolean isOk;

        if (category.isEmpty()) {
            Toast.makeText(this, "Veuillez indiquer la catégorie du bien", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (district.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir le secteur du bien", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (address.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir une adresse", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Veuillez indiquer une description", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (realEstateAgent.isEmpty()) {
            Toast.makeText(this, "Veuillez indiquer le nom de l'agent immobilier", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (price == 0) {
            Toast.makeText(this, "Veuillez indiquer le prix", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (area == 0) {
            Toast.makeText(this, "Veuillez indiquer la surface", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (numberOfRooms == 0) {
            Toast.makeText(this, "Veuillez indiquer le nombre de pièce", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (numberOfBedRooms == 0) {
            Toast.makeText(this, "Veuillez indiquer le nombre de chambre", Toast.LENGTH_LONG).show();
            isOk = false;
        } else if (numberOfBathrooms == 0) {
            Toast.makeText(this, "Veuillez indiquer le nombre de salle de bain", Toast.LENGTH_LONG).show();
            isOk = false;
        } else {
            isOk = true;
        }
        return isOk;

    }

    private void collectInputForIllustration() {
        tvDescription = null;
    }

    private void prepopulateTextView(House houseToDisplay) {
        categoryInput.setText(houseToDisplay.getCategory());
        districtInput.setText(houseToDisplay.getDistrict());
        descriptionValue.setText(houseToDisplay.getDescription());
        addressValue.setText(houseToDisplay.getAddress());
        agentNameValue.setText(houseToDisplay.getRealEstateAgent());

        priceInput.setText(String.valueOf(houseToDisplay.getPrice()));
        areaInput.setText(String.valueOf(houseToDisplay.getArea()));
        roomInput.setText(String.valueOf(houseToDisplay.getNumberOfRooms()));
        bedroomInput.setText(String.valueOf(houseToDisplay.getNumberOfBedrooms()));
        bathroomInput.setText(String.valueOf(houseToDisplay.getNumberOfBathrooms()));

        if (houseToDisplay.getSchool() == 1) {
            schoolCb.setChecked(true);
        }
        if (houseToDisplay.getShopping() == 1) {
            shoppingCb.setChecked(true);
        }
        if (houseToDisplay.getPublicTransport() == 1) {
            publicTransportCb.setChecked(true);
        }
        if (houseToDisplay.getSwimmingPool() == 1) {
            swimmingPoolCb.setChecked(true);
        }
    }

    private void getCurrentHouse(long id) {
        this.realEstateManagerViewModel.getHouse(id).observe(this, this::prepopulateTextView);
    }

    private void getHouseToUpdate(long id) {
        this.realEstateManagerViewModel.getHouse(id).observe(this, this::updateHouseAndUpdateDatabase);
    }

    private void getHouseToUpdateIllustration(long id) {
        this.realEstateManagerViewModel.getHouse(id).observe(this, this::updateHouseIllustrationInDatabase);
    }

    private void updateHouseIllustrationInDatabase(House house) {
    }

    private void getAllHousesFromDatabase() {
        this.realEstateManagerViewModel.getAll().observe(this, this::updateList);
    }

    private void updateList(List<House> houses) {
        houseList = new ArrayList<>();
        houseList.addAll(houses);
    }

    private void createHouseAndAddItToDatabase() {
        if (picture == null) {
            picture = "";
        }
        houseToAdd = new House(category, district, true, price, area, numberOfRooms, numberOfBathrooms, numberOfBedRooms, school, shopping, publicTransport, swimmingPool,
                description, picture, address, available, dateOfEntry, null, realEstateAgent);
        this.realEstateManagerViewModel.createHouse(houseToAdd);
    }

    private void createIllustrationAndAddItToDatabase(Long id) {
        if (picture == null) {
            picture = "";
        }
        illustrationToAdd = new Illustration(id, tvDescription, picture);
        this.realEstateManagerViewModel.createIllustration(illustrationToAdd);
    }

    private void updateHouseAndUpdateDatabase(House house) {

        if (!dateOfSale.isEmpty()) {
            available = false;
        }
        dateOfEntry = house.getDateOfEntry();
        houseToUpdate = new House(category, district, true, price, area, numberOfRooms, numberOfBathrooms, numberOfBedRooms,
                school,
                shopping,
                publicTransport,
                swimmingPool,
                description, picture, address, available, dateOfEntry, dateOfSale, realEstateAgent);

        this.realEstateManagerViewModel.updateHouse(category, district,true, price, area, numberOfRooms, numberOfBathrooms, numberOfBedRooms,
                school,
                shopping,
                publicTransport,
                swimmingPool,
                description,
                address,
                available,
                dateOfEntry,
                dateOfSale,
                realEstateAgent,
                id);
    }

    private void updateHouseIllustrationInDatabase() {
        if (picture != null) {
            this.realEstateManagerViewModel.updateIllustration(picture, id);
        }
    }

    //Start Activity for get picture from device
    public void addPictureFromDevice() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_ADD_PICTURE);
    }

    //Start Activity for take picture with device
    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.e("Test", "TAKE PICTURE est appelée ");
        //check intent can be managed
        if (intent.resolveActivity(getPackageManager()) != null) {
            //Create a unique file name
            String time = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            File pictureDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File pictureFile = File.createTempFile("picture" + time, ".jpg", pictureDir);
                //Save full path
                picturePath = pictureFile.getAbsolutePath();
                //Create uri
                Uri pictureUri = FileProvider.getUriForFile(AddActivity.this, AddActivity.this.getApplicationContext().getPackageName() + ".provider", pictureFile);
                //Uri to intent for save picture in temporary file
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                //Open activity
                startActivityForResult(intent, RESULT_TAKE_PICTURE);
                Log.e("Test", "TAKE PICTURE est lancée ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e("Test", "TAKE PICTURE est pas lancée ");
    }

    //OnClick method
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_activity:

                if (id == -1 || id == 0) {
                    //Create new house in database
                    this.collectInputUser();
                    if (checkInput()) {
                        this.createHouseAndAddItToDatabase();
                        Toast.makeText(this, "Le bien a été ajouté ", Toast.LENGTH_LONG).show();
                        AddActivity.this.finish();
                    }

                } else {
                    //Update house in database
                    this.collectInputUser();
                    this.getHouseToUpdate(id);
                    Toast.makeText(this, "Le bien a été modifié ", Toast.LENGTH_LONG).show();
                    AddActivity.this.finish();

                }
                break;

            case R.id.bt_add_activity_add_description_picture:
                //Update description picture in database
                this.updateHouseIllustrationInDatabase();
                Toast.makeText(this, "L'image de description a été modifiée ", Toast.LENGTH_LONG).show();
                break;

            case R.id.bt_add_activity_add_gallerie_picture:
                //Add illustration picture in database
                createIllustrationAndAddItToDatabase(id);
                Toast.makeText(this, "La photo a été ajoutée dans la galerie ", Toast.LENGTH_LONG).show();
                break;

            case R.id.bt_add_activity_select_description_picture:
                //Add picture from device
                openDescriptionPictureDialog();
                break;

            case R.id.bt_add_activity_select_gallerie_picture:
                //Take picture with device
                openGalleryPictureDialog();
                break;
        }
    }

    //Picture dialog
    private void openGalleryPictureDialog() {
        GalleryPictureDialog galleryPictureDialog = new GalleryPictureDialog();
        galleryPictureDialog.show(getSupportFragmentManager(), "gallery picture dialog");
    }

    private void openDescriptionPictureDialog() {
        DescriptionPictureDialog descriptionPictureDialog = new DescriptionPictureDialog();
        descriptionPictureDialog.show(getSupportFragmentManager(), "description picture dialog");
    }

    //Result for Add Picture and Take Picture
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case RESULT_ADD_PICTURE:
                    //Access to picture from data
                    selectedPicture = data.getData();
                    getPathFromUri(selectedPicture);
                    break;

                case RESULT_TAKE_PICTURE:
                    //Get picture
                    picture = picturePath;
                    Log.e("Test", "TAKE PICTURE : " + requestCode);
                    break;
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        //Cursor for access
        Cursor cursor = this.getContentResolver().query(uri, filePathColumn, null, null, null);
        //position on line
        cursor.moveToFirst();
        //get path
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgPath = cursor.getString(columnIndex);
        cursor.close();
        this.picture = imgPath;

        return picture;
    }

    @Override
    public void applyDescription(String pictureDescription) {
        tvDescription = pictureDescription;
        Log.e("Test", "tvDescription = " + tvDescription);
    }
}