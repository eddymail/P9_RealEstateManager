package com.openclassrooms.realestatemanager;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.database.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.provider.RealEstateContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class RealEstateContentProviderTest {

    //DATA set for test
    private static final long HOUSE_ID = 1;
    //For DATA
    private ContentResolver contentResolver;

    @Before
    public void setUp() {
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("House.db");
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
        contentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();
    }

    @Test
    public void getHouseWhenNoHouseInserted() {
        final Cursor cursor = contentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_HOUSE, HOUSE_ID),
                null, null, null, null);
        assertThat(cursor, notNullValue());
        if (cursor != null) {
            assertThat(cursor.getCount(), is(0));
            cursor.close();
        }
    }

    @Test
    public void insertAndGetHouse() {
        // BEFORE : Adding demo house
        final Uri houseUri = contentResolver.insert(RealEstateContentProvider.URI_HOUSE, generateHouse());
        // TEST
        final Cursor cursor =
                contentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_HOUSE, HOUSE_ID),
                        null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("category")), is("Maison"));
        contentResolver.delete(Objects.requireNonNull(houseUri), null, null);
    }

    private ContentValues generateHouse() {
        final ContentValues values = new ContentValues();
        values.put("category", "Maison");
        values.put("district", "HOURTON");
        values.put("price", 900000);
        values.put("isEuro", true);
        values.put("area", 300);
        values.put("numberOfRooms", 8);
        values.put("numberOfBathrooms", 4);
        values.put("numberOfBedrooms", 4);
        values.put("school", 1);
        values.put("shopping", 1);
        values.put("publicTransport", 1);
        values.put("swimmingPool", 1);
        values.put("description", "Magnifique villa à Saint Aubin de Médoc située dans un quartier recherché.");
        values.put("illustration", "");
        values.put("address", "15 route de Hourton, 33160 Saint-Aubin-de-Médoc");
        values.put("dateOfEntry", "20/01/2021");
        values.put("dateOfSale", "null");
        values.put("realEstateAgent", "Eddy");

        return values;
    }
}
