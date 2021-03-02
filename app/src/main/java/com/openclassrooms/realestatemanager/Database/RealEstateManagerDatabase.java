package com.openclassrooms.realestatemanager.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.Database.Dao.HouseDao;
import com.openclassrooms.realestatemanager.Database.Dao.IllustrationDao;
import com.openclassrooms.realestatemanager.Model.House;
import com.openclassrooms.realestatemanager.Model.Illustration;

@Database(entities = {House.class, Illustration.class}, version = 1, exportSchema = false)
public abstract class RealEstateManagerDatabase extends RoomDatabase {

    //Singleton
    private static volatile RealEstateManagerDatabase INSTANCE;

    //Dao
    public abstract HouseDao houseDao();
    public abstract IllustrationDao illustrationDao();

    //Instance
    public static RealEstateManagerDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateManagerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDatabase.class,"MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("category", "Maison");
                contentValues.put("district", "Hourton");
                contentValues.put("price", 741000);
                contentValues.put("area", 380);
                contentValues.put("room", 11);
                contentValues.put("bathroom", 2);
                contentValues.put("bedroom",6);
                contentValues.put("pointOfInterest", "Commerce, école, métro");
                contentValues.put("description", "Magnifique villa à Saint Aubin de Médoc située dans un quartier recherché. Vous découvrirez une entrée, une belle pièce de vie avec salon et salle à manger, un deuxième salon séparé par une cheminée centrale, une cuisine fermée donnant sur la cuisine d'été, une buanderie, une salle de jeu, une cave. Le rez-de-chaussée est complété par une chambre avec salle d'eau, un bureau et une salle de sport avec sauna et salle d'eau. A l'étage, vous accédez à une suite parentale avec une chambre donnant sur une terrasse, une salle de bains avec baignoire et douche, un double dressing, puis trois chambres avec dressing et une salle de bains avec baignoire et douche. L'ensemble sur un jardin paysagé d'environ 1400 m² dispose d'une grande terrasse en bois, d'une cuisine d'été, d'une piscine au sel chauffée. Très belles prestations pour cette maison d'exception à quelques minutes du centre de Saint Aubin de Médoc, des écoles, collège et lycée. Situation idéale à 15 kms de Bordeaux, 35 kms de Lacanau, 7 kms d'un parcours de golf. Un coin de paradis à découvrir sans tarder.");
                contentValues.put("illustration","https://v.seloger.com/s/width/861/visuels/0/r/i/1/0ri18eygqgnn0yj8yzyiwv9mx1f5x0yku96bf1a0w.jpg" );
                contentValues.put("address", "15 route de Hourton, 33160 Saint-Aubin-de-Médoc");
                contentValues.put("available", true);
                contentValues.put("dateOfEntry", "20/01/2021");
                contentValues.put("dateOfSale", "null");
                contentValues.put("realEstateAgent", "Eddy");

                db.insert("House", OnConflictStrategy.IGNORE, contentValues);

                ContentValues contentValues1 = new ContentValues();

                contentValues.put("id", 1);
                contentValues.put("houseId", 1);
                contentValues.put("description", "Salon");
                contentValues.put("url","https://v.seloger.com/s/cdn/x/visuels/0/m/2/v/0m2v0q1zbvnr9zhz2zpadwxwn2h2s4wc800plrsw0.jpg");

                db.insert("Illustration", OnConflictStrategy.IGNORE, contentValues1);

            }
        };
    }

}
