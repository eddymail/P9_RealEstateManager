package com.openclassrooms.realestatemanager.Ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.Repositories.HouseDataRepository;
import com.openclassrooms.realestatemanager.Repositories.IllustrationDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class HouseViewModel extends ViewModel {

    //Repositories
    private final HouseDataRepository houseDataSource;
    private final IllustrationDataRepository illustrationDataSource;
    private final Executor executor;

    //Data
    private LiveData<House> currentHouse;

    public HouseViewModel(HouseDataRepository houseDataSource, IllustrationDataRepository illustrationDataSource, Executor executor) {
        this.houseDataSource = houseDataSource;
        this.illustrationDataSource = illustrationDataSource;
        this.executor = executor;
    }

    public void init(long houseId) {
        if (this.currentHouse != null) {
            return;
        }
        currentHouse = houseDataSource.getHouse(houseId);
    }

    //For house
    public void createHouse(House house) {
        executor.execute(() -> {
            houseDataSource.createHouse(house);
        });
    }

    public void updateHouse(House house) {
        executor.execute(() -> {
            houseDataSource.updateHouse(house);
        });
    }

    public LiveData<List<House>> getAll() { return houseDataSource.getAll(); }

    public LiveData<House> getHouse(long houseId) { return  houseDataSource.getHouse(houseId); }

    //For illustration
    public void createIllustration(Illustration illustration) {
        executor.execute(() -> {
            illustrationDataSource.createIllustration(illustration);
        });
    }

    public LiveData<List<Illustration>> getGallery(long houseId) { return illustrationDataSource.getGallery(houseId); }
}
