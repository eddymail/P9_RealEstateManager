package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.HouseDao;
import com.openclassrooms.realestatemanager.Model.House;

import java.util.List;

public class HouseDataRepository {

    private final HouseDao houseDao;

    public HouseDataRepository(HouseDao houseDao) { this.houseDao = houseDao; }

    //Get house
    public LiveData<House> getHouse(long houseId ) { return this.houseDao.getHouse(houseId); }

    //Get all
    public LiveData<List<House>> getAll() { return this.houseDao.getAll(); }

    //Create house
    public void createHouse(House house) { houseDao.createHouse(house); }

    //Update house
    public void updateHouse(House house) { houseDao.updateHouse(house); }

}
