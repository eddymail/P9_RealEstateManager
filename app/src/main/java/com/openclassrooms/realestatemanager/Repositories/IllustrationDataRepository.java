package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.IllustrationDao;
import com.openclassrooms.realestatemanager.Model.Illustration;

import java.util.List;

public class IllustrationDataRepository {

    private final IllustrationDao illustrationDao;

    public IllustrationDataRepository(IllustrationDao illustrationDao) {
        this.illustrationDao = illustrationDao;
    }

    //Create illustration
    public void createIllustration(Illustration illustration) {
        illustrationDao.createIllustration(illustration);
    }

    //Get gallery
    public LiveData<List<Illustration>> getGallery(long houseId) {
        return this.illustrationDao.getGallery(houseId);
    }

}
