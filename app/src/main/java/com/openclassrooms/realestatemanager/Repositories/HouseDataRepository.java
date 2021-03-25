package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.HouseDao;
import com.openclassrooms.realestatemanager.Model.House;

import java.util.List;

public class HouseDataRepository {

    private final HouseDao houseDao;

    public HouseDataRepository(HouseDao houseDao) { this.houseDao = houseDao; }

    //Create house
    public void createHouse(House house) { houseDao.createHouse(house); }

    //Get house
    public LiveData<House> getHouse(long houseId ) { return this.houseDao.getHouse(houseId); }

    //Get all
    public LiveData<List<House>> getAll() { return this.houseDao.getAll(); }

    //Update isEuro
    public void updateIsEuro(boolean isEuro, long id) {
        houseDao.updateIsEuro(isEuro, id);
    }

    //Update house
    public void updateHouse(String category, String district, boolean isEuro, int price, int area, int numberOfRooms, int numberOfBathrooms, int numberOfBedRooms,
                            String pointOfInterest, String description, String illustration, String address,Boolean available, String dateOfEntry,
                            String dateOfSale, String realEstateAgent, long id)
    {
        houseDao.updateHouse(category, district, price, isEuro, area, numberOfRooms, numberOfBathrooms,
            numberOfBedRooms,pointOfInterest, description, illustration, address, true,
            dateOfEntry,dateOfSale, realEstateAgent, id);
    }

}
