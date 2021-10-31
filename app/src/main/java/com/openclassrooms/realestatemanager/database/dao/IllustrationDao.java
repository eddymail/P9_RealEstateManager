package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.Illustration;

import java.util.List;

@Dao
public interface IllustrationDao {

    @Insert
    long createIllustration(Illustration illustration);

    @Query("SELECT * FROM Illustration WHERE houseId = :houseId")
    LiveData<List<Illustration>> getGallery(long houseId);

}
