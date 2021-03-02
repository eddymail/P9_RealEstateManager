package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.Model.Illustration;

import java.util.List;

@Dao
public interface IllustrationDao {

    @Insert
    long createIllustration(Illustration illustration);

    @Query("SELECT * FROM Illustration WHERE houseId = :houseId")
    LiveData<List<Illustration>> getGallery(long houseId);

 /*   @Update
    int updateIllustration(Illustration illustration);*/

}
