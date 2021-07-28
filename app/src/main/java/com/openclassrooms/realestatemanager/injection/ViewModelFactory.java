package com.openclassrooms.realestatemanager.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.HouseDataRepository;
import com.openclassrooms.realestatemanager.repositories.IllustrationDataRepository;
import com.openclassrooms.realestatemanager.ui.RealEstateManagerViewModel;

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

    @androidx.annotation.NonNull
    @Override
    public <T extends ViewModel> T create(@androidx.annotation.NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RealEstateManagerViewModel.class)) {
            return (T) new RealEstateManagerViewModel(houseDataSource, illustrationDataSource, executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
