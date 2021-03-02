package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Update
    int updateHouse(House house);

}
