package com.openclassrooms.realestatemanager.Injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Repositories.HouseDataRepository;
import com.openclassrooms.realestatemanager.Repositories.IllustrationDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static HouseDataRepository provideHouseDataSource(Context context) {
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getINSTANCE(context);
        return new HouseDataRepository(database.houseDao());
    }

    public static IllustrationDataRepository provideIllustrationDataSource(Context context) {
        RealEstateManagerDatabase database = RealEstateManagerDatabase.getINSTANCE(context);
        return new IllustrationDataRepository(database.illustrationDao());
    }

    public static Executor provideExecutor() { return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        HouseDataRepository dataSourceHouse = provideHouseDataSource(context);
        IllustrationDataRepository dataSourceIllustration = provideIllustrationDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceHouse, dataSourceIllustration, executor);
    }
}
