package com.openclassrooms.realestatemanager.Ui;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;
import com.openclassrooms.realestatemanager.Repositories.HouseDataRepository;
import com.openclassrooms.realestatemanager.Repositories.IllustrationDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class RealEstateManagerViewModel extends ViewModel {

    //Repositories
    private final HouseDataRepository houseDataSource;
    private final IllustrationDataRepository illustrationDataSource;
    private final Executor executor;

    //Data
    private LiveData<House> currentHouse;

    public RealEstateManagerViewModel(HouseDataRepository houseDataSource, IllustrationDataRepository illustrationDataSource, Executor executor) {
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

    public void updateIsEuro(boolean isEuro, long id) {
        executor.execute(() -> {
            houseDataSource.updateIsEuro(isEuro,id);
        });
    }

    public void updateHouse(String category,
                            String district,
                            boolean isEuro,
                            int price,
                            int area,
                            int numberOfRooms,
                            int numberOfBathrooms,
                            int numberOfBedRooms,
                            String pointOfInterest,
                            String description,
                            String address,
                            boolean available,
                            String dateOfEntry,
                            String dateOfSale,
                            String realEstateAgent,
                            long id) {
        executor.execute(() -> {
            houseDataSource.updateHouse(category,
                    district,
                    isEuro,
                    price,
                    area,
                    numberOfRooms,
                    numberOfBathrooms,
                    numberOfBedRooms,
                    pointOfInterest,
                    description,
                    address,
                    available,
                    dateOfEntry,
                    dateOfSale,
                    realEstateAgent,
                    id);
        });
    }

    public void updateIllustration(String illustration, long id) {
        executor.execute(() -> {
            houseDataSource.updateIllustration(illustration, id);
        });
    }

    public LiveData<List<House>> getAll() { return houseDataSource.getAll(); }

    public LiveData<House> getHouse(long houseId) { return  houseDataSource.getHouse(houseId); }

    public LiveData<List<House>> getSearchedHouse(String district,
                                                  String miniPrice,
                                                  String maxiPrice,
                                                  int miniArea,
                                                  int maxiArea,
                                                  String pointOfInterest,
                                                  String dateOfEntry,
                                                  String dateOfSale) {
        return houseDataSource.getSearchedHouse(district,
                miniPrice,
                maxiPrice,
                miniArea,
                maxiArea,
                pointOfInterest,
                dateOfEntry,
                dateOfSale);}

    //For illustration
    public void createIllustration(Illustration illustration) {
        executor.execute(() -> {
            illustrationDataSource.createIllustration(illustration);
        });
    }

    public LiveData<List<Illustration>> getGallery(long houseId) { return illustrationDataSource.getGallery(houseId); }
}
