package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.Model.Illustration;

import java.util.List;

@Dao
public interface IllustrationDao {

    @Query("SELECT * FROM Illustration WHERE houseId = :houseId")
    LiveData<List<Illustration>> getGallery(long houseId);

    @Insert
    long insertIllustration(Illustration illustration);
}
