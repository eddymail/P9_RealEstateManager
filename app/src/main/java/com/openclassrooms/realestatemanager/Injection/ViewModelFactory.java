package com.openclassrooms.realestatemanager.Injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Repositories.HouseDataRepository;
import com.openclassrooms.realestatemanager.Repositories.IllustrationDataRepository;
import com.openclassrooms.realestatemanager.Ui.RealEstateManagerViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final HouseDataRepository houseDataSource;
    private final IllustrationDataRepository illustrationDataSource;
    private final Executor executor;

    public ViewModelFactory(HouseDataRepository houseDataSource, IllustrationDataRepository illustrationDataSource, Executor executor) {
        this.houseDataSource = houseDataSource;
        this.illustrationDataSource = illustrationDataSource;
        this.executor = executor;
    }

/*
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RealEstateManagerViewModel.class)) {
            return (T) new RealEstateManagerViewModel(houseDataSource, illustrationDataSource,executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }*/

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(RealEstateManagerViewModel.class)) {
            return (T) new RealEstateManagerViewModel(houseDataSource, illustrationDataSource,executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
