package com.openclassrooms.realestatemanager.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.Model.House;

public class HouseContentProvider extends ContentProvider {

    //For data
    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.Provider";
    public static final String TABLE_NAME = House.class.getSimpleName();
    public static final Uri URI_HOUSE = Uri.parse("conten://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if(getContext() != null) {
            long houseId = ContentUris.parseId(uri);
            final Cursor cursor = RealEstateManagerDatabase.getINSTANCE(getContext()).houseDao().getHouseWithCursor(houseId);
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            return cursor;
        }

        throw new IllegalArgumentException("Failed to query raw for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.house/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
      /* if(getContext() != null) {
         RealEstateManagerDatabase.getINSTANCE(getContext()).houseDao().createHouse(House.fromContentValues(contentValues));

         getContext().getContentResolver().notifyChange(uri, null);
         return ContentUris.withAppendedId(uri,)
       }*/
       return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
