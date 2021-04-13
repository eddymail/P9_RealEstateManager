package com.openclassrooms.realestatemanager.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import android.database.Cursor;
import com.openclassrooms.realestatemanager.Model.House;

import java.util.List;

@Dao
public interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createHouse(House house);

    @Query("SELECT * FROM House WHERE id = :houseId")
    LiveData<House> getHouse(long houseId);

    @Query("SELECT * FROM House WHERE id = :houseId")
    Cursor getHouseWithCursor(long houseId);

    @Query("SELECT * FROM House")
    LiveData<List<House>> getAll();

    @Query("UPDATE House SET isEuro = :isEuro WHERE id = :id")
    int updateIsEuro(boolean isEuro, long id);

    @Query("UPDATE House SET category = :category,  district = :district,isEuro = :isEuro, price = :price, area = :area,numberOfRooms = :numberOfRooms, numberOfBathrooms= :numberOfBathrooms, numberOfBedRooms = :numberOfBedRooms, pointOfInterest = :pointOfInterest, description = :description, illustration = :illustration, address = :address, available = :available, dateOfEntry = :dateOfEntry, dateOfSale = :dateOfSale, realEstateAgent = :realEstateAgent WHERE id = :id")
    int updateHouse(String category, String district, int price, boolean isEuro,int area, int numberOfRooms, int numberOfBathrooms, int numberOfBedRooms,String pointOfInterest, String description, String illustration, String address,boolean available, String dateOfEntry, String dateOfSale, String realEstateAgent, long id);

    @Query("SELECT * FROM House WHERE district LIKE :district OR price BETWEEN :miniPrice AND :maxiPrice OR area BETWEEN :miniArea AND :maxiArea OR pointOfInterest LIKE :pointOfInterest OR dateOfEntry LIKE :dateOfEntry OR dateOfSale LIKE :dateOfSale")
    LiveData<List<House>> searchDatabase(String district, String miniPrice, String maxiPrice, int miniArea, int maxiArea, String pointOfInterest,String dateOfEntry, String dateOfSale);
}
