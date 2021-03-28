package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.Model.House;

import java.util.List;

@Dao
public interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createHouse(House house);

    @Query("SELECT * FROM House WHERE id = :houseId")
    LiveData<House> getHouse(long houseId);

    @Query("SELECT * FROM House")
    LiveData<List<House>> getAll();

    @Query("UPDATE House SET isEuro = :isEuro WHERE id = :id")
    int updateIsEuro(boolean isEuro, long id);

    @Query("UPDATE House SET category = :category,  district = :district,isEuro = :isEuro, price = :price, area = :area,numberOfRooms = :numberOfRooms, numberOfBathrooms= :numberOfBathrooms, numberOfBedRooms = :numberOfBedRooms, pointOfInterest = :pointOfInterest, description = :description, illustration = :illustration, address = :address, available = :available, dateOfEntry = :dateOfEntry, dateOfSale = :dateOfSale, realEstateAgent = :realEstateAgent WHERE id = :id")
    int updateHouse(String category, String district, int price, boolean isEuro,int area, int numberOfRooms, int numberOfBathrooms, int numberOfBedRooms,String pointOfInterest, String description, String illustration, String address,Boolean available, String dateOfEntry, String dateOfSale, String realEstateAgent, long id);

    @Query("SELECT * FROM House WHERE district LIKE :searchQuery OR price LIKE :searchQuery OR area LIKE :searchQuery OR pointOfInterest LIKE :searchQuery OR dateOfEntry LIKE :searchQuery OR dateOfSale LIKE :searchQuery")
    LiveData<List<House>> searchDatabase(String searchQuery);
}
